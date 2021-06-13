package com.portfolioproject.demo.room;

import com.portfolioproject.demo.reservation.Reservation;
import com.portfolioproject.demo.reservation.ReservationViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/rooms")
public class RoomViewController {


    private RoomService roomService;

    @Autowired
    public RoomViewController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("roomView")
    public String showRoom(@RequestParam String number, Model model){
        Room room = roomService.readByNumber(number).get();
        model.addAttribute("reservation", ReservationViewController.getCurrentReservation());
        model.addAttribute("choosenRoom", room);



        return "roomView";
    }
}
