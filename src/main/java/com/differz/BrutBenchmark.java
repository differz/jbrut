package com.differz;

import org.openjdk.jmh.annotations.*;

import java.security.MessageDigest;

@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(3)
@State(Scope.Benchmark)
public class BrutBenchmark {

    @Benchmark
    public void testFinder() throws Exception {
        String key = "999999";
        String from = "1234567890";
        String type = "SHA-256";

        MessageDigest digest = MessageDigest.getInstance(type);
        byte[] hash = digest.digest(key.getBytes());
        byte[] chars = from.getBytes();

        Finder finder = new Finder(type, hash, chars, key.length());
        String result = finder.find();
        finder.stop();
    }
}
