package com.portfolioproject.demo.app;

import com.portfolioproject.demo.guest.GuestViewController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping(value = {"index", ""})
    String register() {
        if (GuestViewController.getCurrentGuest() != null) {
            GuestViewController.resetCurrentGuest();
        }
        return "index";
    }

    @GetMapping(value = "home")
    String homePage() {
        return "home";
    }

    @GetMapping(value = "about")
    String aboutPage() {
        return "about";
    }

    @GetMapping(value = "about2")
    String about2Page() {
        return "about2";
    }

}
