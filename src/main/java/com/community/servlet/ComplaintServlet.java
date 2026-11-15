package com.community.servlet;

import com.community.model.Complaint;
import com.community.model.User;
import com.community.service.ComplaintService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/complaint")
public class ComplaintServlet extends HttpServlet {
    private ComplaintService complaintService = new ComplaintService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listComplaints(request, response);
                break;
            case "adminList":
                adminListComplaints(request, response);
                break;
            case "myComplaints": 
                listMyComplaints(request, response);
                break;
            case "add":
                showAddComplaintForm(request, response);
                break;
            case "resolve":
                showResolveComplaintForm(request, response);
                break;
            case "reject":
                showRejectComplaintForm(request, response);
                break;
            default:
                listComplaints(request, response);
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
                addComplaint(request, response);
                break;
            case "resolve":
                resolveComplaint(request, response);
                break;
            case "reject":
                rejectComplaint(request, response);
                break;
            default:
                listComplaints(request, response);
        }
    }

    private void listComplaints(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceIdParam = request.getParameter("serviceId");
        if (serviceIdParam == null || serviceIdParam.isEmpty()) {
            request.setAttribute("error", "缺少必要的参数");
            request.getRequestDispatcher("/user_center.jsp").forward(request, response);
            return;
        }
        
        int serviceId = Integer.parseInt(serviceIdParam);
        List<Complaint> complaints = complaintService.getComplaintsByServiceId(serviceId);
        request.setAttribute("complaints", complaints);
        request.setAttribute("serviceId", serviceId);
        request.getRequestDispatcher("/service_list.jsp").forward(request, response);
    }

    private void showAddComplaintForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceIdParam = request.getParameter("serviceId");
        if (serviceIdParam == null || serviceIdParam.isEmpty()) {
            request.setAttribute("error", "缺少必要的参数");
            request.getRequestDispatcher("/user_center.jsp").forward(request, response);
            return;
        }
        
        int serviceId = Integer.parseInt(serviceIdParam);
        request.setAttribute("serviceId", serviceId);
        request.getRequestDispatcher("/service_list.jsp").forward(request, response);
    }

    private void addComplaint(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }

        String serviceIdParam = request.getParameter("serviceId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        if (serviceIdParam == null || serviceIdParam.isEmpty() || title == null || title.isEmpty() || content == null || content.isEmpty()) {
            request.setAttribute("error", "缺少必要的参数");
            request.getRequestDispatcher("/service_list.jsp").forward(request, response);
            return;
        }
        
        int serviceId = Integer.parseInt(serviceIdParam);
        
        Complaint complaint = new Complaint();
        complaint.setUserId(user.getId());
        complaint.setServiceId(serviceId);
        complaint.setTitle(title);
        complaint.setContent(content);

        if (complaintService.addComplaint(complaint)) {
            response.sendRedirect(request.getContextPath() + "/service?action=detail&id=" + serviceId);
        } else {
            request.setAttribute("error", "添加投诉失败");
            request.setAttribute("serviceId", serviceId);
            request.getRequestDispatcher("/service_list.jsp").forward(request, response);
        }
    }

    private void listMyComplaints(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否登录
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }
        
        // 按用户ID查询投诉
        List<Complaint> myComplaints = complaintService.getComplaintsByUserId(user.getId());
        request.setAttribute("myComplaints", myComplaints);
        request.getRequestDispatcher("/my_complaints.jsp").forward(request, response);
    }

    private void adminListComplaints(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否为管理员
        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 获取所有投诉列表
        List<Complaint> allComplaints = complaintService.getAllComplaints();
        request.setAttribute("complaints", allComplaints);
        
        // 转发到管理员投诉列表页面
        request.getRequestDispatcher("/admin/complaint_list.jsp").forward(request, response);
    }

    private void showResolveComplaintForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否为管理员
        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/complaint?action=adminList");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        request.setAttribute("id", id);
        request.getRequestDispatcher("/admin/resolve_complaint.jsp").forward(request, response);
    }

    private void resolveComplaint(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否为管理员
        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        String idParam = request.getParameter("id");
        String responseContent = request.getParameter("response");
        
        if (idParam == null || idParam.isEmpty() || responseContent == null || responseContent.isEmpty()) {
            request.setAttribute("error", "缺少必要的参数");
            request.getRequestDispatcher("/admin/resolve_complaint.jsp").forward(request, response);
            return;
        }
        
        int id = Integer.parseInt(idParam);
        
        if (complaintService.resolveComplaint(id, responseContent)) {
            response.sendRedirect(request.getContextPath() + "/complaint?action=adminList");
        } else {
            request.setAttribute("error", "解决投诉失败");
            request.setAttribute("id", id);
            request.getRequestDispatcher("/admin/resolve_complaint.jsp").forward(request, response);
        }
    }

    private void showRejectComplaintForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否为管理员
        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/complaint?action=adminList");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        request.setAttribute("id", id);
        request.getRequestDispatcher("/admin/reject_complaint.jsp").forward(request, response);
    }

    private void rejectComplaint(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否为管理员
        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        String idParam = request.getParameter("id");
        String responseContent = request.getParameter("response");
        
        if (idParam == null || idParam.isEmpty() || responseContent == null || responseContent.isEmpty()) {
            request.setAttribute("error", "缺少必要的参数");
            request.getRequestDispatcher("/admin/reject_complaint.jsp").forward(request, response);
            return;
        }
        
        int id = Integer.parseInt(idParam);
        
        if (complaintService.rejectComplaint(id, responseContent)) {
            response.sendRedirect(request.getContextPath() + "/complaint?action=adminList");
        } else {
            request.setAttribute("error", "驳回投诉失败");
            request.setAttribute("id", id);
            request.getRequestDispatcher("/admin/reject_complaint.jsp").forward(request, response);
        }
    }
}