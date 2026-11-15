package com.community.dao;

import com.community.model.User;

import java.util.List;

public interface UserDAO {
    User findById(int id);
    User findByUsername(String username);
    User findByEmail(String email);
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    List<User> getAllUsers();
    boolean updateUserStatus(int id, boolean status);
    int getTotalUsers();
}