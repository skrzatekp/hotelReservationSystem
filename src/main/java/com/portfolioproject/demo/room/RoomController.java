package com.portfolioproject.demo.room;


import com.portfolioproject.demo.guest.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @GetMapping("all")
    ResponseEntity<List<Room>> readAllRooms() {
        Optional<List<Room>> response = roomService.readAllRooms();
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<?> deleteRoomByNumber(@RequestParam String number) {
        roomService.deleteRoom(number);
        return ResponseEntity.ok().build();
    }


    @Transactional
    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> addNewRoom(@RequestBody Room room) {
        if (roomService.roomAlreadyExist(room)) {
            //TODO change to throwing "Room already exist exception"
            return ResponseEntity.status(400).build();
        } else {
            roomService.addRoom(room);
            return ResponseEntity.ok(roomService.readByNumber(room.getNumber()).get());
        }
    }


    @Transactional
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> actualizeRoomData(@RequestBody Room room) {
        Optional<Room> actualizedRoom = roomService.actualizeRoomData(room);
        return actualizedRoom.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }


}
