package com.portfolioproject.demo.reservation;

import com.portfolioproject.demo.guest.GuestService;
import com.portfolioproject.demo.room.Room;
import com.portfolioproject.demo.room.RoomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    @Test
    @DisplayName("Should calculate total cost for 5 days for room cost per day 100$")
    void calculateTotalCost() {
        //given
        ReservationRepository reservationRepo = Mockito.mock(ReservationRepository.class);
        GuestService guestService = Mockito.mock(GuestService.class);
        RoomService roomService = Mockito.mock(RoomService.class);
        ReservationService reservationService = new ReservationService(reservationRepo, roomService, guestService);
        //when
        Room room = new Room();
        Reservation reservation = new Reservation();
        room.setCost(100);
        reservation.setStart(LocalDate.of(2021, 6, 10));
        reservation.setEnd(LocalDate.of(2021, 6, 15));
        //then
        assertEquals(reservationService.calculateTotalCost(reservation, room), 500);
    }
}