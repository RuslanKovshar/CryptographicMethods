package ruslan.encoder;

import ruslan.view.Alphabets;

public class CaesarEncoder implements EncoderWithKey {
    @Override
    public String encode(String text, String key) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = encodeCharacter(setAlphabet(chars[i]), chars[i], Integer.parseInt(key));
        }
        return new String(chars);
    }

    @Override
    public String decode(String text, String key) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = decodeCharacter(setAlphabet(chars[i]), chars[i], Integer.parseInt(key));
        }
        return new String(chars);
    }

    private String setAlphabet(char letter) {
        String abc = "";
        if (Alphabets.UA_ALPHABET_CAPITAL.indexOf(letter) != -1) abc = Alphabets.UA_ALPHABET_CAPITAL;
        if (Alphabets.UA_ALPHABET.indexOf(letter) != -1) abc = Alphabets.UA_ALPHABET;
        if (Alphabets.EN_ALPHABET.indexOf(letter) != -1) abc = Alphabets.EN_ALPHABET;
        if (Alphabets.EN_ALPHABET_CAPITAL.indexOf(letter) != -1) abc = Alphabets.EN_ALPHABET_CAPITAL;
        return abc;
    }

    private char encodeCharacter(String abc, char symbol, int key) {
        int i = abc.indexOf(symbol);
        if (i == -1) {
            return symbol;
        }
        int newPos = Math.floorMod((i + key), abc.length());
        return abc.charAt(newPos);
    }

    private char decodeCharacter(String abc, char symbol, int key) {
        int i = abc.indexOf(symbol);
        if (i == -1) {
            return symbol;
        }
        int newPos = Math.floorMod((i + abc.length() - (Math.floorMod(key, abc.length()))), abc.length());
        return abc.charAt(newPos);
    }


    @Override
    public String toString() {
        return "CaesarEncoder";
    }
}
