package com.portfolioproject.demo.reservation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.portfolioproject.demo.guest.Guest;
import com.portfolioproject.demo.room.Room;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;


@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private int id;

    @NotBlank
    private String uuid;

    @OneToOne
    @JoinColumn(name = "guest_id")
    @NotNull
    private Guest guest;


    @OneToOne
    @JoinColumn(name = "room_id")
    @NotNull
    private Room room;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate start;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate end;

    public Reservation() {
    }

    public Reservation(@NotNull Guest guest, @NotNull Room room, LocalDate start, LocalDate end) {
        this.guest = guest;
        this.room = room;
        this.start = start;
        this.end = end;
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid.toString();
    }

    public boolean haveConflictWith(LocalDate newStart, LocalDate newEnd) {

        LocalDate oldStart = this.start;
        LocalDate oldEnd = this.end;

        if (newStart.isAfter(oldStart) && newStart.isBefore(oldEnd)) {
            return true;
        }

        if (newEnd.isAfter(newStart) && newEnd.isBefore(oldEnd)) {
            return true;
        }

        if (newStart.isBefore(oldStart) && newEnd.isAfter(oldEnd)) {
            return true;
        }

        if (newStart.isEqual(oldStart) ||
                newStart.isEqual(oldEnd) ||
                newEnd.isEqual(oldStart) ||
                newEnd.isEqual(oldEnd)) {
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }


    public double getTotalCost() {
        double costPerNight = this.room.getCost();
        long days = ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
        return costPerNight * days;
    }
}
