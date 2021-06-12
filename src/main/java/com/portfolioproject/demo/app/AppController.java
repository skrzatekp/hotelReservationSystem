package com.portfolioproject.demo.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;

@Controller
public class AppController {

    @Transactional
    @GetMapping(value = "index")
    String mainPage() {
        return "index";
    }


    @GetMapping(value = "about")
    String aboutPage() {
        return "about";
    }


}
