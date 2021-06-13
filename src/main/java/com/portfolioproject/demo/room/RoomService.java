package com.portfolioproject.demo.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Optional<List<Room>> readAllRooms() {
        return Optional.of(roomRepository.findAll());
    }

    public List<Room> readFreeRooms(LocalDate start, LocalDate end) {
        Optional<List<Room>> allRooms =  Optional.of(roomRepository.findAll());
        List<Room> freeRooms = new ArrayList<>();

        if(allRooms.isPresent()){
            for (Room room: allRooms.get()) {
                if(room.isRoomFree(start, end)){
                    freeRooms.add(room);
                }
            }
        }
        return freeRooms;
    }


    public Optional<Room> readByNumber(String number) {
        return roomRepository.findByNumber(number);
    }


    // TODO add a feature of temporarily block room for reservations

    @Transactional
    void deleteRoom(String number) {
        //TODO add feature: you can't delete room when room has open reservations
        roomRepository.deleteByNumber(number);
    }


    @Transactional
    Room addRoom(Room room) {
        if (roomRepository.findByNumber(room.getNumber()).isPresent()) {
            //TODO throw room by given number already exist
            throw new IllegalArgumentException("room by given number already exist");
        } else {
            roomRepository.save(room);
            return roomRepository.findByNumber(room.getNumber()).get();
        }
    }

    @Transactional
    Optional<Room> actualizeRoomData(Room room) {

        if (roomRepository.findById(room.getId()).isEmpty()) {
            return Optional.empty();
            //TODO throw no such room
            // TODO Refactor that if statements
        } else {
            Room updatedRoom = roomRepository.findById(room.getId()).get();

            updatedRoom.setBeds(room.getBeds());
            updatedRoom.setFloor(room.getFloor());
            updatedRoom.setNumber(room.getNumber());

            return Optional.of(roomRepository.save(updatedRoom));
        }
    }

    boolean roomAlreadyExist(Room room) {
        return roomRepository.findByNumber(room.getNumber()).isPresent();
    }

//    double getCostPerNight(String roomNumber){
//        if(roomRepository.existsByNumber(roomNumber)){
//            return roomRepository.findByNumber(roomNumber).get().getCost();
//        } else {
//            throw new IllegalArgumentException("No room with given number");
//        }
//    }





}
