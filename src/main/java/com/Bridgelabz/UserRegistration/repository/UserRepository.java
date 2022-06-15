package com.Bridgelabz.UserRegistration.repository;

import com.Bridgelabz.UserRegistration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>
{
    @Query(value = "SELECT * FROM userregistration.userdata Where email= :email", nativeQuery = true)
    Optional<User> findByEmailId(String email);

    Optional<User> findByToken(String token);
}
