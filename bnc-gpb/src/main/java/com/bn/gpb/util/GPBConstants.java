package com.bn.gpb.util;

public final class GPBConstants {
	
	public static final String ACCOUNTAUTH_COMMAND ="AccountAuth";
	public static final String ACCOUNTAUTH_VERSION_V1 ="1";
	
	public static final String ADDCREDITCARD_COMMAND ="AddCreditCard";
	public static final String ADDCREDITCARD_VERSION_V1 ="1";
	
	public static final String CCHASH_COMMAND ="CCHash";
	public static final String CCHASH_VERSION_V1 ="1";

	public static final String CHANGETIMEZONE_COMMAND ="ChangeTimeZone";
	public static final String CHANGETIMEZONE_VERSION_V1 ="1";
	
	public static final String CCMASTERDATA_COMMAND ="CCMasterData";
	public static final String CCMASTERDATA_VERSION_V1 ="1";
	
	public static final String CHECKFORSWUPDATE_COMMAND ="CheckForUpdate";
	public static final String CHECKFORSWUPDATE_VERSION_V1 ="1";
	
	public static final String CREATEACCOUNT_COMMAND ="CreateAccount";
	public static final String CREATEACCOUNT_VERSION_V1 ="1";
	
	public static final String DEVICEAUTH_COMMAND ="DeviceAuth";
	public static final String DEVICEAUTH_VERSION_V1 ="1";

	public static final String GETACCOUNTCCS_COMMAND ="GetAccountCCs";
	public static final String GETACCOUNTCCS_VERSION_V1 ="1";

	public static final String GETSECURITYQUESTIONS_COMMAND ="GetSecurityQuestion";
	public static final String GETSECURITYQUESTIONS_VERSION_V1 ="1";

    public static final String GETACCOUNTLESSURLSANDLICENSE_COMMAND ="GetAccountLessUrlsAndLicense";
    public static final String GETACCOUNTLESSURLSANDLICENSE_VERSION_V1 ="1";
	
	public static final String GETINSTOREURLSANDLICENSE_COMMAND ="GetInStoreUrlsAndLicense";
	public static final String GETINSTOREURLSANDLICENSE_VERSION_V1 ="1";
	
	public static final String GETURLSANDLICENSE_COMMAND ="GetUrlsAndLicense";
	public static final String GETURLSANDLICENSE_VERSION_V1 ="1";
	
	public static final String HEARTBEAT_COMMAND ="HeartBeat";
	public static final String HEARTBEAT_VERSION_V1 ="1";
	
	public static final String UPDATEPASSWORD_COMMAND = "UpdatePassword";
	public static final int    UPDATEPASSWORD_VERSION_V1 = 1;
	
	public static final String UPDATEEMAIL_COMMAND = "UpdateEmail";
	public static final int    UPDATEEMAIL_VERSION_V1 = 1;

	public static final String MYSECURITYQUESTION_COMMAND = "MySecurityQuestion";
	public static final int    MYSECURITYQUESTION_VERSION_V1 = 1;

	public static final String PASSWORDRESETEMAIL_COMMAND = "PasswordResetEmail";
	public static final int    PASSWORDRESETEMAIL_VERSION_V1 = 1;

	public static final String PASSWORDRESET_COMMAND = "PasswordReset";
	public static final int    PASSWORDRESET_VERSION_V1 = 1;

	public static final String EULAOPTIONS_COMMAND = "EulaOptions";
	public static final int    EULAOPTIONS_VERSION_V1 = 1;
	
	public static final String INSTORETOKEN_COMMAND = "InStoreToken";
	public static final String INSTORETOKEN_VERSION_V1 = "1";
	
	public static final String ISEMAILAVAILABLE_COMMAND ="IsEmailAvailable";
	public static final String ISEMAILAVAILABLE_VERSION_V1 ="1";
	
	public static final String LENDOFFER_COMMAND ="LendOffer";
	public static final String LENDOFFER_VERSION_V1 ="1";
	public static final int    LENDOFFER_VERSION_V2 =2;

	public static final String GETLENDOFFERID_COMMAND = "GetLendOfferId";
	public static final int    GETLENDOFFERID_VERSION_V1 =1;
	
	public static final String LENDOFFERACCEPTED_COMMAND ="LendOfferAccepted";
	public static final String LENDOFFERACCEPTED_VERSION_V1 ="1";
	public static final int    LENDOFFERACCEPTED_VERSION_V2 =2;
	
	public static final String LENDOFFERREJECTED_COMMAND ="LendOfferRejected";
	public static final String LENDOFFERREJECTED_VERSION_V1 ="1";
	public static final int    LENDOFFERREJECTED_VERSION_V2 =2;
	
	public static final String LENDOFFERRETURNED_COMMAND ="LendOfferReturned";
	public static final String LENDOFFERRETURNED_VERSION_V1 ="1";
	public static final int    LENDOFFERRETURNED_VERSION_V2 =2;
	
	public static final String MERGEPOD_COMMAND ="MerchantPod";
	public static final String MERGEPOD_VERSION_V1 ="1";
	
	public static final String PURCHASE_COMMAND ="Purchase";
	public static final String SIDELOADED_RP_COMMAND ="SideLoadedItemRPCommand";
	public static final String PURCHASE_VERSION_V1 ="1";
	public static final int    PURCHASE_VERSION_V2 =2;

	public static final String ADDITEMSTOLOCKER_COMMAND = "AddItemsToLocker";
	public static final int	   ADDITEMSTOLOCKER_VERSION_V1 = 1;
	public static final int	   ADDITEMSTOLOCKER_VERSION_V2 = 2;
	
	public static final String PURCHASECHECK_COMMAND ="PurchaseCheck";
	public static final int    PURCHASECHECK_VERSION_V1 = 1;

    public static final String CANCELSUBSCRIPTION_COMMAND = "CancelSubscription";
    public static final int    CANCELSUBSCRIPTION_VERSION_V1 = 1;
	
	public static final String REGISTERACCOUNTDEVCE_COMMAND ="RegisterAccountDevice";
	public static final String REGISTERACCOUNTDEVCE_VERSION_V1 ="1";
	
	public static final String PRIVACY_COMMAND ="SetPrivacy";
	public static final String PRIVACY_VERSION_V1 ="1";
	
	public static final String UNREGISTERACCOUNTDEVCE_COMMAND ="UnRegisterAccountDevice";
	public static final String UNREGISTERACCOUNTDEVCE_VERSION_V1 ="1";
	
	public static final String NOTIFYDOWNLOADFAILURE_COMMAND ="NotifyDownloadFailure";
	public static final String NOTIFYDOWNLOADFAILURE_VERSION_V1 ="1";
	
	public static final String VERIFYACCOUNT_COMMAND ="VerifyAccount";
	public static final String VERIFYACCOUNT_VERSION_V1 ="1";
	
	public static final String SWUPDATESTATUS_COMMAND ="UpdateStatus";
	public static final String SWUPDATESTATUS_VERSION_V1 ="1";
	
	public static final String TEXTBOOKRENTAL_COMMAND = "TextBookRental";
	public static final int	   TEXTBOOKRENTAL_VERSION_V1 = 1;

	public static final String GETESSENTIALS_COMMAND = "GetEssentials";
	public static final int	   GETESSENTIALS_VERSION_V1 = 1;

	public static final String PAYMENTURL_COMMAND = "PaymentUrl";
	public static final int	   PAYMENTURL_VERSION_V1 = 1;

	public static final String GETCONFIG_COMMAND = "GetConfig";
	public static final int	   GETCONFIG_VERSION_V1 = 1;

    public static final String ISCOPPACOMPLIANT_COMMAND = "IsCOPPACompliant";
    public static final int    ISCOPPACOMPLIANT_VERSION_V1 = 1;

	public static final String GETSHORTLIVEDTOKEN_COMMAND = "GetShortLivedToken";
	public static final int    GETSHORTLIVEDTOKEN_VERSION_V1 = 1;

	//Partner integration
	public static final String GETPARTNERTOKEN_COMMAND = "GetPartnerTokens";
	public static final int    GETPARTNERTOKEN_V1 = 1;

	public static final String CHANGECOR_COMMAND = "ChangeCOR";
	public static final int    CHANGECOR_V1 = 1;

	public static final String GETPARTNERAUTH_COMMAND = "GetPartnerAuth";
	public static final int    GETPARTNERAUTH_V1 = 1;

	public static final String AUTHORIZECLIENT_COMMAND = "AuthorizeClient";
	public static final int    AUTHORIZECLIENT_V1 = 1;

	public static final String DEAUTHORIZECLIENT_COMMAND = "DeAuthorizeClient";
	public static final int    DEAUTHORIZECLIENT_V1 = 1;

	public static final String GETAUTHORIZECLIENTS_COMMAND = "GetAuthorizeClients";
	public static final int    GETAUTHORIZECLIENTS_V1 = 1;

	public static final String SENDLINKDETAIL_COMMAND = "SendLinkDetail";
	public static final int    SENDLINKDETAIL_V1 = 1;

	public static final String ISUVLINKED_COMMAND = "IsUVLinked";
	public static final int    ISUVLINKED_V1 = 1;

	public static final String GETPARTNERFORM_COMMAND = "GetPartnerForm";
	public static final int    GETPARTNERFORM_V1 = 1;

	//Account Profile
	public static final String CREATEACCOUNTPROFILE_COMMAND = "CreateAccountProfile";
	public static final int	   CREATEACCOUNTPROFILE_VERSION_V1 = 1;

	public static final String GETINTERESTS_COMMAND = "GetInterests";
	public static final int	   GETINTERESTS_VERSION_V1 = 1;

	public static final String GETPRODUCTSFORINTERESTS_COMMAND = "GetProductsForInterests";
	public static final int	   GETPRODUCTSFORINTERESTS_VERSION_V1 = 1;
	
	
	//Non B&N registration
	public static final String ENDPOINT_GETPASSPHRASE = "EndpointGetPassPhrase";
	public static final String ENDPOINT_REGISTER = "EndpointRegister";
	public static final String ENDPOINT_CREATEUSER = "EndpointCreateAccount";
	public static final String ENDPOINT_REGISTERUSER = "EndpointRegisterAccount";
    public static final String SOCIAL_LOGIN = "SocialLogin";
	
	public static final String ADDNOTIFICATIONS_COMMAND ="addNotifications";
	public static final String ADDNOTIFICATIONS_VERSION_V1 ="1";
	
	public static final String SYNC_COMMAND ="sync";
    public static final int SYNC_VERSION_V1 =1;
    public static final int SYNC_VERSION_V2 =2;
	public static final int SYNC_VERSION_V3 =3;
	public static final int SYNC_VERSION_V4 =4;
    public static final int SYNC_VERSION_V5 =5;
	
	public static final String CLEARNOTIFICATIONS_COMMAND ="clearNotifications";
	public static final String CLEARNOTIFICATIONS_VERSION_V1 ="1";
	
	public static final String RECOMMENDTOPUBLIC_COMMAND ="RecommendToPublic";
	public static final String RECOMMENDTOPUBLIC_VERSION_V1 ="1";
	
	public static final String SHAREQUOTETOPUBLIC_COMMAND ="ShareQuoteToPublic";
	public static final String SHAREQUOTETOPUBLIC_VERSION_V1 ="1";
	
	public static final String ADDLISTITEM_COMMAND ="AddListItem";
	public static final String ADDLISTITEM_VERSION_V1 ="1";
	
	public static final String CREATELIST_COMMAND ="CreateList";
	public static final String CREATELIST_VERSION_V1 ="1";
	
	public static final String UPDATELIST_COMMAND ="UpdateList";
	public static final String UPDATELIST_VERSION_V1 ="1";
	
	public static final String DELETELIST_COMMAND ="DeleteList";
	public static final String DELETELIST_VERSION_V1 ="1";
	
	public static final String GETUSERLISTS_COMMAND ="GetUserLists";
	public static final String GETUSERLISTS_VERSION_V1 ="1";
	
	public static final String GETLISTITEMS_COMMAND ="GetListItems";
	public static final String GETLISTITEMS_VERSION_V1 ="1";
	
	public static final String UPDATELISTITEMS_COMMAND ="UpdateListItems";
	public static final String UPDATELISTITEMS_VERSION_V1 ="1";
	
	public static final String DELETELISTITEMS_COMMAND ="DeleteListItems";
	public static final String DELETELISTITEMS_VERSION_V1 ="1";
	
	public static final String RECOMMENDTOFRIEND_COMMAND ="RecommendToFriend";
	public static final String RECOMMENDTOFRIEND_VERSION_V1 ="1";
	
	public static final String WRITEREVIEW_COMMAND ="WriteReview";
	public static final String WRITEREVIEW_VERSION_V1 ="1";
	public static final String WRITEREVIEW_VERSION_V2 ="2";
	
	public static final String RATEBOOK_COMMAND ="RateBook";
	public static final String RATEBOOK_VERSION_V1 ="1";
	
	public static final String VOTEFORREVIEW_COMMAND ="VoteForReview";
	public static final String VOTEFORREVIEW_VERSION_V1 ="1";

	public static final String GET_IDS_FROM_EMAILS_COMMAND ="GetBnIdsFromEmails";
	public static final String GET_IDS_FROM_EMAILS_VERSION_V1 ="1";

    public static final String IS_DEVICE_REGISTERED_COMMAND="IsDeviceRegistered";
    public static final String IS_DEVICE_REGISTERED_VERSION_V1 ="1";

    public static final String GET_AUTH_COMMAND="GetAuth";
    public static final String GET_AUTH_VERSION_V1="1";

    public static final String GET_PRODUCTS_FOR_DEVICE_COMMAND="GetProductsForDevice";
    public static final String GET_PRODUCTS_FOR_DEVICE_VERSION_V1="1";

    public static final String FEDERATED_LOGIN_COMMAND="FederatedLogin";
    public static final String FEDERATED_LOGIN_V1="1";

    public static final String FEDERATED_LINK_ACCOUNT="FederatedLinkAccount";
    public static final String FEDERATED_LINK_ACCOUNT_V1="1";

    public static final String FEDERATED_MERGE_ACCOUNT="FederatedMergeAccount";
    public static final String FEDERATED_MERGE_ACCOUNT_V1="1";

    public static final String FEDERATED_UN_LINK_ACCOUNT="FederatedUnLinkAccount";
    public static final String FEDERATED_UN_LINK_ACCOUNT_V1="1";

	//NOOKSTUDY COMMANDS
	public static final String GETUTCTIME_COMMAND = "GetUTCTime";
	public static final int    GETUTCTIME_VERSION_V1 = 1;
	
	public static final String GETSCHOOLS_COMMAND ="GetSchools";
	public static final String GETSCHOOLS_VERSION_V1 ="1";
	
	public static final String SETSCHOOL_COMMAND = "SetSchool";
	public static final int    SETSCHOOL_VERSION_V1 = 1;
	
	public static final String REDEEMACCESSCODE_COMMAND = "RedeemAccessCode";
	public static final int	   REDEEMACCESSCODE_VERSION_V1 = 1;
	
	public static final String GETNOOKSTUDYACCOUNT_COMMAND = "GetNookStudyAccount";
	public static final int	   GETNOOKSTUDYACCOUNT_VERSION_V1 = 1;
	
	public static final String VERIFYNOOKSTUDYACCOUNT_COMMAND = "VerifyNookStudyAccount";
	public static final int	   VERIFYNOOKSTUDYACCOUNT_VERSION_V1 = 1;
	
	public static final String SETNOOKSTUDYACCOUNT_COMMAND = "SetNookStudyAccount";
	public static final int	   SETNOOKSTUDYACCOUNT_VERSION_V1 = 1;
	
	public static final String CREATENOOKSTUDYACCOUNT_COMMAND = "CreateNookStudyAccount";
	public static final int	   CREATENOOKSTUDYACCOUNT_VERSION_V1 = 1;
	
	public static final String REGISTERADOBEDEVICE_COMMAND = "RegisterAdobeDevice";
	public static final int	   REGISTERADOBEDEVICE_VERSION_V1 = 1;
	
	public static final String ADDTEXTBOOKFREETRIAL_COMMAND = "AddTextBookFreeTrial";
	public static final int	   ADDTEXTBOOKFREETRIALVERSION_V1 = 1;
	

	/* PRODUCT CATALOG COMMANDS */
	public static final String QUICKSEARCH_COMMAND ="QuickSearch";
	public static final String QUICKSEARCH_COMMAND_V1 ="1";
	
	public static final String PRODUCTSEARCH_COMMAND ="ProductSearch";
	public static final String PRODUCTSEARCH_VERSION_V1 ="1";
	public static final String PRODUCTSEARCH_VERSION_V2 ="2";
    public static final String PRODUCTSEARCH_VERSION_V3 ="3";
    public static final String PRODUCTSEARCH_VERSION_V4 ="4";

	public static final String PRODUCTCATEGORY_COMMAND ="ProductCategory";
	public static final String PRODUCTCATEGORY_VERSION_V1 ="1";
    public static final String PRODUCTCATEGORY_VERSION_V2 ="2";

    public static final String PRODUCTDETAILS_COMMAND ="ProductDetails";
	public static final String PRODUCTDETAILS_VERSION_V1 ="1";
	public static final String PRODUCTDETAILS_VERSION_V2 ="2";
	
	public static final String PRODUCTSYNOPSIS_COMMAND ="ProductSynopsis";
	public static final String PRODUCTSYNOPSIS_VERSION_V1 ="1";
	
	public static final String PRODUCTDETAILSLIST_COMMAND ="ProductDetailsList";
	public static final String PRODUCTDETAILSLIST_VERSION_V1 ="1";
	public static final String PRODUCTDETAILSLIST_VERSION_V2 ="2";
	
	public static final String CUSTOMERREVIEWS_COMMAND ="CustomerReviews";
	public static final String CUSTOMERREVIEWS_VERSION_V1 ="1";
	public static final String CUSTOMERREVIEWS_VERSION_V2 ="2";
    public static final String CUSTOMERREVIEWS_VERSION_V3 ="3";

	public static final String CURATEDLIST_COMMAND ="CuratedList";
	public static final String CURATEDLIST_VERSION_V1 ="1";
	public static final String CURATEDLIST_VERSION_V2 ="2";
	
	public static final String CURATEDLISTDETAILS_COMMAND ="CuratedListDetails";
	public static final String CURATEDLISTDETAILS_VERSION_V1 ="1";
	public static final String CURATEDLISTDETAILS_VERSION_V2 ="2";
	public static final String CURATEDLISTDETAILS_VERSION_V3 ="3";
	
	public static final String EDITORIALREVIEWS_COMMAND ="EditorialReviews";
	public static final String EDITORIALREVIEWS_VERSION_V1 ="1";
    public static final String EDITORIALREVIEWS_VERSION_V2 ="2";

	public static final String STOREEVENTS_COMMAND ="StoreEvents";
	public static final String STOREEVENTS_VERSION_V1 ="1";
	
	public static final String STORELOCATIONS_COMMAND ="StoreLocations";
	public static final String STORELOCATIONS_VERSION_V1 ="1";
	
	public static final String GETALLSHOPMERCHANDISING_COMMAND ="GetAllShopMerchandising";
	public static final String GETALLSHOPMERCHANDISING_VERSION_V1 ="1";
	
	public static final String GETPRODUCTRECOMMENDATIONS_COMMAND ="GetProductRecommendations";
	public static final String GETPRODUCTRECOMMENDATIONS_VERSION_V1 ="1";
    public static final String GETPRODUCTRECOMMENDATIONS_VERSION_V2 ="2";

	public static final String GETCUSTOMERPRODUCTRECOMMENDATIONS_COMMAND ="GetCustomerProductRecommendations";
	public static final String GETCUSTOMERPRODUCTRECOMMENDATIONS_VERSION_V1 ="1";
    public static final String GETCUSTOMERPRODUCTRECOMMENDATIONS_VERSION_V2 ="2";

    public static final String GETSHORTPRODUCTURL_COMMAND ="GetShortProductUrl";
	public static final String GETSHORT_VERSION_V1 ="1";
	
	public static final String SHAREREADINGTATUS_COMMAND ="ShareReadingStatus";
	public static final String SHAREREADINGTATUS_VERSION_V1 ="1";
	
	public static final String DISCOVERLENDABLEBOOKS_BY_BOOK_COMMAND ="DiscoverLendableBooksByBook";
	public static final String DISCOVERLENDABLEBOOKS_BY_BOOK_VERSION_V1 ="1";
	public static final String DISCOVERLENDABLEBOOKS_BY_BOOK_VERSION_V2 ="2";
	
	public static final String DISCOVERLENDABLEBOOKS_BY_CONTACT_COMMAND ="DiscoverLendableBooksByContact";
	public static final String DISCOVERLENDABLEBOOKS_BY_CONTACT_VERSION_V1 ="1";
	public static final String DISCOVERLENDABLEBOOKS_BY_CONTACT_VERSION_V2 ="2";
	
	public static final String GET_DISCOVERABLE_BOOKS_COMMAND ="GetDiscoverableBooks";
	public static final String GET_DISCOVERABLE_BOOKS_VERSION_V1 ="1";
	
	public static final String UPDATE_DISOCVERABLE_BOOK_STATUS_COMMAND ="UpdateDiscoverableBookStatus";
	public static final String UPDATE_DISOCVERABLE_BOOK_STATUS_VERSION_V1 ="1";
	
	public static final String SEND_LEND_REQUEST_TO_CONTACT_COMMAND ="SendLendRequestToContact";
	public static final String SEND_LEND_REQUEST_TO_CONTACT__VERSION_V1 ="1";
	
	public static final String SHAREQUOTETOFRIEND_COMMAND ="ShareQuoteToFriend";
	public static final String SHAREQUOTETOFRIEND_VERSION_V1 ="1";
	
	public static final String FACEBOOK_LEND_OFFER_COMMAND ="FacebookLendOffer";
	public static final String FACEBOOK_LEND_OFFER_VERSION_V1 ="1";
	public static final String SET_DISCOVER_PRIVACY_OPTION_COMMAND ="SetDiscoverPrivacyOption";
	public static final String SET_DISCOVER_PRIVACY_OPTION_VERSION_V1 ="1";
	
	public static final String PUBLIC_LEND_OFFER_COMMAND ="PublicLendOffer";
	public static final String PUBLIC_LEND_OFFER_VERSION_V1 ="1";
	
	public static final String LEND_REQUEST_ANSWER ="LendRequestAnswer";
	public static final String LEND_REQUEST_ANSWER_VERSION_V1 ="1";
	
	public static final String FACEBOOK_LIKE ="FacebookLike";
	public static final String FACEBOOK_LIKE_VERSION_V1 ="1";
	
	public static final String DISCOVER_BOOKS_BY_FRIEND ="DiscoverBooksByFriend";
	public static final String DISCOVER_BOOKS_BY_FRIEND_VERSION_V1 ="1";
	
	public static final String REPORT_REVIEW_COMMAND = "ReportReview";
	public static final int REPORT_REVIEW_VERSION_V1 = 1;
	
	public static final String FLAG_REVIEW_COMMAND = "FlagReview";
	public static final int FLAG_REVIEW_VERSION_V1 = 1;
	
	public static final String CHECKFOREXTRASUPDATE= "CheckForExtrasUpdate";
	public static final int CHECKFOREXTRASUPDATE_VERSION_V1= 1;
	
	public static final String CURRENTREADPOSITION = "CurrentReadPosition";
	public static final int CURRENTREADPOSITION_VERSION_V1= 1;
	
	public static final String SETCURRENTREADPOSITION = "SetCurrentReadPosition";
	public static final int SETCURRENTREADPOSITION_VERSION_V1= 1;
	
	public static final String GET_FACEBOOK_LIKES ="GetFacebookLikes";
	public static final String GET_FACEBOOK_LIKES_VERSION_V1 ="1";
	
	public static final String GET_SOCIAL_RECOMMENDATIONS ="GetSocialRecommendations";
	public static final String GET_SOCIAL_RECOMMENDATIONS_VERSION_V1 ="1";
	
	public static final String GET_SOCIAL_USER_ACTIVITY ="GetSocialUserActivty";
	public static final String GET_SOCIAL_USER_ACTIVITY_VERSION_V1 ="1";
	
	public static final String IS_DEVICE_COMPATIBLE_FOR_EAN="IsDeviceCompatibleForEan";
	public static final String GET_DEVICE_COMPATIBLE_FOR_EAN_VERSION_V1 = "1";
	
	public static final String GET_BNIDS_FROM_EMAILS="GetBnIdsFromEmails";
	public static final int GET_BNIDS_FROM_EMAILS_VERSION_V1 = 1;
	
	public static final String UPDATE_CREDITCARD_COMMAND ="UpdateCreditCard";
	public static final String UPDATE_CREDITCARD_VERSION_V1 ="1";

	public static final String ADD_GIFTCARD_COMMAND ="AddGiftCard";
	public static final String ADD_GIFTCARD_VERSION_V1 ="1";
	
	public static final String GET_GIFTCARD_COMMAND ="GetGiftCard";
	public static final String GET_GIFTCARD_VERSION_V1 ="1";

	public static final String CREATE_BN_TINY_URL ="CreateBNTinyUrl";
	public static final String CREATE_BN_TINY_URL_V1 ="1";
	
	public static final String OAUTH_UNLINK_ACCOUNT = "UnlinkAccount";
	public static final int OAUTH_UNLINK_ACCOUNT_VERSION_V1 = 1;	

	public static final String GET_INSTANT_WATCH = "GetInstantWatch";
	public static final int GET_INSTANT_WATCH_VERSION_V1 = 1;	
	
	public static final String GET_MOVIES_FOR_MORE = "GetMoviesForMore";
	public static final int GET_MOVIES_FOR_MORE_VERSION_V1 = 1;	
	
	public static final String GET_SOCIAL_SETTINGS = "GetSocialSettings";
	public static final int GET_SOCIAL_SETTINGS_VERSION_V1 = 1;
	
	public static final String SET_SOCIAL_SETTINGS = "SetSocialSettings";
	public static final int SET_SOCIAL_SETTINGS_VERSION_V1 = 1;
	
	public static final String GET_BIILING_ADDRESS = "GetBillingAddress";
	public static final int GET_BIILING_ADDRESS_VERSION_V1 = 1;
	
	public static final String UPDATE_BIILING_ADDRESS = "UpdateBillingAddress";
	public static final int UPDATE_BIILING_ADDRESS_VERSION_V1 = 1;
	
	public static final String GET_FACEBOOK_LIKE_COUNTS = "GetFacebookLikeCounts";
	public static final int GET_FACEBOOK_LIKE_COUNTS_V1 = 1;

	public static final String SYNC_UPGRADE = "syncUpgrade";
	public static final int SYNC_UPGRADE_VERSION_V1 = 1;

	public static final String GET_BROWSE_SHOP_TOP_CATEGORIES = "BrowseShopTopCategory";
	public static final int GET_BROWSE_SHOP_TOP_CATEGORIES_V1 = 1;
	
	public static final String GET_SOCIAL_FEED = "GetSocialFeed";
	public static final int GET_SOCIAL_FEED_V1 = 1;

	public static final String GET_DEVICE_PROVISIONING = "GetDeviceProvisioning";
	public static final int GET_DEVICE_PROVISIONING_V1 = 1;

    public static final String DEVICE_PROVISIONING_ACK = "DeviceProvisioningAck";
    public static final int DEVICE_PROVISIONING_ACK_V1 = 1;

	public static final String RETAILER_DETAILS = "RetailerDetails";
	public static final int RETAILER_DETAILS_V1 = 1;

    public static final String GET_LIST = "GetList";
    public static final String GET_LIST_V1="1";

    public static final String GET_LIST_DETAILS = "GetListDetails";
    public static final String GET_LIST_DETAILS_V1="1";

    public static final String GET_CUSTOMER_PRODUCT_RECOMMENDATIONS_FOR_MULTIPLE_EANS="GetCustomerProductRecommendationsForMultipleEans";
    public static final String GET_CUSTOMER_PRODUCT_RECOMMENDATIONS_FOR_MULTIPLE_EANS_V1="1";

    public static final String INSTANT_COLLECTIONS = "GetInstantCollections";
    public static final String INSTANT_COLLECTIONsS_V1 = "1";

    public static final String INSTANT_COLLECTION_DETAILS = "InstantCollectionDetails";
    public static final String INSTANT_COLLECTION_DETAILS_V1 = "1";


    public static final String GET_HDCP_LICENSE = "GetHdcpLicense";
    public static final int GET_HDCP_LICENSE_V1 = 1;

    public static final String GET_COUNTRY_OF_RESIDENCE_LIST = "CountryOfResidence";
    public static final String GET_COUNTRY_OF_RESIDENCE_LIST_V1 = "1";

    public static final String GET_ASSEMBLED_HTML = "Assemble";
    public static final String GET_ASSEMBLED_HTML_V1 = "1";

    public static final String GET_RELATED_PRODUCTS = "GetRelatedProducts";
    public static final int GET_RELATED_PRODUCTS_V1 = 1;
    public static final int GET_RELATED_PRODUCTS_V2 = 2;

    public static final String LOGIN_USING_LIVE_ID = "LiveIdLoginAccount";
    public static final int LOGIN_USING_LIVE_ID_V1 = 1;

    public static final String LINK_ACCOUNTS = "LinkAccounts";
    public static final int LINK_ACCOUNTS_V1 = 1;

    public static final String UNLINK_ACCOUNTS = "UnLinkAccounts";
    public static final int UNLINK_ACCOUNTS_V1 = 1;

    public static final String GET_LINK_ACCOUNTS = "GetLinkAccounts";
    public static final int GET_LINK_ACCOUNTS_V1 = 1;

    public static final String DEVICE_PREREGISTRATION="DevicePreregistration";
    public static final int DEVICE_PREREGISTRATION_V1 = 1;

    public static final String DOES_LIVE_ID_ACCOUNT_EXIST = "DoesLiveIdAccountExist";
    public static final int DOES_LIVE_ID_ACCOUNT_EXIST_CURRENT_VERSION = 1;

    /**
     *    GetPasswordInfo command entry for testing purpose
     * */
    public static final String PASSWORD_INFO ="GetPasswordInfo";

    public static final String GETPASSWORDINFO_COMMAND = "GetPasswordInfo";
    public static final int GETPASSWORDINFO_VERSION_V1 = 1;

    public static final String DEVICE_LOCATION = "DeviceLocation";
    public static final int DEVICE_LOCATION_VERSION_V1 = 1;
    public static final String SOCIALLOGIN_COMMAND = "SocialLogin";
    public static final int SOCIALLOGIN_VERSION_V1 = 1;

    public static final String UPDATEPAGECOUNT_COMMAND = "UpdatePageCount";
    public static final int UPDATEPAGECOUNT_VERSION_V1 = 1;

    public static final String REPORTING_EVENT_COMMAND = "ReportingEventCommand";
    public static final int REPORTING_EVENT_COMMAND_VERSION_V1 = 1;


    public static final String GET_SESSION_KEY = "GetSessionKey";
    public static final int GET_SESSION_KEY_V1 = 1;

    public static final String GET_CREDIT_SUBSCRIPTION = "GetCreditSubscriptionCommand";
    public static final int GET_CREDIT_SUBSCRIPTION_V1 = 1;
    public static final String PURCHASE_CREDIT_SUBSCRIPTION = "PurchaseCreditSubscription";
    public static final int PURCHASE_CREDIT_SUBSCRIPTION_V1 = 1;
    public static final String AUDIO_BOOK_LICENSE_COMMAND = "AudiobookLicenseCommand";
    public static final int AUDIO_BOOK_LICENSE_COMMAND_V1 = 1;
    public static final String CREDIT_SUBSCRIPTION_OPTIONS_COMMAND = "CreditSubscriptionOptionsCommand";
	public static final String CANCEL_CREDITS_REASONS_COMMAND = "CancelCreditsReasonsCommand";
    public static final int CREDIT_SUBSCRIPTION_OPTIONS_COMMAND_V1 = 1;
    public static final String CANCEL_CREDIT_PURCHASE_SUBSCRIPTION = "CancelPurchaseCreditSubscription";
    public static final int CANCEL_CREDIT_PURCHASE_SUBSCRIPTION_V1 = 1;
    public static final String AUDIO_LICENSE_COMMAND = "AudiobookLicenseCommand";
    public static final int AUDIO_LICENSE_COMMAND_V1 = 1;
    public static final String BAR_CODE_EAN_SEARCH = "BarcodeEanSearch";
    public static final int BAR_CODE_EAN_SEARCH_V1 = 1;

    public static final String GET_LATEST_FILE_VERSION_COMMAND = "GetLatestFileVersionCommand";
    public static final int GET_LATEST_FILE_VERSION_COMMAND_V1 = 1;

    public static final String PURCHASE_BLOCK_STATUS_COMMAND = "PurchaseBlockStatus";
    public static final int PURCHASE_BLOCK_STATUS_COMMAND_V1 = 1;

    public static final String PURCHASE_ALLOW_TYPE_COMMAND = "PurchaseAllowType";
    public static final int PURCHASE_ALLOW_TYPE_COMMAND_V1 = 1;

	public static final String PDP_EMAIL_SEND_COMMAND = "PDPEmailSendCommand";
	public static final int PDP_EMAIL_SEND_COMMAND_V1 = 1;

	public static final String FAVORITE_CATEGORY_COMMAND = "FavoriteCategory";
	public static final int FAVORITE_CATEGORY_COMMAND_V1 = 1;

	public static final String SET_FAVORITE_CATEGORY_COMMAND = "SetFavoriteCategory";
	public static final int SET_FAVORITE_CATEGORY_COMMAND_V1 = 1;

	public static final String GET_ALL_CATEGORY_COMMAND = "GetAllCategory";
	public static final int GET_ALL_CATEGORY_COMMAND_V1 = 1;
}
