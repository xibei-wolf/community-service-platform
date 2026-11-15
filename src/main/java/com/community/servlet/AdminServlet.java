package com.community.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.community.model.ServiceInfo;
import com.community.model.User;
import com.community.service.AppointmentService;
import com.community.service.ReviewService;
import com.community.service.ServiceInfoService;
import com.community.service.UserService;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private UserService userService = new UserService();
    private ServiceInfoService serviceInfoService = new ServiceInfoService();
    private AppointmentService appointmentService = new AppointmentService();
    private ReviewService reviewService = new ReviewService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }

        String action = request.getParameter("action");

        if (action == null) {
            action = "dashboard";
        }

        switch (action) {
            case "dashboard":
                showDashboard(request, response);
                break;
            case "users":
                listUsers(request, response);
                break;
            case "services":
            case "allServices":
                listServices(request, response);
                break;
            case "pendingServices":
                listPendingServices(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            case "complaints":
                listComplaints(request, response);
                break;
            case "approveService":
                approveService(request, response);
                break;
            case "rejectService":
                rejectService(request, response);
                break;
            case "updateUserStatus":
                updateUserStatus(request, response);
                break;
            default:
                showDashboard(request, response);
        }
    }

    private void showDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取统计信息
        int totalUsers = userService.getTotalUsers();
        int totalServices = serviceInfoService.getTotalServices();
        int pendingServices = serviceInfoService.getPendingServices();
        int totalAppointments = appointmentService.getTotalAppointments();
        int pendingAppointments = appointmentService.getPendingAppointments();
        int totalReviews = reviewService.getTotalReviews();
  
        request.setAttribute("totalUsers", totalUsers);
        request.setAttribute("totalServices", totalServices);
        request.setAttribute("pendingServices", pendingServices);
        request.setAttribute("totalAppointments", totalAppointments);
        request.setAttribute("pendingAppointments", pendingAppointments);
        request.setAttribute("totalReviews", totalReviews);

        request.getRequestDispatcher("/admin/admin_dashboard.jsp").forward(request, response);
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users = userService.getAllUsers();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/admin/user_list.jsp").forward(request, response);
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ServiceInfo> services = serviceInfoService.getAllServices();
        request.setAttribute("services", services);
        request.getRequestDispatcher("/admin/service_admin_list.jsp").forward(request, response);
    }

    private void listPendingServices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<ServiceInfo> pendingServices = serviceInfoService.getServicesByStatus("pending");
        request.setAttribute("services", pendingServices);
        request.getRequestDispatcher("/admin/service_admin_list.jsp").forward(request, response);
    }

    private void approveService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        serviceInfoService.approveService(id);
        response.sendRedirect(request.getContextPath() + "/admin?action=pendingServices");
    }

    private void rejectService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        serviceInfoService.rejectService(id);
        response.sendRedirect(request.getContextPath() + "/admin?action=pendingServices");
    }

    private void updateUserStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        userService.updateUserStatus(id, status);
        response.sendRedirect(request.getContextPath() + "/admin?action=users");
    }
    
    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
    
    private void listComplaints(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否为管理员
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }
        
        // 转发到投诉管理页面
        request.getRequestDispatcher("/admin/complaint_list.jsp").forward(request, response);
    }
}