package com.portfolioproject.demo.guest;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.portfolioproject.demo.reservation.Reservation;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private int id;

    @NotBlank
    private String uuid;

    @NotBlank(message = "Input first name")
    private String firstName;

    @NotBlank(message = "Input second name")
    private String secondName;

    @Email(regexp = ".+@.+\\..+",
            message = "Input valid email address like: example@gmail.com")
    private String email;

    @Pattern(regexp = "(\\d{9})|(\\d{3} \\d{3} \\d{3})|(\\d{3}-\\d{3}-\\d{3})",
            message = "Input valid phone number like: 123456789 or 123 456 789 or 123-456-789")
    private String phone;

    @OneToMany
    @JoinColumn(name = "guest_id")
    @JsonBackReference
    private Set<Reservation> reservations;

    public Guest() {
        this.setUuid();
        this.setReservations();
    }

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setPhone(String phone) {
        this.phone = phone.trim().replaceAll("-", "").replaceAll(" ", "");
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            this.uuid = uuid.toString();
        }
    }

    public void setReservations() {
        if (this.reservations == null) {
            this.reservations = new HashSet<>();
        }
    }

    public void setReservations2(Set res) {
        if (this.reservations == null) {
            this.reservations = new HashSet<>();
        } else {
            this.reservations = res;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(uuid, guest.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
