package com.community.service;

import com.community.dao.ReviewDAO;
import com.community.dao.ReviewDAOImpl;
import com.community.model.Review;

import java.sql.Timestamp;
import java.util.List;

public class ReviewService {
    private ReviewDAO reviewDAO = new ReviewDAOImpl();

    public Review getReviewById(int id) {
        return reviewDAO.findById(id);
    }

    public List<Review> getReviewsByUserId(int userId) {
        return reviewDAO.findByUserId(userId);
    }

    public List<Review> getReviewsByServiceId(int serviceId) {
        return reviewDAO.findByServiceId(serviceId);
    }

    public boolean addReview(Review review) {
        // 验证评分是否在1-5之间
        if (review.getRating() < 1 || review.getRating() > 5) {
            return false;
        }
        
        // 设置创建时间
        review.setCreateTime(new Timestamp(System.currentTimeMillis()));

        return reviewDAO.addReview(review);
    }

    public boolean updateReview(Review review) {
        // 验证评分是否在1-5之间
        if (review.getRating() < 1 || review.getRating() > 5) {
            return false;
        }
        return reviewDAO.updateReview(review);
    }

    public boolean deleteReview(int id) {
        return reviewDAO.deleteReview(id);
    }

    public List<Review> getAllReviews() {
        return reviewDAO.getAllReviews();
    }
    
    // 统计所有评价总数
    public int getTotalReviews() {
        return reviewDAO.getTotalReviews();
    }
}