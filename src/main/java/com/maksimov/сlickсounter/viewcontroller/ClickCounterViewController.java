package com.maksimov.сlickсounter.viewcontroller;

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
        model.addAttribute("counter", clickCounterService.read().getCounter());
        return "clickCounter";
    }

    @PostMapping("/")
    public String updateClickCounter() {
        clickCounterService.update();
        return "redirect:/";
    }
}
