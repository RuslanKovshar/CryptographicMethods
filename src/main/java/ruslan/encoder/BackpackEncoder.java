package ruslan.encoder;

import java.util.ArrayList;
import java.util.List;

public class BackpackEncoder implements EncoderWithoutKey {
    private final int N = 8;
    private List<Integer> W;
    private int q;
    private int r;
    private List<Integer> B;

    @Override
    public String encode(String text) {
        secretSequence();
        int sum = W.stream().mapToInt(Integer::intValue).sum();
        q = (sum + 1) + (int) (Math.random() * 400);
        r = check();
        openSequence();

        List<List<Integer>> binaryRes = new ArrayList<>();
        for (int i = 0; i < text.length(); i++) {
            String binaryString = Integer.toBinaryString(text.charAt(i));
            StringBuilder builder = new StringBuilder(binaryString);
            builder = makeConstSize(builder);

            List<Integer> integers = new ArrayList<>();
            String s = builder.toString();
            for (int j = 0; j < s.length(); j++) {
                char c = s.charAt(j);
                integers.add(Integer.parseInt(c + ""));
            }
            binaryRes.add(integers);
        }

        int sum1;
        List<Integer> result = new ArrayList<>();
        for (List<Integer> integers : binaryRes) {
            sum1 = 0;
            for (int j = 0; j < integers.size(); j++) {
                sum1 += B.get(j) * integers.get(j);
            }
            result.add(sum1);
        }
        return result.stream().collect(StringBuilder::new,
                (sb, value) -> sb.append(value).append(" "),
                StringBuilder::append)
                .toString();
    }

    private StringBuilder makeConstSize(StringBuilder builder) {
        int newSize = N - builder.length();
        builder.reverse();
        for (int i = 0; i < newSize; i++) {
            builder.append('0');
        }
        return builder.reverse();
    }

    private int check() {
        boolean isFound = false;
        int element = 0;
        while (!isFound) {
            element = 1 + (int) (Math.random() * (q - 1));
            if (gcd(q, element) == 1) isFound = true;
        }
        return element;
    }

    private int modInverse() {
        int inverse = 0;
        for (int i = 1; i < q; i++) {
            if ((r * i) % q == 1) {
                inverse = i;
                break;
            }
        }
        return inverse;
    }


    @Override
    public String decode(String text) {
        String[] numbers = text.split(" +");
        List<Integer> integers = new ArrayList<>();
        for (String s : numbers) {
            integers.add(Integer.parseInt(s));
        }

        StringBuilder characters = new StringBuilder();
        for (Integer i : integers) {
            int mod = modInverse();
            int temp = Math.floorMod(i * mod, q);
            StringBuilder builder = new StringBuilder();
            for (int j = W.size() - 1; j >= 0; j--) {
                int w;
                if ((w = temp - W.get(j)) >= 0) {
                    builder.append('1');
                    temp = w;
                } else {
                    builder.append('0');
                }
            }
            String result = builder.reverse().toString();
            char c = (char) Integer.parseInt(result, 2);
            characters.append(c);
        }
        return characters.toString();
    }

    private int gcd(int a, int b) {
        int t;
        while (b != 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    private void secretSequence() {
        W = new ArrayList<>();
        W.add(1 + (int) (Math.random() * 4));
        for (int i = 1; i < N; i++) {
            W.add(W.get(i - 1) + (int) (Math.random() * ((W.get(i - 1) * 2) + 1)));
        }
    }

    private void openSequence() {
        B = new ArrayList<>();
        for (Integer integer : W) {
            B.add(Math.floorMod(integer * r, q));
        }
    }

    @Override
    public String toString() {
        return "BackpackEncoder";
    }
}
