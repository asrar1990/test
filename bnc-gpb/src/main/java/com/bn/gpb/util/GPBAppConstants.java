package com.bn.gpb.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GPBAppConstants {
    public static final String LENDABLE_LIST_NAME = "lendableBooks";
    public static final String LENDABLE_LIST_CATEGORY = "LENDABLE";

    public static final String WISHLIST_NAME ="wishlist";
    public static final String WISHLIST_CATEGORY ="WISHLIST";

    public static final String USER_CREATED_LIST_CATEGORY ="USERLIST";

    public static final String SHELVES_CATEGORY ="SHELVES";

    // Network provider types
    public static final int FACEBOOK = 1;
    public static final int GOOGLE = 2;
    public static final int LINKEDIN = 3;
    public static final int TWITTER = 4;
    public static final int YAHOO = 5;
    public static final int NETFLIX = 7;

    //Partner identifier
    public static final String PARTNER_DELUXE = "DELUXE";
    public static final String PARTNER_TOKENTYPE_USER = "USER";
    public static final String PARTNER_TOKENTYPE_DEVICE = "DEVICE";

    public static final String PARTNER_UV = "UV";

    // Network provider status
    public static final int ACCOUNT_UNLINKED = 0;
    public static final int ACCOUNT_LINKED = 1;
    public static final int ACCOUNT_ERROR = 2;
    public static final int OAUTH_ABORTED = 3;

    //Contributor types
    public static final int AUTHOR = 0;
    public static final int REVIEWER = 1;
    public static final int ACTOR = 2;
    public static final int DIRECTOR = 3;
    public static final int PRODUCER = 4;
    public static final int ARTIST = 5;
    public static final int WRITER = 6;
    public static final int INTRODUCTION = 7;

    public static final int DRMTYPE_NONE = 0;
    public static final int DRMTYPE_BASIC = 1;
    public static final int DRMTYPE_ENHANCED = 2;

    public static final String NETFLIX_SERIES ="series";
    public static final String NETFLIX_MOVIES ="movies";
    public static final String NETFLIX_PROGRAMS ="programs";
    public static final String NETFLIX_DISCS ="discs";

    //Download restriction cause
    public static final int DOWNLOAD_RESTRICTION_NONE=0;
    public static final int DOWNLOAD_RESTRICTION_INVALIDPURCHASE=1;
    public static final int DOWNLOAD_RESTRICTION_SUBSCRIPTION_PARENT=2;
    public static final int DOWNLOAD_RESTRICTION_ARCHIVED=3;
    public static final int DOWNLOAD_RESTRICTION_LENDING=4;
    public static final int DOWNLOAD_RESTRICTION_SAMPLE_NOT_AVAILABLE=5;
    public static final int DOWNLOAD_RESTRICTION_APP_VERSION_NOT_COMPATIABLE=6;
    public static final int DOWNLOAD_RESTRICTION_CONTENT_NOT_COMPATIABLE=7;
    public static final int DOWNLOAD_RESTRICTION_PREORDER=8;
    public static final int DOWNLOAD_RESTRICTION_DRM=9;
    public static final int DOWNLOAD_RESTRICTION_UNKNOWN=100;

    //Media Types
    public static final String MEDIA_TYPE_EPUB="epub";
    public static final String MEDIA_TYPE_FOLIO="folio";
    public static final String MEDIA_TYPE_BNAPK="bnapk";
    public static final String MEDIA_TYPE_PDF="pdf";
    public static final String MEDIA_TYPE_EPIB="epib";
    public static final String MEDIA_TYPE_DRP="drp";
    public static final String MEDIA_TYPE_ISSUE="issue";
    public static final String MEDIA_TYPE_EHEPUBP="ehepubp";
    public static final String MEDIA_TYPE_HFF="hff";
    public static final String MEDIA_TYPE_BNWWS="bnwws";
    public static final String MEDIA_TYPE_OFIP="ofip";

    //Device Content type
    public static final String DEVICECONTENT_TYPE_DICTIONARY = "DICT";
    public static final String DEVICECONTENT_TYPE_USERGUIDE = "UGS";
    public static final String DEVICECONTENT_TYPE_TANDC = "TC";
    public static final String DEVICECONTENT_TYPE_WIFI = "WIFI";
    public static final String DEVICECONTENT_TYPE_APP = "APP";
    public static final String DEVICECONTENT_TYPE_READER = "READER";

    //*****************PROFILE RELATED CONSTANTS*******************//
    //Profile Info Types - different types of profile info
    public static final String PROFILE_BASE_ATTRIBUTES = "baseAttributes";
    public static final String PROFILE_PERMISSIONS = "permissions";
    public static final String PROFILE_PREFERENCES = "preferences";
    public static final String PROFILE_INTERESTS = "interests";
    public static final String USER_PREFERENCES = "userPreferences";
    public static final String USER_PREFERENCES_TYPE_AUTO_ARCHIVE = "AUTO_ARCHIVE";

    //Profile type key
    public static final String PROFILE_TYPE = "profileType";
    //Profile type values
    public static final int PROFILE_TYPE_PRIMARY = 0;
    public static final int PROFILE_TYPE_DEPENDENT = 2;
    public static final int PROFILE_TYPE_ADDITIONAL = 1;

    //Profile status key
    public static final String PROFILE_STATUS = "profileStatus";
    //Profile status values
    public static final int PROFILE_STATUS_ACTIVE = 1;
    public static final int PROFILE_STATUS_INACTIVE = 0;
    public static final int PROFILE_STATUS_DELETED = -1;

    public static final String PROFILE_FIRST_NAME = "firstName";
    public static final String PROFILE_LAST_NAME = "lastName";
    public static final String PROFILE_NICK_NAME = "nickName";

    public static final String PROFILE_GENDER = "gender";
    public static final String PROFILE_GENDER_MALE = "M";
    public static final String PROFILE_GENDER_FEMALE = "F";
    public static final String PROFILE_GENDER_OTHER = "O";

    public static final String PROFILE_USERNAME = "username";
    public static final String PROFILE_PASSWORD = "password";
    public static final String PROFILE_PIN = "pin";

    public static final String PROFILE_BIRTH_DATE = "birthDate";

    public static final String PROFILE_TTS_ENABLED = "ttsEnabled";

    public static final String PROFILE_WISH_LIST = "wishList";

    //Profile interest related attributes
    public static final String PROFILE_INTEREST_ID = "id";
    public static final String PROFILE_INTEREST_TITLE = "title";
    public static final String PROFILE_INTEREST_DESCRIPTION = "description";
    public static final String PROFILE_INTEREST_IMAGE_URL = "imageURL";

    // Permitted to browse shop
    public static final String PROFILE_PERMISSION_SHOP = "shop";
    // Permitted to make purchase from shop
    public static final String PROFILE_PERMISSION_PURCHASE_BOOK = "purchaseBook";
    public static final String PROFILE_PERMISSION_PURCHASE_VIDEO = "purchaseVideo";
    public static final String PROFILE_PERMISSION_PURCHASE_NEWS = "purchaseNews";
    public static final String PROFILE_PERMISSION_PURCHASE_NOOKEXTRA = "purchaseNookExtra";
    public static final String PROFILE_PERMISSION_PURCHASE_MAGAZINE = "purchaseMagazine";
    // Permitted to rate
    public static final String PROFILE_PERMISSION_RATE="rate";
    // Permitted to recommend
    public static final String PROFILE_PERMISSION_RECOMMEND="recommend";
    // Permitted to write review
    public static final String PROFILE_PERMISSION_REVIEW="review";
    // Permitted to share
    public static final String PROFILE_PERMISSION_SHARE="share";
    // Permitted to RIS
    public static final String PROFILE_PERMISSION_READ_IN_STORE="readInStore";
    // Permitted to use browser
    public static final String PROFILE_PERMISSION_USE_WEBBROWSER="useWebBrowser";
    // Permitted to use lending functionality
    public static final String PROFILE_PERMISSION_USE_LENDING="useLending";
    // Permitted to use social
    public static final String PROFILE_PERMISSION_USE_SOCIAL="useSocial";
    // Permitted to view kids friendly content only – flag used by Search / Recommendation / Shop engine
    public static final String PROFILE_PERMISSION_VIEW_KIDS_CONTENT_ONLY="viewKidsContent";
    // Permitted to view Apps in Library
    public static final String PROFILE_PERMISSION_VIEW_NOOKEXTRA="viewNookExtra";
    // Permitted to view Books in Library
    public static final String PROFILE_PERMISSION_VIEW_BOOK="viewBook";
    // Permitted to view Magazines in Library
    public static final String PROFILE_PERMISSION_VIEW_MAGAZINE="viewMagazine";
    // Permitted to view Videos in Library
    public static final String PROFILE_PERMISSION_VIEW_VIDEO="viewVideo";
    // Permitted to view NP in Library
    public static final String PROFILE_PERMISSION_VIEW_NEWSPAPER="viewNewsPaper";
    // Permitted to view Catalogs in Library
    public static final String PROFILE_PERMISSION_VIEW_CATALOG="viewCatalog";
    // Permitted to view side loaded items in Library
    public static final String PROFILE_PERMISSION_VIEW_SIDELOADED="viewSideLoaded";
    // Permitted to view scrapbook items in Library
    public static final String PROFILE_PERMISSION_VIEW_SCRAPBOOK="viewScrapBook";

    // Allow users to play videos
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_G="g";
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_PG="pg";
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_PG13="pg13";
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_R="r";
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_NR="nr";
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_TVY="tvy";
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_TVY7="tvy7";
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_TVG="tvg";
    public static final String PROFILE_PREFERENCE_ALLOWED_VIDEO_RATING_OTHER="other";

    // Require Passcode to be entered when switching to adult profile
    public static final String PROFILE_PREFERENCE_USE_PROFILE_PASSCODE="needPasscode";
    // Require Account password when purchasing
    public static final String PROFILE_PREFERENCE_PASSWORD_PROTECT_PURCHASE="needPasswordToPurchase";

    //Account Profile Preference
    public static final String PROFILE_PREFERENCE_TYPE_SETTINGS = "settings";
    public static final String PROFILE_PREFERENCE_KEY_AVATAR = "avatar";
    public static final String PROFILE_PREFERENCE_TYPE_ENTITLEMENT = "entitlement";
    public static final String PROFILE_PREFERENCE_KEY_BOOK = "ebook";
    public static final String PROFILE_PREFERENCE_KEY_VIDEO = "media";
    public static final String PROFILE_PREFERENCE_KEY_NEWS = "enews";
    public static final String PROFILE_PREFERENCE_KEY_NOOKEXTRA = "extra";
    public static final String PROFILE_PREFERENCE_KEY_MAGAZINE = "emagazine";

    public static final int PROFILE_PREFERENCE_ENTITLED_NONE = 0;
    public static final int PROFILE_PREFERENCE_ENTITLED_ALL_CONTENT_ = 1;
    public static final int PROFILE_PREFERENCE_ENTITLED_KIDS_CONTENT_ONLY = 2;

    // Automatically save downloaded video to external sd card
    public static final String PROFILE_PREFERENCE_SAVE_VIDEO_ON_EXTERNAL_SDCARD="saveVideo";

    //*****************************END**************************************//

    //Device Registration constants
    public static final String EXTRAINFO_TYPE_RETAILER = "RETAILER";
    public static final String EXTRAINFO_TYPE_BOOKMARK="BOOKMARK";
    public static final String EXTRAINFO_TYPE_SUPPORT="SUPPORT";
    public static final String EXTRAINFO_TYPE_HOMEPAGE="HOMEPAGE";
    public static final String EXTRAINFO_TYPE_PARTNER="PARTNER";

    public static final String SOURCE_ID = "SOURCE_ID";
    public static final String EXTRAINFO_KEY_RETAILERID ="RETAILERID";
    public static final String EXTRAINFO_KEY_COUNTRYID="COUNTRYID";
    public static final String EXTRAINFO_KEY_TELEPHONE = "TEL";
    public static final String EXTRAINFO_KEY_URL = "URL";

    public static final String EXTRAINFO_KEY_WIFIURL = "WIFIURL";
    public static final String EXTRAINFO_KEY_POTTERMOREURL = "PotterMore";

    public static final String EXTRAINFO_KEY_MYNOOKURL = "MyNook";
    public static final String EXTRAINFO_KEY_SUPPORTEMAIL = "EMAIL";
    public static final String EXTRAINFO_KEY_SUPPORTURL = "URL";
    public static final String EXTRAINFO_KEY_SUPPORTTELEPHONE = "TEL";
    public static final String EXTRAINFO_KEY_UPDATEURL = "UPDATE_URL";
    public static final String EXTRAINFO_KEY_ROOTURL = "ROOTURL";

    //Partner constants
    public static final String EXTRAINFO_KEY_UVURL = "UVLINK";

    public static final String PARTNER_UV_OAUTHURL = "UVOAUTH_URL";
    public static final String EXTRAINFO_DEVICELIMIT = "DEVICE_LIMIT";

    //Bookmarks
    public static final String EXTRAINFO_BKMRK_YOUTUBE = "YouTube";
    public static final String EXTRAINFO_BKMRK_FACEBOOK = "Facebook";
    public static final String EXTRAINFO_BKMRK_TWITTER = "Twitter";
    public static final String EXTRAINFO_BKMRK_GETSTARTED = "Getting Started";
    public static final String EXTRAINFO_BKMRK_BLOG = "Nook Blog";
    public static final String EXTRAINFO_BKMRK_MAPP = "Mobile Apps";

    public static final int PRODUCT_CODE_PARTNER_APK=101;
    public static final int PRODUCT_CODE_SYSTEM_SOFTWARE=100;
    public static final int PRODUCT_CODE_PARTNER_APK_UPDATE_ONLY=102;
    public static final int PRODUCT_CODE_NOOKEXTRAS=200;

    public static final String PRODUCTTYPE_CODE_RU = "RU";

    //License & DRM constants
    public static final int READER_RMSDK = 1;
    public static final int READER_DRP = 2;
    public static final int READER_NOOK = 3;

    // See DrmVersion enum. 0 = None, 1 = Fiction Wise PDB which is unused
    public static final int ADOBE_PASS_HASH_LICENSE = 2;
    public static final int ADOBE_HARD_DRM_LICENSE = 3;
    public static final int BN_LICENSE = 4;

    //replaces enum EncryptionType { INVALID=0; CCHASH=1; USER_SYM_KEY=2; USER_PRIVATE_KEY=3; DEVICE_KEY=4;}
    public static final int ENCTYPE_INVALID=0;
    public static final int ENCTYPE_CCHASH=1;
    public static final int ENCTYPE_USER_SYM_KEY=2;
    public static final int ENCTYPE_USER_PRIVATE_KEY =3;
    public static final int ENCTYPE_DEVICE_KEY=4;

    public static final String MICROSOFT_PARTNER_CODE = "MSFT";
    public static final String MICROSOFT_PARTNER_CODE_WEB = "MSFTW";

    public enum ListParamName{
        DREF("DREF"), OFFSET("OFFSET"), SIZE("SIZE"), EAN("EAN"), PID("PID"), STORE("STORE"), REQUEST_TYPE("REQUST_TYPE"),
        CUSTOMERID("CUSTOMERID"), SORT("SORT"), LISTID("LIST"), WORKID("WORKID"), LOCALE("LOCALE"), MONTHSAGO("MONTHSAGO");
        private String name;
        ListParamName(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }
    }
    public enum Endpoint{
        OTHERS(0, null, null),
        SEARCH(1, GPBConstants.PRODUCTSEARCH_COMMAND, GPBConstants.PRODUCTSEARCH_VERSION_V2),
        CDS(2, GPBConstants.CURATEDLIST_COMMAND, GPBConstants.CURATEDLIST_VERSION_V2),
        PNR(3, GPBConstants.GET_CUSTOMER_PRODUCT_RECOMMENDATIONS_FOR_MULTIPLE_EANS,GPBConstants.GET_CUSTOMER_PRODUCT_RECOMMENDATIONS_FOR_MULTIPLE_EANS_V1),
        CATEGORIES(4, GPBConstants.PRODUCTCATEGORY_COMMAND,GPBConstants.PRODUCTCATEGORY_VERSION_V1),
        INSTANT_COLLECTIONS(5, null, null),
        INSTANT_COLLECTION_DETAILS(6, GPBConstants.INSTANT_COLLECTION_DETAILS, GPBConstants.INSTANT_COLLECTION_DETAILS_V1),
        PDS(7, GPBConstants.PRODUCTDETAILS_COMMAND, GPBConstants.PRODUCTDETAILS_VERSION_V2),
        CDS_LIST(8, GPBConstants.CURATEDLIST_COMMAND, GPBConstants.CURATEDLIST_VERSION_V2),
        CDS_PRODUCTS(9, GPBConstants.CURATEDLISTDETAILS_COMMAND, GPBConstants.CURATEDLISTDETAILS_VERSION_V2);
        private int id;
        private String command;
        private String version;

        Endpoint(int id, String command, String version){
            this.id = id;
            this.setCommand(command);
            this.setVersion(version);
        }
        public int getId(){
            return id;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
    public enum DisplayType{
        INSTORE(1,"INSTORE"),
        SHOP_PROMO_STATIC(2,"SHOP_PROMO_STATIC");
        private int displayType;
        private String strDisplayType;
        DisplayType(int displayType, String strDisplayType){
            this.displayType = displayType;
            this.strDisplayType = strDisplayType;
        }
        public static DisplayType getDisplayType(String displayType){
            if (INSTORE.strDisplayType.equalsIgnoreCase(displayType)){
                return INSTORE;
            }else{
                return SHOP_PROMO_STATIC;
            }
        }
    }
    public enum ListContext{
        SHOP(1),
        HOME_BOOKS(2),
        HOME_MOVIES(3),
        HOME_MUSIC(4),
        HOME_APPS(5),
        SHOP_BROWSE(6),
        HOME(7),
        SHOP_LISTS(8);
        private int context;
        ListContext(int context){
            this.context = context;
        }
        public int getNumber(){
            return context;
        }
    }

    public enum ContentType{
        PRODUCT(1),
        LIST(2),
        PRODUCTS(3),
        CATEGORIES(4);
        private int contentType;
        ContentType(int contentType){
            this.contentType = contentType;
        }
        public int getIntValue(){
            return contentType;
        }
    }
    public enum RenderSection{
        HIDDEN(0,"HIDDEN"),
        TOP(1,"TOP"),
        BOTTOM(2,"BOTTOM"),
        LEFT(3,"left"),
        RIGHT(4,"right"),
        TOP_LEFT(5,"top-left"),
        TOP_RIGHT(6,"top-right"),
        BOTTOM_LEFT(7,"bottom-left"),
        BOTTOM_RIGHT(8,"bottom-right");
        private int section;
        private String strSection;
        RenderSection(int section, String strSection){
            this.section = section;
            this.strSection = strSection;
        }
        public int getIntValue(){
            return section;
        }
        public String getStringvalue(){
            return strSection;
        }
        public static RenderSection getEnum(int section){
            for (RenderSection rSection: RenderSection.values()){
                if (rSection.getIntValue()== section) return rSection;
            }
            return HIDDEN;
        }
    }
    public enum ReviewSortOrder{
        MOST_HELPFUL(1),
        HIGHEST_TO_LOWEST(2),
        MOST_RECENT(3),
        LOWEST_TO_HIGHEST(4);
        private int sortOrder = 1;
        ReviewSortOrder(int sortOrder){
            this.sortOrder = sortOrder;
        }
        public int getValue(){
            return sortOrder;
        }
    }
    public enum ReviewFlag{
        HELPFUL(1),
        NOT_HELPFUL(0);
        private int flag;
        ReviewFlag(int flag){
            this.flag = flag;
        }
        public int getIntValue(){
            return flag;
        }
        public static ReviewFlag valueOf(int value){
            return value==1?HELPFUL:value==0?NOT_HELPFUL:null;
        }

    }
    public enum ReviewCommentValue{
        PLOT_SPOILER(9),
        INAPPROPRIATE(11),
        OFF_TOPIC(12),
        SPAM(13),
        UNDERAGE(14),
        VIOLATE_TERMS(17);
        private int value=13;
        ReviewCommentValue(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }
    }

    public enum ProfileType{
        ADULT(0),
        KIDS(1);
        private int profileType;
        ProfileType(int profileType){
            this.profileType = profileType;
        }
        public int getIntValue(){
            return this.profileType;
        }
    }
    public enum CuratedListIdentifier{
        //negative identifiers because these are **** SPECIAL ***
        SHOP_MOVIE_TV_APPS(-1, new ArrayList<Integer>(Arrays.asList(40291))),
        POPULAR_MOVIE_TV_APPS(-2,new ArrayList<Integer>(Arrays.asList(40293))),
        POPULAR_MUSIC_APPS(-3, new ArrayList<Integer>(Arrays.asList(40292))),
        TOP_FREE_NEWSSTAND_TRIALS(-4, new ArrayList<Integer>(Arrays.asList(38716))),
        ;
        private List<Integer> pids;
        private int id;
        CuratedListIdentifier(int id, ArrayList<Integer> pids){
            this.id = id;
            this.pids = pids;
        }
        public boolean containsPid(int pid){
            return pids.contains(pid);
        }
        public int getPid(){
            if (pids.isEmpty()) return 0;
            return pids.get(0);
        }
        public int getId(){
            return id;
        }
        public static int getPidForIdentifier(int identifier){
            if (identifier == SHOP_MOVIE_TV_APPS.getId()){
                return SHOP_MOVIE_TV_APPS.getPid();
            }else if (identifier == POPULAR_MOVIE_TV_APPS.getId()){
                return POPULAR_MOVIE_TV_APPS.getPid();
            }else if (identifier == POPULAR_MUSIC_APPS.getId()){
                return POPULAR_MOVIE_TV_APPS.getPid();
            }
            return 0;
        }

    }

    public static final int BROWSE_NOOK_STORE_TOP_CATEGORY_PID=39778;

    public static final int RESOLUTION_TYPE_SD = 1;
    public static final int RESOLUTION_TYPE_HD = 2;

    public static final int PURCHASE_TYPE_PURCHASE = 1;
    public static final int PURCHASE_TYPE_RENTAL = 2;

    public static final int PRODUCT_TYPE_EBOOK = 1;
    public static final int PRODUCT_TYPE_EMAGAZINE = 2;
    public static final int PRODUCT_TYPE_ENEWS = 3;
    public static final int PRODUCT_TYPE_EXTRA = 4;
    public static final int PRODUCT_TYPE_MOVIE = 5;
    public static final int PRODUCT_TYPE_TV = 6;
    public static final int PRODUCT_TYPE_RETAILER_CATALOG = 7;
    public static final int PRODUCT_TYPE_AUDIO = 8;
    public static final int PRODUCT_TYPE_NKCOMICS = 9;
    public static final int PRODUCT_TYPE_NULLPTYPE = -1;
    public static final int PRODUCT_TYPE_EBOOK_ONLY = 10;
    public static final int PRODUCT_TYPE_EBOOK_DA = 11;

    public static final String VIDEO_RIGHT_TYPE_PURCHASE = "Purchase";
    public static final String VIDEO_RIGHT_TYPE_RENTAL = "Rental";

    public enum RequestNotificationCategory{
        LIST_INTERACTION(0);
        private int value;
        RequestNotificationCategory(int value){
            this.value = value;
        }
        public int getIntValue(){
            return value;
        }
    }


    public static final int SYNCHEADER_OPTION_ONLY_UNARCHIVED_ITEMS  =0;
    public static final int SYNCHEADER_OPTION_ONLY_MAGAZINES  =1;

}
