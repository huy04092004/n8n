package org.example.appointment.service;

import org.example.appointment.mapper.UserMapper;
import org.example.appointment.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public boolean registerUser(User user) {
        if (userMapper.findByEmail(user.getEmail()).isPresent()) {
            return false; // Email đã tồn tại
        }
        user.setPassword(hashPassword(user.getPassword())); // Mã hóa mật khẩu trước khi lưu
        userMapper.insertUser(user);
        return true;
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userMapper.findByEmail(email);
        if (user.isPresent()) {
            System.out.println("🔍 Tìm thấy user: " + user.get().getEmail());
            if (checkPassword(password, user.get().getPassword())) {
                return user;
            } else {
                System.out.println("❌ Mật khẩu sai!");
            }
        } else {
            System.out.println("❌ Không tìm thấy user!");
        }
        return Optional.empty();
    }


    private String hashPassword(String password) {
        return password; // TODO: Thay thế bằng BCrypt để mã hóa mật khẩu
    }

    private boolean checkPassword(String rawPassword, String hashedPassword) {
        return rawPassword.equals(hashedPassword); // TODO: Sử dụng BCrypt để kiểm tra mật khẩu
    }
}
