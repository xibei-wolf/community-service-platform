package com.community.dao;

import com.community.model.ServiceInfo;

import java.util.List;

public interface ServiceInfoDAO {
    ServiceInfo findById(int id);
    List<ServiceInfo> findByUserId(int userId);
    List<ServiceInfo> findByCategory(String category);
    List<ServiceInfo> findByStatus(String status);
    List<ServiceInfo> searchServices(String keyword);
    boolean addServiceInfo(ServiceInfo serviceInfo);
    boolean updateServiceInfo(ServiceInfo serviceInfo);
    boolean deleteServiceInfo(int id);
    boolean updateServiceStatus(int id, String status);
    List<ServiceInfo> getAllServiceInfos();
    int getTotalServices();
    int getPendingServices();
    List<ServiceInfo> searchServicesWithFilters(String keyword, String category, String status);
}