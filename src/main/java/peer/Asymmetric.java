package peer;

import javax.crypto.Cipher;
import java.security.*;

public class Asymmetric {
    private static final String RSA = "RSA";
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public Asymmetric() {
        KeyPair keyPair = generateRSAKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public static KeyPair generateRSAKeyPair() {
        SecureRandom secureRandom = new SecureRandom();

        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        keyPairGenerator.initialize(2048, secureRandom);

        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] do_RSAEncryption (byte[] plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainText);
    }

    public  byte[] do_RSADecryption(byte[] cipherText)throws Exception{
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(cipherText);

        return result;
    }
}
