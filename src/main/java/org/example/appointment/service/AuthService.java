package org.example.appointment.service;

import org.example.appointment.mapper.UserMapper;
import org.example.appointment.model.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserMapper userMapper;

    public AuthService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // Đăng ký tài khoản
    public String registerUser(User user) {
        // Kiểm tra email đã tồn tại chưa
        Optional<User> existingUser = userMapper.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return "Email đã tồn tại!";
        }

        // Mã hóa mật khẩu trước khi lưu
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        // Thêm user vào database
        userMapper.insertUser(user);
        return "Đăng ký thành công!";
    }

    // Xác thực đăng nhập
    public Optional<User> login(String email, String password) {
        Optional<User> user = userMapper.findByEmail(email);

        // Kiểm tra email có tồn tại và mật khẩu có đúng không
        if (user.isPresent() && BCrypt.checkpw(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }
}
