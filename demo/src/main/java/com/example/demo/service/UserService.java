package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String register(User user) {
        // ตรวจสอบ username ซ้ำ
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return "Username already exists";
        }

        // กำหนด role default เป็น "user" หากไม่ได้ส่งมา
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("user");
        }

        // บันทึกรหัสผ่าน plain text
        userRepository.save(user);
        return "Register success";
    }

    public boolean login(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
}
