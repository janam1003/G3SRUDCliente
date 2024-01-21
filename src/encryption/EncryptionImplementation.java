package encryption;

public class EncryptionImplementation implements Encryption {
	public static encryptWithPublicKey(String password) { 
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
			byte[] encryptedData = cipher.doFinal(password.getBytes());
			return (String)encryptedData;
		} catch (Exception e) {
			throw Exception("Exception while encrypting" + e);
		}
	}
}
