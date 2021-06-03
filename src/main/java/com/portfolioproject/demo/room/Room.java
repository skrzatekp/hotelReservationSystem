package com.portfolioproject.demo.room;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.portfolioproject.demo.reservation.Reservation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int id;

    @Pattern(regexp = "\\d+")
    private String number;

    @Min(value = 0)
    private int floor;

    @Min(value = 1)
    private int beds;

    @OneToMany
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Set<Reservation> reservations;

    Room() {
    }


    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public int getFloor() {
        return floor;
    }

    public int getBeds() {
        return beds;
    }

    public Set<Reservation> getReservations() {
        if(this.reservations == null){
            this.reservations = new HashSet<>();
        }
        return reservations;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(number, room.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
