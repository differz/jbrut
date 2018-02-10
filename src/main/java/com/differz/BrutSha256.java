package com.differz;

import java.security.MessageDigest;

public class BrutSha256 {

    public static void main(String[] args) throws Exception {
        String key = "99999999";
        String from = "1234567890";
        String type = "SHA-256";

        MessageDigest digest = MessageDigest.getInstance(type);
        byte[] hash = digest.digest(key.getBytes());
        byte[] chars = from.getBytes();

        long start = System.nanoTime();

        Finder finder = new Finder(type, hash, chars, key.length());
        String result = finder.find();
        finder.stop();

        long end = System.nanoTime();
        double difference = (end - start) / 1e6;
        System.out.printf("Result is: %s\n", result);
        System.out.printf("Search complete in %f ms\n", difference);
    }
}
