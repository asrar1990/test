package com.bn.account.validator;

import com.bn.account.rest.model.Account;
import com.bn.account.rest.model.Device;
import com.bn.common.dto.device.DeviceInfo;
import com.bn.common.exception.BNException;
import com.bn.common.validator.Validator;

public class AccountValidator {
	
	public static void validateCreateAccount(Account aInfo, DeviceInfo dInfo, String password
			, String securityAnswer, String userHash, String userRand)
            throws BNException
	{		
		if (aInfo==null)
			throw BNException.getInstance("AD1006");
		
		if (Validator.isBlank(userHash))
			throw BNException.getInstance("AD1001");
		
		else if (Validator.isBlank(userRand))
			throw BNException.getInstance("AD1003");
		
		else if (Validator.isBlank(password))
			throw BNException.getInstance("AD1102");
		
		else if (Validator.isBlank(securityAnswer))
			throw BNException.getInstance("AD1106");
		
		else if (Validator.isBlank(aInfo.getEmail()))
			throw BNException.getInstance("AD1101");
		
		else if (Validator.isBlank(aInfo.getFirstName()))
			throw BNException.getInstance("AD1107");
		
		else if (Validator.isBlank(aInfo.getLastName()))
			throw BNException.getInstance("AD1107");
		
		else validateDevice(dInfo);
	}
	
	public static void validateEndpointGetHash(String uniqueid, String modelid)
	throws BNException
	{
		if (Validator.isBlank(uniqueid)) {
			throw BNException.getInstance("AD3001");
		}
		
		if (Validator.isBlank(modelid)) {
			throw BNException.getInstance("AD1203");
		}
	}

	public static void validateUserAuth(DeviceInfo dInfo, String userHash, String userRand)
	throws BNException 
	{	
		//if pre-register then account = null
		/**
		else if (acctAuth.getAccount()==null)
			throw BNException.getInstance("AD1006");
		**/
		if (dInfo==null)
			throw BNException.getInstance("AD1200");
		
		if (Validator.isBlank(dInfo.getSerialNumber()))
			throw BNException.getInstance("AD1201");
		
		else if (Validator.isBlank(userHash))
			throw BNException.getInstance("AD1001");
			
		else if (Validator.isBlank(userRand))
			throw BNException.getInstance("AD1003");
	}

	public static void validateRegisterUserDevice(Account aInfo, Device dInfo, String password
			, String userHash, String userRand)
	throws BNException
	{
		if (aInfo==null)
			throw BNException.getInstance("AD1006");

		if (dInfo==null)
			throw BNException.getInstance("AD1200");

		if (Validator.isBlank(userHash))
			throw BNException.getInstance("AD1001");

		else if (Validator.isBlank(userRand))
			throw BNException.getInstance("AD1003");

		else if (Validator.isBlank(dInfo.getSerialNumber()))
			throw BNException.getInstance("AD1201");
		
		else if (Validator.isBlank(aInfo.getEmail()))
			throw BNException.getInstance("AD1101");

		else if (Validator.isBlank(password))
			throw BNException.getInstance("AD1102");

	}
	
	public static void validateAccountDeviceInfo(Account aInfo, DeviceInfo dInfo) 
	throws BNException
	{	
		if (aInfo==null || dInfo==null)
			throw BNException.getInstance("AD1004", "acctIsNull or deviceIsNull");
		
		if (Validator.isBlank(dInfo.getSerialNumber()))
			throw BNException.getInstance("AD1201");
		
		else if (aInfo.getAccountId() < 1)
			throw BNException.getInstance("AD1123");
		
	}
	
	public static void validateDeviceAuth(DeviceInfo dInfo, String deviceRand, String deviceHash)
	throws BNException
	{
		if (Validator.isBlank(deviceHash))
			throw BNException.getInstance("AD1008");
		
		else if (Validator.isBlank(deviceRand))
			throw BNException.getInstance("AD1003");
		
		else
			validateDevice(dInfo);
		
	}
	
	public static void validateDevice(DeviceInfo dInfo) 
	throws BNException
	{
		if (dInfo==null)
			throw BNException.getInstance("AD1200");
		
		if (Validator.isBlank(dInfo.getSerialNumber()))
			throw BNException.getInstance("AD1201");
		
		else if (Validator.isBlank(dInfo.getModel()))
			throw BNException.getInstance("AD1203");
		
		else if (Validator.isBlank(dInfo.getCurrentBuildNumber()))
			throw BNException.getInstance("AD1204");
	}

}
