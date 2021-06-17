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

    public void incrementCount(){
        this.counter++;
    }

}
