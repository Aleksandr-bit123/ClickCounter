package com.maksimov.сlickсounter.dto;

import lombok.Data;

@Data
public class ClickCounter {
    private Long id;
    private Long counter;

    public ClickCounter() {
        this.id = 1L;
        this.counter = 0L;
    }

    public ClickCounter(Long counter) {
        this.id = 1L;
        this.counter = counter;
    }

    public void increment() {
        this.counter = this.counter + 1L;
    }
}
