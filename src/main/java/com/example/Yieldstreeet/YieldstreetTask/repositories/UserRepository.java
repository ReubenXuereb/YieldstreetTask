package com.example.Yieldstreeet.YieldstreetTask.repositories;

import com.example.Yieldstreeet.YieldstreetTask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
