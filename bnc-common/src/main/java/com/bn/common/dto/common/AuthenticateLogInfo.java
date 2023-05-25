package com.bn.common.dto.common;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class AuthenticateLogInfo {
	
	private long id;
	private long deviceId;
	private long accountId;
	private String token;
	private Date createDate;
	private int status;
	private String reason;
	private Date expireDate;
	
	public AuthenticateLogInfo() {
		
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}
	
	public long getDeviceId() {
		return this.deviceId;
	}
	
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
	public long getAccountId() {
		return this.accountId;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	
	public Date getExpireDate() {
		return this.expireDate;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("accountid", accountId)
			.append("deviceid", deviceId)
			.append("token", token)
			.append("status", status)
			.append("reason", reason)
			.append("expDate", expireDate)
			.toString();
	}
}
