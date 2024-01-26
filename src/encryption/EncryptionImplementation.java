package encryption;

import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

public class EncryptionImplementation{
	
    public static String encryptWithPublicKey(String password) { 
        byte[] encryptedData = null; 
		try {
			// Load Public Key
			FileInputStream fis = new FileInputStream("c:\\trastero\\publicKey.der");
			byte[] publicKeyBytes = new byte[fis.available()];
			fis.read(publicKeyBytes);
			fis.close();

			KeyFactory keyFactory = KeyFactory.getInstance("EC");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
		
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

			// Encrypt and send data
			Cipher cipher = Cipher.getInstance("ECIES");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedData = cipher.doFinal(password.getBytes());
			
                        return encryptedData.toString();
                        
		} catch (Exception e) {
			//throw LogicException("Exception while encrypting" + e);
                }
                return encryptedData.toString();
	}
}
