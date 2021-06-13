package com.portfolioproject.demo.reservation;

import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    @Test
    void checksIfReservationHaveConflictWithNewReservationStart() {
        //given
        Reservation reservation = new Reservation();
        //when
        reservation.setStart(LocalDate.of(2021, 6, 10));
        reservation.setEnd(LocalDate.of(2021, 6, 20));
        LocalDate newStart = LocalDate.of(2021, 6, 15);
        LocalDate newEnd = LocalDate.of(2021, 6, 22);
        //then
        assertTrue(reservation.haveConflictWith(newStart,newEnd));
    }

    @Test
    void checksIfReservationHaveConflictWithNewReservationEnd() {
        //given
        Reservation reservation = new Reservation();
        //when
        reservation.setStart(LocalDate.of(2021, 6, 10));
        reservation.setEnd(LocalDate.of(2021, 6, 20));
        LocalDate newStart = LocalDate.of(2021, 6, 9);
        LocalDate newEnd = LocalDate.of(2021, 6, 19);
        //then
        assertTrue(reservation.haveConflictWith(newStart,newEnd));
    }

    @Test
    void checksIfReservationHaveConflictWithNewReservation() {
        //given
        Reservation reservation = new Reservation();
        //when
        reservation.setStart(LocalDate.of(2021, 6, 10));
        reservation.setEnd(LocalDate.of(2021, 6, 20));
        LocalDate newStart = LocalDate.of(2021, 6, 9);
        LocalDate newEnd = LocalDate.of(2021, 6, 22);
        //then
        assertTrue(reservation.haveConflictWith(newStart,newEnd));
    }

    @Test
    void checksIfReservationHaveNoConflictWithNewReservation() {
        //given
        Reservation reservation = new Reservation();
        //when
        reservation.setStart(LocalDate.of(2021, 6, 10));
        reservation.setEnd(LocalDate.of(2021, 6, 20));
        LocalDate newStart = LocalDate.of(2021, 6, 21);
        LocalDate newEnd = LocalDate.of(2021, 6, 23);
        //then
        assertFalse(reservation.haveConflictWith(newStart,newEnd));
    }
}