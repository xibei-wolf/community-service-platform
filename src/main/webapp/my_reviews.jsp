<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.User" %>
<%@ page import="com.community.model.Review" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>我的评价 - 社区便民服务平台</title>
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
        tr:hover {
            background-color: #f5f5f5;
        }
        .no-reviews {
            text-align: center;
            padding: 50px;
            color: #666;
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
        <h1>社区便民服务平台</h1>
        <p>欢迎您，<%= user.getUsername() %>！</p>
    </div>
    <div class="container">
        <div class="sidebar">
            <h3>菜单</h3>
            <a href="user_center.jsp">个人信息</a>
            <a href="service?action=myServices">我的服务</a>
            <a href="appointment?action=myAppointments">我的预约</a>
            <a href="review?action=myReviews">我的评价</a>
            <a href="service?action=publish">发布服务</a>
            <a href="user?action=logout">退出登录</a>
        </div>
        <div class="main-content">
            <h2>我的评价</h2>
            
            <%-- 获取评价列表 --%>
            <% List<Review> myReviews = (List<Review>) request.getAttribute("myReviews"); %>
            
            <% if (myReviews != null && !myReviews.isEmpty()) { %>
                <table>
                    <tr>
                        <th>服务名称</th>
                        <th>评分</th>
                        <th>评价内容</th>
                        <th>评价时间</th>
                        <th>操作</th>
                    </tr>
                    <% for (Review review : myReviews) { %>
                        <tr>
                            <td><%= review.getServiceInfo() != null && review.getServiceInfo().getTitle() != null ? review.getServiceInfo().getTitle() : "未知服务" %></td>
                            <td><%= "★".repeat(review.getRating()) %></td>
                            <td><%= review.getComment() != null ? review.getComment() : "无评价内容" %></td>
                            <td><%= review.getCreateTime() != null ? review.getCreateTime() : "未知时间" %></td>
                            <td>
                                <a href="review?action=delete&id=<%= review.getId() %>" class="btn btn-danger" onclick="return confirm('确定要删除这条评价吗？');">删除</a>
                            </td>
                        </tr>
                    <% } %>
                </table>
            <% } else { %>
                <div class="no-reviews">
                    <h3>您还没有发表任何评价</h3>
                    <p>快去体验服务并发表评价吧！</p>
                    <a href="service?action=list" class="btn">浏览服务</a>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>