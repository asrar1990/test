package com.bn.common.dto.profile;

import com.bn.common.dto.util.SerializationUtil;
import com.bn.common.dto.util.ToStringUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Years;

import javax.xml.bind.annotation.XmlTransient;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Note that this object is also directly converted JSON and XML by Jersey. See ProfileRestService.
 * User: ktran
 * Date: 4/13/12
 */
public class ProfileInfo implements Serializable {

    private static final Logger logger = LogManager.getLogger(ProfileInfo.class);
    private static final long serialVersionUID = 1L;
    private static final int ADULT = 18;

    private int version = 3;
    private int profileVersion = 1;
    private long accountId;
    private long profileId;
    private int profileType;
    private String customerId;
    private Map<String, String> permissions = new HashMap<String, String>();
    private List<String> profileWishList = new ArrayList<String>();
    private Collection<ProfileInterestInfo> interests = new HashSet<ProfileInterestInfo>(ProfileConstants.MAX_INTERESTS);
    private Map<String, String> preferences = new HashMap<String, String>();
    private Collection<ProfileSampleInfo> samples = new HashSet<ProfileSampleInfo>();
    private Map<String, String> profileBaseInfoMap = new HashMap<String, String>(10);
    private int status = ProfileConstants.PROFILE_STATUS_ACTIVE;
    private Date createDate;
    private long modTime;
    private int shelfSortBy;
    private List<UserPreference> userPreferences;

    public ProfileInfo() {
    }

    public ProfileInfo(int profileType, long accountId) {
        this.profileType = profileType;
        this.accountId = accountId;
    }

    public ProfileInfo(long accountId, long profileId) {
        this.accountId = accountId;
        this.profileId = profileId;
    }

    /**
     * @return account id and profile id with a hyphen in the middle
     */
    public String getId() {
        return accountId + "-" + profileId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public int getShelfSortBy() {
        return shelfSortBy;
    }

    public void setShelfSortBy(int shelfSortBy) {
        this.shelfSortBy = shelfSortBy;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return profileBaseInfoMap.get(ProfileConstants.PROFILE_USERNAME);
    }

    public void setUsername(String username) {
        profileBaseInfoMap.put(ProfileConstants.PROFILE_USERNAME, username);
    }

    public String getFirstName() {
        return profileBaseInfoMap.get(ProfileConstants.PROFILE_FIRST_NAME);
    }

    public void setFirstName(String firstName) {
        profileBaseInfoMap.put(ProfileConstants.PROFILE_FIRST_NAME, firstName);
    }

    public String getLastName() {
        return profileBaseInfoMap.get(ProfileConstants.PROFILE_LAST_NAME);
    }

    public void setLastName(String lastName) {
        profileBaseInfoMap.put(ProfileConstants.PROFILE_LAST_NAME, lastName);
    }

    @XmlTransient
    public String getPassword() {
        return profileBaseInfoMap.get(ProfileConstants.PROFILE_PASSWORD);
    }

    public void setPassword(String password) {
        profileBaseInfoMap.put(ProfileConstants.PROFILE_PASSWORD, password);
    }

    @XmlTransient
    public String getPin() {
        return profileBaseInfoMap.get(ProfileConstants.PROFILE_PIN);
    }

    public void setPin(String pin) {
        profileBaseInfoMap.put(ProfileConstants.PROFILE_PIN, pin);
    }

    public String getNickname() {
        return profileBaseInfoMap.get(ProfileConstants.PROFILE_NICK_NAME);
    }

    public void setNickname(String nickname) {
        profileBaseInfoMap.put(ProfileConstants.PROFILE_NICK_NAME, nickname);
    }

    public String getGender() {
        return profileBaseInfoMap.get(ProfileConstants.PROFILE_GENDER);
    }

    public void setGender(String gender) {
        profileBaseInfoMap.put(ProfileConstants.PROFILE_GENDER, gender);
    }

    public Date getBirthDate() {
        final Long dateL = getBirthDateMs();
        if(dateL == null) {
            return null;
        }
        return new Date(dateL);
    }

    private Long getBirthDateMs() {
        Long dateL = null;
        final String dob = profileBaseInfoMap.get(ProfileConstants.PROFILE_BIRTH_DATE);
        if(dob != null) {
            try {
                dateL = Long.parseLong(dob);
            }
            catch(NumberFormatException e) {
                //not a long value. birth date will remain null.
                logger.debug("invalid number parsing birth date, dob=" + dob, e);
            }
        }
        return dateL;
    }

    public void setBirthDate(Date birthDate) {
        String time = null;
        if(birthDate != null) {
            time = Long.toString(birthDate.getTime());
        }
        profileBaseInfoMap.put(ProfileConstants.PROFILE_BIRTH_DATE, time);
    }

    public List<ProfileInterestInfo> getProfileInterestInfoList() {
        return new ArrayList<ProfileInterestInfo>(interests);
    }

    public void addInterest(ProfileInterestInfo profileInterestInfo) {
        if(profileInterestInfo != null && StringUtils.isNotBlank(profileInterestInfo.getId())) {
            interests.add(profileInterestInfo);
        }
    }

    /**
     * no sense outputting via ProfileRestService because that service doesn't bother looking it up
     * @return profile wish list. Often times not populated because it is stored in LMS, not cloud
     */
    @XmlTransient
    public List<String> getProfileWishList() {
        return profileWishList;
    }

    public void setProfileWishList(List<String> profileWishList) {
        if(profileWishList != null) {
            this.profileWishList = profileWishList;
        }
    }

    /**
     * Note that this method appears unused but remains because it is used by bn.com via Jersey serialization
     */
    public Set<Map.Entry<String, String>> getProfilePermissionInfoList() {
        return permissions.entrySet();
    }

    /**
     * marked transient to avoid duplication of {@link #getProfilePermissionInfoList()} which bn.com already relies on
     */
    @XmlTransient
    public Map<String, String> getPermissions() {
        return Collections.unmodifiableMap(permissions);
    }

    public void addPermission(String key, String value) {
        put(permissions, key, value);
    }

    /**
     * Note that this method appears unused but remains because it is used by bn.com via Jersey serialization
     */
    public Set<Map.Entry<String, String>> getProfilePreferenceInfoList() {
        return preferences.entrySet();
    }

    /**
     * marked transient to avoid duplication of {@link #getProfilePreferenceInfoList()} which bn.com already relies on
     */
    @XmlTransient
    public Map<String, String> getPreferences() {
        return Collections.unmodifiableMap(preferences);
    }

    public void addPreference(String key, String value) {
        put(preferences, key, value);
    }

    public void addBaseAttribute(String key, String value) {
        put(profileBaseInfoMap, key, value);
    }

    public List<ProfileSampleInfo> getProfileSampleInfoList() {
        return Collections.unmodifiableList(new ArrayList<ProfileSampleInfo>(samples));
    }

    public void addSample(ProfileSampleInfo sample) {
        if(sample != null && StringUtils.isNotBlank(sample.getEan())) {
            samples.add(sample);
        }
    }

    public int getProfileType() {
        return profileType;
    }

    public void setProfileType(int profileType) {
        this.profileType = profileType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public long getModTime() {
        return modTime;
    }

    public void setModTime(long modTime) {
        this.modTime = modTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isTtsEnabled() {
        return BooleanUtils.toBoolean(profileBaseInfoMap.get(ProfileConstants.PROFILE_TTS_ENABLED));
    }

    public void setTtsEnabled(boolean ttsEnabled) {
        profileBaseInfoMap.put(ProfileConstants.PROFILE_TTS_ENABLED, Boolean.toString(ttsEnabled));
    }

    public int getProfileVersion() {
        return profileVersion;
    }

    public void setProfileVersion(int profileVersion) {
        this.profileVersion = profileVersion;
    }

    @XmlTransient
    public Map<String, String> getProfileBaseInfoMap() {
        return Collections.unmodifiableMap(profileBaseInfoMap);
    }

    public int getAge() {
        final Long birthDate = getBirthDateMs();
        if(birthDate == null || birthDate == 0) {
            return ADULT;
        }
        return Years.yearsBetween(new DateTime(birthDate), new DateTime()).getYears();
    }

    public boolean isChild() {
        return profileType == ProfileConstants.PROFILE_DEPENDENT;
    }

    public void merge(ProfileInfo copyFrom) {
        if(copyFrom != null) {
            updateProfileBaseAttributes(copyFrom);
            permissions.putAll(copyFrom.permissions);
            preferences.putAll(copyFrom.preferences);
            interests.addAll(copyFrom.interests);
            profileWishList = copyFrom.profileWishList;
            userPreferences = copyFrom.userPreferences;
        }
    }

    private void updateProfileBaseAttributes(ProfileInfo newProfile) {
        for(Map.Entry<String, String> newBaseAttribute : newProfile.profileBaseInfoMap.entrySet()) {
            if(!ProfileConstants.PROFILE_FIRST_NAME.equals(newBaseAttribute.getKey()) || StringUtils.isNotEmpty(newBaseAttribute.getValue())) {
                put(profileBaseInfoMap, newBaseAttribute.getKey(), newBaseAttribute.getValue());
            }
        }
    }

    private static void put(Map<String, String> map, String key, String value) {
        if(StringUtils.isNotEmpty(key)) {
            map.put(key, value);
        }
    }

    public List<UserPreference> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(List<UserPreference> userPreferences) {
        this.userPreferences = userPreferences;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeLong(serialVersionUID);
        out.writeInt(version);
        out.writeLong(accountId);
        out.writeLong(profileId);
        out.writeObject(SerializationUtil.serialize(interests));
        out.writeObject(SerializationUtil.serialize(profileWishList));
        out.writeInt(profileType);
        out.writeObject(SerializationUtil.serialize(permissions));
        out.writeObject(SerializationUtil.serialize(preferences));
        out.writeInt(status);
        out.writeLong(SerializationUtil.serialize(createDate));
        out.writeLong(modTime);
        out.writeObject(SerializationUtil.serialize(profileBaseInfoMap));
        out.writeObject(SerializationUtil.serialize(samples));
        out.writeObject(SerializationUtil.serialize(customerId));
        out.writeObject(SerializationUtil.serialize(userPreferences));
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.readLong();
        version = in.readInt();
        if(version < 3) {
            throw new IllegalStateException("Cannot de-serialize, version=" + version);
        }
        accountId = in.readLong();
        profileId = in.readLong();
        interests = SerializationUtil.deserialize(Collection.class, in.readObject());
        profileWishList = SerializationUtil.deserialize(List.class, in.readObject());
        profileType = in.readInt();
        permissions = SerializationUtil.deserialize(Map.class, in.readObject());
        preferences = SerializationUtil.deserialize(Map.class, in.readObject());
        status = in.readInt();
        createDate = SerializationUtil.deserialize(in.readLong());
        modTime = in.readLong();
        profileBaseInfoMap = SerializationUtil.deserialize(Map.class, in.readObject());
        samples = SerializationUtil.deserialize(Collection.class, in.readObject());
        customerId = SerializationUtil.deserialize((String)in.readObject());
        userPreferences = SerializationUtil.deserialize(List.class, in.readObject());
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("profileId", profileId)
            .append("accountId", accountId)
            .append("customerId", customerId)
            .append("profileType", profileType)
            .append("status", status)
            .append("firstName", ToStringUtil.wrapDoubleQuotes(getFirstName()))
            .append("lastName", ToStringUtil.wrapDoubleQuotes(getLastName()))
            .toString();
    }
}
