package com.portfolioproject.demo.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/guests")
public class GuestController {

    private final GuestService guestService;

    @Autowired
    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping("all")
    ResponseEntity<List<Guest>> readAllGuests() {
        Optional<List<Guest>> response = guestService.readAllGuests();
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("uuid")
    ResponseEntity<Guest> readGuestByUuid(@RequestParam String uuid) {
        Optional<Guest> response = guestService.readByUuid(uuid);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).build());
    }

    @GetMapping("phone")
    ResponseEntity<Guest> readGuestByPhone(@RequestParam String phone) {
        String preparedPhone = phone.trim().replaceAll(" ", "").replaceAll("-", "");
        Optional<Guest> response = guestService.readByPhone(preparedPhone);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).build());
    }

    @GetMapping("email")
    ResponseEntity<Guest> readGuestByEmail(@RequestParam String email) {
        Optional<Guest> response = guestService.readByEmail(email);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).build());
    }

    @GetMapping("name")
    ResponseEntity<Guest> readGuestByName(@RequestParam String firstName, String secondName) {
        Optional<Guest> response = guestService.readByName(firstName, secondName);
        return response.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).build());
    }

    //prevent from getting all users by "/guest" address
    @GetMapping
    ResponseEntity<List<Guest>> getEmptyPage() {
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @DeleteMapping("uuid")
    public ResponseEntity<?> deleteGuestByUuid(@RequestParam String uuid) {
        if (guestService.readByUuid(uuid).isPresent()) {
            guestService.deleteGuest(uuid);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @Transactional
    @PostMapping(value = "add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Guest> addNewGuest(@RequestBody Guest guest) {
        if (guestService.phoneOrEmailAlreadyExists(guest)){
            //TODO change to throwing "User already exist exception" or "User with given data already exist"
            return ResponseEntity.status(400).build();
        } else {
            guestService.addGuest(guest);
            return ResponseEntity.ok(guestService.readByPhone(guest.getPhone()).get());
        }
    }


    
    @Transactional
    @PatchMapping( consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Guest> actualizeGuestData(@RequestBody Guest guest) {
        Optional<Guest> actualizedGuest = guestService.actualizeGuestData(guest);
        return actualizedGuest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

}


// TODO change returning objects from Guest to Guest DTO -> I don't want to return id number from database