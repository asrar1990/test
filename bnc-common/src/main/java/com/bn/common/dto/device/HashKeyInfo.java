package com.bn.common.dto.device;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HashKeyInfo
{
	private String uniqueid;
	private String modelid;
	private String passphrase;
	private String hashkey;
	
	public HashKeyInfo() {
		
	}
	
	public HashKeyInfo(String uniqueid, String modelid, String passphrase, String hashkey) {
		this.uniqueid = uniqueid;
		this.modelid = modelid;
		this.passphrase = passphrase;
		this.hashkey = hashkey;
	}

	public String getUniqueid()
	{
		return uniqueid;
	}

	public void setUniqueid(String uniqueid)
	{
		this.uniqueid = uniqueid;
	}

	public String getModelid()
	{
		return modelid;
	}

	public void setModelid(String modelid)
	{
		this.modelid = modelid;
	}

	public String getPassphrase()
	{
		return passphrase;
	}

	public void setPassphrase(String passphrase)
	{
		this.passphrase = passphrase;
	}

	public String getHashkey()
	{
		return hashkey;
	}

	public void setHashkey(String hashkey)
	{
		this.hashkey = hashkey;
	}
	
	public String toString() {
		return new ToStringBuilder(this)
		.append("uniqueid", uniqueid)
		.append("modelid", modelid)
		.append("passphrase", passphrase)
		.append("hashkey", hashkey)
		.toString();
	}
}
