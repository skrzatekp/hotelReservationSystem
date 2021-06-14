package com.portfolioproject.demo.room;

import com.portfolioproject.demo.reservation.ReservationViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/rooms")
public class RoomViewController {

    private RoomService roomService;
    private ReservationViewController reservationViewController;

    @Autowired
    public RoomViewController(RoomService roomService, ReservationViewController reservationViewController, ReservationViewController reservationViewController1) {
        this.roomService = roomService;
        this.reservationViewController = reservationViewController1;
    }

    @PostMapping("roomView")
    public String showRoom(@RequestParam String number, Model model) {
        Room room = roomService.readByNumber(number).get();
        model.addAttribute("reservation", reservationViewController.getCurrentReservation());
        model.addAttribute("choosenRoom", room);
        return "roomView";
    }
}
