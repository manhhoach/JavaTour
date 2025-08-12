package com.manhhoach.JavaTour.helpers;

import java.util.Random;

public class StringGenerate {
    private static final String BASE62_ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = BASE62_ALPHABET.length();
    private static final int CODE_LENGTH = 6;
    private static final Random random = new Random();

    private static String encodeBase62(long number) {
        if (number == 0) {
            return String.valueOf(BASE62_ALPHABET.charAt(0));
        }

        StringBuilder encoded = new StringBuilder();
        while (number > 0) {
            int remainder = (int) (number % BASE);
            encoded.append(BASE62_ALPHABET.charAt(remainder));
            number = number / BASE;
        }
        return encoded.reverse().toString();
    }

    public static String generateShortCode(long id){
        String base62 = encodeBase62(id);
        while (base62.length() < CODE_LENGTH) {
            char randomChar = BASE62_ALPHABET.charAt(random.nextInt(BASE));
            base62 = randomChar + base62;
        }
        return base62;
    }
}
