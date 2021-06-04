package com.portfolioproject.demo.reservation;

import com.portfolioproject.demo.guest.Guest;
import com.portfolioproject.demo.guest.GuestService;
import com.portfolioproject.demo.room.Room;
import com.portfolioproject.demo.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final GuestService guestService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RoomService roomService, GuestService guestService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.guestService = guestService;
    }

    Reservation addReservation(String guestUuid, String roomNumber, LocalDate start, LocalDate end) {

        Room room;
        if (roomService.readByNumber(roomNumber).isPresent()) {
            room = roomService.readByNumber(roomNumber).get();
        } else {
            //TODO throw not such room Exception
            throw new IllegalArgumentException("No such room");
        }

        Set<Reservation> roomReservations = room.getReservations();

        Guest guest;
        if (guestService.readByUuid(guestUuid).isPresent()) {
            guest = guestService.readByUuid(guestUuid).get();
        } else {
            //TODO throw not such room Exception
            throw new IllegalArgumentException("No such guest");
        }

        for (Reservation res : roomReservations) {
            if (res.reservationsHaveConflict(start, end)) {
                throw new IllegalArgumentException("Reservation have conflict with another reservation");
            }
        }
        Reservation reservation = new Reservation(guest, room, start, end);
        return reservationRepository.save(reservation);
    }


    Optional<List<Reservation>> readAllReservations() {
        return Optional.of(reservationRepository.findAll());
    }


    List<Reservation> getAllGuestReservationsFor(String guestUuid) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> guestReservations = new ArrayList<>();

        for (Reservation r : all) {
            if (guestUuid.equals(r.getGuest().getUuid())) {
                guestReservations.add(r);
            }
        }
        return guestReservations;
    }

    List<Reservation> getAllRoomReservationsFor(String roomNumber) {
        List<Reservation> all = reservationRepository.findAll();
        List<Reservation> roomReservations = new ArrayList<>();

        for (Reservation r : all) {
            if (roomNumber.equals(r.getRoom().getNumber())) {
                roomReservations.add(r);
            }
        }
        return roomReservations;
    }

    @Transactional
    void deleteReservation(String uuid) {
        //TODO add feature: you can't delete reservation when it is i dont know one day before
        reservationRepository.deleteByUuid(uuid);
    }

    public Optional<Reservation> readByUuid(String uuid) {
      return  reservationRepository.findByUuid(uuid);
    }

}