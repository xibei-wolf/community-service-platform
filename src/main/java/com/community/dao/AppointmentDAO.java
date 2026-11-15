package com.community.dao;

import com.community.model.Appointment;

import java.util.List;

public interface AppointmentDAO {
    Appointment findById(int id);
    List<Appointment> findByUserId(int userId);
    List<Appointment> findByServiceId(int serviceId);
    List<Appointment> findByStatus(String status);
    boolean addAppointment(Appointment appointment);
    boolean updateAppointment(Appointment appointment);
    boolean deleteAppointment(int id);
    boolean updateAppointmentStatus(int id, String status);
    List<Appointment> getAllAppointments();
    int getTotalAppointments();
    int getPendingAppointments();
}