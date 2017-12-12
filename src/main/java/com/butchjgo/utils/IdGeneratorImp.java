package com.butchjgo.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGeneratorImp implements IdGenerator {
    AtomicInteger atomicInteger = new AtomicInteger();

    @Override
    public String generate() {
        String res =
                String.format("SE%04d", atomicInteger.incrementAndGet());
        return res;
    }
}
