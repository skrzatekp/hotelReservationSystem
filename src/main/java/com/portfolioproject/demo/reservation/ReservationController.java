package com.portfolioproject.demo.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }


    @Transactional
    @PostMapping(value = "add")
    public ResponseEntity<Reservation> addNewReservation(@RequestParam String guestUuid,
                                                         @RequestParam String roomNumber,
                                                         @RequestParam LocalDate start,
                                                         @RequestParam LocalDate end) {
        Reservation r = reservationService.addReservation(guestUuid, roomNumber, start, end);
        return ResponseEntity.ok(r);
    }

    @GetMapping("all")
    ResponseEntity<List<Reservation>> readAllReservations() {
        Optional<List<Reservation>> response = reservationService.readAllReservations();
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("guest/{uuid}")
    ResponseEntity<List<Reservation>> readAllReservationsForGuest(@PathVariable String uuid) {
        List<Reservation> response = reservationService.getAllGuestReservationsFor(uuid);
        return ResponseEntity.ok(response);
    }

    @GetMapping("room/{roomNumber}")
    ResponseEntity<List<Reservation>> readAllReservationsForRoom(@PathVariable String roomNumber) {
        List<Reservation> response = reservationService.getAllRoomReservationsFor(roomNumber);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping()
    public ResponseEntity<?> deleteReservationByUuid(@RequestParam String uuid) {
        if (reservationService.readByUuid(uuid).isPresent()) {
            reservationService.deleteReservation(uuid);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }
}
