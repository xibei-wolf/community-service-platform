<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.Appointment" %>
<%@ page import="com.community.model.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>我的预约 - 社区便民服务平台</title>
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
            max-width: 1200px;
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
        .btn-danger {
            background-color: #f44336;
        }
        .btn-danger:hover {
            background-color: #da190b;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #5D83A6;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
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
    %>
    <div class="header">
        <h1>我的预约</h1>
        <p>欢迎您，<%= user.getUsername() %>！</p>
    </div>
    <div class="container">
        <h2>预约列表</h2>
        
        <%-- 显示错误信息 --%>
        <% if (request.getAttribute("error") != null) { %>
            <div style="color: red; margin-bottom: 20px;">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <%-- 显示成功信息 --%>
        <% if (request.getAttribute("success") != null) { %>
            <div style="color: green; margin-bottom: 20px;">
                <%= request.getAttribute("success") %>
            </div>
        <% } %>
        
        <table>
            <thead>
                <tr>
                    <th>服务名称</th>
                    <th>预约时间</th>
                    <th>状态</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
                    if (appointments != null && !appointments.isEmpty()) {
                        for (Appointment appointment : appointments) {
                %>
                <tr>
                    <td><%= appointment.getServiceInfo().getTitle() %></td>
                    <td><%= appointment.getAppointmentTime() %></td>
                    <td><%= appointment.getStatus() %></td>
                    <td><%= appointment.getNotes() %></td>
                    <td>
                        <a href="../convenience-platform/appointment?action=detail&id=<%= appointment.getId() %>" class="btn btn-secondary">详情</a>
                        <% if (appointment.getStatus().equals("pending")) { %>
                            <a href="../convenience-platform/appointment?action=cancel&id=<%= appointment.getId() %>" class="btn btn-danger">取消</a>
                        <% } %>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" style="text-align: center; padding: 20px;">您还没有任何预约</td>
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <div style="margin-top: 20px;">
            <a href="../convenience-platform/user_center.jsp" class="btn">返回用户中心</a>
            <a href="../convenience-platform/service?action=myServices" class="btn">浏览服务</a>
        </div>
    </div>
</body>
</html>