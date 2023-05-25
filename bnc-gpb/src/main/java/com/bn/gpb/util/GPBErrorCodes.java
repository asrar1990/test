package com.bn.gpb.util;

public class GPBErrorCodes
{
	public static final String ERRORCODE_INVALIDUSERTOKEN 		       = "AD1002";		//invalid user token
	public static final String ERRORCODE_INVALIDDEVICETOKEN  		   = "AD1007";		//invalid user or device token
	public static final String ERRORCODE_DEVICEBLACKLISTED 		       = "AD1221";		//device black listed
	
	//COMMUNITY ERROR CODES - GENERAL
	public static final String ERRORCODE_SQL_ERROR                     = "CU0002";  //General SQl error
	public static final String ERRORCODE_INAVLID_EAN				   = "CU0027";  //EAN has na invalid format
	public static final String ERRORCODE_INAVLID_EMAIL_PARAMETER	   = "CU0030";  //Error while preparing email content
	public static final String ERRORCODE_REMOTE_SERVICE_NOT_REACHABLE  = "CU0043";  //Remote service not reachable
	
	//COMMUNITY ERROR CODES - LISTMANAGEMNT
	public static final String ERRORCODE_LIST_ALREADY_EXISTS           = "CU0001";  //List alredy exists
	public static final String ERRORCODE_LIST_INVALID_POSITIONS        = "CU0005";  //Tried to inser/update incorrect list position
	public static final String ERRORCODE_LIST_EAN_ALREADY_EXISTS       = "CU0020";  //Duplicate ean in list
	public static final String ERRORCODE_LIST_TYPE_OPERATION_NOT_SUPPORTED				      = "CU0052";  //This list does not support adds or deletes
	public static final String ERRORCODE_LIST_CONFLICT				                          = "CU0053";  //A newer version of this list was found. Please retrieve the new list before and update / delete
	public static final String ERRORCODE_LIST_PARAMETERS_INVALID				              = "CU0054";  //List name max characters are 100 and description size max characters is 200
	
	
	//COMMUNITY ERROR CODES - SOCIAL ACTIVITIES
	public static final String ERRORCODE_SOCIAL_NETWORK_AUTHORIZATION           = "CU0006";  //Authorization error so social network
	public static final String ERRORCODE_FACEBOOK_API_LIMIT                     = "CU0007";  //Facebook API limit reached
	public static final String ERRORCODE_TWITTER_API_LIMIT                      = "CU0041";  //Facebook API limit reached
	public static final String ERRORCODE_TWITTER_DUPLICATE_STATUS               = "CU0008";  //Trying to insert duplciate status update on Twitter
	public static final String ERRORCODE_FACEBOOK_GENERAL_ERROR                 = "CU0009";  //General error while updating Facebook
	public static final String ERRORCODE_TWITTER_GENERAL_ERROR                  = "CU0010";  //General error while updating Twitter
	public static final String ERRORCODE_EMAIL_INVALID_FORMAT                   = "CU0014";  //Invalid email format or size (>255)
	public static final String ERRORCODE_TINY_URL_GENERAL                       = "CU0015";  //General error while posting to Tiny url sevice
	public static final String ERRORCODE_EMAIL_MESSAGE_SIZE_EXCEEDED            = "CU0016";  //Email message too long (>1000)
	public static final String ERRORCODE_QUOTE_SIZE_EXCEEDED                    = "CU0017";  //Quote too long (>420)
	public static final String ERRORCODE_FACEBOOK_MESSAGE_SIZE_EXCEEDED         = "CU0018";  //Facebook message too long (>420)
	public static final String ERRORCODE_TWITTER_MESSAGE_SIZE_EXCEEDED          = "CU0019";  //Facebook message too long (>140)
	public static final String ERRORCODE_SYNC_RECOMMNENDATION_VIEWED            = "CU0025";  //Recommendation could not be makred as viewds
	public static final String ERRORCODE_SYNC_UPDATE_RECOMMNENDATION            = "CU0026";  //Cannot update a recommendation
	public static final String ERRORCODE_READSTATUS_INVALID_PARAM               = "CU0028";  //Invalid read status parameter
	public static final String ERRORCODE_READSTATUS_FACEBOOK_INVALID_PARAM      = "CU0029";  //Invalid read status parameter when posting to Facebook, chapter and page can't be empty
	public static final String ERRORCODE_SOCIAL_CREDENTIALS                     = "CU0031";  //No credentials to post to this social network
	public static final String ERRORCODE_TWITTER_OVER_CAPACITY                  = "CU0045";  //Twitter Over capacity. Try again later
	public static final String ERRORCODE_SHARE_TO_SAME_EMAILADDRESS             = "CU0046";  //User tried to sahre,recommend to user  own email address
	public static final String ERRORCODE_USER_NOT_VISIBLE                       = "CU0047";  //User tried to share to frineds wall, but friend prohibited posts from friends
	public static final String ERRORCODE_DAILY_QUOTE_LIMIT_REACHED              = "CU0048";  //Daily limit reached for sharing a quote
	
	
	//COMMUNITY ERROR CODES - REVIEWS
	public static final String ERRORCODE_REVIEW_TEXT_SIZE_EXCEEDED     = "CU0011";  //Review text too long, only 3500 character
	public static final String ERRORCODE_REVIEW_TITLE_SIZE_EXCEEDED    = "CU0012";  //Review title too long, only 250 character
	public static final String ERRORCODE_REVIEW_FAILURE_POSTING_REVIEW = "CU0013";  //General error while psoting review
	public static final String ERRORCODE_REVIEW_ID_NOT_VALID		   = "CU0042";  //Invalid Review ID
	
	//COMMUNITY ERROR CODES - LENDING DISCOVERY AND FACEBOOK LENDOFFER CODES
	public static final String ERRORCODE_DISCOVERY_LEND_REQUEST_NOT_LENDABLE                  = "CU0021";  //The book is not lendable anymore (>140)
	public static final String ERRORCODE_DISCOVERY_ALREADY_REQUESTED                          = "CU0022";  //User already requested this book once
	public static final String ERRORCODE_FACEBOOK_LEND_OFFER_PROCESS_FROM_API_FAILED_NY       = "CU0023";  //Response from NY lend offer failed
	public static final String ERRORCODE_FACEBOOK_LEND_OFFER_API_FAILED_NY                    = "CU0024";  //Lend offeer api in NY failed
	public static final String ERRORCODE_DISCOVERY_INVALID_PARAM                              = "CU0032";  //Offset and limit can't be <0
	public static final String ERRORCODE_FACEBOOK_MAX_LOANS_REACHED                          = "CU0033";  //Max number of loans reached
	public static final String ERRORCODE_FACEBOOK_INPUT_DATA_VALIDATION_ERROR                = "CU0034";  //Input data validation error
	public static final String ERRORCODE_FACEBOOK_ITEM_IS_NOT_AVAILABLE_TO_LOAN              = "CU0035";  //Item is not available to loan
	public static final String ERRORCODE_FACEBOOK_LEND_BLOCKED_OUTSTANDING_LEND              = "CU0036";  //Lend blocked outstanding lend
	public static final String ERRORCODE_FACEBOOK_DB_ERROR_NY 					              = "CU0037";  //Db error on NY side
	public static final String ERRORCODE_FACEBOOK_DUPLICATE_ITEM_ERROR_NY 					  = "CU0038";  //Duplicate item error from NY
	public static final String ERRORCODE_FACEBOOK_DUPLICATE__BORROW_ITEM 					  = "CU0039";  //Duplicate borrow item error from NY
	public static final String ERRORCODE_FACEBOOK_NY_UNEXPECTED_ERROR					      = "CU0040";  //Unexpected error from NY
	public static final String ERRORCODE_LEND_REQUEST_ANSWER_NOT_FOUND					      = "CU0049";  //No info was found in db for the lend request answer.
	public static final String ERRORCODE_LEND_REQUEST_EXPIRED				    			  = "CU0050";  //Lend request expired.
	public static final String ERRORCODE_LEND_REQUEST_ALREADY_REPLIED				    	  = "CU0051";  //Lend request already replied
	
	//not used: public static final String ERRORCODE_DISCOVER_EAN_NOT_OWNED_BY_USER         			  = "CU0044";  //EAN is not owned by this user
	
	public static final String ERRORCODE_ABC_DUPLICATE_CONTACT								= "AB2000";		// Duplicate contact was discovered
	public static final String ERRORCODE_ABC_ILLEGAL_OPERATION								= "AB2001";		// Illegal operation
	public static final String ERRORCODE_ABC_FRIEND_YOURSELF								= "AB2002";		// Friending yourself is prohibited.
	
}
