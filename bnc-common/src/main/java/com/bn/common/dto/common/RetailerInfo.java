package com.bn.common.dto.common;

/**
 * User: ktran
 * Date: 5/25/12
 */
public class RetailerInfo {

	private String retailerCode;
	private String countryCode;

	public RetailerInfo(String retailerCode, String countryCode) {
		this.retailerCode = retailerCode;
		this.countryCode = countryCode;
	}

	public String getRetailerCode() {
		return retailerCode;
	}

	public void setRetailerCode(String retailerCode) {
		this.retailerCode = retailerCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

    /**
     * handy method to detect NOK GB device/retailer
     * @return true if Country=GB and Retailer=NOK
     */
    public boolean isNokGB() {
        return "NOK".equalsIgnoreCase(retailerCode) && "GB".equalsIgnoreCase(countryCode);
    }

	public String toString() {
        return "retailerCode=" + retailerCode + ", countryCode=" + countryCode;
	}
}
