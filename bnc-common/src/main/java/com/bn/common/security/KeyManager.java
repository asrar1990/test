package com.bn.common.security;

import com.bn.common.util.CryptoUtil;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.spec.KeySpec;

/**
 * A simple singleton class to mint and use encryption key based on a fixed secret.
 * 
 * @author rrajamiyer
 *
 */
public class KeyManager {
	
	public static final String UTF8 = "UTF-8";
	public static final String DES_ENCRYPTION_SCHEME = CryptoUtil.DES;
	private final static String SOCIAL_ENCRYPT_KEY = "ThisIsTopSecret";
	private final static String ACCOUNT_DEVICE_ENCRYPT_KEY = "47kYe3Y29HjK";
	private final static String ADDRESS_BOOK_ENCRYPT_KEY = "Th4Ab74deQx";
	
	private static KeyManager instance;
	private final SecretKey socialNetworkKey;
	private final SecretKey accountDeviceKey;
	private final SecretKey addressBookKey;
	
	private KeyManager() throws Exception {
		socialNetworkKey = getDesSecretKey(SOCIAL_ENCRYPT_KEY);
		accountDeviceKey = getDesSecretKey(ACCOUNT_DEVICE_ENCRYPT_KEY);
		addressBookKey = getDesSecretKey(ADDRESS_BOOK_ENCRYPT_KEY);
	}
	
	public static KeyManager getInstance() throws Exception {
		synchronized (KeyManager.class) {
			if(instance == null) {
				instance = new KeyManager();
			}
		}
		return instance;
	}
	
	public SecretKey getSocialNetworkKey() {
		return socialNetworkKey;
	}
	
	public SecretKey getAccountDeviceKey() {
		return accountDeviceKey;
	}
	
	public SecretKey getAddressBookKey() {
		return addressBookKey;
	}
	
	public String encryptAccountDevice(String value, boolean utf8) throws Exception {
		String encrypted = CryptoUtil.encrypt(value, getAccountDeviceKey());
		if ( utf8 )
			return URLEncoder.encode(encrypted, UTF8);
		return encrypted;
	}
	
	public String decryptAccountDevice(String value, boolean utf8) throws Exception {
		if ( utf8 )
			value = URLDecoder.decode(value, UTF8);
		String decrypted = CryptoUtil.decrypt(value, getAccountDeviceKey());
		return decrypted;		
	}
	
	public static String encryptString(String key, String value) throws Exception {
		String encrypted = CryptoUtil.encrypt(value, getDesSecretKey(key));
		return URLEncoder.encode(encrypted, UTF8);
	}
	
	public static String decryptString(String key, String value) throws Exception {
		value = URLDecoder.decode(value, UTF8);
		String decrypted = CryptoUtil.decrypt(value, getDesSecretKey(key));
		return decrypted;		
	}

	
	public static SecretKey getDesSecretKey(String key) throws Exception {
		if ( key==null )
			return null;
		KeySpec keySpec = new DESKeySpec(key.getBytes(CryptoUtil.UTF8));
		return SecretKeyFactory.getInstance(DES_ENCRYPTION_SCHEME).generateSecret(keySpec);
	}
}
