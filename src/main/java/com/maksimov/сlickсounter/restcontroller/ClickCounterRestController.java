package com.maksimov.сlickсounter.restcontroller;

import com.maksimov.сlickсounter.service.ClickCounterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClickCounterRestController {
    private final ClickCounterService clickCounterService;

    public ClickCounterRestController(ClickCounterService clickCounterService) {
        this.clickCounterService = clickCounterService;
    }

    @GetMapping(value = "/content")
    public String read() {
        return clickCounterService.read().getCounter().toString();
    }
}
