package com.community.servlet;

import com.community.model.Appointment;
import com.community.model.User;
import com.community.service.AppointmentService;
import com.community.service.ServiceInfoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/appointment")
public class AppointmentServlet extends HttpServlet {
    private AppointmentService appointmentService = new AppointmentService();
    private ServiceInfoService serviceInfoService = new ServiceInfoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
            case "myAppointments":
                listAppointments(request, response);
                break;
            case "detail":
                showAppointmentDetail(request, response);
                break;
            case "add":
            case "create":
                showAddAppointmentForm(request, response);
                break;
            case "delete":
                deleteAppointment(request, response);
                break;
            case "confirm":
                confirmAppointment(request, response);
                break;
            case "complete":
                completeAppointment(request, response);
                break;
            case "cancel":
                cancelAppointment(request, response);
                break;
            case "adminList":
                adminListAppointments(request, response);
                break;
            case "pendingList":
                pendingListAppointments(request, response);
                break;
            default:
                listAppointments(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "add";
        }

        switch (action) {
            case "add":
                addAppointment(request, response);
                break;
            default:
                listAppointments(request, response);
        }
    }

    private void listAppointments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }

        List<Appointment> appointments = appointmentService.getAppointmentsByUserId(user.getId());
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/appointment/list.jsp").forward(request, response);
    }

    private void showAppointmentDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user_center.jsp");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        Appointment appointment = appointmentService.getAppointmentById(id);
        if (appointment == null) {
            response.sendRedirect(request.getContextPath() + "/user_center.jsp");
            return;
        }
        
        request.setAttribute("appointment", appointment);
        request.getRequestDispatcher("/appointment/detail.jsp").forward(request, response);
    }

    private void showAddAppointmentForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/service_list.jsp");
            return;
        }
        
        int serviceId = Integer.parseInt(idParam);
        request.setAttribute("serviceId", serviceId);
        request.getRequestDispatcher("/appointment/create.jsp").forward(request, response);
    }

    private void addAppointment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }

        String serviceIdParam = request.getParameter("serviceId");
        String appointmentTime = request.getParameter("appointmentTime");
        String notes = request.getParameter("notes");
        
        if (serviceIdParam == null || serviceIdParam.isEmpty() || appointmentTime == null || appointmentTime.isEmpty()) {
            request.setAttribute("error", "缺少必要的参数");
            request.getRequestDispatcher("/service_list.jsp").forward(request, response);
            return;
        }

        int serviceId = Integer.parseInt(serviceIdParam);

        Appointment appointment = new Appointment();
        appointment.setUserId(user.getId());
        appointment.setServiceId(serviceId);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setNotes(notes);

        if (appointmentService.addAppointment(appointment)) {
            response.sendRedirect(request.getContextPath() + "/user_center.jsp");
        } else {
            request.setAttribute("error", "预约服务失败");
            request.setAttribute("serviceId", serviceId);
            request.getRequestDispatcher("/appointment/create.jsp").forward(request, response);
        }
    }

    private void deleteAppointment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 检查用户是否登录且是管理员
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/appointment?action=adminList");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        appointmentService.deleteAppointment(id);
        response.sendRedirect(request.getContextPath() + "/appointment?action=adminList");
    }

    private void confirmAppointment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 检查用户是否登录且是管理员
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/appointment?action=adminList");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        appointmentService.confirmAppointment(id);
        response.sendRedirect(request.getContextPath() + "/appointment?action=adminList");
    }

    private void completeAppointment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 检查用户是否登录且是管理员
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/appointment?action=adminList");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        appointmentService.completeAppointment(id);
        response.sendRedirect(request.getContextPath() + "/appointment?action=adminList");
    }

    private void cancelAppointment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 检查用户是否登录
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/appointment?action=list");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        Appointment appointment = appointmentService.getAppointmentById(id);
        
        // 检查是否是管理员或者是预约的所有者
        if (!"admin".equals(user.getRole()) && appointment.getUserId() != user.getId()) {
            response.sendRedirect(request.getContextPath() + "/appointment?action=list");
            return;
        }
        
        appointmentService.cancelAppointment(id);
        
        // 根据用户角色重定向到不同页面
        if ("admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/appointment?action=adminList");
        } else {
            response.sendRedirect(request.getContextPath() + "/appointment?action=list");
        }
    }

    private void adminListAppointments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 检查用户是否登录且是管理员
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Appointment> appointments = appointmentService.getAllAppointments();
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/admin/appointment_list.jsp").forward(request, response);
    }

    private void pendingListAppointments(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // 检查用户是否登录且是管理员
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        List<Appointment> appointments = appointmentService.getAppointmentsByStatus("pending");
        request.setAttribute("appointments", appointments);
        request.getRequestDispatcher("/admin/pending_appointments.jsp").forward(request, response);
    }
}