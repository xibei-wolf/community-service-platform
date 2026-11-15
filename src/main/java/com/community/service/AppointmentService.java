package com.community.service;

import com.community.dao.AppointmentDAO;
import com.community.dao.AppointmentDAOImpl;
import com.community.model.Appointment;

import java.sql.Timestamp;
import java.util.List;

public class AppointmentService {
    private AppointmentDAO appointmentDAO = new AppointmentDAOImpl();

    public Appointment getAppointmentById(int id) {
        return appointmentDAO.findById(id);
    }

    public List<Appointment> getAppointmentsByUserId(int userId) {
        return appointmentDAO.findByUserId(userId);
    }

    public List<Appointment> getAppointmentsByServiceId(int serviceId) {
        return appointmentDAO.findByServiceId(serviceId);
    }

    public boolean addAppointment(Appointment appointment) {
        // 设置默认状态为待确认
        appointment.setStatus("pending");
        
        // 设置创建时间和更新时间
        Timestamp now = new Timestamp(System.currentTimeMillis());
        appointment.setCreateTime(now);
        appointment.setUpdateTime(now);

        return appointmentDAO.addAppointment(appointment);
    }

    public boolean updateAppointment(Appointment appointment) {
        // 更新更新时间
        appointment.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return appointmentDAO.updateAppointment(appointment);
    }

    public boolean deleteAppointment(int id) {
        return appointmentDAO.deleteAppointment(id);
    }

    public boolean confirmAppointment(int id) {
        return appointmentDAO.updateAppointmentStatus(id, "confirmed");
    }

    public boolean completeAppointment(int id) {
        return appointmentDAO.updateAppointmentStatus(id, "completed");
    }

    public boolean cancelAppointment(int id) {
        return appointmentDAO.updateAppointmentStatus(id, "canceled");
    }

    public List<Appointment> getAllAppointments() {
        return appointmentDAO.getAllAppointments();
    }

    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentDAO.findByStatus(status);
    }
    
    // 统计所有预约总数
    public int getTotalAppointments() {
        return appointmentDAO.getTotalAppointments();
    }
    
    // 统计待处理预约数（根据定义的待处理状态，status = 'pending'）
    public int getPendingAppointments() {
        return appointmentDAO.getPendingAppointments();
    }
}