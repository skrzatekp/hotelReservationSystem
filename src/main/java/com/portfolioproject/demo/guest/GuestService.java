package com.portfolioproject.demo.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {
    private final GuestRepository guestRepository;

    @Autowired
    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }


    public Optional<List<Guest>> readAllGuests() {
        return Optional.of(guestRepository.findAll());
    }

    public Optional<Guest> readByUuid(String uuid) {
        return guestRepository.findByUuid(uuid);
    }

    Optional<Guest> readByPhone(String phone) {
        return guestRepository.findByPhone(phone);
    }

    Optional<Guest> readByEmail(String email) {
        return guestRepository.findByEmail(email);
    }

    Optional<Guest> readByName(String firstName, String secondName) {
        return guestRepository.findByFirstNameAndSecondName(firstName, secondName);
    }

    @Transactional
    void deleteGuest(String uuid) {
        //TODO add feature: you can't delete guest when he has open reservations
        guestRepository.deleteByUuid(uuid);
    }

    @Transactional
    Guest addGuest(Guest guest) {
        guestRepository.save(guest);
        return guestRepository.findByPhone(guest.getPhone()).get();
    }

    @Transactional
    Optional<Guest> actualizeGuestData(Guest guest) {

        if (guestRepository.findById(guest.getId()).isEmpty()) {
            return Optional.empty();
            //TODO add some informations that uuid can't be changed
            //TODO add some informations or exceptions that email or phone number already exist
            //TODO throw no such Guest
            // TODO Refactor that if statements
        } else {
            Guest updatedGuest = guestRepository.findById(guest.getId()).get();

            if (guest.getPhone() != null && !guest.getPhone().isEmpty()) {
                updatedGuest.setPhone(guest.getPhone());
            }

            if (guest.getEmail() != null && !guest.getEmail().isEmpty()) {
                updatedGuest.setEmail(guest.getEmail());
            }

            if (guest.getFirstName() != null && !guest.getFirstName().isEmpty()) {
                updatedGuest.setFirstName(guest.getFirstName());
            }

            if (guest.getSecondName() != null && !guest.getSecondName().isEmpty()) {
                updatedGuest.setSecondName(guest.getSecondName());
            }

            return Optional.of(guestRepository.save(updatedGuest));
        }
    }

    boolean phoneOrEmailAlreadyExists(Guest guest) {
        boolean phoneExist = guestRepository.findByPhone(guest.getPhone()).isPresent();
        boolean emailExist = guestRepository.findByEmail(guest.getEmail()).isPresent();
        return phoneExist || emailExist;
    }


}
