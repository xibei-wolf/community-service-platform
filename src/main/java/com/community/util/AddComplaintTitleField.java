package com.community.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AddComplaintTitleField {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        try {
            // Connect to the database
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();

            // Execute ALTER TABLE statement to add title field
            String sql = "ALTER TABLE complaints ADD COLUMN title VARCHAR(255) NOT NULL";
            stmt.executeUpdate(sql);

            
            System.out.println("成功添加title字段到complaints表");

        } catch (SQLException e) {
            // 处理SQL异常
            if (e.getMessage().contains("Duplicate column name")) {
                System.out.println("title字段已经存在于complaints表中");
            } else {
                System.out.println("添加字段失败: " + e.getMessage());
                e.printStackTrace();
            }
        } finally {
            // Close resources
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}