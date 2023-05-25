package com.bn.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility methods for creating various kinds of digests and signatures while
 * communicating with external networks
 * 
 * @author rrajamiyer
 * 
 */
@Slf4j
public class CryptoUtil {
	public enum CipherType {
		AES,
		DES,
	}
	public final static String UTF8 = "UTF-8";
	public final static String ISO_8859_1 = "ISO-8859-1";
	
	public final static String HmacSHA1 = "HmacSHA1";
	public final static String MD5 = "MD5";
	public final static String AES = "AES";
	public final static String DES = "DES";

	/**
	 * Creates a Base64-encoded HMAC-DHA1 digest of a given string.
	 * @param data Input string
	 * @param key Secret key to encrypt the data
	 * @return
	 */
	public static String getHMACSHA1Digest(String data, String key) throws Exception {
		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(UTF8), HmacSHA1);
		Mac mac = Mac.getInstance(HmacSHA1);
		mac.init(signingKey);
		byte[] rawHmac = mac.doFinal(data.getBytes(UTF8));
		return new String(Base64.encodeBase64(rawHmac));
	}

	public static String encrypt(String data2Encrypt, Key key) throws Exception {
		return encrypt(data2Encrypt, key, CipherType.DES);
	}
	
	/**
	 * Encrypt a string with a secret key
	 * 
	 * @param unencryptedString
	 * @param key
	 * @return
	 */
	public static String encrypt(String unencryptedString, Key key, CipherType type) throws Exception {
		String encryptedString = null;
		Cipher cipher = Cipher.getInstance(type.toString());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plainText = unencryptedString.getBytes(UTF8);
		byte[] encryptedText = cipher.doFinal(plainText);
		byte[] encryptedBytes = Base64.encodeBase64(encryptedText);
		encryptedString = new String(encryptedBytes);
		return encryptedString;
	}

	public static String encryptIso8859(String unencryptedString, Key key, CipherType type) throws Exception {
		String encryptedString = null;
		Cipher cipher = Cipher.getInstance(type.toString());
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plainText = unencryptedString.getBytes(UTF8);
		byte[] encryptedText = cipher.doFinal(plainText);
		encryptedString = new String(encryptedText, ISO_8859_1);
		return encryptedString;
	}
	
	public static String decrypt(String encryptedString, Key key) throws Exception {
		return decrypt(encryptedString, key, CipherType.DES);
	}
	
	/**
	 * Decrypt a string with the secret key
	 * 
	 * @param encryptedString
	 * @param key
	 * @return
	 */
	public static String decrypt(String encryptedString, Key key, CipherType type) throws Exception {
		String decryptedText = null;
		Cipher cipher = Cipher.getInstance(type.toString());
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] encryptedText = Base64.decodeBase64(encryptedString
				.getBytes(UTF8));
		byte[] decryptedBytes = cipher.doFinal(encryptedText);
		decryptedText = new String(decryptedBytes);
		return decryptedText;
	}

	/**
	 * Decrypt a string with the secret key
	 * 
	 * @param encryptedString
	 * @param key
	 * @return
	 */
	public static String decryptIso8859(String encryptedString, Key key, CipherType type) throws Exception {
		String decryptedText = null;
		Cipher cipher = Cipher.getInstance(type.toString());
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] encryptedText = encryptedString.getBytes(ISO_8859_1);
		byte[] decryptedBytes = cipher.doFinal(encryptedText);
		decryptedText = new String(decryptedBytes);
		return decryptedText;
	}
	
	/**
	 * Returns MD5 digest of a string
	 * 
	 * @param value
	 * @return
	 */
	public static String generateMD5Hash(String value) throws Exception {
		MessageDigest md = MessageDigest.getInstance(MD5);
		byte[] bytes;
		try {
			bytes = value.getBytes(UTF8);
		} catch (UnsupportedEncodingException e1) {
			log.error("generateMD5Hash "+e1);
			bytes = value.getBytes();
		}
		StringBuilder result = new StringBuilder();
		for (byte b : md.digest(bytes)) {
			result.append(Integer.toHexString((b & 0xf0) >>> 4));
			result.append(Integer.toHexString(b & 0x0f));
		}
		return result.toString();
	}
	
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }
	 
    public static String hashToMd5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
	        MessageDigest md;
	        md = MessageDigest.getInstance(MD5);
	        md.update(text.getBytes("iso-8859-1"), 0, text.length());
	        byte[] md5hash = md.digest();
	        return convertToHex(md5hash);
	}

    public static void main(String args[]) {
    	try {
    	} catch (Exception ex) {
    		// ex.printStackTrace();
    	}
    }
}
