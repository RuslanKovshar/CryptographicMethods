package ruslan.encoder;

public interface EncoderWithKey extends Encoder {
    String encode(String text, String key);
    String decode(String text, String key);
}
