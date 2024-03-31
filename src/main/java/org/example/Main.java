package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    public static AtomicInteger firstCount = new AtomicInteger();
    public static AtomicInteger secondCount = new AtomicInteger();
    public static AtomicInteger thridCount = new AtomicInteger();
    public static List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindrom = new Thread(() -> {
            for (String nickname : texts) {
                if (nickname.contentEquals(new StringBuilder(nickname).reverse())){
                    operation(nickname);
                }
            }
        });
        threads.add(palindrom);

        Thread same = new Thread(() -> {
            for (String nickname : texts) {
                if (isSame(nickname)) {
                    operation(nickname);
                }
            }
        });
        threads.add(same);

        Thread alphabet = new Thread(() -> {
            for (String nickname : texts) {
                if (isAlphabet(nickname)) {
                    operation(nickname);
                }
            }
        });
        threads.add(alphabet);

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(
                "Красивых слов с длиной 3: " + firstCount + "шт\n" +
                "Красивых слов с длиной 4: " + secondCount + "шт\n" +
                "Красивых слов с длиной 5: " + thridCount + "шт"
        );
    }
    public static boolean isAlphabet (String nickname) {
        for (int i = 1; i < nickname.length(); i++) {
            if (nickname.charAt(i) < nickname.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
    public static boolean isSame (String nickname) {
        for (int i = 1; i < nickname.length(); i++) {
            if (nickname.charAt(i) != nickname.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }
    public static void operation (String nickname) {
        int operation = nickname.length();
        switch (operation) {
            case 3:
                firstCount.getAndIncrement();
                break;
            case 4:
                secondCount.getAndIncrement();
                break;
            case 5:
                thridCount.getAndIncrement();
                break;
            default:
                break;
        }
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}