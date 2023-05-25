package com.bn.common.dto.common;

import com.bn.common.dto.util.SerializationUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class TokenInfo implements Serializable {

	private static final long serialVersionUID = 1;
    private int version = 1;

	protected long accountId;
	protected long deviceId;
	protected String tokenType;
	protected String token;
	protected Date tokenCreateDate;
	protected Date tokenExpiresOn;
	protected long duration;
	protected int tokenState;
	
	public TokenInfo() {
		
	}
	
	public TokenInfo(String token, String tokenType, int tokenState, Date expiresOn) {
		this.token = token;
		this.tokenType = tokenType;
		this.tokenState = tokenState;
		this.tokenExpiresOn = expiresOn;
	}
	
	public TokenInfo(String token) {
		this.token = token;
	}
	
	public TokenInfo(String token, String tokenType) {
		this.token = token;
		this.tokenType = tokenType;
	}
	
	public TokenInfo(String token, Date expiresOn, String tokenType) {
		this.token = token;
		this.tokenExpiresOn = expiresOn;
		this.tokenType = tokenType;
	}
	
	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getTokenCreateDate()
	{
		return tokenCreateDate;
	}

	public void setTokenCreateDate(Date tokenCreateDate)
	{
		this.tokenCreateDate = tokenCreateDate;
	}

	public Date getTokenExpiresOn() {
		return tokenExpiresOn;
	}

	public void setTokenExpiresOn(Date tokenExpiresOn) {
		this.tokenExpiresOn = tokenExpiresOn;
	}
	
	public long getDuration()
	{
		return duration;
	}

	public void setDuration(long duration)
	{
		this.duration = duration;
	}

	public int getTokenState()
	{
		return tokenState;
	}

	public void setTokenState(int tokenState)
	{
		this.tokenState = tokenState;
	}

	public long getAccountId()
	{
		return accountId;
	}

	public void setAccountId(long accountId)
	{
		this.accountId = accountId;
	}

	public long getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(long deviceId)
	{
		this.deviceId = deviceId;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("token", token)
			.append("tokenExpiresOn", tokenExpiresOn)
			.append("tokenType", tokenType)
			.append("duration", duration)
			.toString();
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeLong(accountId);
		out.writeLong(deviceId);
		out.writeObject(SerializationUtil.serialize(tokenType));
		out.writeObject(SerializationUtil.serialize(token));
		out.writeLong(SerializationUtil.serialize(tokenCreateDate));
		out.writeLong(SerializationUtil.serialize(tokenExpiresOn));
		out.writeLong(duration);
		out.writeInt(tokenState);
	}
	
	private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
	{
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			accountId = in.readLong();
			deviceId = in.readLong();
			tokenType = SerializationUtil.deserialize((String)in.readObject());
			token = SerializationUtil.deserialize((String)in.readObject());
			tokenCreateDate = SerializationUtil.deserialize(in.readLong());
			tokenExpiresOn = SerializationUtil.deserialize(in.readLong());
			duration = in.readLong();
			tokenState = in.readInt();
		}
	}
}
