package com.bn.common.dto.common;

/**
 * Created with IntelliJ IDEA.
 * User: ktran
 * Date: 5/9/12
 * Time: 8:35 AM
 * To change this template use File | Settings | File Templates.
 */
public enum TokenType {
	DEVICE_AUTH("DA")
	, USER_AUTH("UA")
    , USER_AUTH_REST("UAR")
	, INSTORE_AUTH("IS")
	, USERIDENTIFIER("UI")
	, USER_PASSWORDRESET("PWR")
	, PARTNER_ACCESS("PAT")
	, SHORT_LIVED_TOKEN("SLT")
    , FACEBOOK_ACCESS_TOKEN("FAT")
    , FACEBOOK_REFRESH_TOKEN("FRT")
    , GOOGLE_ACCESS_TOKEN("GAT")
    , GOOGLE_REFRESH_TOKEN("GRT")
    , TEST_TOKEN("FOO");

	private String tokenType;
	TokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getTokenType() {
		return this.tokenType;
	}

	public static TokenType fromString(String tokenType) {
		if (tokenType!=null) {
			for (TokenType tt : TokenType.values()) {
				if (tokenType.equalsIgnoreCase(tt.tokenType))
					return tt;
			}
		}
		return null;
	}
}
