//package org.example.appointment.controller;
//
//import org.example.appointment.model.User;
//import org.example.appointment.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/users")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @PostMapping("/register")
//    public String registerUser(@RequestBody User user) {
//        userService.registerUser(user);
//        return "Đăng ký thành công!";
//    }
//
//    @GetMapping("/{id}")
//    public User getUserById(@PathVariable int id) {
//        return userService.getUserById(id);
//    }
//
//    @GetMapping("/email/{email}")
//    public User getUserByEmail(@PathVariable String email) {
//        return userService.getUserByEmail(email);
//    }
//
//    @GetMapping("/all")
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }
//
//    @PutMapping("/{id}")
//    public String updateUser(@PathVariable int id, @RequestBody User user) {
//        user.setId(id);
//        userService.updateUser(user);
//        return "Cập nhật thông tin thành công!";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteUser(@PathVariable int id) {
//        userService.deleteUser(id);
//        return "Xóa tài khoản thành công!";
//    }
//}
