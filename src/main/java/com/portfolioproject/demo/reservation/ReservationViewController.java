package com.portfolioproject.demo.reservation;

import com.portfolioproject.demo.guest.Guest;
import com.portfolioproject.demo.guest.GuestService;
import com.portfolioproject.demo.guest.GuestViewController;
import com.portfolioproject.demo.room.Room;
import com.portfolioproject.demo.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/reservation")
public class ReservationViewController {

    //po testach zmienić na currentGuest = GuestViewController.getCurrentGuest();

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


    @GetMapping(value = "bookRoom")
    String bookRoom(Model model) {
        currentGuest = GuestViewController.getCurrentGuest();
//        currentGuest = guestService.readByUuid("df520b4c-c172-11eb-8529-0242ac130003").get();
        model.addAttribute("currentGuest", currentGuest);
        currentReservation = new Reservation();
        model.addAttribute("reservation", currentReservation);
        return "bookRoom";
    }

    @PostMapping(value = "bookRoom", params = "showRooms")
    String bookRoom(@RequestParam(required = false) String freeRooms,  @ModelAttribute Reservation reservation,@ModelAttribute Room room, Model model) {
        currentRoom = new Room();
        model.addAttribute("chosenRoom", currentRoom);
        model.addAttribute("currentGuest", currentGuest);
        currentReservation = reservation;
        model.addAttribute("reservation", currentReservation);
        model.addAttribute("showingRooms", true);
        if("true".equals(freeRooms)){
            model.addAttribute("listOfRooms", roomService.readFreeRooms(currentReservation.getStart(), currentReservation.getEnd()));
        } else{
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

        return "reservationSummary";
    }

    @GetMapping(value = "payment")
    String payment(Model model) {
        reservationService.addReservation(currentGuest.getUuid(), currentRoom.getNumber(), currentReservation.getStart(), currentReservation.getEnd());

        return "payment";
    }


}