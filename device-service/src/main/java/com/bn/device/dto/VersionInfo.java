package com.bn.device.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class VersionInfo implements Serializable {

	private final String clientModel;			//device model
	private final String clientVersion;		//version which is supported by content control
	private final String deviceVersion;		//version of the device
	private final float serverVersion;		//version of server
	private Integer productDeviceId = 0;	//product device id for the given client version
	private String clientDescription = "default";
	private String licenseType;
	private boolean isCategoryCachingEnabled;
	private boolean purchaseBlockStatus;
	private String purchaseAllowType;

	public boolean isCategoryCachingEnabled() {
		return isCategoryCachingEnabled;
	}

	public void setCategoryCachingEnabled(boolean isCategoryCachingEnabled) {
		this.isCategoryCachingEnabled = isCategoryCachingEnabled;
	}

	public VersionInfo(String clientModel, String clientVersion, String deviceVersion, float serverVersion)
	{
		this.clientModel = clientModel;
		this.clientVersion = clientVersion;
		this.deviceVersion = deviceVersion;
		this.serverVersion = serverVersion;
	}
	
	public VersionInfo(String clientModel, String clientVersion, String deviceVersion, float serverVersion
			, String licenseType)
	{
		this.clientModel = clientModel;
		this.clientVersion = clientVersion;
		this.deviceVersion = deviceVersion;
		this.serverVersion = serverVersion;
		this.licenseType = licenseType;
	}

    public VersionInfo(String clientModel, String clientVersion, String deviceVersion, float serverVersion,
            String licenseType, boolean purchaseBlockStatus, String purchaseAllowType) {
        this.clientModel = clientModel;
        this.clientVersion = clientVersion;
        this.deviceVersion = deviceVersion;
        this.serverVersion = serverVersion;
        this.licenseType = licenseType;
        this.purchaseBlockStatus = purchaseBlockStatus;
        this.purchaseAllowType = purchaseAllowType;
    }
	
	public String getClientModel() {
		return clientModel;
	}
	public String getClientVersion() {
		return clientVersion;
	}
	
	public String getDeviceReportedVersion(){
		return this.deviceVersion;
	}
	
	public float getServerVersion()
	{
		return serverVersion;
	}
	
	public Integer getProductDeviceId() {
		return productDeviceId;
	}

	public void setProductDeviceId(Integer productDeviceId) {
		this.productDeviceId = productDeviceId;
	}

	public String getClientDescription()
	{
		return clientDescription;
	}

	public void setClientDescription(String clientDescription)
	{
		this.clientDescription = clientDescription;
	}

	public String getLicenseType()
	{
		return licenseType;
	}

	public boolean isPurchaseBlockStatus() {
		return purchaseBlockStatus;
	}

	public String getPurchaseAllowType() {
		return purchaseAllowType;
	}

	public String toString() {
		return new ToStringBuilder(this)
			.append("clientModel", clientModel)
			.append("clientDescription", clientDescription)
			.append("clientVersion", clientVersion)
			.append("deviceVersion", deviceVersion)
			.append("serverVersion", serverVersion)
			.append("productDeviceid", productDeviceId)
			.append("purchaseBlockStatus", purchaseBlockStatus)
			.append("purchaseAllowType", purchaseAllowType)
			.toString();
	}
	
}
