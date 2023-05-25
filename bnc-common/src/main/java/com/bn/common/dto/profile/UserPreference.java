package com.bn.common.dto.profile;

import java.io.Serializable;
import java.util.Date;

public class UserPreference implements Serializable {

	private static final long serialVersionUID = 1L;
	private String customerId;
	private long accountId;
	private UserPreferenceType type;
	private String value;
	private Date createdDate;
	private Date modifiedDate;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public UserPreferenceType getType() {
		return type;
	}

	public void setType(UserPreferenceType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "UserPreference{" +
				"customerId='" + customerId + '\'' +
				", accountId=" + accountId +
				", type=" + type +
				", value='" + value + '\'' +
				", createdDate=" + createdDate +
				", modifiedDate=" + modifiedDate +
				'}';
	}
}
