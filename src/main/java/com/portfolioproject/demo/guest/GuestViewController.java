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
        model.addAttribute("guest", new Guest());
        return "addGuest";
    }

    @PostMapping("register")
    String readAllGuests2(Guest guest, Model model) {
        guestService.addGuest(guest);
        model.addAttribute("guest", guest);
        return "info";
    }



}
