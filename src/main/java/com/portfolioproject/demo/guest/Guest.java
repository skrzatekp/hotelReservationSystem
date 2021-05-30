package com.portfolioproject.demo.guest;

import com.portfolioproject.demo.reservation.Reservation;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Set;


@Entity
@Table(name = "guests")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "guest_id")
    private int id;

    @NotBlank
    private String uuid;

    @NotBlank(message = "Input first name")
    private String firstName;

    @NotBlank(message = "Input second name")
    private String secondName;

    @Email(regexp = ".+@.+\\..+",
            message = "Input valid email address like:  example@gmail.com")
    private String email;

    @Pattern(regexp = "(\\d{9})|(\\d{3} \\d{3} \\d{3})|(\\d{3}-\\d{3}-\\d{3})",
            message = "Input valid phone number lke: 123456789 or 123 456 789 or 123-456-789")
    private String phone;

    @OneToMany
    @JoinColumn(name = "guest_id")
    private Set<Reservation> reservations;

    public Guest() {
    }
}
