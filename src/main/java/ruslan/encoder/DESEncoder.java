package ruslan.encoder;

import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DESEncoder implements EncoderWithoutKey {

    private Cipher encrypt;
    private Cipher decrypt;

    public DESEncoder() {
        SecretKey key = null;
        try {
            key = KeyGenerator.getInstance("DES").generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            encrypt = Cipher.getInstance("DES");
            encrypt.init(Cipher.ENCRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        try {
            decrypt = Cipher.getInstance("DES");
            decrypt.init(Cipher.DECRYPT_MODE, key);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String encode(String text) {
        byte[] utf8;
        utf8 = text.getBytes(StandardCharsets.UTF_8);
        byte[] enc = new byte[0];
        try {
            enc = encrypt.doFinal(utf8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(enc);
    }

    @Override
    public String decode(String text) {
        byte[] dec = Base64.getDecoder().decode(text);
        byte[] utf8 = new byte[0];
        try {
            utf8 = decrypt.doFinal(dec);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return new String(utf8, StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "DESEncoder";
    }
}
