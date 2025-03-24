package org.example.appointment.mapper;

import org.apache.ibatis.annotations.*;
import org.example.appointment.model.User;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO users (full_name, phone, email, password, role, created_at) " +
            "VALUES (#{fullName}, #{phone}, #{email}, #{password}, COALESCE(#{role}, 'patient'), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertUser(User user);


    @Select("SELECT * FROM users WHERE id = #{id}")
    Optional<User> getUserById(int id);

    @Select("SELECT * FROM users WHERE email = #{email}")
    Optional<User> findByEmail(String email);

    @Select("SELECT * FROM users")
    List<User> getAllUsers();

    @Update("<script>" +
            "UPDATE users " +
            "<set>" +
            "<if test='fullName != null'>full_name = #{fullName}, </if>" +
            "<if test='phone != null'>phone = #{phone}, </if>" +
            "<if test='email != null'>email = #{email}, </if>" +
            "<if test='role != null'>role = #{role}, </if>" +
            "</set>" +
            "WHERE id = #{id}" +
            "</script>")
    void updateUser(User user);

    @Update("UPDATE users SET password = #{password} WHERE id = #{id}")
    void updatePassword(@Param("id") int id, @Param("password") String password);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void deleteUser(int id);
}
