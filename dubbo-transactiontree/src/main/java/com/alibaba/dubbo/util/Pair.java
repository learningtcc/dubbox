package com.alibaba.dubbo.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Pair<K, V> implements Serializable {

	private K first;
	private V second;

	public Pair() {
	}

	public Pair(K first, V second) {
		this.first = first;
		this.second = second;
	}

	public K first() {
		return first;
	}

	public V second() {
		return second;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		} else if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		Pair other = (Pair) obj;
		return new EqualsBuilder().append(first, other.first).append(second, other.second).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(first).append(second).hashCode();
	}
}