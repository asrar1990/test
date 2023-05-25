package com.bn.common.dto.profile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;

/**
 * User: ktran
 * Date: 8/1/12
 */
public class ProfileConstants {

    public static final String UNKNOWN_ERROR = "AD1255";

    // Number of lines to be logged during exception
    public static final int ASYNC_LOG_NUM = 10;

    // maximum number of profiles that can exist
    public static final int MAX_PROFILES = 6;
    // soft maximum number of interests per profile
    public static final int MAX_INTERESTS = 40;

    //Profile Id
    public static final String PROFILE_ID = "profileId";
    //Account Id
    public static final String ACCOUNT_ID = "accountId";

/*
    //Profile Version
    public static final String PROFILE_VERSION = "profileVersion";

    //Profile create date
    public static final String PROFILE_CREATE_DATE = "createDate";
    //Profile modified date
    public static final String PROFILE_MODIFIED_DATE = "modifiedDate";
    //Profile mod time
    public static final String PROFILE_MOD_TIME = "modTime";
*/

    //Profile type key
    public static final String PROFILE_TYPE = "profileType";
    //Profile type values
	public static final int PROFILE_PRIMARY = 0;
	public static final int PROFILE_ADDITIONAL = 1;
	public static final int PROFILE_DEPENDENT = 2;

    //Profile status key
//    public static final String PROFILE_STATUS = "profileStatus";
    //Profile status values
	public static final int PROFILE_STATUS_ACTIVE = 1;
//    public static final int PROFILE_STATUS_INACTIVE = 0;
	public static final int PROFILE_STATUS_DELETED = -1;

    //Profile Info Types - used to store in different Mongo DB collections based on the type
    public static final String PROFILE_BASE_ATTRIBUTES = "baseAttributes";
    public static final String PROFILE_PERMISSIONS = "permissions";
    public static final String PROFILE_PREFERENCES = "preferences";
    public static final String USER_PREFERENCES = "userPreferences";
//    public static final String PROFILE_INTERESTS = "interests";

    //Profile name info
    public static final String PROFILE_FIRST_NAME = "firstName";
    public static final String PROFILE_LAST_NAME = "lastName";
    public static final String PROFILE_NICK_NAME = "nickName";

    //Profile gender key
    public static final String PROFILE_GENDER = "gender";
    //Profile gender values
    public static final String PROFILE_GENDER_MALE = "M";
    public static final String PROFILE_GENDER_FEMALE = "F";

    //Profile authentication details
    public static final String PROFILE_USERNAME = "username";
    public static final String PROFILE_PASSWORD = "password";
    public static final String PROFILE_PIN = "pin";

    //Profile birthdate key
    public static final String PROFILE_BIRTH_DATE = "birthDate";

    //TTS-Text To Speak
    public static final String PROFILE_TTS_ENABLED = "ttsEnabled";

/*
    //Profile wishlist key
    public static final String PROFILE_WISH_LIST = "wishList";

    //Profile interest related attributes
    public static final String PROFILE_INTEREST_ID = "id";
    public static final String PROFILE_INTEREST_TITLE = "title";
    public static final String PROFILE_INTEREST_DESCRIPTION = "description";
    public static final String PROFILE_INTEREST_IMAGE_URL = "imageURL";

    //Kids profile entitlement constants
    public static final String ENTITLE_VIDEO_RATED_G = "entitleVideoRatedG";
    public static final String ENTITLE_VIDEO_RATED_PG = "entitleVideoRatedPG";
    public static final String ENTITLE_VIDEO_RATED_PG13 = "entitleVideoRatedPG13";
    public static final String ENTITLE_VIDEO_RATED_R = "entitleVideoRatedR";
    public static final String ENTITLE_VIDEO_RATED_NR = "entitleVideoRatedNR";
    public static final String ENTITLE_VIDEO_RATED_TVY = "entitleVideoRatedTVY";
    public static final String ENTITLE_VIDEO_RATED_TVY7 = "entitleVideoRatedTVY7";
    public static final String ENTITLE_VIDEO_RATED_TVG = "entitleVideoRatedTVG";
    public static final String ENTITLE_VIDEO_RATED_TVOTHER = "entitleVideoRatedTVOther";
    public static final String ENTITLE_VIDEO_RATED_TVMA = "entitleVideoRatedTVMA";
    public static final String ENTITLE_VIDEO_RATED_TVPG = "entitleVideoRatedTVPG";
    public static final String ENTITLE_VIDEO_RATED_TV14 = "entitleVideoRatedTV14";
    public static final String ENTITLE_BOOKS = "entitleBooks";
    public static final String ENTITLE_MAGAZINES = "entitleMagazines";
    public static final String ENTITLE_NEWSPAPERS = "entitleNewspapers";
    public static final String ENTITLE_EXTRAS = "entitleExtras";
    public static final String ENTITLE_GAMES = "entitleGames";
*/

    public static final Collection<ProfileInfoType> ALL_COLLECTIONS = Collections.unmodifiableCollection(EnumSet.allOf(ProfileInfoType.class));

    /**
     * Profile information type enumeration
     */
    public enum ProfileInfoType {

        PREFERENCES,
        PERMISSIONS,
        INTERESTS;

        private static final Logger logger = LogManager.getLogger(ProfileInfoType.class);

        public static ProfileInfoType getProfileInfoType(String profileInfoType) {
            if(profileInfoType == null) {
                return null;
            }
            try {
                return valueOf(profileInfoType.toUpperCase());
            }
            catch(IllegalArgumentException e) {
                logger.debug("Error parsing, profileInfoType=" + profileInfoType, e);
                return null;
            }
        }
    }
}
