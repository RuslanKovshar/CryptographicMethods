package ruslan.encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

public class RSAEncoder implements EncoderWithoutKey {

    private Cipher encrypt;
    private Cipher decrypt;

    public RSAEncoder() {
        try {
            KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = pairGenerator.generateKeyPair();
            Key privateKey = keyPair.getPrivate();
            Key publicKey = keyPair.getPublic();

            encrypt = Cipher.getInstance("RSA");
            encrypt.init(Cipher.ENCRYPT_MODE, publicKey);

            decrypt = Cipher.getInstance("RSA");
            decrypt.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String encode(String text) {
        byte[] bytes = new byte[0];
        try {
            bytes = encrypt.doFinal(text.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public String decode(String text) {
        byte[] bytes = Base64.getDecoder().decode(text);
        byte[] decryptedBytes = new byte[0];
        try {
            decryptedBytes = decrypt.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(decryptedBytes);
    }

    @Override
    public String toString() {
        return "RSAEncoder";
    }
}
