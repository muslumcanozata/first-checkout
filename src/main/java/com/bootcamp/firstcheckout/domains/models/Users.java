package com.bootcamp.firstcheckout.domains.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Email(message = "E-Mail should be valid")
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Pattern(regexp="(^$|[0-9]{10})")
    @Column(name = "phone_number")
    private String phoneNumber;
}
