<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.User" %>
<html>
<head>
    <title>管理员控制台 - 社区便民服务平台</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .header {
            background-color: #5D83A6;
            color: white;
            padding: 15px;
            text-align: center;
        }
        .container {
            display: flex;
            max-width: 1200px;
            margin: 0 auto;
        }
        .sidebar {
            width: 200px;
            background-color: #333;
            color: white;
            padding: 20px;
            min-height: 600px;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
            display: block;
            padding: 10px 0;
            border-bottom: 1px solid #555;
        }
        .sidebar a:hover {
            background-color: #555;
            padding-left: 10px;
        }
        .main-content {
            flex: 1;
            padding: 20px;
            background-color: white;
        }
        .stats {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 20px;
            margin-bottom: 20px;
        }
        .stat-card {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
        }
        .stat-number {
            font-size: 36px;
            font-weight: bold;
            color: #5D83A6;
            margin: 10px 0;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #5D83A6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 0 5px;
        }
        .btn:hover {
            background-color: #4A6B8A;
        }
        .btn-secondary {
            background-color: #2196F3;
        }
        .btn-secondary:hover {
            background-color: #0b7dda;
        }
        .btn-danger {
            background-color: #f44336;
        }
        .btn-danger:hover {
            background-color: #da190b;
        }
        .dashboard {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        .dashboard-card {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            text-align: center;
        }
        .dashboard-card h3 {
            color: #333;
            margin-top: 0;
        }
        .dashboard-card .number {
            font-size: 36px;
            font-weight: bold;
            color: #5D83A6;
            margin: 10px 0;
        }
        .quick-actions {
            margin-top: 30px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <% 
        User user = (User) session.getAttribute("user");
        if (user == null || !"admin".equals(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/user?action=login");
            return;
        }
    %>
    <div class="header">
        <h1>管理员控制台</h1>
        <p>欢迎您，管理员<%= user.getUsername() %>！</p>
    </div>
    <div class="container">
        <div class="sidebar">
            <h3>管理菜单</h3>
            <a href="admin?action=dashboard">控制台首页</a>
            <a href="admin?action=users">用户管理</a>
            <a href="admin?action=pendingServices">待审核服务</a>
            <a href="admin?action=allServices">所有服务</a>
            <a href="appointment?action=adminList">预约管理</a>
            <a href="appointment?action=pendingList">待处理预约</a>
            <a href="${pageContext.request.contextPath}/complaint?action=adminList">投诉管理</a>
            <a href="admin?action=logout">退出登录</a>
        </div>
        <div class="main-content">
            <div class="dashboard">
                <h2>平台统计信息</h2>
                <div class="dashboard-grid">
                    <div class="dashboard-card">
                        <h3>用户总数</h3>
                        <div class="number"><%= request.getAttribute("totalUsers") != null ? request.getAttribute("totalUsers") : "0" %></div>
                        <a href="admin?action=users" class="btn btn-secondary">查看详情</a>
                    </div>
                    <div class="dashboard-card">
                        <h3>服务总数</h3>
                        <div class="number"><%= request.getAttribute("totalServices") != null ? request.getAttribute("totalServices") : "0" %></div>
                        <a href="admin?action=allServices" class="btn btn-secondary">查看详情</a>
                    </div>
                    <div class="dashboard-card">
                        <h3>预约总数</h3>
                        <div class="number"><%= request.getAttribute("totalAppointments") != null ? request.getAttribute("totalAppointments") : "0" %></div>
                        <a href="appointment?action=adminList" class="btn btn-secondary">查看详情</a>
                    </div>
                    <div class="dashboard-card">
                        <h3>评价总数</h3>
                        <div class="number"><%= request.getAttribute("totalReviews") != null ? request.getAttribute("totalReviews") : "0" %></div>
                        <a href="review?action=adminList" class="btn btn-secondary">查看详情</a>
                    </div>
                    <div class="dashboard-card">
                        <h3>待审核服务</h3>
                        <div class="number"><%= request.getAttribute("pendingServices") != null ? request.getAttribute("pendingServices") : "0" %></div>
                        <a href="admin?action=pendingServices" class="btn btn-secondary">查看详情</a>
                    </div>
                    <div class="dashboard-card">
                        <h3>待处理预约</h3>
                        <div class="number"><%= request.getAttribute("pendingAppointments") != null ? request.getAttribute("pendingAppointments") : "0" %></div>
                        <a href="appointment?action=pendingList" class="btn btn-secondary">查看详情</a>
                    </div>
                    <div class="dashboard-card">
                        <h3>待处理投诉</h3>
                        <div class="number"><%= request.getAttribute("pendingComplaints") != null ? request.getAttribute("pendingComplaints") : "0" %></div>
                        <a href="${pageContext.request.contextPath}/complaint?action=adminList" class="btn btn-secondary">查看详情</a>
                    </div>
                </div>
            </div>
            
            <div class="quick-actions">
                <h3>快速操作</h3>
                <a href="admin?action=pendingServices" class="btn">审核服务</a>
                <a href="admin?action=users" class="btn btn-secondary">管理用户</a>
                <a href="appointment?action=adminList" class="btn btn-secondary">管理预约</a>
                <a href="review?action=adminList" class="btn btn-secondary">管理评价</a>
                <a href="${pageContext.request.contextPath}/complaint?action=adminList" class="btn btn-danger">处理投诉</a>
            </div>
        </div>
    </div>
</body>
</html>