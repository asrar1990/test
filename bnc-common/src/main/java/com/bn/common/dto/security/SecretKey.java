package com.bn.common.dto.security;

import com.bn.common.dto.util.StringUtil;
import com.bn.common.exception.BNException;
import com.bn.common.validator.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

// TODO [gfeigenson@book.com 11/28/11] - I've hacked in CBCSecretKey as we cannot currently make breaking changes, but
// TODO [gfeigenson@book.com 11/28/11] - we really need to come in here and do the right thing: split this class up into
// TODO [gfeigenson@book.com 11/28/11] - constituent parts, and get rid of any trace of ECB block mode.

// TODO [gfeigenson@book.com 11/28/11] - We should also see about fixing the Validator calls - they seem to be treating
// TODO [gfeigenson@book.com 11/28/11] - non-word characters as invalid (IE: trim, length comparison), and that's not
// TODO [gfeigenson@book.com 11/28/11] - such a great idea here.

/**
 * Provides a set of cryptographically-based functionality for handling data.<p/>
 * 
 * The encryption/decryption methods found in encryptData and decryptData are considered insecure as they use Electronic
 * Codebook (ECB) block mode. As such this class is considered deprecated, and users doing new encryption/decryption 
 * should instead use the {@link CBCSecretKey} class.<p/>
 */
@SuppressWarnings("restriction")
@Deprecated
@Slf4j
public class SecretKey {
    private final static String MD5 = "MD5";
    private final static String SHA1 = "SHA-1";
    private final static String SHA256 = "SHA-256";
    
    public SecretKey() {
    	
    }

    public String getDeviceKey(int seedData, String serialNum, String model) {
        String key = null;

        if (!Validator.isBlank(serialNum) && !Validator.isBlank(model)) {
            String password = String.format("%s-%s-%s", serialNum, model, Integer.toString(seedData));
            key = getDigest(password);
        }

        return key;
    }

    public String getUserKey(String password) {
        return getDigest(password);
    }

    public String encryptData(String tData, byte[] secretKey) throws BNException {
        String enData = null;

        if (!Validator.isBlank(tData) && secretKey != null && secretKey.length > 15) {
            byte[] sKey = Arrays.copyOfRange(secretKey, 0, 16);
            if (log.isDebugEnabled()) {
                log.debug(String.format("encryptData:%s:%s", tData, sKey));
            }

            try {
                byte[] bData = tData.getBytes("UTF-8");
                Cipher c = Cipher.getInstance("AES");
                SecretKeySpec k = new SecretKeySpec(sKey, "AES");
                c.init(Cipher.ENCRYPT_MODE, k);
                byte[] encryptedData = c.doFinal(bData);
                enData = new String(Base64.encodeBase64(encryptedData));

            }
            catch (Exception e) {
                log.info(String.format("encryptData:%s:%s:%s", tData, secretKey, e.getMessage()));
                throw BNException.getInstance("CM1003");
            }
        }
        else {
            log.info(String.format("Invalid data for encrypting data:%s:%s", tData, secretKey));
        }

        return enData;
    }

    public String encryptData(String tData, String secretKey) throws BNException {
        if (!Validator.isBlank(tData) && !Validator.isBlank(secretKey) && Validator.isLengthGreaterThan(secretKey, 15)) {
            return encryptData(tData, secretKey.getBytes());
        }
        else {
            log.info(String.format("Invalid data for encrypting data:%s:%s", tData, secretKey));
            return null;
        }
    }

     public String origEncryptData(String tData, String secretKey) throws BNException {
        String enData = null;

        if (!Validator.isBlank(tData) && !Validator.isBlank(secretKey)
                && Validator.isLengthGreaterThan(secretKey, 15))
        {
            String newKey = secretKey.substring(0,16);
            if (log.isDebugEnabled()) {
                log.debug(String.format("encryptData:%s:%s", tData, secretKey));
            }

            try {
                byte[] sKey = newKey.getBytes("UTF-8");
                byte[] bData = tData.getBytes("UTF-8");
                Cipher c = Cipher.getInstance("AES");
                SecretKeySpec k = new SecretKeySpec(sKey, "AES");
                c.init(Cipher.ENCRYPT_MODE, k);
                byte[] encryptedData = c.doFinal(bData);
                enData = new String(Base64.encodeBase64(encryptedData));

            } catch (Exception e) {
                log.info(String.format("encryptData:%s:%s:%s", tData, secretKey, e.getMessage()));
                throw BNException.getInstance("CM1003");
            }

        } else {
            log.info(String.format("Invalid data for encrypting data:%s:%s", tData, secretKey));
        }

        return enData; 
    }

    public String decryptData(String enData, byte[] secretKey) throws BNException {
        String data = null;

        if (!Validator.isBlank(enData) && secretKey != null && secretKey.length > 15) {
            byte[] sKey = Arrays.copyOfRange(secretKey, 0, 16);
            if (log.isDebugEnabled()) {
                log.debug(String.format("decryptData:%s:%s:%s", enData, secretKey, sKey));
            }

            try {
                byte[] bData = Base64.decodeBase64(enData.getBytes("UTF-8"));
                Cipher c = Cipher.getInstance("AES");
                SecretKeySpec k = new SecretKeySpec(sKey, "AES");
                c.init(Cipher.DECRYPT_MODE, k);
                byte[] deData = c.doFinal(bData);
                data = new String(deData);

            }
            catch (Exception e) {
                log.info(String.format("decryptData:%s:%s:%s", enData, secretKey, e.getMessage()));
                throw BNException.getInstance("CM1004");
            }
        }
        else {
            log.info(String.format("Invalid data for de-encrypting data:%s:%s", enData, secretKey));
        }

        return data;
    }

    public String decryptData(String enData, String secretKey) throws BNException {
        if (!Validator.isBlank(enData) && !Validator.isBlank(secretKey) && Validator.isLengthGreaterThan(secretKey, 15)) {
            return decryptData(enData, secretKey.getBytes());
        }
        else {
            log.info(String.format("Invalid data for de-encrypting data:%s:%s", enData, secretKey));
            return null;
        }
    }

    public String origDecryptData(String enData, String secretKey) throws BNException {
        String data = null;

        if (!Validator.isBlank(enData) && !Validator.isBlank(secretKey)
                && Validator.isLengthGreaterThan(secretKey, 15)) {
            String newKey = secretKey.substring(0, 16);
            if (log.isDebugEnabled()) {
                log.debug(String.format("decryptData:%s:%s:%s", enData, secretKey, newKey));
            }

            try {
                byte[] bData = Base64.decodeBase64(enData.getBytes("UTF-8"));
                byte[] sKey = newKey.getBytes("UTF-8");
                Cipher c = Cipher.getInstance("AES");
                SecretKeySpec k = new SecretKeySpec(sKey, "AES");
                c.init(Cipher.DECRYPT_MODE, k);
                byte[] deData = c.doFinal(bData);
                data = new String(deData);

            }
            catch (Exception e) {
                log.info(String.format("decryptData:%s:%s:%s", enData, secretKey, e.getMessage()));
                throw BNException.getInstance("CM1004");
            }

        }
        else {
            log.info(String.format("Invalid data for de-encrypting data:%s:%s", enData, secretKey));
        }

        return data;
    }

    public String getDigest(String key) {
        String hash = null;
        if (!Validator.isBlank(key))
            key = StringUtil.removeSpaces(key);

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte raw[] = md.digest(key.getBytes("UTF-8"));
            hash = (new BASE64Encoder()).encode(raw);

        }
        catch (UnsupportedEncodingException e) {
            log.error(String.format("getDigest:%s", e.getMessage()));

        }
        catch (NoSuchAlgorithmException e) {
            log.info(String.format("getDigest:%s", e.getMessage()));
        }
        return hash;
    }
    
    public String getDigest(String key, String hashType, int times) {
        String hash = null;
        if (!Validator.isBlank(key))
            key = StringUtil.removeSpaces(key);

        try {
            MessageDigest md = MessageDigest.getInstance(hashType);
            byte raw[] = md.digest(key.getBytes("UTF-8"));
            for (int i=1; i<times; i++) {
            	raw = md.digest(raw);
            }
            hash = (new BASE64Encoder()).encode(raw);
        }
        catch (UnsupportedEncodingException e) {
            log.error(String.format("getDigest:%s", e.getMessage()));

        }
        catch (NoSuchAlgorithmException e) {
            log.info(String.format("getDigest:%s", e.getMessage()));
        }
        return hash;
    }

    public boolean isHashValid(String hashData, byte[] secretKey, String serialNum
            , String model, String randNum)
    throws BNException 
    {
        boolean isValid = false;

        if (!Validator.isBlank(hashData) && secretKey!=null
                && !Validator.isBlank(serialNum) && !Validator.isBlank(model)
                && !Validator.isBlank(randNum)) 
        {
            String dMsg = decryptData(hashData, secretKey);
            String oMsg = String.format("%s|%s|%s", serialNum, model, randNum);
            //hack for NOOKCLOUD-1579, client may be using wrong model
            String altOmsg = String.format("%s|%s|%s", serialNum, "SGTV300", randNum);
            if (!Validator.isBlank(dMsg)
                    && !Validator.isBlank(oMsg)
                    && (dMsg.equals(oMsg) || dMsg.equals(altOmsg)))
            {
                isValid = true;

            } else {
                log.info(String.format("dMsg<>oMsg:%s:%s", dMsg, oMsg));
            }
        }

        return isValid;
    }
    
    public static String hex(byte[] array) {
        StringBuilder sb = new StringBuilder();

        for (final byte anArray : array)
        {
            sb.append(Integer.toHexString((anArray & 0xFF) | 0x100).substring(1, 3));
        }

        return sb.toString();
    }
	
	public static void main(String args[]) {
//		String hashData = "wzMQIRW0Qt1x2zy5473MT52JaLlwjd2Qb00xu5CCC2r4NyRpWABq1RAfcjJcmHOkf6ts1hOAbdc+XrUnOJFnlQWyllUILRdxjo3YO6nYOrw=";
//		String hashKey = "pwcSphpZifF4884zH+aynw==";
//		String serialNumber = "Web-62520997-b27b-4818-8800-3529f4ddb357";
//		String deviceModelName = "PORTAL";
//		String randNum = "6227749619381707617 ";
//
//		SecretKey sk = new SecretKey();
//		byte[] secretKey = hashKey.getBytes();
//
//		try {
//			if (!sk.isHashValid(hashData, secretKey, serialNumber, deviceModelName, randNum))
//			{
//				String error = "validateEncryption:invalid Hash:serial#:" + serialNumber
//						+ " :randNum:" + randNum + " :hashData:" + hashData
//						+ " :hashPrivKey:" + hashKey + " :secretKey:" + secretKey;
//				System.out.println("error:" + error);
//			}
//		} catch (Exception e) {
//			System.out.println("error:" + e.getMessage());
//		}
	}
}
