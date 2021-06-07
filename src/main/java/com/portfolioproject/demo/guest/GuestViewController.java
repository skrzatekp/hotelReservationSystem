package com.portfolioproject.demo.guest;

import com.portfolioproject.demo.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path = "/guests")
public class GuestViewController {

    private static Guest currentGuest;
    private GuestService guestService;


    @Autowired
    public GuestViewController(GuestService guestService) {
        this.guestService = guestService;

    }

    public static Guest getCurrentGuest(){
        return currentGuest;
    }

    @GetMapping("register")
    String registerGuest(Model model) {
        Guest guest = new Guest();
        model.addAttribute("guest", guest);
        currentGuest = guest;
        return "addGuest";
    }

    @PostMapping("register")
    String saveNewGuest(@ModelAttribute(value = "guest") Guest guest, Model model) {

        model.addAttribute("guest", guest);
        currentGuest = guest;
        guestService.addGuest(guest);
        return "info";
    }

    @PostMapping(value = "account", params = "accountView")
    String goToGuestAccount(Model model) {
        model.addAttribute("guest", currentGuest);
        model.addAttribute("actualizeData", false);
        return "guestAccount";
    }



    @GetMapping(value = "account")
    String guestAccount(Model model) {
        model.addAttribute("guest", currentGuest);
        return "guestAccount";
    }


    @GetMapping(value = "account", params = "deleteAccount")
    String deleteGuest(Model model) {

        String uuid = currentGuest.getUuid();
        guestService.deleteGuest(uuid);
        currentGuest = null; //
        model.addAttribute("message", "Your account was deleted");
        return "index";
    }

    @GetMapping(value = "account", params = "changeData")
    String actualizeGuestData(Model model) {
        model.addAttribute("guest", currentGuest);
        model.addAttribute("actualizeData", true);
        return "changeGuestAccountData";
    }

    @PostMapping(value = "account", params = "saveChanges")
    String saveActualizedGuestData(@ModelAttribute(value = "guest") Guest guest, Model model) {

        model.addAttribute("guest", currentGuest);
        currentGuest.setFirstName(guest.getFirstName());
        currentGuest.setSecondName(guest.getSecondName());
        currentGuest.setEmail(guest.getEmail());
        currentGuest.setPhone(guest.getPhone());
        guestService.actualizeGuestData(currentGuest);

        model.addAttribute("actualizeData", false);
        return "guestAccount";
    }


}
