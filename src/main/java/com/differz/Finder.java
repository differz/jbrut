package com.differz;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Finder {
    private final int threads;
    private final ExecutorService executor;
    private final String type;
    private final byte[] hash;
    private final byte[] chars;
    private final int length;

    public Finder(String type, byte[] hash, byte[] chars, int length) {
        this.threads = Runtime.getRuntime().availableProcessors();
        this.executor = Executors.newFixedThreadPool(threads);
        this.type = type;
        this.hash = hash;
        this.chars = chars;
        this.length = length;
    }

    public String find() throws Exception {
        List<Callable<String>> todo = new ArrayList<>(threads);
        int max = (int) Math.pow(chars.length, length);
        int delta = max / threads;

        for (int i = 0; i < threads; i++) {
            int start = i * delta;
            int stop = start + delta;
            if (i == threads - 1) {
                stop = max;
            }
            todo.add(new FindTask(executor, MessageDigest.getInstance(type), start, stop, hash, chars, length));
        }

        for (Future<String> future : executor.invokeAll(todo)) {
            String s = future.get();
            if (!s.isEmpty()) {
                return s;
            }
        }
        return "";
    }

    public void stop() {
        executor.shutdown();
    }
}
