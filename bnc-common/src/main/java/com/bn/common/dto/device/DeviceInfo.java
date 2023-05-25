package com.bn.common.dto.device;

import com.bn.common.dto.util.SerializationUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class DeviceInfo implements Serializable {

    private static final String WEB_PORTAL_SOURCE_ID = "P000000003";
    private static final long serialVersionUID = 1;

	private int version = 9;
	private long id;
	private String serialNumber;
	private String model;
	private String modelDesc;
	private int bnModel;
	private String imei;
	private String imsi;
	private String modemFirmware;
	private String softwareVersion;
	private String initialBuildNumber;
	private String currentBuildNumber;
	private int currentBuildMajor;
	private int currentBuildMinor;
	private int currentBuildRevision;
	private Date createDate;
	private Date modifyDate;
	private String publicKey;
	private String hashPrivateKey;
	private int preReg;
	private int blackListed;
	private long familyId;
	private String deviceToken;
	private String sourceID;
	private int encoding;
	private boolean isActive;
	private String timeZone;
	private String iccid;
	private String endpointType;
	private float supportedVersion;
	private String deviceAlias;
	private String adobeDeviceId;
    private Date softwareUpdatedDate;

	private long accountId; // DEVICE.ACCOUNT_ID
	private String modelId; // DEVICE.MODEL_ID
	private int inStoreStatus; // DEVICE.INSTORE_STATUS
	private Date inStoreDate; // DEVICE.INSTORE_TIME;
	private String inStoreId; // DEVICE.INSTORE_ID;

	//version 4
	private Date addFreeTitleTime;

	//version 5
	private boolean isActiveWebReader;
	private int wRRefreshFrequency;
	private int wRRefreshOffset;

	//version 6
	private String retailer = "BN2";
	private String languageId = "en";
	private String countryId = "US";
	private String ean;

    //version 7
    private boolean adsSupported;

    //version 8
    private String countryOfResidence;

    // version 9
    /**
     * The siliconId that the manufactor set for a device.This will be populated to Cloud upon device registration from
     * EDM database
     */
    private String siliconId;

    /** This flag will decide whether the device is allowed to receive a new HDCP license key */
    private boolean hdcpBlocked;

	private boolean videoEnabled;
	private String audioSessioKey;

    /* FamilyId maps to this name in the device_family table. Needed for email via analytic event, not part of device table.*/
    private transient String familyName;

    public DeviceInfo() {

	}

	public DeviceInfo(String serialNumber, String deviceModel) {
		this.serialNumber = serialNumber;
		this.model = deviceModel;
	}

	public DeviceInfo(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getBnModel() {
		return bnModel;
	}

	public String getModelDesc() {
		return modelDesc;
	}

	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}

	public void setBnModel(int bnModel) {
		this.bnModel = bnModel;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getModemFirmware() {
		return modemFirmware;
	}

	public void setModemFirmware(String modemFirmware) {
		this.modemFirmware = modemFirmware;
	}

	public String getSoftwareVersion() {
		if(softwareVersion ==null){
			return "";
		}
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getInitialBuildNumber() {
		return initialBuildNumber;
	}

	public void setInitialBuildNumber(String initialBuildNumber) {
		this.initialBuildNumber = initialBuildNumber;
	}

	public String getCurrentBuildNumber() {
		if(currentBuildNumber ==null){
			return "";
		}
		return currentBuildNumber;
	}

	public void setCurrentBuildNumber(String currentBuildNumber) {
		this.currentBuildNumber = currentBuildNumber;
	}

	public int getCurrentBuildMajor() {
		return currentBuildMajor;
	}

	public void setCurrentBuildMajor(int currentBuildMajor) {
		this.currentBuildMajor = currentBuildMajor;
	}

	public int getCurrentBuildMinor() {
		return currentBuildMinor;
	}

	public void setCurrentBuildMinor(int currentBuildMinor) {
		this.currentBuildMinor = currentBuildMinor;
	}

	public int getCurrentBuildRevision() {
		return currentBuildRevision;
	}

	public void setCurrentBuildRevision(int currentBuildRevision) {
		this.currentBuildRevision = currentBuildRevision;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getHashPrivateKey() {
		return hashPrivateKey;
	}

	public void setHashPrivateKey(String hashPrivateKey) {
		this.hashPrivateKey = hashPrivateKey;
	}

	public int getPreReg() {
		return preReg;
	}

	public void setPreReg(int preReg) {
		this.preReg = preReg;
	}

	public int getBlackListed()
	{
		return blackListed;
	}

	public void setBlackListed(int blackListed)
	{
		this.blackListed = blackListed;
	}

	public long getFamilyId()
	{
		return familyId;
	}

	public void setFamilyId(long familyId)
	{
		this.familyId = familyId;
	}

	public String getDeviceToken()
	{
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken)
	{
		this.deviceToken = deviceToken;
	}

	public String getSourceID()
	{
		return sourceID;
	}

	public void setSourceID(String sourceID)
	{
		this.sourceID = sourceID;
	}

	public int getEncoding()
	{
		return encoding;
	}

	public void setEncoding(int encoding)
	{
		this.encoding = encoding;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	public String getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(String timeZone)
	{
		this.timeZone = timeZone;
	}

	public String getIccid()
	{
		return iccid;
	}

	public void setIccid(String iccid)
	{
		this.iccid = iccid;
	}

	public String getEndpointType()
	{
		return endpointType;
	}

	public void setEndpointType(String endpointType)
	{
		this.endpointType = endpointType;
	}

	public float getSupportedVersion()
	{
		return supportedVersion;
	}

	public void setSupportedVersion(float supportedVersion)
	{
		this.supportedVersion = supportedVersion;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public int getInStoreStatus() {
		return inStoreStatus;
	}

	public void setInStoreStatus(int isStatus) {
		this.inStoreStatus = isStatus;
	}

	public Date getInStoreDate() {
		return inStoreDate;
	}

	public void setInStoreDate(Date isDate) {
		this.inStoreDate = isDate;
	}

	public String getInStoreId() {
		return inStoreId;
	}

	public void setInStoreId(String isId) {
		this.inStoreId = isId;
	}

	public String getDeviceAlias()
	{
		return deviceAlias;
	}

	public void setDeviceAlias(String deviceAlias)
	{
		this.deviceAlias = deviceAlias;
	}

	public String getAdobeDeviceId()
	{
		return adobeDeviceId;
	}

	public void setAdobeDeviceId(String adobeDeviceId)
	{
		this.adobeDeviceId = adobeDeviceId;
	}

	public Date getAddFreeTitleTime() {
		return addFreeTitleTime;
	}

	public void setAddFreeTitleTime(Date freeTitleTime) {
		this.addFreeTitleTime = freeTitleTime;
	}

    public boolean isActiveWebReader() {
        return isActiveWebReader;
    }

    public void setActiveWebReader(boolean isActiveWebReader) {
        this.isActiveWebReader = isActiveWebReader;
    }

    public int getwRRefreshFrequency() {
        return wRRefreshFrequency;
    }

    public void setwRRefreshFrequency(int wRRefreshFrequency) {
        this.wRRefreshFrequency = wRRefreshFrequency;
    }

    public int getwRRefreshOffset() {
        return wRRefreshOffset;
    }

    public void setwRRefreshOffset(int wRRefreshOffset) {
        this.wRRefreshOffset = wRRefreshOffset;
    }

	public String getRetailer()
	{
		return retailer;
	}

	public void setRetailer(String retailer)
	{
		this.retailer = retailer;
	}

	public String getLanguageId() {
		return this.languageId;
	}

	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getCountryId() {
		return this.countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getEan()
	{
		return ean;
	}

	public void setEan(String ean)
	{
		this.ean = ean;
	}

    public boolean isAdsSupported() {
        return adsSupported;
    }

    public void setAdsSupported(boolean adsSupported) {
        this.adsSupported = adsSupported;
    }

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getSiliconId() {
        return siliconId;
    }

    public void setSiliconId(String siliconId) {
        this.siliconId = siliconId;
    }

    public void setHdcpBlocked(boolean hdcpBlocked) {
        this.hdcpBlocked = hdcpBlocked;
    }

    public boolean isHdcpBlocked() {
        return hdcpBlocked;
    }

	public boolean isVideoEnabled() {
		return videoEnabled;
	}

	public void setVideoEnabled(boolean videoEnabled) {
		this.videoEnabled = videoEnabled;
	}

	public String getAudioSessioKey()
	{
		return audioSessioKey;
	}

	public void setAudioSessioKey(String audioSessioKey)
	{
		this.audioSessioKey = audioSessioKey;
	}

	public DeviceInfo clone() {
		DeviceInfo dInfo = new DeviceInfo();
		dInfo.setAccountId(accountId);
		dInfo.setActive(isActive);
		dInfo.setBlackListed(blackListed);
		dInfo.setBnModel(bnModel);
		dInfo.setCreateDate(createDate);
		dInfo.setCurrentBuildMajor(currentBuildMajor);
		dInfo.setCurrentBuildMinor(currentBuildMinor);
		dInfo.setCurrentBuildNumber(currentBuildNumber);
		dInfo.setCurrentBuildRevision(currentBuildRevision);
		dInfo.setDeviceToken(deviceToken);
		dInfo.setEncoding(encoding);
		dInfo.setEndpointType(endpointType);
		dInfo.setFamilyId(familyId);
		dInfo.setHashPrivateKey(hashPrivateKey);
		dInfo.setIccid(iccid);
		dInfo.setId(id);
		dInfo.setImei(imei);
		dInfo.setImsi(imsi);
		dInfo.setInitialBuildNumber(initialBuildNumber);
		dInfo.setInStoreDate(inStoreDate);
		dInfo.setInStoreId(inStoreId);
		dInfo.setInStoreStatus(inStoreStatus);
		dInfo.setModel(model);
		dInfo.setModelDesc(modelDesc);
		dInfo.setModelId(modelId);
		dInfo.setModemFirmware(modemFirmware);
		dInfo.setModifyDate(modifyDate);
		dInfo.setPreReg(preReg);
		dInfo.setPublicKey(publicKey);
		dInfo.setSerialNumber(serialNumber);
		dInfo.setSoftwareVersion(softwareVersion);
		dInfo.setSourceID(sourceID);
		dInfo.setSupportedVersion(supportedVersion);
		dInfo.setTimeZone(timeZone);
		dInfo.setDeviceAlias(deviceAlias);
		dInfo.setAdobeDeviceId(adobeDeviceId);
		dInfo.setAddFreeTitleTime(addFreeTitleTime);
		dInfo.setActiveWebReader(isActiveWebReader);
		dInfo.setwRRefreshFrequency(wRRefreshFrequency);
		dInfo.setwRRefreshOffset(wRRefreshOffset);
		dInfo.setRetailer(retailer);
		dInfo.setCountryId(countryId);
		dInfo.setLanguageId(languageId);
		dInfo.setEan(ean);
        dInfo.setAdsSupported(adsSupported);
        dInfo.setCountryOfResidence(countryOfResidence);
        dInfo.setSoftwareUpdatedDate(softwareUpdatedDate);
        dInfo.setHdcpBlocked(hdcpBlocked);
        dInfo.setSiliconId(siliconId);
	    dInfo.setVideoEnabled(videoEnabled);
        dInfo.setFamilyName(familyName);
		return dInfo;
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException
	{
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeLong(this.id);
		out.writeObject(serialNumber);
		out.writeObject(SerializationUtil.serialize(model));
		out.writeObject(SerializationUtil.serialize(modelDesc));
		out.writeInt(bnModel);
		out.writeObject(SerializationUtil.serialize(imei));
		out.writeObject(SerializationUtil.serialize(imsi));
		out.writeObject(SerializationUtil.serialize(modemFirmware));
		out.writeObject(SerializationUtil.serialize(softwareVersion));
		out.writeObject(SerializationUtil.serialize(initialBuildNumber));
		out.writeObject(SerializationUtil.serialize(currentBuildNumber));
		out.writeInt(currentBuildMajor);
		out.writeInt(currentBuildMinor);
		out.writeInt(currentBuildRevision);
		out.writeLong(SerializationUtil.serialize(createDate));
		out.writeLong(SerializationUtil.serialize(modifyDate));
		out.writeObject(SerializationUtil.serialize(publicKey));
		out.writeObject(SerializationUtil.serialize(hashPrivateKey));
		out.writeInt(preReg);
		out.writeInt(blackListed);
		out.writeLong(familyId);
		out.writeObject(SerializationUtil.serialize(deviceToken));
		out.writeObject(SerializationUtil.serialize(sourceID));
		out.writeInt(encoding);
		out.writeBoolean(isActive);
		out.writeFloat(supportedVersion);
		out.writeLong(accountId);
		out.writeObject(SerializationUtil.serialize(modelId));
		out.writeInt(inStoreStatus);
		out.writeLong(SerializationUtil.serialize(inStoreDate));
		out.writeObject(SerializationUtil.serialize(inStoreId));
		out.writeObject(SerializationUtil.serialize(endpointType));
		out.writeObject(SerializationUtil.serialize(timeZone));
		out.writeObject(SerializationUtil.serialize(iccid));
		out.writeObject(SerializationUtil.serialize(deviceAlias));
		out.writeObject(SerializationUtil.serialize(adobeDeviceId));

		//version 4
		out.writeLong(SerializationUtil.serialize(addFreeTitleTime));

		//version 5
		out.writeBoolean(SerializationUtil.serialize(isActiveWebReader));
		out.writeInt(SerializationUtil.serialize(wRRefreshFrequency));
		out.writeInt(SerializationUtil.serialize(wRRefreshOffset));

		//version 6
		out.writeObject(SerializationUtil.serialize(retailer));
		out.writeObject(SerializationUtil.serialize(languageId));
		out.writeObject(SerializationUtil.serialize(countryId));
		out.writeObject(SerializationUtil.serialize(ean));

        //version 7
        out.writeBoolean(SerializationUtil.serialize(adsSupported));

        //version 8
        out.writeObject(SerializationUtil.serialize(countryOfResidence));
        out.writeLong(SerializationUtil.serialize(softwareUpdatedDate));

        // version 9
        out.writeObject(SerializationUtil.serialize(siliconId));
        out.writeBoolean(SerializationUtil.serialize(hdcpBlocked));
		out.writeBoolean(SerializationUtil.serialize(videoEnabled));
	}

	private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
	{
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			id = in.readLong();
			serialNumber = (String)in.readObject();
			model = SerializationUtil.deserialize((String)in.readObject());
			modelDesc = SerializationUtil.deserialize((String)in.readObject());
			bnModel = in.readInt();
			imei = SerializationUtil.deserialize((String)in.readObject());
			imsi = SerializationUtil.deserialize((String)in.readObject());
			modemFirmware = SerializationUtil.deserialize((String)in.readObject());
			softwareVersion = SerializationUtil.deserialize((String)in.readObject());
			initialBuildNumber = SerializationUtil.deserialize((String)in.readObject());
			currentBuildNumber = SerializationUtil.deserialize((String)in.readObject());
			currentBuildMajor = in.readInt();
			currentBuildMinor = in.readInt();
			currentBuildRevision = in.readInt();
			createDate = SerializationUtil.deserialize(in.readLong());
			modifyDate = SerializationUtil.deserialize(in.readLong());
			publicKey = SerializationUtil.deserialize((String)in.readObject());
			hashPrivateKey = SerializationUtil.deserialize((String)in.readObject());
			preReg = in.readInt();
			blackListed = in.readInt();
			familyId = in.readLong();
			deviceToken = SerializationUtil.deserialize((String)in.readObject());
			sourceID = SerializationUtil.deserialize((String)in.readObject());
			encoding = in.readInt();
			isActive = in.readBoolean();
			supportedVersion = in.readFloat();
			accountId = in.readLong();
			modelId = SerializationUtil.deserialize((String) in.readObject());
			inStoreStatus =in.readInt();
			inStoreDate = SerializationUtil.deserialize(in.readLong());
			inStoreId = SerializationUtil.deserialize((String) in.readObject());
			endpointType = SerializationUtil.deserialize((String)in.readObject());
			timeZone = SerializationUtil.deserialize((String)in.readObject());
			iccid = SerializationUtil.deserialize((String)in.readObject());
		}
		if (version>=2) {
			deviceAlias = SerializationUtil.deserialize((String)in.readObject());
		}
		if (version>=3) {
			adobeDeviceId = SerializationUtil.deserialize((String)in.readObject());
		}
		if (version>= 4) {
			addFreeTitleTime = SerializationUtil.deserialize(in.readLong());
		}
		if (version>= 5) {
			isActiveWebReader = SerializationUtil.deserialize(in.readBoolean());
			wRRefreshFrequency = SerializationUtil.deserialize(in.readInt());
			wRRefreshOffset = SerializationUtil.deserialize(in.readInt());
		}
		if (version>=6) {
			retailer = SerializationUtil.deserialize((String) in.readObject());
			languageId = SerializationUtil.deserialize((String) in.readObject());
			countryId = SerializationUtil.deserialize((String) in.readObject());
			ean = SerializationUtil.deserialize((String) in.readObject());
		}
        if (version >=7){
            //version 7
            adsSupported = SerializationUtil.deserialize(in.readBoolean());
        }

        if (version>=8) {
            countryOfResidence = SerializationUtil.deserialize((String) in.readObject());
            softwareUpdatedDate = SerializationUtil.deserialize(in.readLong());
        }

        if( version >= 9){
            siliconId = SerializationUtil.deserialize((String) in.readObject());
            hdcpBlocked =  SerializationUtil.deserialize(in.readBoolean());
	        videoEnabled =  SerializationUtil.deserialize(in.readBoolean());
        }
    }

    /**
     * @param client where the user initiated the link. Can be null for web service calls.
     * @return if valid, return {@link DeviceInfo#getSourceID()}. Otherwise return "P000000003"
     */
    public static String toSourceId(DeviceInfo client) {
        if(client == null || StringUtils.isBlank(client.getSourceID())) {
            return WEB_PORTAL_SOURCE_ID;
        }
        return client.getSourceID();
    }

	public String toString() {
		return new ToStringBuilder(this)
			.append("id", id)
			.append("serialNumber", serialNumber)
			.append("model", model)
			.append("imei", imei)
			.append("imsi", imsi)
			.append("currentBuildNumber", currentBuildNumber)
			.append("modemFirmware", modemFirmware)
			.append("softwareVersion", softwareVersion)
			.append("preReg", preReg)
			.append("familyId", familyId)
			.append("sourceID", sourceID)
			.append("isActive", isActive)
			.append("iccid", iccid)
			.append("endpointType", endpointType)
			.append("accountId", accountId)
			.append("modelId", modelId)
			.append("instoreStatus", inStoreStatus)
			.append("instoreDate", inStoreDate)
			.append("inStoreId", inStoreId)
			.append("deviceAlias", deviceAlias)
			.append("addFreeTrialTime", addFreeTitleTime)
			.append("isActiveWebReader", isActiveWebReader)
			.append("wRRefreshFrequency", wRRefreshFrequency)
			.append("wRRefreshOffset", wRRefreshOffset)
            .append("retailer", retailer)
            .append("languageId", languageId)
            .append("countryId", countryId)
            .append("ean", ean)
            .append("adsSupported", adsSupported)
            .append("countryOfResidence", countryOfResidence)
            .append("softwareUpdatedDate", softwareUpdatedDate)
            .append("siliconId", siliconId)
            .append("hdcpBlocked", hdcpBlocked)
             .append("familyName",familyName)
                .toString();
	}

    public Date getSoftwareUpdatedDate() {
        return softwareUpdatedDate;
    }

    public void setSoftwareUpdatedDate(Date softwareUpdatedDate) {
        this.softwareUpdatedDate = softwareUpdatedDate;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(final String familyName) {
        this.familyName = familyName;
    }
}
