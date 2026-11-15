package com.community.dao;

import com.community.model.Complaint;
import com.community.model.ServiceInfo;
import com.community.model.User;
import com.community.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAOImpl implements ComplaintDAO {
    private UserDAO userDAO = new UserDAOImpl();
    private ServiceInfoDAO serviceInfoDAO = new ServiceInfoDAOImpl();

    @Override
    public Complaint findById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Complaint complaint = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM complaints WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                complaint = mapResultSetToComplaint(rs);
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

        return complaint;
    }

    @Override
    public List<Complaint> findByUserId(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Complaint> complaints = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM complaints WHERE user_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
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

        return complaints;
    }

    @Override
    public List<Complaint> findByServiceId(int serviceId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Complaint> complaints = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM complaints WHERE service_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, serviceId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
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

        return complaints;
    }

    @Override
    public boolean addComplaint(Complaint complaint) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO complaints (user_id, service_id, title, content, status, create_time, update_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, complaint.getUserId());
            stmt.setInt(2, complaint.getServiceId());
            stmt.setString(3, complaint.getTitle());
            stmt.setString(4, complaint.getContent());
            stmt.setString(5, complaint.getStatus());
            stmt.setTimestamp(6, complaint.getCreateTime());
            stmt.setTimestamp(7, complaint.getUpdateTime());

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
    public boolean updateComplaint(Complaint complaint) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE complaints SET title = ?, content = ?, status = ?, response = ?, update_time = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, complaint.getTitle());
            stmt.setString(2, complaint.getContent());
            stmt.setString(3, complaint.getStatus());
            stmt.setString(4, complaint.getResponse());
            stmt.setTimestamp(5, complaint.getUpdateTime());
            stmt.setInt(6, complaint.getId());

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
    public boolean updateComplaintStatus(int id, String status, String response) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE complaints SET status = ?, response = ?, update_time = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setString(2, response);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(4, id);

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
    public boolean deleteComplaint(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM complaints WHERE id = ?";
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
    public List<Complaint> getAllComplaints() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Complaint> complaints = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM complaints";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                complaints.add(mapResultSetToComplaint(rs));
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

        return complaints;
    }

    @Override
    public int getTotalComplaints() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int totalComplaints = 0;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM complaints";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                totalComplaints = rs.getInt(1);
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

        return totalComplaints;
    }

    private Complaint mapResultSetToComplaint(ResultSet rs) throws SQLException {
        Complaint complaint = new Complaint();
        complaint.setId(rs.getInt("id"));
        complaint.setUserId(rs.getInt("user_id"));
        complaint.setServiceId(rs.getInt("service_id"));
        complaint.setTitle(rs.getString("title"));
        complaint.setContent(rs.getString("content"));
        complaint.setStatus(rs.getString("status"));
        complaint.setResponse(rs.getString("response"));
        complaint.setCreateTime(rs.getTimestamp("create_time"));
        complaint.setUpdateTime(rs.getTimestamp("update_time"));

        // 设置关联对象
        User user = userDAO.findById(complaint.getUserId());
        ServiceInfo serviceInfo = serviceInfoDAO.findById(complaint.getServiceId());
        complaint.setUser(user);
        complaint.setServiceInfo(serviceInfo);

        return complaint;
    }
}