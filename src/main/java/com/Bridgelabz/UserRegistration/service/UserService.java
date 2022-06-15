package com.Bridgelabz.UserRegistration.service;

import com.Bridgelabz.UserRegistration.Util.EmailSenderService;
//import com.Bridgelabz.UserRegistration.Util.TokenUtility;
import com.Bridgelabz.UserRegistration.Util.TokenUtility;
import com.Bridgelabz.UserRegistration.dto.ResponseDTO;
import com.Bridgelabz.UserRegistration.dto.UserDTO;
import com.Bridgelabz.UserRegistration.dto.UserLoginDTO;
import com.Bridgelabz.UserRegistration.exception.UserException;
import com.Bridgelabz.UserRegistration.model.User;
import com.Bridgelabz.UserRegistration.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements IUserService
{
    private static long EXPIRE_TOKEN_AFTER_MINUTES=30;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    EmailSenderService mailService;

    @Autowired
    TokenUtility util;

    @Override
    public String addUser(UserDTO userDTO) {
        User newUser= new User(userDTO);
        userRepository.save(newUser);
        String token = util.createToken(newUser.getUserId());
        mailService.sendEmail(newUser.getEmail(), "Test Email", "Registered SuccessFully, hii: "
                +newUser.getFirstName()+"Please Click here to get data-> "
                +"http://localhost:8009/user/getBy/"+token);
        return token;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> getUsers= userRepository.findAll();
        return getUsers;
    }

    @Override
    public ResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        ResponseDTO dto = new ResponseDTO();
        Optional<User> login = userRepository.findByEmailId(userLoginDTO.getEmail());
        if(login.isPresent()){
            String pass = login.get().getPassword();
            if(pass.equals(userLoginDTO.getPassword())){
                dto.setMessage("login successful ");
                dto.setData(login.get());
                return dto;
            }

            else {
                dto.setMessage("Sorry! login is unsuccessful");
                dto.setData("Wrong password");
                return dto;
            }
        }
        return new ResponseDTO("User not found!","Wrong email");
    }
    @Override
    public Object forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmailId(email);

        String token = userOptional.get().getToken();
        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        } else {
            User user = userOptional.get();
            user.setToken(generateToken());
            user.setTokenCreationDate(LocalDateTime.now());

            user = userRepository.save(user);

            mailService.sendEmail(userOptional.get().getEmail(), "Reset Password", "Hi " + userOptional.get().getFirstName() + "\n"
                    + "You're receiving this email because you requested a password reset \n"
                    + "Click the following link to change the password : " + "http://localhost:8009/user/reset-password?token=" + user.getToken());
        }

        return token;
    }

    public String resetPassword(String token, String password) {
        Optional<User> userOptional = userRepository.findByToken(token);

        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token Expired.";
        }
        User user = userOptional.get();

        user.setPassword(password);
        user.setToken(null);
        user.setTokenCreationDate(null);

        userRepository.save(user);
        return "Your password successfully updated.";
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }

    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

//    @Override
//    public String forgotPassword(String email, String password) {
//        Optional<User> isUserPresent = userRepository.findByEmailId(email);
//
//        if(!isUserPresent.isPresent()) {
//            throw new UserException("Book record does not found");
//        }
//        else {
//            User user = isUserPresent.get();
//            user.setPassword(password);
//            userRepository.save(user);
//            return "Password updated successfully";
//        }
//
//    }

    @Override
    public Object getUserByEmailId(String email) {

        return userRepository.findByEmailId(email);
    }

    /**
     * create a method name as getAllUserDataByToken by token
     * */
    @Override
    public List<User> getAllUserDataByToken(String token) {
        int id=util.decodeToken(token);
        Optional<User> isContactPresent=userRepository.findById(id);
        if(isContactPresent.isPresent()) {
            List<User> listOfUsers=userRepository.findAll();
            mailService.sendEmail("vishakakadam19@gmail.com", "Test Email", "Get your data with this token, hii: "
                    +isContactPresent.get().getEmail()+"Please Click here to get data-> "
                    +"http://localhost:8009/user/getAll/"+token);
            return listOfUsers;
        }else {
            System.out.println("Exception ...Token not found!");
            return null;	}
    }

    /**
     * create a method name as updateRecordById by id
     * */
    @Override
    public User updateRecordById(int  userId, UserDTO userDTO) {

        Optional<User> addressBook = userRepository.findById(userId);
        if(addressBook.isPresent()) {
            User newBook = new User(userDTO);
            userRepository.save(newBook);
            return newBook;

        }
        throw new UserException("User Details for id not found");
    }




}
