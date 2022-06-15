package com.Bridgelabz.UserRegistration.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


public @Data class UserDTO
{
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Address book is FirstNameInvalid ")
    @NotEmpty(message = "first name cannot be null")
    private String firstName;
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Address book is LastNameInvalid ")
    @NotEmpty(message = "last name cannot be null")
    private String lastName;
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "date of birth must be in YYYY-MM-DD format")
    private String dob;
  @Email(message = "Insert valid email")
    private String email;
    @NotBlank(message = "Please enter your password")
    private String password;
    private String confirmPassword;
    @Pattern(regexp = "[A-Za-z\\s]+", message = "First letter of City should be in upperCase")
    private String city;


}
