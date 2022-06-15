package com.Bridgelabz.UserRegistration.service;

import com.Bridgelabz.UserRegistration.dto.ResponseDTO;
import com.Bridgelabz.UserRegistration.dto.UserDTO;
import com.Bridgelabz.UserRegistration.dto.UserLoginDTO;
import com.Bridgelabz.UserRegistration.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService
{
    String addUser(UserDTO userDTO);
    List<User> getAllUsers();
    ResponseDTO loginUser(UserLoginDTO userLoginDTO);
    Object forgotPassword(String email);
    Object getUserByEmailId(String email);
    List<User> getAllUserDataByToken(String token);
    User updateRecordById(int  userId, UserDTO userDTO);
    String resetPassword(String token, String password);

}
