package com.portfolioproject.demo.room;

import com.portfolioproject.demo.reservation.Reservation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "room_id")
    private int id;

    @Pattern(regexp = "\\d+")
    private String number;

    @Min(value = 0)
    @NotBlank
    private int floor;

    @Min(value = 1)
    @NotBlank
    private int beds;

    @OneToMany
    @JoinColumn(name = "room_id")
    private Set<Reservation> reservations;

    Room() {
    }

}
