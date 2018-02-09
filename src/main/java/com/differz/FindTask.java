package com.differz;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class FindTask implements Callable<String> {
    private ExecutorService executor;
    private int start;
    private int stop;
    private byte[] hash;
    private byte[] chars;
    private int length;

    public FindTask(ExecutorService executor, int start, int stop, byte[] hash, byte[] chars, int length) {
        this.executor = executor;
        this.start = start;
        this.stop = stop;
        this.hash = hash;
        this.chars = chars;
        this.length = length;
    }

    @Override
    public String call() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        int base = chars.length;
        byte[] buf = new byte[length];

        for (int i = start; i < stop; i++) {
            if (executor.isShutdown()) {
                break;
            }

            int num = i;
            for (int j = 0; j < length; j++) {
                buf[j] = chars[num % base];
                num /= base;
            }

            if (Arrays.equals(hash, digest.digest(buf))) {
                executor.shutdownNow();
                return new String(buf, "UTF-8");
            }
        }
        return "";
    }
}
