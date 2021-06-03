package com.portfolioproject.demo.reservation;

import com.portfolioproject.demo.guest.Guest;
import com.portfolioproject.demo.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findByUuid(String uuid);

    Optional<List<Reservation>> findByGuest(Guest guest);

    Optional<List<Reservation>> findByRoom(Room room);

    @Override
    Reservation save(Reservation reservation);

    void deleteByUuid(String uuid);

}
