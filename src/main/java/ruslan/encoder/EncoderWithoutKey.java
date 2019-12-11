package ruslan.encoder;

public interface EncoderWithoutKey extends Encoder {
    String encode(String text);
    String decode(String text);
}
