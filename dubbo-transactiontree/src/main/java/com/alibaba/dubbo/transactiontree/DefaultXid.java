package com.alibaba.dubbo.transactiontree;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.alibaba.dubbo.transactiontree.api.Xid;

/**
 * 
 * @author fuhaining
 */
public class DefaultXid implements Xid {

	private String rootTransactionId;

	private String parentTransactionId;

	private String transactionId;
	
	public DefaultXid() {
	}

	public DefaultXid(String rootTransactionId, String parentTransactionId, String transactionId) {
		this.rootTransactionId = rootTransactionId;
		this.parentTransactionId = parentTransactionId;
		this.transactionId = transactionId;
	}

	@Override
	public String getRootTransactionId() {
		return this.rootTransactionId;
	}

	@Override
	public void setRootTransactionId(String rootTransactionId) {
		this.rootTransactionId = rootTransactionId;
	}

	@Override
	public String getParentTransactionId() {
		return this.parentTransactionId;
	}

	@Override
	public void setParentTransactionId(String parentTransactionId) {
		this.parentTransactionId = parentTransactionId;
	}

	@Override
	public String getTransactionId() {
		return this.transactionId;
	}

	@Override
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || obj.getClass() != getClass()) {
			return false;
		}
		
		DefaultXid other = (DefaultXid) obj;
		return new EqualsBuilder()
				.append(rootTransactionId, other.rootTransactionId)
				.append(parentTransactionId, other.parentTransactionId)
				.append(transactionId, other.transactionId)
				.isEquals(); 
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(rootTransactionId)
				.append(parentTransactionId)
				.append(transactionId)
				.hashCode();
	}
}