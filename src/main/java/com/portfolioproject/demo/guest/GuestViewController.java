package com.portfolioproject.demo.guest;

import com.portfolioproject.demo.reservation.Reservation;
import com.portfolioproject.demo.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping(path = "/guests")
public class GuestViewController {

    private static Guest currentGuest;
    private final GuestService guestService;
    private final ReservationService reservationService;

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
        if (currentGuest == null || currentGuest.getPhone() == null) {
            Guest guest = new Guest();
            model.addAttribute("guest", guest);
            currentGuest = guest;
        } else {
            model.addAttribute("guest", currentGuest);
        }
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
            return goToAccountView(model);
        }
    }

    private String goToAccountView(Model model) {
        model.addAttribute("guest", currentGuest);
        model.addAttribute("actualizeData", false);
        model.addAttribute("reservations",
                reservationService.getAllGuestReservationsFor(currentGuest.getUuid()).stream()
                        .sorted(Comparator.comparing(Reservation::getStart))
                        .collect(Collectors.toList()));
        model.addAttribute("todayDate", LocalDate.now());
        return "guestAccount";
    }

    @PostMapping("register")
    String saveNewGuest(@ModelAttribute(value = "guest") @Valid Guest guest,
                        BindingResult bindingResult,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "addGuest";
        }
        model.addAttribute("guest", guest);
        currentGuest = guest;
        guestService.addGuest(guest);
        return "info";
    }

    @PostMapping(value = "account", params = "accountView")
    String goToGuestAccount(Model model) {
        return goToAccountView(model);
    }

    @PostMapping(value = "account", params = "cancelReservation")
    String cancelReservation(@RequestParam String cancelReservation, Model model) {
        model.addAttribute("guest", currentGuest);
        reservationService.deleteReservation(cancelReservation);
        model.addAttribute("reservations", reservationService.getAllGuestReservationsFor(currentGuest.getUuid()).stream()
                .sorted(Comparator.comparing(Reservation::getStart))
                .collect(Collectors.toList()));
        model.addAttribute("todayDate", LocalDate.now());
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

        if (currentGuest == null) {
            model.addAttribute("alreadyDeleted", true);
            model.addAttribute("guest", new Guest());

            return "addGuest";
        }

        String uuid = currentGuest.getUuid();

        // prevents from deleting testing purpose account
        if (uuid.equals("df520b4c-c172-11eb-8529-0242ac130003")) {
            model.addAttribute("existingAccount", true);
            model.addAttribute("guest", currentGuest);
            model.addAttribute("reservations", reservationService.getAllGuestReservationsFor(currentGuest.getUuid()));
            model.addAttribute("todayDate", LocalDate.now());
            return "guestAccount";
        }

        guestService.deleteGuest(uuid);
        currentGuest = null;
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
    String saveActualizedGuestData(@ModelAttribute(value = "guest") @Valid Guest guest, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("actualizeData", true);
            return "changeGuestAccountData";
        }
        model.addAttribute("guest", currentGuest);

        currentGuest.setFirstName(guest.getFirstName());
        currentGuest.setSecondName(guest.getSecondName());
        if (!currentGuest.getPhone().equals(guest.getPhone())) {
            if (guestService.phoneExists(guest.getPhone())) {
                model.addAttribute("phoneExists", true);
                bindingResult.addError(new ObjectError("phoneExists", null, null, "Phone already exists"));
            } else {
                currentGuest.setPhone(guest.getPhone());
            }
        }

        if (!currentGuest.getEmail().equals(guest.getEmail())) {
            if (guestService.emailExists(guest.getEmail())) {
                model.addAttribute("emailExists", true);
                bindingResult.addError(new ObjectError("emailExists", null, null, "Email already exists"));
            } else {
                currentGuest.setEmail(guest.getEmail());
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("actualizeData", true);
            return "changeGuestAccountData";
        }

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
