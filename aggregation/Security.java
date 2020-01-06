package aggregation;
//class to encryption uising AES
import javax.crypto.Cipher;
//class to generate key for AES
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;
//class declaration
public class Security {
	static String keydata;
	//generating keys
	public static String keySet(){
		String keyset[] = {"ThisIsASecretKey","secretkeyencrypt","encryptkeysecret"};
		Random r = new Random();
		return keyset[r.nextInt(keyset.length)];
	}
	public static byte[] genKeys(){
		keydata = keySet();
		byte[] keyBytes = keydata.getBytes();
		return keyBytes;
	}
	//method to encrypt data 
	public static byte[] encryption(byte filedata[])throws Exception{
		//getting keys
		byte keys[] = genKeys();
		//creating object on key
		SecretKeySpec key = new SecretKeySpec(keys,0,keys.length,"AES");
	    //getting AES instance
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		//initializing instance in encrypt mode
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//doing encryption
	    byte encbytes[] = cipher.doFinal(filedata);
	   	//returning back encrypted data to caller
		return encbytes;
	}
	//method to do decryption process
	public static byte[] decryption(byte filedata[],String keyvalue)throws Exception {
		//getting keys to decrypt data
		byte keys[] = keyvalue.getBytes();
		//creating object for given key
		SecretKeySpec key = new SecretKeySpec(keys,0,keys.length,"AES");
		 //getting AES instance
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		//initializing instance in decrypt mode
		cipher.init(Cipher.DECRYPT_MODE, key);
		//doing decryption
		byte decbytes[] = cipher.doFinal(filedata);
		//converting decrypted bytes to string and send back to caller
		return decbytes;
	}
	
}         