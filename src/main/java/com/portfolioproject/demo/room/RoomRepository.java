package com.portfolioproject.demo.room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByNumber(String number);

    void deleteByNumber(String number);

    boolean existsByNumber(String number);

    @Override
    Room save(Room room);

}
