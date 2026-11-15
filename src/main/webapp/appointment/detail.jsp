<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.Appointment" %>
<%@ page import="com.community.model.User" %>
<html>
<head>
    <title>预约详情 - 社区便民服务平台</title>
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
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
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
        .profile {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .profile p {
            margin: 10px 0;
        }
        .profile strong {
            display: inline-block;
            width: 120px;
            color: #333;
        }
    </style>
</head>
<body>
    <% 
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("../login.jsp");
            return;
        }
        
        Appointment appointment = (Appointment) request.getAttribute("appointment");
        if (appointment == null) {
            response.sendRedirect("list");
            return;
        }
    %>
    <div class="header">
        <h1>预约详情</h1>
        <p>欢迎您，<%= user.getUsername() %>！</p>
    </div>
    <div class="container">
        <h2>预约信息</h2>
        <div class="profile">
            <p><strong>服务名称：</strong><%= appointment.getServiceInfo().getTitle() %></p>
            <p><strong>服务提供者：</strong><%= appointment.getServiceInfo().getUser().getUsername() %></p>
            <p><strong>预约时间：</strong><%= appointment.getAppointmentTime() %></p>
            <p><strong>预约状态：</strong><%= appointment.getStatus() %></p>
            <p><strong>备注：</strong><%= appointment.getNotes() %></p>
            <p><strong>创建时间：</strong><%= appointment.getCreateTime() %></p>
            <p><strong>更新时间：</strong><%= appointment.getUpdateTime() %></p>
        </div>
        
        <div style="margin-top: 20px;">
            <a href="${pageContext.request.contextPath}/appointment?action=list" class="btn">返回预约列表</a>
            <a href="${pageContext.request.contextPath}/user_center.jsp" class="btn btn-secondary">返回用户中心</a>
        </div>
    </div>
</body>
</html>