package ru.beerbis.springer.entity;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ProductIdSequence {
    private static AtomicInteger nextId = new AtomicInteger(0);
    public Integer getNext() {
        return nextId.getAndIncrement();
    }
}
