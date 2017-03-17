package com.alibaba.dubbo.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * The guts of this implementation come from Dustin Sallings' work on the
 * Spy Memcached client:
 * http://github.com/dustin/java-memcached-client/blob/master/src/main/java/net/spy/memcached/KetamaNodeLocator.java
 *
 * This implementation just serves to generify so as not to be memcached-specific
 */

public class KetamaNodeLocator {

    private static final int DEFAULT_REPETITIONS = 100;
    private static MessageDigest MD5_DIGEST = null;
    static {
        try {
            MD5_DIGEST = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not supported", e);
        }
    }

    private KetamaData data;

    public KetamaNodeLocator(final List<String> nodes) {
        setKetamaNodes(nodes);
    }

    public void updateLocator(List<String> nodes) {
        setKetamaNodes(nodes);
    }

    public String getPrimary(final String key) {
        final List<String> rv = getNodesForKey(hash(key), 1);
        assert rv != null && rv.get(0) != null: "Found no node for key " + key;
        return rv.get(0);
    }

    public List<String> getPriorityList(final String key, final int listSize) {
        final List<String> rv = getNodesForKey(hash(key), listSize);
        assert rv != null : "Found no node for key " + key;
        return rv;
    }

    protected List<String> getNodesForKey(final long hash, final int listSize) {
        long target = hash;
        // Local reference so the nodes aren't changed in the middle of calculation.
        final KetamaData localData = data;
        if (!data.getNodeMap().containsKey(target)) {
            // Java 1.6 adds a ceilingKey method, but I'm still stuck in 1.5
            // in a lot of places, so I'm doing this myself.
            final SortedMap<Long, String> tailMap = data.getNodeMap().tailMap(target);
            target = tailMap.isEmpty() ? data.getNodeMap().firstKey() : tailMap.firstKey();
        }
        final int maxSize = data.getNodeSet().size() > listSize ? listSize : data.getNodeSet().size();
        final List<String> results = new ArrayList<String>(maxSize);
        results.add(data.getNodeMap().get(target));

        // We're done if we're only looking for the first one.
        if (listSize == 1) { return results; }

        // Find the rest of the list (uniquely) in order.
        final Set<String> accounted = new TreeSet<String>();
        accounted.add(data.getNodeMap().get(target));

        Long pointerKey = data.getNodeMap().higherKey(target);
        while ((pointerKey == null || pointerKey.longValue() != target) && accounted.size() < maxSize) {
            if (pointerKey == null) { pointerKey = data.getNodeMap().firstKey(); }
            final String node = data.getNodeMap().get(pointerKey);
            // Only add nodes that haven't already been accounted for.
            if (node != null && !accounted.contains(node)) {
                results.add(node);
                accounted.add(node);
            }
            pointerKey = data.getNodeMap().higherKey(pointerKey);
        }

        return results;
    }

    protected void setKetamaNodes(List<String> nodes) {
        final TreeMap<Long, String> newNodeMap = new TreeMap<Long, String>();
        final Set<String> newNodeSet = new HashSet<String>();
        final Map<String, Integer> nodeCounts = new TreeMap<String, Integer>();
        final int numReps = DEFAULT_REPETITIONS;
        for (final String node : nodes) {
            if (node == null) { throw new InvalidParameterException("Nodes may not be NULL."); }
            // This is the magic that will allow us to do weighted nodes.
            final int nodeCount = nodeCounts.containsKey(node) ? nodeCounts.get(node).intValue() + 1 : 1;
            final String nodePlusCount = decorateWithCounter(node, nodeCount);

            nodeCounts.put(node, nodeCount);
            newNodeSet.add(node);

            // Ketama does some special work with md5 where it reuses chunks.
            for (int i = 0; i < numReps / 4; i++) {
                final byte[] digest = computeMd5(decorateWithCounter(nodePlusCount, i));
                for (int h = 0; h < 4; h++) {
                    final Long k = ((long) (digest[3 + h * 4] & 0xFF) << 24)
                          | ((long) (digest[2 + h * 4] & 0xFF) << 16)
                          | ((long) (digest[1 + h * 4] & 0xFF) << 8)
                          | (digest[h * 4] & 0xFF);
                    newNodeMap.put(k, node);
                }
            }
        }

        // This is really only true at small sizes. With many nodes, there is a possibility of collision.
        assert newNodeSet.size() < 1000 ? newNodeMap.size() == numReps * nodes.size() : true : "Size: " + newNodeMap.size() + ", expected: " + (numReps * nodes.size());
        data = new KetamaData(newNodeMap, newNodeSet);
    }

    /**
     * Get the md5 of the given key.
     */
    protected static byte[] computeMd5(String k) {
        MessageDigest md5;
        try {
            // I believe this is done to prevent multi-threading/synchronization problems
            md5 = (MessageDigest) MD5_DIGEST.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("clone of MD5 not supported", e);
        }
        md5.update(getKeyBytes(k));
        return md5.digest();
    }

    protected long hash(final String k) {
        final byte[] bKey = computeMd5(k);
        final long rv = ((long) (bKey[3] & 0xFF) << 24)
            | ((long) (bKey[2] & 0xFF) << 16)
            | ((long) (bKey[1] & 0xFF) << 8)
            | (bKey[0] & 0xFF);
        return rv & 0xffffffffL; /* Truncate to 32-bits */
    }

    /**
     * Get the bytes for a key.
     *
     * @param k the key
     * @return the bytes
     */
    protected static byte[] getKeyBytes(String k) {
        try {
            return k.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    protected static String decorateWithCounter(final String input, final int counter) {
        return new StringBuilder(input).append('%').append(counter).append('%').toString();
    }

    /**
     * Creating a complex object to hold the data references so they won't be un-syncronized.
     */
    private static final class KetamaData {
        private TreeMap<Long, String> nodeMap;
        private Set<String> nodeSet;

        private KetamaData(final TreeMap<Long, String> nodeMap, final Set<String> nodeSet) {
            // Ideally we'd convert these to unmodifiable versions, but no such thing exists for TreeMap.
            this.nodeMap = nodeMap;
            this.nodeSet = nodeSet;
        }

        public TreeMap<Long, String> getNodeMap() {
            return nodeMap;
        }

        public Set<String> getNodeSet() {
            return nodeSet;
        }
    }
}