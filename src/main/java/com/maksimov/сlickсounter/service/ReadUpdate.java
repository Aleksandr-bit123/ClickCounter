package com.maksimov.сlickсounter.service;

import com.maksimov.сlickсounter.dto.ClickCounter;

public interface ReadUpdate {
    ClickCounter read();
    boolean update();
}
