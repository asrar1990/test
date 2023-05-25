package com.bn.common.dto.common;

import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.generic.Triple;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Note that this class's public methods and their return objects, for better or worse, are directly written to JSON
 * via the PartnerService.getVideoDevice()
 * and so changes to public get() method signatures can break bn.com's video interface
 */
public class AuthInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int version = 2;
	
	private DeviceInfo device;
	private TokenInfo deviceToken;
	
	private AccountInfo account;
	private TokenInfo accountToken;

    private List<Triple<String, String, String>> extraInfo = new ArrayList<Triple<String, String, String>>();

	public AuthInfo() {
		
	}

    public AuthInfo(DeviceInfo device, AccountInfo account) {
        this.device = device;
        this.account = account;
    }

	public AccountInfo getAccount()
	{
		return account;
	}

	public void setAccount(AccountInfo account)
	{
		this.account = account;
	}

	public TokenInfo getAccountToken()
	{
		return accountToken;
	}

	public void setAccountToken(TokenInfo accountToken)
	{
		this.accountToken = accountToken;
	}

	public DeviceInfo getDevice()
	{
		return device;
	}

	public void setDevice(DeviceInfo device)
	{
		this.device = device;
	}

	public TokenInfo getDeviceToken()
	{
		return deviceToken;
	}

	public void setDeviceToken(TokenInfo deviceToken)
	{
		this.deviceToken = deviceToken;
	}


	public List<Triple<String, String, String>> getExtraInfo() {
		if (extraInfo==null)
			extraInfo = new ArrayList<Triple<String, String, String>>();
		return extraInfo;
	}

	public void setExtraInfo(List<Triple<String, String, String>> extraInfo) {
		this.extraInfo = extraInfo;
	}

    public AuthInfo clone() {
        AuthInfo aai = new AuthInfo();
        if(account != null) {
            aai.setAccount(account.clone());
        }
        if(device != null) {
            aai.setDevice(device.clone());
        }
        if(accountToken != null) {
            aai.setAccountToken(accountToken);
        }
        if(deviceToken != null) {
            aai.setDeviceToken(deviceToken);
        }
        if(extraInfo != null) {
            aai.setExtraInfo(extraInfo);
        }
        return aai;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeObject(device);
		out.writeObject(deviceToken);
		out.writeObject(account);
		out.writeObject(accountToken);
	}
	
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			device = (DeviceInfo)in.readObject();
			deviceToken = (TokenInfo)in.readObject();
			account = (AccountInfo)in.readObject();
			accountToken = (TokenInfo)in.readObject();
		}
	}

    @Override
    public String toString() {
        return new ToStringBuilder(this).
            append("device", device).
            append("account", account).
            append("deviceToken", deviceToken).
            append("accountToken", accountToken).
            toString();
    }
}
