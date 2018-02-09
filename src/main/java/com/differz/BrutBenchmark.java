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
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(key.getBytes());
        byte[] availableChars = "1234567890".getBytes();

        Finder finder = new Finder(hash, availableChars, key.length());
        String result = finder.find();
        finder.stop();
    }
}
