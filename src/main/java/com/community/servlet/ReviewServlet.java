package com.community.servlet;

import com.community.model.Review;
import com.community.model.User;
import com.community.service.ReviewService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
    private ReviewService reviewService = new ReviewService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listReviews(request, response);
                break;
            case "adminList":
                adminListReviews(request, response);
                break;
            case "myReviews": 
                listMyReviews(request, response);
                break;
            case "add":
                showAddReviewForm(request, response);
                break;
            case "delete":
                deleteReview(request, response);
                break;
            default:
                listReviews(request, response);
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
                addReview(request, response);
                break;
            default:
                listReviews(request, response);
        }
    }

    private void listReviews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String serviceIdParam = request.getParameter("serviceId");
        if (serviceIdParam == null || serviceIdParam.isEmpty()) {
            request.setAttribute("error", "缺少必要的参数");
            request.getRequestDispatcher("/user_center.jsp").forward(request, response);
            return;
        }
        
        int serviceId = Integer.parseInt(serviceIdParam);
        List<Review> reviews = reviewService.getReviewsByServiceId(serviceId);
        request.setAttribute("reviews", reviews);
        request.setAttribute("serviceId", serviceId);
        request.getRequestDispatcher("/service_list.jsp").forward(request, response);
    }

    private void showAddReviewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void addReview(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }

        String serviceIdParam = request.getParameter("serviceId");
        String ratingParam = request.getParameter("rating");
        String comment = request.getParameter("comment");
        
        if (serviceIdParam == null || serviceIdParam.isEmpty() || ratingParam == null || ratingParam.isEmpty()) {
            request.setAttribute("error", "缺少必要的参数");
            request.getRequestDispatcher("/service_list.jsp").forward(request, response);
            return;
        }
        
        int serviceId = Integer.parseInt(serviceIdParam);
        int rating = Integer.parseInt(ratingParam);
        
        // 验证评分范围
        if (rating < 1 || rating > 5) {
            request.setAttribute("error", "评分必须在1-5之间");
            request.setAttribute("serviceId", serviceId);
            request.getRequestDispatcher("/service_list.jsp").forward(request, response);
            return;
        }

        Review review = new Review();
        review.setUserId(user.getId());
        review.setServiceId(serviceId);
        review.setRating(rating);
        review.setComment(comment);

        if (reviewService.addReview(review)) {
            response.sendRedirect(request.getContextPath() + "/service?action=detail&id=" + serviceId);
        } else {
            request.setAttribute("error", "添加评价失败");
            request.setAttribute("serviceId", serviceId);
            request.getRequestDispatcher("/service_list.jsp").forward(request, response);
        }
    }


    private void listMyReviews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否登录
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }
        
        // 按用户ID查询评价
        List<Review> myReviews = reviewService.getReviewsByUserId(user.getId());
        request.setAttribute("myReviews", myReviews);
        request.getRequestDispatcher("/my_reviews.jsp").forward(request, response);
    }

    private void deleteReview(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/service_list.jsp");
            return;
        }
        
        int id = Integer.parseInt(idParam);
        Review review = reviewService.getReviewById(id);
        if (review != null) {
            reviewService.deleteReview(id);
            response.sendRedirect(request.getContextPath() + "/service?action=detail&id=" + review.getServiceId());
        } else {
            response.sendRedirect(request.getContextPath() + "/service_list.jsp");
        }
    }
    
    private void adminListReviews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // 检查用户是否为管理员
        if (user == null || !user.getRole().equals("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        // 获取所有评价列表
        List<Review> allReviews = reviewService.getAllReviews();
        request.setAttribute("reviews", allReviews);
        
        // 转发到管理员评价列表页面
        request.getRequestDispatcher("/admin/review_list.jsp").forward(request, response);
    }
}