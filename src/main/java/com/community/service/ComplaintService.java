package com.community.service;

import com.community.dao.ComplaintDAO;
import com.community.dao.ComplaintDAOImpl;
import com.community.model.Complaint;

import java.sql.Timestamp;
import java.util.List;

public class ComplaintService {
    private ComplaintDAO complaintDAO = new ComplaintDAOImpl();

    public Complaint getComplaintById(int id) {
        return complaintDAO.findById(id);
    }

    public List<Complaint> getComplaintsByUserId(int userId) {
        return complaintDAO.findByUserId(userId);
    }

    public List<Complaint> getComplaintsByServiceId(int serviceId) {
        return complaintDAO.findByServiceId(serviceId);
    }

    public boolean addComplaint(Complaint complaint) {
        // 设置默认状态为pending
        complaint.setStatus("pending");
        
        // 设置创建时间和更新时间
        Timestamp now = new Timestamp(System.currentTimeMillis());
        complaint.setCreateTime(now);
        complaint.setUpdateTime(now);

        return complaintDAO.addComplaint(complaint);
    }

    public boolean updateComplaint(Complaint complaint) {
        // 更新修改时间
        complaint.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return complaintDAO.updateComplaint(complaint);
    }

    public boolean resolveComplaint(int id, String response) {
        return complaintDAO.updateComplaintStatus(id, "resolved", response);
    }

    public boolean rejectComplaint(int id, String response) {
        return complaintDAO.updateComplaintStatus(id, "rejected", response);
    }

    public boolean deleteComplaint(int id) {
        return complaintDAO.deleteComplaint(id);
    }

    public List<Complaint> getAllComplaints() {
        return complaintDAO.getAllComplaints();
    }
    
    public int getTotalComplaints() {
        return complaintDAO.getTotalComplaints();
    }
}