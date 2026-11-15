package com.community.servlet;

import com.community.model.User;
import com.community.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "login";
        }

        switch (action) {
            case "login":
                showLoginForm(request, response);
                break;
            case "register":
                showRegisterForm(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            case "list":
                getUsersList(request, response);
                break;
            case "update":
            case "updatePassword":
                showUpdatePasswordForm(request, response);
                break;
            default:
                showLoginForm(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "login";
        }

        switch (action) {
            case "login":
                login(request, response);
                break;
            case "register":
                register(request, response);
                break;
            case "updatePassword":
                updatePassword(request, response);
                break;
            default:
                showLoginForm(request, response);
        }
    }

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void showRegisterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userService.loginUser(username, password)) {
            User user = userService.getUserByUsername(username);
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            // 根据用户角色跳转到不同页面
            if (user.getRole().equals("admin")) {
                response.sendRedirect(request.getContextPath() + "/admin?action=dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/user_center.jsp");
            }
        } else {
            request.setAttribute("error", "用户名或密码错误");
            showLoginForm(request, response);
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);

        if (userService.registerUser(user)) {
            request.setAttribute("success", "注册成功，请登录");
            showLoginForm(request, response);
        } else {
            request.setAttribute("error", "注册失败，用户名或邮箱已存在");
            showRegisterForm(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private void getUsersList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否为管理员
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 获取所有用户列表
        java.util.List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        
        // 转发到用户列表页面
        request.getRequestDispatcher("/admin/user_list.jsp").forward(request, response);
    }
    
    private void showUpdatePasswordForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否已登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 转发到修改密码页面
        request.getRequestDispatcher("/update_password.jsp").forward(request, response);
    }
    
    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否已登录
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 获取表单参数
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // 验证密码是否正确
        if (!user.getPassword().equals(oldPassword)) {
            request.setAttribute("error", "原密码错误");
            showUpdatePasswordForm(request, response);
            return;
        }
        
        // 验证新密码和确认密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "新密码和确认密码不一致");
            showUpdatePasswordForm(request, response);
            return;
        }
        
        // 更新密码
        user.setPassword(newPassword);
        if (userService.updateUser(user)) {
            // 更新session中的用户信息
            session.setAttribute("user", user);
            request.setAttribute("success", "密码修改成功");
        } else {
            request.setAttribute("error", "密码修改失败");
        }
        
        showUpdatePasswordForm(request, response);
    }
}