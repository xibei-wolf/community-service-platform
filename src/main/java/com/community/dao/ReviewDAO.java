package com.community.dao;

import com.community.model.Review;

import java.util.List;

public interface ReviewDAO {
    Review findById(int id);
    List<Review> findByUserId(int userId);
    List<Review> findByServiceId(int serviceId);
    boolean addReview(Review review);
    boolean updateReview(Review review);
    boolean deleteReview(int id);
    List<Review> getAllReviews();
    int getTotalReviews();
}