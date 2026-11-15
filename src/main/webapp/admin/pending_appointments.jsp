<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.Appointment" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>待处理预约- 社区便民服务平台</title>
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
        .status-pending {
            color: orange;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>管理员控制台</h1>
        <p>待处理预约</p>
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
            <a href="admin?action=complaints">投诉管理</a>
            <a href="admin?action=logout">退出登录</a>
        </div>
        <div class="main-content">
            <h2>待处理预约记录</h2>
            <a href="appointment?action=adminList" class="btn btn-secondary">查看所有预约</a>
            
            <table>
                <tr>
                    <th>预约ID</th>
                    <th>用户ID</th>
                    <th>服务ID</th>
                    <th>预约时间</th>
                    <th>状态</th>
                    <th>创建时间</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
                <% 
                    List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
                    if (appointments != null && !appointments.isEmpty()) {
                        for (Appointment appointment : appointments) {
                %>
                <tr>
                    <td><%= appointment.getId() %></td>
                    <td><%= appointment.getUserId() %></td>
                    <td><%= appointment.getServiceId() %></td>
                    <td><%= appointment.getAppointmentTime() %></td>
                    <td class="status-pending">待处理</td>
                    <td><%= appointment.getCreateTime() %></td>
                    <td><%= appointment.getNotes() != null ? appointment.getNotes() : "无" %></td></td>
                    <td>
                        <a href="appointment?action=detail&id=<%= appointment.getId() %>" class="btn">查看详情</a>
                        <a href="appointment?action=confirm&id=<%= appointment.getId() %>" class="btn btn-secondary">确认预约</a>
                        <a href="appointment?action=cancel&id=<%= appointment.getId() %>" class="btn btn-danger">取消预约</a>
                    </td>
                </tr>
                <% } } else { %>
                <tr>
                    <td colspan="8" style="text-align: center;">暂无待处理预约</td>
                </tr>
                <% } %>
            </table>
        </div>
    </div>
</body>
</html>