package net.dumbinter.netclip;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
	private static final String ALGO = "AES/CBC/PKCS5Padding";
	private static byte[] keyValue = null;
	private static SecureRandom sr;
	private static int aesKeysize;
	
	public static void init(int aesKeysize) throws UnsupportedEncodingException{
		keyValue = Config.get().getProperty("netclip.key").getBytes("US-ASCII");
		sr = new SecureRandom();
		Crypto.aesKeysize=aesKeysize;
	}

	public static synchronized byte[] encrypt(byte[] data) throws Exception {
		
		System.out.println("Encrypting: " + bytesToHex(data));
		
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		
		byte[] iv = sr.generateSeed(c.getBlockSize());
		
		c.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
		byte[] encVal = c.doFinal(data);
		
		System.out.println("Encrypted: " + bytesToHex(encVal));
		
		return concatByteArray(iv,encVal);
	}

	public static synchronized byte[] decrypt(byte[] encryptedData) throws Exception {
		
		System.out.println("Decrypting: " + bytesToHex(encryptedData));
		
		Key key = generateKey();
		Cipher c = Cipher.getInstance(ALGO);
		
		byte[] iv = Arrays.copyOfRange(encryptedData, 0, c.getBlockSize());
		byte[] data = Arrays.copyOfRange(encryptedData, c.getBlockSize(), encryptedData.length);
		
		
		c.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
		byte[] decValue = c.doFinal(data);
		
		System.out.println("Decrypted: " + bytesToHex(decValue));
		
		return decValue;
	}

	private static Key generateKey() throws Exception {
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		KeySpec spec = new PBEKeySpec(new String(keyValue).toCharArray(),new byte[]{1,3,3,7,4,2,2,3,1,3,3,7,4,2,2,3},65536, aesKeysize);
		SecretKey tmp = factory.generateSecret(spec);
		SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
		return secret;
	}
	
	private static byte[] concatByteArray(byte[] a, byte[]b){
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}

}
