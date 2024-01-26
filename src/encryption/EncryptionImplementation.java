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
                        //Añadir a archivo de Porpiedades ¡¡¡¡¡¡¡¡¡¡BOBO!!!!!!!!!
			FileInputStream fis = new FileInputStream("C:\\Users\\danid\\Desktop\\Keys\\keyspublicKey.der");
			byte[] publicKeyBytes = new byte[fis.available()];
			fis.read(publicKeyBytes);
			fis.close();

			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

			// Encrypt and send data
                        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedData = cipher.doFinal(password.getBytes());
			
                        return new String(encryptedData);
                        
		} catch (Exception e) {
			System.out.print("Pene");
                        e.printStackTrace();
                }
                return new String(encryptedData);
	}
}
