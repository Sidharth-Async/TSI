package com.traffic.tsi.repos;

import com.traffic.tsi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface user_Repo extends JpaRepository<User,String> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    Optional<User> findById(String userid);
    Optional<User> findByEmail(String email);
}
