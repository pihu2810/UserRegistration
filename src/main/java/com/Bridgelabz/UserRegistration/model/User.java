package com.Bridgelabz.UserRegistration.model;

import com.Bridgelabz.UserRegistration.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "userdata")
@Data
public class User
{
    @Id
    @GeneratedValue
    private int userId;
    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String password;
    private String confirmPassword;
    private String city;

   // @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime tokenCreationDate;
    private String token;

    public User(UserDTO userDTO) {
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.dob = userDTO.getDob();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.confirmPassword = userDTO.getConfirmPassword();
        this.city = userDTO.getCity();
    }

    public User() {

    }

    public User(String id, UserDTO userDTO) {
        this.email = id;
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.confirmPassword = userDTO.getConfirmPassword();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.dob = userDTO.getDob();
        this.city = userDTO.getCity();
    }

    public User(Integer userId, UserDTO userDTO) {
        this.userId = userId;
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.confirmPassword = userDTO.getConfirmPassword();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.dob = userDTO.getDob();
        this.city = userDTO.getCity();
    }

}
