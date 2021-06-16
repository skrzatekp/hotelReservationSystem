package com.portfolioproject.demo.reservation;

import com.portfolioproject.demo.guest.Guest;
import com.portfolioproject.demo.guest.GuestService;
import com.portfolioproject.demo.guest.GuestViewController;
import com.portfolioproject.demo.room.Room;
import com.portfolioproject.demo.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping(path = "/reservation")
public class ReservationViewController {

    private GuestService guestService;
    private Guest currentGuest;
    private RoomService roomService;
    private Reservation currentReservation;
    private Room currentRoom;
    private ReservationService reservationService;

    @Autowired
    public ReservationViewController(GuestService guestService, RoomService roomService, ReservationService reservationService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.reservationService = reservationService;
    }

    public Reservation getCurrentReservation() {
        return currentReservation;
    }

    @GetMapping(value = "bookRoom")
    String bookRoom(Model model) {
        currentGuest = GuestViewController.getCurrentGuest();
        model.addAttribute("currentGuest", currentGuest);
        model.addAttribute("reservation", currentReservation);
        return "bookRoom";
    }

    @GetMapping(value = "/rooms/nav")
    String goToRoomsByNavigation(Model model) {
        currentGuest = GuestViewController.getCurrentGuest();

        if (currentGuest == null || currentGuest.getPhone() == null) {
            Guest guest = new Guest();
            model.addAttribute("guest", guest);
            currentGuest = guest;
            return "addGuest";
        } else {
            currentGuest = GuestViewController.getCurrentGuest();
            model.addAttribute("currentGuest", currentGuest);
            currentReservation = new Reservation();
            model.addAttribute("reservation", currentReservation);
            return "bookRoom";
        }
    }

    @PostMapping(value = "bookRoom", params = "showRooms")
    String bookRoom(@RequestParam(required = false) String freeRooms, @ModelAttribute Reservation reservation, BindingResult bindingResult, @ModelAttribute Room room, Model model) {

        currentRoom = new Room();
        model.addAttribute("chosenRoom", currentRoom);
        model.addAttribute("currentGuest", currentGuest);

        if (reservation.getStart() == null || reservation.getEnd() == null) {
            bindingResult.addError(new ObjectError("wrongDate", null, null, "Start and equal can't be null"));
            model.addAttribute("startOrEndNull", true);
            return "bookRoom";
        }

        if (!reservation.getStart().isBefore(reservation.getEnd())) {
            bindingResult.addError(new ObjectError("wrongDate", null, null, "Start cant be after end"));
            model.addAttribute("wrongDate", true);
        }

        if (reservation.getStart().isEqual(reservation.getEnd())) {
            bindingResult.addError(new ObjectError("wrongDate", null, null, "Start and end can't be equal"));
            model.addAttribute("theSameDate", true);
        }

        if (reservation.getStart().isBefore(LocalDate.now())) {
            bindingResult.addError(new ObjectError("wrongDate", null, null, "Start and equal can't be in the past"));
            model.addAttribute("inThePast", true);
        }

        if (bindingResult.hasErrors()) {
            return "bookRoom";
        }

        currentReservation = reservation;


        model.addAttribute("reservation", currentReservation);
        model.addAttribute("showingRooms", true);
        if ("true".equals(freeRooms)) {
            model.addAttribute("listOfRooms", roomService.readFreeRooms(currentReservation.getStart(), currentReservation.getEnd()));
        } else {
            model.addAttribute("listOfRooms", roomService.readAllRooms().get());
        }
        currentRoom.setNumber(room.getNumber());
        return "bookRoom";
    }


    @PostMapping(value = "summary")
    String reservationSummary(@RequestParam String number, Model model) {
        currentRoom = roomService.readByNumber(number).get();
        model.addAttribute("currentGuest", currentGuest);
        model.addAttribute("showingRooms", true);
        model.addAttribute("listOfRooms", roomService.readAllRooms().get());
        model.addAttribute("reservation", currentReservation);
        model.addAttribute("chosenRoom", currentRoom);
        model.addAttribute("totalCost", reservationService.calculateTotalCost(currentReservation, currentRoom));

        return "reservationSummary";
    }

    @GetMapping(value = "payment")
    String payment(Model model) {
        if (!guestService.readByUuid(currentGuest.getUuid()).isPresent()) {
            model.addAttribute("guestNotExist", true);
            model.addAttribute("guest", new Guest());
            return "addGuest";
        }

        for (Reservation reservation : reservationService.getAllGuestReservationsFor(currentGuest.getUuid())) {
            if (currentReservation.getStart().equals(reservation.getStart()) &&
                    currentReservation.getEnd().equals(reservation.getEnd()) &&
                    currentRoom.getNumber().equals(reservation.getRoom().getNumber())
            ) {
                model.addAttribute("reservationAlreadySaved", true);
                return "home";
            }
        }

        reservationService.addReservation(currentGuest.getUuid(), currentRoom.getNumber(), currentReservation.getStart(), currentReservation.getEnd());

        return "payment";
    }
}
