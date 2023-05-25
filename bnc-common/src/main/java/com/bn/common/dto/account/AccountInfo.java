package com.bn.common.dto.account;

import com.bn.common.dto.common.AccountOptions;
import com.bn.common.dto.common.LicenseEncryptionType;
import com.bn.common.dto.util.SerializationUtil;
import com.bn.common.dto.util.ToStringUtil;
import com.bn.common.dto.profile.ProfileInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@XmlRootElement(name = "Account")
public class AccountInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int version = 14;
	private long id;
	private String email;
	private String firstName;
	private String lastName;

	//backend customer id
	private String custId;

	//the active userToken
	private String userToken;

	//the status of the backend customer (active or deleted)
	private String backendStatus;

	private boolean privacy = true;
	private Date createDate;
	private Date modifyDate;
	//last known languageId of the user from locale perspective
	private String languageId;

	//last known countryId of the user from local perspective
	private String countryId;

	private Date activeDate;
	private int activeStatus;
	private String adobeId;
	private String webReaderId;
	private Date webReaderTime;
	private int webReaderCount;
	private String retailer = "BN2";
	private String features;
	private String retailerAccountId;
	private String retailerCountryId = "US";
	private String retailerActiveCountryId = "US";

	//LinkID to link between accounts
	private String linkId;

	//this user's profile id
	private long profileId;

	//it is hash of the customerId so it can be used by partners for identifying
	//our customer without revealing our internal customer id
	private String accountHash;

	//when an account is linked, is this the primary linkedId
	private String primaryLinkId;

	//shadow account partner id
	private String partnerId = "BN2";

	//curr profile information
	private ProfileInfo currProfile = new ProfileInfo();

	//is the account enabled for video based on features attributes containing V
	private boolean videoSupport;

	//is uv linked
	private boolean uvLinked;

    private Map<LicenseEncryptionType,UserLicenseKey> userLicenseKeys = new EnumMap<>(LicenseEncryptionType.class);
    private EnumSet<AccountOptions> accountOptions =  EnumSet.noneOf(AccountOptions.class);

    private Date linkTime;

	//vendor info
	private String vendorId;

    //last samples added Date
    private Date freeTitlesLastAddedDate;
    private String appId;

    private transient String secretKey;

    public AccountInfo() {
		
	}

	public AccountInfo(String email) {
		this.email = email;
	}

	public AccountInfo(String email, String languageId, String countryId) {
		this.email = email;
		this.languageId = languageId;
		this.countryId = countryId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	public String getUserToken()
	{
		return userToken;
	}

	public void setUserToken(String userToken)
	{
		this.userToken = userToken;
	}
	
	public boolean isPrivacy()
	{
		return privacy;
	}

	public void setPrivacy(boolean privacy)
	{
		this.privacy = privacy;
	}
	
	public String getBackendStatus()
	{
		return backendStatus;
	}

	public void setBackendStatus(String backendStatus)
	{
		this.backendStatus = backendStatus;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Date getModifyDate() {
		return this.modifyDate;
	}
	
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getLanguageId() {
		return this.languageId;
	}
	
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

    /**
     * @return What country the user prefers for localization and language
     */
	public String getCountryId() {
		return this.countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	
	public Date getActiveDate() {
		return this.activeDate;
	}
	
	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}
	
	public int getActiveStatus() {
		return this.activeStatus;
	}
	
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	public String getAdobeId()
	{
		return adobeId;
	}

	public void setAdobeId(String adobeId)
	{
		this.adobeId = adobeId;
	}

	public String getWebReaderId() {
		return webReaderId;
	}

	public void setWebReaderId(String webReaderId) {
		this.webReaderId = webReaderId;
	}

	public Date getWebReaderTime() {
		return webReaderTime;
	}

	public void setWebReaderTime(Date webReaderTime) {
		this.webReaderTime = webReaderTime;
	}

	public int getWebReaderCount() {
		return webReaderCount;
	}

	public void setWebReaderCount(int webReaderCount) {
		this.webReaderCount = webReaderCount;
	}

	public Locale getLocale(){
		return new Locale(this.languageId, this.countryId);
	}
	
	public String getFeatures()
	{
		return features;
	}

	public void setFeatures(String features)
	{
		this.features = features;
	}

	public String getRetailer() {
		return retailer;
	}

	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public String getRetailerAccountId() {
		return retailerAccountId;
	}

	public void setRetailerAccountId(String retailerAccountId) {
		this.retailerAccountId = retailerAccountId;
	}

    /**
     * @return Where the device was purchased
     * @deprecated
     */
	public String getRetailerCountryId() {
		return retailerCountryId;
	}

	public void setRetailerCountryId(String retailerCountryId) {
		this.retailerCountryId = retailerCountryId;
	}

    /**
     * @return The country of the user's bank
     */
	public String getRetailerActiveCountryId() {
		return retailerActiveCountryId;
	}

	public void setRetailerActiveCountryId(String retailerActiveCountryId) {
		this.retailerActiveCountryId = retailerActiveCountryId;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

    /**
     * @return primary profile id
     */
	public long getProfileId() {
		return profileId;
	}

    /**
     * @param profileId Primary profile Id
     */
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

    public Map<LicenseEncryptionType,UserLicenseKey> getUserLicenseKeys(){
        return userLicenseKeys;
    }

    public void setUserLicenseKeys(Map<LicenseEncryptionType,UserLicenseKey> userLicenseKeys){
        this.userLicenseKeys = userLicenseKeys;
    }

    public EnumSet<AccountOptions> getAccountOptions(){
        return accountOptions;
    }

    public void setAccountOptions(EnumSet<AccountOptions> accountOptions){
        this.accountOptions = accountOptions;
    }

	public String getAccountHash() {
		return accountHash;
	}

	public void setAccountHash(String accountHash) {
		this.accountHash = accountHash;
	}

	public String getPrimaryLinkId() {
		return primaryLinkId;
	}

	public void setPrimaryLinkId(String primaryLinkId) {
		this.primaryLinkId = primaryLinkId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public ProfileInfo getCurrProfile() {
		return currProfile;
	}

	public void setCurrProfile(ProfileInfo currProfile) {
		this.currProfile = currProfile;
	}

	public boolean isVideoSupport() {
		if (features!=null) {
			return features.contains("V");

		} else
			return  false;
	}

	public boolean isMSWalletEnabled() {
		if (features!=null) {
			return features.contains("M");

		} else
			return  false;
	}

    public Date getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(Date linkTime) {
        this.linkTime = linkTime;
    }

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

    public String getSecretKey() {
        return this.secretKey;
    }
    public void setSecretKey(String secretKey){
        this.secretKey = secretKey;
    }

	public AccountInfo clone() {
		AccountInfo aInfo = new AccountInfo();
		aInfo.setActiveDate(activeDate);
		aInfo.setActiveStatus(activeStatus);
		aInfo.setBackendStatus(backendStatus);
		aInfo.setCountryId(countryId);
		aInfo.setCreateDate(createDate);
		aInfo.setCustId(custId);
		aInfo.setEmail(email);
		aInfo.setFirstName(firstName);
		aInfo.setId(id);
		aInfo.setLanguageId(languageId);
		aInfo.setLastName(lastName);
		aInfo.setModifyDate(modifyDate);
		aInfo.setPrivacy(privacy);
		aInfo.setUserToken(userToken);
		aInfo.setAdobeId(adobeId);
		aInfo.setWebReaderCount(webReaderCount);
		aInfo.setWebReaderId(webReaderId);
		aInfo.setWebReaderTime(webReaderTime);
		aInfo.setRetailer(retailer);
		aInfo.setFeatures(features);
        aInfo.setRetailerAccountId(retailerAccountId);
		aInfo.setRetailerActiveCountryId(retailerActiveCountryId);
		aInfo.setLinkId(linkId);
		aInfo.setProfileId(profileId);
        aInfo.setUserLicenseKeys(userLicenseKeys);
        aInfo.setAccountOptions(accountOptions);
		aInfo.setAccountHash(accountHash);
		aInfo.setPrimaryLinkId(primaryLinkId);
		aInfo.setPartnerId(partnerId);
		aInfo.setCurrProfile(currProfile);
		aInfo.setLinkTime(linkTime);
		aInfo.setVendorId(vendorId);
        aInfo.setFreeTitlesLastAddedDate(freeTitlesLastAddedDate);
        aInfo.setAppId(appId);
        aInfo.setSecretKey(secretKey);
		return aInfo;
	}
	
	private void writeObject(java.io.ObjectOutputStream out) throws IOException 
	{
		out.writeLong(serialVersionUID);
		out.writeInt(version);
		out.writeLong(id);
		out.writeObject(email);
		out.writeObject(SerializationUtil.serialize(firstName));
		out.writeObject(SerializationUtil.serialize(lastName));
		out.writeObject(SerializationUtil.serialize(custId));
		out.writeObject(SerializationUtil.serialize(userToken));
		out.writeBoolean(privacy);
		out.writeObject(SerializationUtil.serialize(backendStatus));
		out.writeLong(SerializationUtil.serialize(createDate));
		out.writeLong(SerializationUtil.serialize(modifyDate));
		out.writeObject(SerializationUtil.serialize(languageId));
		out.writeObject(SerializationUtil.serialize(countryId));
		out.writeLong(SerializationUtil.serialize(activeDate));
		out.writeInt(activeStatus);
		out.writeObject(SerializationUtil.serialize(adobeId));
		out.writeObject(SerializationUtil.serialize(webReaderId));
		out.writeLong(SerializationUtil.serialize(webReaderTime));
		out.writeInt(webReaderCount);
		out.writeObject(SerializationUtil.serialize(retailer));
		out.writeObject(SerializationUtil.serialize(features));
        out.writeObject(SerializationUtil.serialize(retailerAccountId));
		out.writeObject(SerializationUtil.serialize(retailerCountryId));
		out.writeObject(SerializationUtil.serialize(retailerActiveCountryId));
		out.writeObject(SerializationUtil.serialize(linkId));
		out.writeLong(profileId);
        out.writeObject(userLicenseKeys);
        out.writeObject(accountOptions);
		out.writeObject(SerializationUtil.serialize(accountHash));
		out.writeObject(SerializationUtil.serialize(primaryLinkId));
		out.writeObject(SerializationUtil.serialize(partnerId));
		out.writeObject(SerializationUtil.serialize(currProfile));
		out.writeLong(SerializationUtil.serialize(linkTime));
		out.writeObject(SerializationUtil.serialize(vendorId));
        out.writeLong(SerializationUtil.serialize(freeTitlesLastAddedDate));
        out.writeObject(SerializationUtil.serialize(appId));
	}
	
	private void readObject(java.io.ObjectInputStream in) 
	throws IOException, ClassNotFoundException
	{
		in.readLong();
		version = in.readInt();
		if (version>=1) {
			id = in.readLong();
			email = (String)in.readObject();
			firstName = SerializationUtil.deserialize((String)in.readObject());
			lastName = SerializationUtil.deserialize((String)in.readObject());
			custId = SerializationUtil.deserialize((String)in.readObject());
			userToken = SerializationUtil.deserialize((String)in.readObject());
			privacy = in.readBoolean();
			backendStatus = SerializationUtil.deserialize((String)in.readObject());
			createDate = SerializationUtil.deserialize(in.readLong());
			modifyDate = SerializationUtil.deserialize(in.readLong());
			languageId = SerializationUtil.deserialize((String) in.readObject());
			countryId = SerializationUtil.deserialize((String) in.readObject());
			activeDate = SerializationUtil.deserialize(in.readLong());
			activeStatus = in.readInt();
		}
		if (version>=2) {
			adobeId = SerializationUtil.deserialize((String) in.readObject());
		}
		if (version>=3) {
			webReaderId = SerializationUtil.deserialize((String) in.readObject());
			webReaderTime = SerializationUtil.deserialize(in.readLong());
			webReaderCount = in.readInt();
		}
		if (version>=4) {
			retailer = SerializationUtil.deserialize((String) in.readObject());
		}
		if (version>=5) {
			features = SerializationUtil.deserialize((String) in.readObject());
		}
        if (version >=6){
	        retailerAccountId = SerializationUtil.deserialize((String) in.readObject());
        }
		if (version>=7) {
			retailerCountryId = SerializationUtil.deserialize((String) in.readObject());
			retailerActiveCountryId = SerializationUtil.deserialize((String) in.readObject());
			linkId = SerializationUtil.deserialize((String) in.readObject());
		}
		if (version>=8) {
			profileId = in.readLong();
		}

        if(version >= 9){
            userLicenseKeys = (EnumMap<LicenseEncryptionType,UserLicenseKey>) in.readObject();
            accountOptions = (EnumSet<AccountOptions>) in.readObject();
        }

		if(version >= 10){
			accountHash = SerializationUtil.deserialize((String) in.readObject());
			primaryLinkId = SerializationUtil.deserialize((String) in.readObject());
			partnerId = SerializationUtil.deserialize((String) in.readObject());
			currProfile = SerializationUtil.deserialize(ProfileInfo.class, in.readObject());
			//currProfileId = in.readLong();
		}
        if(version>=11){
            linkTime = SerializationUtil.deserialize(in.readLong());
        }
		if (version>=12){
			vendorId = SerializationUtil.deserialize((String) in.readObject());
		}
        if(version>=13){
            freeTitlesLastAddedDate = SerializationUtil.deserialize(in.readLong());
        }
        if(version>=14){
            appId = SerializationUtil.deserialize((String) in.readObject());
        }
	}
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("accountId", id)
			.append("custId", custId)
			.append("email", email)
			.append("firstName", ToStringUtil.wrapDoubleQuotes(firstName))
			.append("lastName", ToStringUtil.wrapDoubleQuotes(lastName))
			.append("privacy", privacy)
			.append("backendStatus", backendStatus)
			.append("createDate", createDate)
			.append("modifyDate", modifyDate)
			.append("languageId", languageId)
			.append("countryId", countryId)
			.append("activeDate", activeDate)
			.append("activeStatus", activeStatus)
			.append("adobeId", adobeId)
			.append("webReaderId", webReaderId)
			.append("webReaderTime", webReaderTime)
			.append("webReaderCount", webReaderCount)
            .append("retailer", retailer)
            .append("features", features)
            .append("retailerAccountId", retailerAccountId)
			.append("retailerCountryId", retailerCountryId)
            .append("accountOptions", AccountOptions.encode(accountOptions))
            .append("primaryLinkId", primaryLinkId)
            .append("partnerId", partnerId)
            .append("currProfile", currProfile)
            .append("linkTime", linkTime)
            .append("freeTitlesLastAddedDate", freeTitlesLastAddedDate)
            .append("appId", appId)
			.toString();
	}

    public Date getFreeTitlesLastAddedDate() {
        return freeTitlesLastAddedDate;
    }

    public void setFreeTitlesLastAddedDate(Date freeTitlesLastAddedDate) {
        this.freeTitlesLastAddedDate = freeTitlesLastAddedDate;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }
}
