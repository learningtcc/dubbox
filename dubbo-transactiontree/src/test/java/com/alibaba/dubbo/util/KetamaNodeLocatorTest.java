package com.alibaba.dubbo.util;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.testng.annotations.Test;

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
 */
public class KetamaNodeLocatorTest {

    public static List<String> generateRandomStrings(final int size) {
        final List<String> results = new ArrayList<String>(size);
        for (int ix = 0; ix < size; ix++) {
            results.add(
                    new StringBuilder(RandomStringUtils.randomAlphanumeric(5))
                            .append(RandomStringUtils.randomAlphabetic(ix % 5))
                            .append(ix)
                            .toString()
            );
        }
        return results;
    }

    @Test
    public void testNodeArrays() {
        final int maxSize = 10;
        final List<String> nodes = generateRandomStrings(maxSize);
        final long start1 = System.currentTimeMillis();
        final KetamaNodeLocator locator = new KetamaNodeLocator(nodes);

        // Make sure the initialization doesn't take too long.
        assertTrue((System.currentTimeMillis() - start1) < 100);

        final List<String> keys = generateRandomStrings(5 + RandomUtils.nextInt(5));

        for (final String key : keys) {
            final String single = locator.getPrimary(key);
            final List<String> sublist = locator.getPriorityList(key, 5);
            final List<String> superlist = locator.getPriorityList(key, 15);

            // Don't go over max size.
            assertTrue(superlist.size() <= maxSize);
            assertTrue(sublist.size() <= 5);

            // The primary should be the same across all the sets.
            assertEquals(single, sublist.get(0));
            assertEquals(single, superlist.get(0));

            // Make sure the sublist and superlist agree.
            for (int jx = 0; jx < 5; jx++) {
                assertEquals(sublist.get(jx), superlist.get(jx));
            }

            // Should contain every node.
            assertTrue(superlist.containsAll(nodes));
            assertTrue(nodes.containsAll(superlist));

            // Ensure no duplicates in the lists.
            final Set<String> subset = new HashSet<String>(sublist);
            assertEquals(sublist.size(), subset.size());
            final Set<String> superset = new HashSet<String>(superlist);
            assertEquals(superlist.size(), superset.size());
        }
    }

    @Test
    public void testDistribution() {
        final int nodeSize = 10;
        final int keySize = 10000;
        final List<String> nodes = generateRandomStrings(nodeSize);

        final long start1 = System.currentTimeMillis();
        final KetamaNodeLocator locator = new KetamaNodeLocator(nodes);

        // Make sure the initialization doesn't take too long.
        assertTrue((System.currentTimeMillis() - start1) < 100);

        final int[] counts = new int[nodeSize];
        for (int ix = 0; ix < nodeSize; ix++) { counts[ix] = 0; }

        final List<String> keys = generateRandomStrings(keySize);

        for (final String key : keys) {
            final String primary = locator.getPrimary(key);
            counts[nodes.indexOf(primary)] += 1;
        }

        // Give about a 30% leeway each way...
        final int min = (keySize * 7) / (nodeSize * 10);
        final int max = (keySize * 13) / (nodeSize * 10);
        int total = 0;
        boolean error = false;
        final StringBuilder sb = new StringBuilder("Key distribution error - \n");

        for (int ix = 0; ix < nodeSize; ix++) {
            if (counts[ix] < min || counts[ix] > max) {
                error = true;
                sb.append("  !!  ");
            } else {
                sb.append("      ");
            }
            sb.append(StringUtils.rightPad(nodes.get(ix), 12)).append(": ").append(counts[ix]).append("\n");
            total += counts[ix];
        }
        // Make sure we didn't miss any keys returning values.
        assertEquals(keySize, total);

//        System.out.println(sb.toString());
        if (error) {
            fail(sb.toString());
        }
    }

    @Test
    public void testWeightedDistribution() {
        final int nodeSize = 5;
        final int keySize = 10000;
        final List<String> nodes = generateRandomStrings(nodeSize);
        final List<String> weightedNodes = new ArrayList<String>(nodes);
        weightedNodes.add(nodes.get(3)); // 20%
        for (int ix = 0; ix < 4; ix++) { weightedNodes.add(nodes.get(4)); } // 50%
        final long start1 = System.currentTimeMillis();
        final KetamaNodeLocator locator = new KetamaNodeLocator(weightedNodes);

        // Make sure the initialization doesn't take too long.
        assertTrue((System.currentTimeMillis() - start1) < 100);

        final int[] counts = new int[nodeSize];
        for (int ix = 0; ix < nodeSize; ix++) { counts[ix] = 0; }

        final List<String> keys = generateRandomStrings(keySize);

        for (final String key : keys) {
            final String primary = locator.getPrimary(key);
            counts[nodes.indexOf(primary)] += 1;
        }

        // Give about a 30% leeway each way...
        final int min = (keySize * 7) / (nodeSize * 2 * 10);
        final int max = (keySize * 13) / (nodeSize * 2 * 10);
        int total = 0;
        boolean error = false;
        final StringBuilder sb = new StringBuilder("Key distribution error - \n");

        for (int ix = 0; ix < nodeSize; ix++) {
            int expectedMin = min;
            int expectedMax = max;
            if (ix == 3) { expectedMin = 2*min; expectedMax=2*max; }
            if (ix == 4) { expectedMin = 5*min; expectedMax=5*max; }
            if (counts[ix] < expectedMin || counts[ix] > expectedMax) {
                error = true;
                sb.append("  !!  ");
            } else {
                sb.append("      ");
            }
            sb.append(StringUtils.rightPad(nodes.get(ix), 12)).append(": ").append(counts[ix]).append("\n");
            total += counts[ix];
        }
        // Make sure we didn't miss any keys returning values.
        assertEquals(keySize, total);

//        System.out.println(sb.toString());
        if (error) {
            fail(sb.toString());
        }
    }

    @Test
    public void testResetNodeList() {
        final int oldSize = 10;
        final List<String> oldNodes = generateRandomStrings(oldSize);
        final long start1 = System.currentTimeMillis();
        final KetamaNodeLocator locator = new KetamaNodeLocator(oldNodes);

        // Make sure the initialization doesn't take too long.
        assertTrue((System.currentTimeMillis() - start1) < 100);

        final List<String> keys = generateRandomStrings(5 + RandomUtils.nextInt(5));

        for (final String key : keys) {
            final List<String> superlist = locator.getPriorityList(key, oldSize);
            assertTrue(superlist.containsAll(oldNodes));
        }

        final List<String> newNodes = new ArrayList<String>();
        newNodes.add(oldNodes.get(2) + "-modified-");
        newNodes.add(oldNodes.get(6) + "-modified-");
        newNodes.add(oldNodes.get(9) + "-modified-");
        locator.updateLocator(newNodes);

        for (final String key : keys) {
            final List<String> superlist = locator.getPriorityList(key, oldSize);
            assertTrue(superlist.containsAll(newNodes));
            assertTrue(newNodes.containsAll(superlist));
        }
    }

    @Test
    public void testNodeListAtScale() {
        final int nodeSize = 10000;
        final List<String> nodes = generateRandomStrings(nodeSize);
        final long start1 = System.currentTimeMillis();
        final KetamaNodeLocator locator = new KetamaNodeLocator(nodes);

        // Make sure the initialization doesn't take too long.
//        System.out.println("Duration: " + (System.currentTimeMillis() - start1));
        assertTrue((System.currentTimeMillis() - start1) < 5000);

        final List<String> keys = generateRandomStrings(5 + RandomUtils.nextInt(5));

        for (final String key : keys) {
            final long start2 = System.currentTimeMillis();
            final List<String> superlist = locator.getPriorityList(key, nodeSize);
//            System.out.println("Duration: " + (System.currentTimeMillis() - start2));
            assertTrue((System.currentTimeMillis() - start2) < 200);
            assertEquals(nodeSize, superlist.size());
        }
    }

    @Test
    public void testNullNodes() {
        final List<String> nodesNull = generateRandomStrings(5);
        nodesNull.add(null);
        nodesNull.addAll(generateRandomStrings(5));

        final List<String> nodesComplete = generateRandomStrings(9);

        // Make sure just creating the locator will error out.
        try {
            new KetamaNodeLocator(nodesNull);
            fail("Expected Exception");
        } catch (InvalidParameterException ex) { }

        final KetamaNodeLocator locator = new KetamaNodeLocator(nodesComplete);

        final List<String> keys = generateRandomStrings(3);

        for (final String key : keys) {
            final List<String> superlist = locator.getPriorityList(key, nodesComplete.size());
            assertEquals(nodesComplete.size(), superlist.size());
        }

        // Now try to update the locator with bad data.
        try {
            locator.updateLocator(nodesNull);
            fail("Expected Exception");
        } catch (InvalidParameterException ex) { }

        // Now we ensure the locator continues to work with the
        // previous data from before we tried to set bad data.
        for (final String key : keys) {
            final List<String> superlist = locator.getPriorityList(key, nodesComplete.size());
            assertEquals(nodesComplete.size(), superlist.size());
        }
    }

}