package com.bn.device.command.gpb.response;

import com.bn.common.command.gpb.AbstractCommandResponse;
import com.bn.common.dto.account.AccountInfo;
import com.bn.common.dto.common.TokenInfo;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.generic.Triple;

import java.util.ArrayList;
import java.util.List;

public class DeviceAuthCommandResponse extends AbstractCommandResponse
{
    private TokenInfo tokenInfo;
    private DeviceInfo deviceInfo;
    private TokenInfo accountTokenInfo;
    private AccountInfo accountInfo;
    private List<Triple<String, String, String>> extraInfo = new ArrayList<Triple<String, String, String>>();

    public TokenInfo getAccountTokenInfo() {
        return accountTokenInfo;
    }

    public void setAccountTokenInfo(TokenInfo accountTokenInfo) {
        this.accountTokenInfo = accountTokenInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public DeviceAuthCommandResponse(String commandName) {
        super(commandName);
    }

    public TokenInfo getTokenInfo() {
        return tokenInfo;
    }

    public void setTokenInfo(TokenInfo tokenInfo) {
        this.tokenInfo = tokenInfo;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

	public List<Triple<String, String, String>> getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(List<Triple<String, String, String>> extraInfo) {
		this.extraInfo = extraInfo;
	}
}
