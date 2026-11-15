package com.community.servlet;

import com.community.model.ServiceInfo;
import com.community.model.User;
import com.community.service.ServiceInfoService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/service")
public class ServiceInfoServlet extends HttpServlet {
    private ServiceInfoService serviceInfoService = new ServiceInfoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listServices(request, response);
                break;
            case "adminList":
                adminListServices(request, response);
                break;
            case "pendingList":
                pendingListServices(request, response);
                break;
            case "detail":
                showServiceDetail(request, response);
                break;
            case "add":
            case "publish":
                showAddServiceForm(request, response);
                break;
            case "edit":
                showEditServiceForm(request, response);
                break;
            case "delete":
                deleteService(request, response);
                break;
            case "approve":
                approveService(request, response);
                break;
            case "reject":
                rejectService(request, response);
                break;
            case "search":
                searchServices(request, response);
                break;
            case "category":
                listServicesByCategory(request, response);
                break;
            default:
                listServices(request, response);
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
                addService(request, response);
                break;
            case "edit":
                editService(request, response);
                break;
            default:
                listServices(request, response);
        }
    }

    private void listServices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");
        String status = request.getParameter("status");
        
        List<ServiceInfo> services;
        if ((keyword != null && !keyword.isEmpty()) || (category != null && !category.isEmpty()) || (status != null && !status.isEmpty())) {
            // 调用带筛选条件的搜索方法
            services = serviceInfoService.searchServicesWithFilters(keyword, category, status);
            request.setAttribute("keyword", keyword);
            request.setAttribute("category", category);
            request.setAttribute("status", status);
        } else {
            // 没有筛选条件，显示所有服务
            services = serviceInfoService.getAllServices();
        }
        
        request.setAttribute("services", services);
        request.getRequestDispatcher("/service_list.jsp").forward(request, response);
    }
    
    private void adminListServices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 管理员可以查看所有服务，包括待审核的
        List<ServiceInfo> services = serviceInfoService.getAllServices();
        request.setAttribute("services", services);
        // 转发到管理员专用的服务列表页面
        request.getRequestDispatcher("/admin/service_admin_list.jsp").forward(request, response);
    }
    
    private void pendingListServices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否为管理员
        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 管理员查看待审核服务
        List<ServiceInfo> pendingServices = serviceInfoService.getServicesByStatus("pending");
        request.setAttribute("services", pendingServices);
        // 转发到管理员专用的服务列表页面
        request.getRequestDispatcher("/admin/service_admin_list.jsp").forward(request, response);
    }

    private void showServiceDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/service_list.jsp");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        ServiceInfo service = serviceInfoService.getServiceById(id);
        if (service == null) {
            response.sendRedirect(request.getContextPath() + "/service_list.jsp");
            return;
        }
        
        request.setAttribute("service", service);
        request.getRequestDispatcher("/service_detail.jsp").forward(request, response);
    }

    private void showAddServiceForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/publish_service.jsp").forward(request, response);
    }

    private void showEditServiceForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/service_list.jsp");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        ServiceInfo service = serviceInfoService.getServiceById(id);
        if (service == null) {
            response.sendRedirect(request.getContextPath() + "/service_list.jsp");
            return;
        }
        
        request.setAttribute("service", service);
        request.getRequestDispatcher("/service_list.jsp").forward(request, response);
    }

    private void addService(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }

        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        double price = Double.parseDouble(request.getParameter("price"));
        String contactInfo = request.getParameter("contactInfo");

        ServiceInfo service = new ServiceInfo();
        service.setUserId(user.getId());
        service.setTitle(title);
        service.setCategory(category);
        service.setDescription(description);
        service.setLocation(location);
        service.setPrice(price);
        service.setContactInfo(contactInfo);

        if (serviceInfoService.addService(service)) {
            response.sendRedirect(request.getContextPath() + "/service?action=list");
        } else {
            request.setAttribute("error", "发布服务失败");
            showAddServiceForm(request, response);
        }
    }

    private void editService(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        ServiceInfo service = serviceInfoService.getServiceById(id);

        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String description = request.getParameter("description");
        String location = request.getParameter("location");
        double price = Double.parseDouble(request.getParameter("price"));
        String contactInfo = request.getParameter("contactInfo");

        service.setTitle(title);
        service.setCategory(category);
        service.setDescription(description);
        service.setLocation(location);
        service.setPrice(price);
        service.setContactInfo(contactInfo);

        if (serviceInfoService.updateService(service)) {
            response.sendRedirect(request.getContextPath() + "/service?action=list");
        } else {
            request.setAttribute("error", "更新服务失败");
            request.setAttribute("service", service);
            request.getRequestDispatcher("/service_list.jsp").forward(request, response);
        }
    }

    private void deleteService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/service?action=adminList");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        serviceInfoService.deleteService(id);
        response.sendRedirect(request.getContextPath() + "/service?action=adminList");
    }
    
    private void approveService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/service?action=adminList");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        ServiceInfo service = serviceInfoService.getServiceById(id);
        if (service != null) {
            service.setStatus("approved");
            serviceInfoService.updateService(service);
        }
        response.sendRedirect(request.getContextPath() + "/service?action=adminList");
    }
    
    private void rejectService(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/service?action=adminList");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        ServiceInfo service = serviceInfoService.getServiceById(id);
        if (service != null) {
            service.setStatus("rejected");
            serviceInfoService.updateService(service);
        }
        response.sendRedirect(request.getContextPath() + "/service?action=adminList");
    }

    private void searchServices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<ServiceInfo> services = serviceInfoService.searchServices(keyword);
        request.setAttribute("services", services);
        request.setAttribute("keyword", keyword);
        request.getRequestDispatcher("/service_list.jsp").forward(request, response);
    }

    private void listServicesByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        List<ServiceInfo> services = serviceInfoService.getServicesByCategory(category);
        request.setAttribute("services", services);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/service_list.jsp").forward(request, response);
    }
}