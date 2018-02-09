package com.differz;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Finder {
    private static final int THREADS = 4;
    private ExecutorService executor;
    private byte[] hash;
    private byte[] chars;
    private int length;

    public Finder(byte[] hash, byte[] chars, int length) {
        this.executor = Executors.newFixedThreadPool(THREADS);
        this.hash = hash;
        this.chars = chars;
        this.length = length;
    }

    public String find() throws InterruptedException, ExecutionException {
        List<Callable<String>> todo = new ArrayList<>(THREADS);
        int max = (int) Math.pow(chars.length, length);
        int delta = max / THREADS;

        for (int i = 0; i < THREADS; i++) {
            int start = i * delta;
            int stop = start + delta;
            if (i == THREADS - 1) {
                stop = max;
            }
            todo.add(new FindTask(executor, start, stop, hash, chars, length));
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
