package com.community.service;

import com.community.dao.UserDAO;
import com.community.dao.UserDAOImpl;
import com.community.model.User;

import java.sql.Timestamp;
import java.util.List;

public class UserService {
    private UserDAO userDAO = new UserDAOImpl();

    public User getUserById(int id) {
        return userDAO.findById(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public boolean registerUser(User user) {
        // 检查用户名是否已存在
        if (getUserByUsername(user.getUsername()) != null) {
            return false;
        }

        // 检查邮箱是否已存在
        if (getUserByEmail(user.getEmail()) != null) {
            return false;
        }

        // 设置默认角色为普通用户
        user.setRole("user");
        
        // 设置注册时间
        Timestamp now = new Timestamp(System.currentTimeMillis());
        user.setRegisterTime(now);
        
        // 设置默认状态为激活
        user.setStatus(true);

        return userDAO.addUser(user);
    }

    public boolean loginUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.isStatus()) {
            return true;
        }
        return false;
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean updateUserStatus(int id, boolean status) {
        return userDAO.updateUserStatus(id, status);
    }

    public int getTotalUsers() {
        return userDAO.getTotalUsers();
    }
}