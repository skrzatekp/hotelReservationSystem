package com.portfolioproject.demo.guest;

import com.portfolioproject.demo.reservation.Reservation;
import com.portfolioproject.demo.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping(path = "/guests")
public class GuestViewController {

    private static Guest currentGuest;
    private GuestService guestService;
    private ReservationService reservationService;


    @Autowired
    public GuestViewController(GuestService guestService, ReservationService reservationService) {
        this.guestService = guestService;

        this.reservationService = reservationService;
    }

    public static Guest getCurrentGuest() {
        return currentGuest;
    }

    @GetMapping("register")
    String registerGuest(Model model) {
        Guest guest = new Guest();
        model.addAttribute("guest", guest);
        currentGuest = guest;
        return "addGuest";
    }

    @GetMapping("account/nav")
    String goToAccountByNavigation(Model model) {
        if (currentGuest == null || currentGuest.getPhone() == null) {
            Guest guest = new Guest();
            model.addAttribute("guest", guest);
            currentGuest = guest;
            return "addGuest";
        } else {
            model.addAttribute("guest", currentGuest);
            model.addAttribute("actualizeData", false);
            model.addAttribute("reservations", reservationService.getAllGuestReservationsFor(currentGuest.getUuid()).stream()
                    .sorted(Comparator.comparing(Reservation::getStart))
                    .collect(Collectors.toList()));
            model.addAttribute("todayDate", LocalDate.now());
            return "guestAccount";
        }

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
        model.addAttribute("reservations", reservationService.getAllGuestReservationsFor(currentGuest.getUuid()).stream()
                .sorted(Comparator.comparing(Reservation::getStart))
                .collect(Collectors.toList()));
        model.addAttribute("todayDate", LocalDate.now());
        return "guestAccount";
    }


    @PostMapping(value = "account", params = "cancelReservation")
    String cancelReservation(@RequestParam String cancelReservation, Model model) {
        model.addAttribute("guest", currentGuest);
//        model.addAttribute("actualizeData", false);
        reservationService.deleteReservation(cancelReservation);
        model.addAttribute("reservations", reservationService.getAllGuestReservationsFor(currentGuest.getUuid()).stream()
                .sorted(Comparator.comparing(Reservation::getStart))
                .collect(Collectors.toList()));
        model.addAttribute("todayDate", LocalDate.now());
//        currentGuest.setReservations2(new HashSet(reservationService.getAllGuestReservationsFor(currentGuest.getUuid())));
        return "guestAccount";
    }


    @GetMapping(value = "account")
    String guestAccount(Model model) {
        model.addAttribute("guest", currentGuest);
        model.addAttribute("reservations", currentGuest.getReservations().stream()
                .sorted(Comparator.comparing(Reservation::getStart))
                .collect(Collectors.toList()));
        return "guestAccount";
    }

    @GetMapping(value = "account/existing")
    String getExistingGuestAccount(Model model) {
        currentGuest = guestService.readByUuid("df520b4c-c172-11eb-8529-0242ac130003").get();
        model.addAttribute("guest", currentGuest);
        model.addAttribute("reservations", currentGuest.getReservations()
                .stream()
                .sorted(Comparator.comparing(Reservation::getStart))
                .collect(Collectors.toList()));
        model.addAttribute("todayDate", LocalDate.now());
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
        model.addAttribute("todayDate", LocalDate.now());
        currentGuest.setReservations2(new HashSet(reservationService.getAllGuestReservationsFor(currentGuest.getUuid())));
        model.addAttribute("reservations", reservationService.getAllGuestReservationsFor(currentGuest.getUuid()).stream()
                .sorted(Comparator.comparing(Reservation::getStart))
                .collect(Collectors.toList()));
        return "guestAccount";
    }


}
