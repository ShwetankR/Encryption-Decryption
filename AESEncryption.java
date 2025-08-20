import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESEncryption {

    private SecretKey secretKey;

    // Constructor to generate a new AES key
    public AESEncryption() throws Exception {
        this.secretKey = generateKey();
    }

    // Constructor to use a Base64 encoded key
    public AESEncryption(String base64Key) throws Exception {
        this.secretKey = decodeBase64Key(base64Key);
    }

    // Generate AES key
    public synchronized SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    // Decode Base64 key
    private SecretKey decodeBase64Key(String base64Key) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    // Encrypt
    public synchronized String encrypt(String text) throws Exception {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Input text cannot be null or empty");
        }
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        byte[] encryptedBytes = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypt
    public synchronized String decrypt(String encryptedText) throws Exception {
        if (encryptedText == null || encryptedText.isEmpty()) {
            throw new IllegalArgumentException("Encrypted text cannot be null or empty");
        }
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public String getKeyAsString() {
        return Base64.getEncoder().encodeToString(this.secretKey.getEncoded());
    }
}