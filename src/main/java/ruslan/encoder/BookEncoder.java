package ruslan.encoder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookEncoder implements EncoderWithoutKey {

    private final String text = "А жінка буває на осінь так схожа:\n" +
            "То тиха й привітна, а то й непогожа.\n" +
            "То скропить сльозою, то сонцем засвітить.\n" +
            "То прагне зими, то вертається в літо.\n" +
            "\n" +
            "А жінка, як осінь, плодами багата\n" +
            "На ніжність, добро, материнство і святість.\n" +
            "Як вересень тихий, зігріє душею,\n" +
            "Не страшно морозу чекати із нею.\n" +
            "\n" +
            "А жінка буває тривожна, як осінь.\n" +
            "То дихає вітром, то ласки попросить,\n" +
            "То болю завдасть, а то вигоїть рани,\n" +
            "Листочком у світ полетить за коханим.\n" +
            "\n" +
            "А осінь в природі – це завжди, як диво,\n" +
            "Так само і жінка: буває вродлива.\n" +
            "Буває примхлива, буває зрадлива...\n" +
            "Нехай тільки кожна з них буде щаслива.\n" +
            "\n" +
            "Промінь сонця запалить ранкову росу, –\n" +
            "І заграє вона, забринить веселково.\n" +
            "Скільки мовлено вже про одвічну красу,\n" +
            "Тільки я про жіночу скажу своє слово.\n" +
            "\n" +
            "Ніжний погляд і довга русява коса\n" +
            "Та смаглявого личка привабна родзинка...\n" +
            "І, хоч кажуть, що світ порятує краса.\n" +
            "Та врятує його тільки жінка.";

    private char[][] chars;
    private int LENGTH;
    private int SIZE;

    private void init() {
        String[] split = text.split("\n");
        String s = Arrays.stream(split).max(Comparator.comparing(String::length)).get();
        SIZE = s.length();
        LENGTH = split.length;
        chars = new char[LENGTH][SIZE];
        int k = 0;
        for (int i = 0; i < LENGTH; i++) {
            while (split[i].length() > k) {
                chars[i][k] = split[i].charAt(k);
                k++;
            }
            k = 0;
        }
    }

    @Override
    public String encode(String text) {
        text = text.toLowerCase();
        init();
        StringBuilder builder = new StringBuilder("{");
        int k = 0;
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (chars[i][j] == text.charAt(k)) {
                    if (builder.length() > 1) builder.append(",");
                    builder.append(i).append("=").append(j);
                    k++;

                }
                if (k >= text.length()) break;
            }
            if (k >= text.length()) break;
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public String decode(String text) {
        init();
        text = text.replaceAll("\\D+", " ");
        String[] strings = text.split(" ");
        StringBuilder builder = new StringBuilder();
        List<Integer> collect = Arrays.stream(strings).filter(s -> !s.equals("")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        for (int i = 0; i < collect.size() - 1; i+=2) {
            builder.append(chars[collect.get(i)][collect.get(i + 1)]);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "BookEncoder";
    }
}
