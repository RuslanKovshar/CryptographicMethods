package ruslan.encoder;

import ruslan.view.Alphabets;

public class GammaEncoder implements EncoderWithKey {
    @Override
    public String encode(String text, String key) {
        key = init(text, key);
        char[] chars = text.toCharArray();
        char[] gamma = key.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = encodeCharacter(setAlphabet(chars[i]), chars[i], gamma[i]);
        }
        return new String(chars);
    }

    private String init(String text, String key) {
        StringBuilder builder = new StringBuilder(key);
        int res = text.length() - key.length();
        for (int i = 0; i < res; i++) {
            int k = i;
            while (k >= key.length()) {
                k = k - key.length();
            }
            builder.append(key.charAt(k));
        }
        return builder.toString();
    }

    @Override
    public String decode(String text, String key) {
        key = init(text, key);
        char[] chars = text.toCharArray();
        char[] gamma = key.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = decodeCharacter(setAlphabet(chars[i]), chars[i], gamma[i]);
        }
        return new String(chars);
    }

    private String setAlphabet(char letter) {
        String alphabet = "";
        if (Alphabets.UA_ALPHABET_CAPITAL.indexOf(letter) != -1) alphabet = Alphabets.UA_ALPHABET_CAPITAL;
        if (Alphabets.UA_ALPHABET.indexOf(letter) != -1) alphabet = Alphabets.UA_ALPHABET;
        if (Alphabets.EN_ALPHABET.indexOf(letter) != -1) alphabet = Alphabets.EN_ALPHABET;
        if (Alphabets.EN_ALPHABET_CAPITAL.indexOf(letter) != -1) alphabet = Alphabets.EN_ALPHABET_CAPITAL;
        return alphabet;
    }

    private char encodeCharacter(String abc, char symbol, char key) {
        int i = abc.indexOf(symbol);
        if (i == -1) {
            return symbol;
        }
        int gamma = abc.indexOf(key);
        int newPos = Math.floorMod((i + gamma), abc.length());
        return abc.charAt(newPos);
    }

    private char decodeCharacter(String abc, char symbol, char key) {
        int i = abc.indexOf(symbol);
        if (i == -1) {
            return symbol;
        }
        int gamma = abc.indexOf(key);
        int newPos = Math.floorMod((i + abc.length() - (Math.floorMod(gamma, abc.length()))), abc.length());
        return abc.charAt(newPos);
    }

    @Override
    public String toString() {
        return "GammaEncoder";
    }
}
