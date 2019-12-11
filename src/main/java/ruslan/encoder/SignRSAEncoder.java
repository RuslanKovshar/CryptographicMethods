package ruslan.encoder;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;

public class SignRSAEncoder implements EncoderWithoutKey {
    private Cipher encrypt;
    private Cipher decrypt;
    private String message;

    public SignRSAEncoder() {
        try {
            KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance("RSA");
            KeyPair keyPair = pairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            encrypt = Cipher.getInstance("RSA");
            encrypt.init(Cipher.ENCRYPT_MODE, privateKey);

            decrypt = Cipher.getInstance("RSA");
            decrypt.init(Cipher.DECRYPT_MODE, publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String encode(String text) {
        this.message = text;
        byte[] messageBytes = text.getBytes();
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageHash = md.digest(messageBytes);
            byte[] digitalSignature = encrypt.doFinal(messageHash);
            result = Base64.getEncoder().encodeToString(digitalSignature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String decode(String text) {
        byte[] encryptedMessageHash = Base64.getDecoder().decode(text);
        boolean isCorrect = false;
        try {
            byte[] decryptedMessageHash = decrypt.doFinal(encryptedMessageHash);
            byte[] messageBytes = message.getBytes();
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] newMessageHash = md.digest(messageBytes);
            isCorrect = Arrays.equals(decryptedMessageHash, newMessageHash);
            System.out.println(isCorrect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isCorrect ? message : "##Sign is not valid##";
    }

    @Override
    public String toString() {
        return "SignRSAEncoder";
    }
}
