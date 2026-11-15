<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>用户中心 - 社区便民服务平台</title>
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
        .profile {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
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
    </style>
</head>
<body>
    <% 
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <div class="header">
        <h1>用户中心</h1>
        <p>欢迎您，<%= user.getUsername() %>！</p>
    </div>
    <div class="container">
        <div class="sidebar">
            <h3>菜单</h3>
            <a href="user_center.jsp">个人信息</a>
            <a href="service?action=myServices">服务列表</a>
            <a href="appointment?action=myAppointments">我的预约</a>
            <a href="review?action=myReviews">我的评价</a>
            <a href="complaint?action=myComplaints">我的投诉</a>
            <a href="service?action=publish">发布服务</a>
            <a href="user?action=logout">退出登录</a>
        </div>
        <div class="main-content">
            <%-- 检查是否有用户评价数据 --%>
            <% List<?> myReviews = (List<?>) request.getAttribute("myReviews"); %>
            <% if (myReviews != null && !myReviews.isEmpty()) { %>
                <h2>我的评价</h2>
                <table border="1" cellspacing="0" cellpadding="10" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
                    <tr style="background-color: #f2f2f2;">
                        <th>服务名称</th>
                        <th>评分</th>
                        <th>评价内容</th>
                        <th>评价时间</th>
                        <th>操作</th>
                    </tr>
                    <% for (Object reviewObj : myReviews) { %>
                        <% com.community.model.Review review = (com.community.model.Review) reviewObj; %>
                        <tr>
                            <td><%= review.getServiceInfo() != null && review.getServiceInfo().getTitle() != null ? review.getServiceInfo().getTitle() : "未知服务" %></td>
                            <td><%= "★".repeat(review.getRating()) %></td>
                            <td><%= review.getComment() != null ? review.getComment() : "无评价内容" %></td>
                            <td><%= review.getCreateTime() != null ? review.getCreateTime() : "未知时间" %></td>
                            <td>
                                <a href="review?action=delete&id=<%= review.getId() %>" class="btn btn-danger" onclick="return confirm('确定要删除这条评价吗？')">删除</a>
                            </td>
                        </tr>
                    <% } %>
                </table>
            <% } else { %>
                <h2>个人信息</h2>
                <div class="profile">
                    <p><strong>用户名：</strong><%= user.getUsername() %></p>
                    <p><strong>邮箱：</strong><%= user.getEmail() %></p>
                    <p><strong>手机号：</strong><%= user.getPhone() %></p>
                    <p><strong>注册时间：</strong><%= user.getRegisterTime() %></p>
                    <p><strong>状态：</strong><%= user.isStatus()? "正常" : "禁用" %></p>
                    <p><strong>角色：</strong><%= user.getRole().equals("admin") ? "管理员" : "普通用户" %></p>
                </div>
                <div>
                    <h3>快速操作</h3>
                    <a href="service?action=publish" class="btn">发布新服务</a>
                    <a href="service?action=list" class="btn btn-secondary">浏览所有服务</a>
                    <a href="user?action=update" class="btn">修改密码</a>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>