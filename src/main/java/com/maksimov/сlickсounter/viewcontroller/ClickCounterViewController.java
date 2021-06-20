package com.maksimov.сlickсounter.viewcontroller;

import com.maksimov.сlickсounter.dto.ClickCounter;
import com.maksimov.сlickсounter.service.ClickCounterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ClickCounterViewController {
    private final ClickCounterService clickCounterService;
    String cC = "clickCounter"; // JSP
    String c = "counter"; //Model attribute

    public ClickCounterViewController(ClickCounterService clickCounterService) {
        this.clickCounterService = clickCounterService;
    }


    @GetMapping()
    public String readClickCounter(Model model) {
        ClickCounter clickCounter = clickCounterService.read();
        if (clickCounter != null) {
            model.addAttribute(c, clickCounter.getCounter());
        } else {
            model.addAttribute(c, "error");
        }
        return cC;
    }

    @PostMapping()
    public String updateClickCounter(Model model) {
        try {
            if (clickCounterService.update()) {
                return "redirect:/";
            }
            model.addAttribute(c, "error");
            return cC;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            model.addAttribute(c, "memory is full");
            return cC;
        }
    }
}
