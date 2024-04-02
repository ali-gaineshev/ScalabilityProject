package com.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.user.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    public User findByUsername(String username);
}
