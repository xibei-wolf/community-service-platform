package com.community.service;

import com.community.dao.ServiceInfoDAO;
import com.community.dao.ServiceInfoDAOImpl;
import com.community.model.ServiceInfo;

import java.sql.Timestamp;
import java.util.List;

public class ServiceInfoService {
    private ServiceInfoDAO serviceInfoDAO = new ServiceInfoDAOImpl();

    public ServiceInfo getServiceById(int id) {
        return serviceInfoDAO.findById(id);
    }

    public List<ServiceInfo> getServicesByUserId(int userId) {
        return serviceInfoDAO.findByUserId(userId);
    }

    public List<ServiceInfo> getServicesByCategory(String category) {
        return serviceInfoDAO.findByCategory(category);
    }

    public List<ServiceInfo> getServicesByStatus(String status) {
        return serviceInfoDAO.findByStatus(status);
    }

    public List<ServiceInfo> searchServices(String keyword) {
        return serviceInfoDAO.searchServices(keyword);
    }

    public boolean addService(ServiceInfo serviceInfo) {
        // 设置默认状态为待审核
        serviceInfo.setStatus("pending");
        
        // 设置创建时间和更新时间
        Timestamp now = new Timestamp(System.currentTimeMillis());
        serviceInfo.setCreateTime(now);
        serviceInfo.setUpdateTime(now);

        return serviceInfoDAO.addServiceInfo(serviceInfo);
    }

    public boolean updateService(ServiceInfo serviceInfo) {
        // 更新更新时间
        serviceInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return serviceInfoDAO.updateServiceInfo(serviceInfo);
    }

    public boolean deleteService(int id) {
        return serviceInfoDAO.deleteServiceInfo(id);
    }

    public boolean approveService(int id) {
        return serviceInfoDAO.updateServiceStatus(id, "approved");
    }

    public boolean rejectService(int id) {
        return serviceInfoDAO.updateServiceStatus(id, "rejected");
    }

    public List<ServiceInfo> getAllServices() {
        return serviceInfoDAO.getAllServiceInfos();
    }
    
    // 统计所有服务总数
    public int getTotalServices() {
        return serviceInfoDAO.getTotalServices();
    }
    
    // 统计待审核服务数（status = 'pending' ）
    public int getPendingServices() {
        return serviceInfoDAO.getPendingServices();
    }
    
    // 带多条件筛选的服务搜索
    public List<ServiceInfo> searchServicesWithFilters(String keyword, String category, String status) {
        return serviceInfoDAO.searchServicesWithFilters(keyword, category, status);
    }
}