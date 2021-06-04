package com.portfolioproject.demo.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/guests")
public class GuestViewController {

    private GuestService guestService;

    @Autowired
    public GuestViewController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("register")
    String readAllGuests(Model model) {
        Guest guest = new Guest();
        model.addAttribute("guest", guest);
        return "addGuest.html";
    }

//    @PostMapping(
//            path = "register",
//            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
//    )
//    public String registerGuest(@RequestBody Guest guest) throws Exception {
//
//        return "/guests/add";
//    }
}
