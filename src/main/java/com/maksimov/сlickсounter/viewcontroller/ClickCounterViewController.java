package com.maksimov.сlickсounter.viewcontroller;

import com.maksimov.сlickсounter.dto.ClickCounter;
import com.maksimov.сlickсounter.service.ClickCounterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClickCounterViewController {
    private final ClickCounterService clickCounterService;

    public ClickCounterViewController(ClickCounterService clickCounterService) {
        this.clickCounterService = clickCounterService;
    }

    @GetMapping("/")
    public String readClickCounter(Model model) {
        ClickCounter clickCounter = clickCounterService.read();
        if (clickCounter != null) {
            model.addAttribute("counter", clickCounter.getCounter());
        } else {
            model.addAttribute("counter", "error");
        }
        return "clickCounter";
    }

    @PostMapping("/")
    public String updateClickCounter(Model model) {
        if (clickCounterService.update()) {
            return "redirect:/";
        }
        model.addAttribute("counter", "error");
        return "clickCounter";
    }
}
