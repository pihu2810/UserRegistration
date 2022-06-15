package com.Bridgelabz.UserRegistration.controller;

import com.Bridgelabz.UserRegistration.dto.ResponseDTO;
import com.Bridgelabz.UserRegistration.dto.UserDTO;
import com.Bridgelabz.UserRegistration.dto.UserLoginDTO;
import com.Bridgelabz.UserRegistration.model.User;
import com.Bridgelabz.UserRegistration.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController
{
    @Autowired
    private IUserService iUserservice;

    //Ability to call api to register user record
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@Valid @RequestBody UserDTO userDTO){
        String newUser= iUserservice.addUser(userDTO);
        ResponseDTO responseDTO=new ResponseDTO("User Registered Successfully",newUser);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }

    //Ability to call api to get all user record
    @GetMapping( "/getAll")
    public ResponseEntity<String> getAllUser() {
        List<User> listOfUsers = iUserservice.getAllUsers();
        ResponseDTO dto = new ResponseDTO("User retrieved successfully (:",listOfUsers);
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    //Ability to call api to get user record
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<ResponseDTO>(iUserservice.loginUser(userLoginDTO),HttpStatus.OK);
    }



    //Ability to call api to get  user record by emailId
    @GetMapping("/getByEmailId/{emailId}")
    public ResponseEntity<ResponseDTO> getUserByEmailId(@PathVariable("emailId") String email) {
        return new ResponseEntity<ResponseDTO>( new
                ResponseDTO("Get User Data by Email",
                iUserservice.getUserByEmailId(email)), HttpStatus.OK);
    }

    //Ability to call api to get  user record by token
    @GetMapping("/getByToken/token")
    public ResponseEntity<ResponseDTO> getAllUserDataByToken(@PathVariable("token") String token){
        return new ResponseEntity<ResponseDTO>( new
                ResponseDTO("Get User Data by token",
                iUserservice.getAllUserDataByToken(token)), HttpStatus.OK);
    }
    //Ability to call api to Update  user record by id
    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateRecordById(@PathVariable int  userId,@Valid @RequestBody UserDTO userDTO){
        User entity = iUserservice.updateRecordById(userId,userDTO);
        ResponseDTO dto = new ResponseDTO("User Record updated successfully",entity);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDTO> forgotPassword(@RequestParam String email){
        Object user = iUserservice.forgotPassword(email);
        ResponseDTO response = new ResponseDTO("Check your email to change the password", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String token, @RequestParam String password){

        String user = iUserservice.resetPassword(token, password);
        ResponseDTO response = new ResponseDTO("Reset Password", user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
