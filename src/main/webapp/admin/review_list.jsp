<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.Review" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>评价管理 - 社区便民服务平台</title>
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
        .rating {
            color: #FFD700;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>管理员控制台</h1>
        <p>评价管理</p>
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
            <a href="review?action=adminList">评价管理</a>
            <a href="admin?action=complaints">投诉管理</a>
            <a href="admin?action=logout">退出登录</a>
        </div>
        <div class="main-content">
            <h2>所有评价记录</h2>
            
            <table>
                <tr>
                    <th>评价ID</th>
                    <th>用户ID</th>
                    <th>服务ID</th>
                    <th>评分</th>
                    <th>评价内容</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                <% 
                    List<Review> reviews = (List<Review>) request.getAttribute("reviews");
                    if (reviews != null && !reviews.isEmpty()) {
                        for (Review review : reviews) {
                %>
                <tr>
                    <td><%= review.getId() %></td>
                    <td><%= review.getUserId() %></td>
                    <td><%= review.getServiceId() %></td>
                    <td class="rating"><%= review.getRating() %>/5</td>
                    <td><%= review.getComment() != null ? review.getComment() : "无" %></td></td>
                    <td><%= review.getCreateTime() %></td>
                    <td>
                            <a href="review?action=delete&id=<%= review.getId() %>" class="btn btn-danger" onclick="return confirm('确定要删除这条评价吗？');">删除</a>
                        </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7" style="text-align: center; padding: 20px;">暂无评价记录</td>
                </tr>
                <% 
                    }
                %>
            </table>
        </div>
    </div>
</body>
</html>