package com.community.dao;

import com.community.model.ServiceInfo;
import com.community.model.User;
import com.community.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceInfoDAOImpl implements ServiceInfoDAO {
    private UserDAO userDAO = new UserDAOImpl();

    @Override
    public ServiceInfo findById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ServiceInfo serviceInfo = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM service_info WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                serviceInfo = mapResultSetToServiceInfo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return serviceInfo;
    }

    @Override
    public List<ServiceInfo> findByUserId(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServiceInfo> serviceInfos = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM service_info WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                serviceInfos.add(mapResultSetToServiceInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return serviceInfos;
    }

    @Override
    public List<ServiceInfo> findByCategory(String category) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServiceInfo> serviceInfos = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM service_info WHERE category = ? AND status = 'approved'";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, category);
            rs = stmt.executeQuery();

            while (rs.next()) {
                serviceInfos.add(mapResultSetToServiceInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return serviceInfos;
    }

    @Override
    public List<ServiceInfo> findByStatus(String status) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServiceInfo> serviceInfos = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM service_info WHERE status = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            rs = stmt.executeQuery();

            while (rs.next()) {
                serviceInfos.add(mapResultSetToServiceInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return serviceInfos;
    }

    @Override
    public List<ServiceInfo> searchServices(String keyword) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServiceInfo> serviceInfos = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM service_info WHERE (title LIKE ? OR description LIKE ? OR category LIKE ?) AND status = 'approved'";
            stmt = conn.prepareStatement(sql);
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            rs = stmt.executeQuery();

            while (rs.next()) {
                serviceInfos.add(mapResultSetToServiceInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return serviceInfos;
    }

    @Override
    public boolean addServiceInfo(ServiceInfo serviceInfo) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO service_info (user_id, title, category, description, location, price, contact_info, status, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, serviceInfo.getUserId());
            stmt.setString(2, serviceInfo.getTitle());
            stmt.setString(3, serviceInfo.getCategory());
            stmt.setString(4, serviceInfo.getDescription());
            stmt.setString(5, serviceInfo.getLocation());
            stmt.setDouble(6, serviceInfo.getPrice());
            stmt.setString(7, serviceInfo.getContactInfo());
            stmt.setString(8, serviceInfo.getStatus());
            stmt.setTimestamp(9, serviceInfo.getCreateTime());
            stmt.setTimestamp(10, serviceInfo.getUpdateTime());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateServiceInfo(ServiceInfo serviceInfo) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE service_info SET title = ?, category = ?, description = ?, location = ?, price = ?, contact_info = ?, status = ?, update_time = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, serviceInfo.getTitle());
            stmt.setString(2, serviceInfo.getCategory());
            stmt.setString(3, serviceInfo.getDescription());
            stmt.setString(4, serviceInfo.getLocation());
            stmt.setDouble(5, serviceInfo.getPrice());
            stmt.setString(6, serviceInfo.getContactInfo());
            stmt.setString(7, serviceInfo.getStatus());
            stmt.setTimestamp(8, serviceInfo.getUpdateTime());
            stmt.setInt(9, serviceInfo.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean deleteServiceInfo(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM service_info WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateServiceStatus(int id, String status) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE service_info SET status = ?, update_time = CURRENT_TIMESTAMP() WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, id);

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<ServiceInfo> getAllServiceInfos() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<ServiceInfo> serviceInfos = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM service_info";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                serviceInfos.add(mapResultSetToServiceInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return serviceInfos;
    }

    @Override
    public int getTotalServices() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int totalServices = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM service_info";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                totalServices = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalServices;
    }

    @Override
    public int getPendingServices() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int pendingServices = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM service_info WHERE status = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "pending");
            rs = stmt.executeQuery();

            if (rs.next()) {
                pendingServices = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return pendingServices;
    }

    private ServiceInfo mapResultSetToServiceInfo(ResultSet rs) throws SQLException {
        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setId(rs.getInt("id"));
        serviceInfo.setUserId(rs.getInt("user_id"));
        serviceInfo.setTitle(rs.getString("title"));
        serviceInfo.setCategory(rs.getString("category"));
        serviceInfo.setDescription(rs.getString("description"));
        serviceInfo.setLocation(rs.getString("location"));
        serviceInfo.setPrice(rs.getDouble("price"));
        serviceInfo.setContactInfo(rs.getString("contact_info"));
        serviceInfo.setStatus(rs.getString("status"));
        serviceInfo.setCreateTime(rs.getTimestamp("create_time"));
        serviceInfo.setUpdateTime(rs.getTimestamp("update_time"));

        // 设置用户信息
        User user = userDAO.findById(serviceInfo.getUserId());
        serviceInfo.setUser(user);

        return serviceInfo;
    }

    @Override
    public List<ServiceInfo> searchServicesWithFilters(String keyword, String category, String status) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServiceInfo> serviceInfos = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM service_info WHERE 1=1");
        List<Object> params = new ArrayList<>();

        try {
            // 如果用户指定了状态，则按状态筛选，否则不限制状态
            if (status != null && !status.isEmpty()) {
                sql.append(" AND status = ?");
                params.add(status);
            }

            // 添加关键字搜索条件
            if (keyword != null && !keyword.isEmpty()) {
                sql.append(" AND (title LIKE ? OR description LIKE ? OR category LIKE ?)");
                String searchKeyword = "%" + keyword + "%";
                params.add(searchKeyword);
                params.add(searchKeyword);
                params.add(searchKeyword);
            }

            // 添加分类筛选条件
            if (category != null && !category.isEmpty()) {
                sql.append(" AND category = ?");
                params.add(category);
            }

            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql.toString());

            // 设置参数
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            rs = stmt.executeQuery();

            // 处理结果集
            while (rs.next()) {
                serviceInfos.add(mapResultSetToServiceInfo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(conn);
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return serviceInfos;
    }
}