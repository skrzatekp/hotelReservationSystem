package com.portfolioproject.demo.guest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
interface GuestRepository extends JpaRepository<Guest, Integer> {

    Optional<Guest> findByUuid(String uuid);

    Optional<Guest> findByPhone(String phone);

    Optional<Guest> findByEmail(String email);

    Optional<Guest> findByFirstNameAndSecondName(String firstName, String secondName);

    void deleteByUuid(String uuid);

    @Override
    Guest save(Guest guest);


}
