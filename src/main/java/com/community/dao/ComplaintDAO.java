package com.community.dao;

import com.community.model.Complaint;

import java.util.List;

public interface ComplaintDAO {
    Complaint findById(int id);
    List<Complaint> findByUserId(int userId);
    List<Complaint> findByServiceId(int serviceId);
    boolean addComplaint(Complaint complaint);
    boolean updateComplaint(Complaint complaint);
    boolean updateComplaintStatus(int id, String status, String response);
    boolean deleteComplaint(int id);
    List<Complaint> getAllComplaints();
    int getTotalComplaints();
}