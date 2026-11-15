<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.User" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>用户管理 - 社区便民服务平台</title>
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
            width: 250px;
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
        .user-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        .user-table th,
        .user-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .user-table th {
            background-color: #5D83A6;
            color: white;
        }
        .user-table tr:hover {
            background-color: #f5f5f5;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #5D83A6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin: 0 5px;
            border: none;
            cursor: pointer;
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
        .btn-small {
            padding: 4px 8px;
            font-size: 14px;
        }
        .status-active {
            color: green;
            font-weight: bold;
        }
        .status-inactive {
            color: red;
            font-weight: bold;
        }
        .page-title {
            font-size: 24px;
            margin-bottom: 20px;
            color: #333;
        }
    </style>
</head>
<body>
    <% 
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !currentUser.getRole().equals("admin")) {
            response.sendRedirect("login.jsp");
            return;
        }
    %>
    <div class="header">
        <h1>用户管理</h1>
        <p>欢迎您，管理员<%= currentUser.getUsername() %>！</p>
    </div>
    <div class="container">
        <div class="sidebar">
            <h3>管理菜单</h3>
            <a href="admin?action=dashboard">管理首页</a>
            <a href="user?action=list">用户管理</a>
            <a href="service?action=adminList">服务管理</a>
            <a href="appointment?action=adminList">预约管理</a>
            <a href="review?action=adminList">评价管理</a>
            <a href="user?action=logout">退出登录</a>
        </div>
        <div class="main-content">
            <h2 class="page-title">用户列表</h2>
            <table class="user-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>用户名</th>
                        <th>邮箱</th>
                        <th>电话</th>
                        <th>角色</th>
                        <th>注册时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<User> users = (List<User>) request.getAttribute("users");
                        if (users != null && !users.isEmpty()) {
                            for (User user : users) {
                    %> 
                    <tr>
                        <td><%= user.getId() %></td>
                        <td><%= user.getUsername() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getPhone() %></td>
                        <td><%= user.getRole() %></td>
                        <td><%= user.getRegisterTime().toString().substring(0, 19) %></td>
                        <td>
                            <% if (user.isStatus()) { %>
                                <span class="status-active">已激活</span>
                            <% } else { %>
                                <span class="status-inactive">已禁用</span>
                            <% } %>
                        </td>
                        <td>
                            <a href="#" class="btn btn-small btn-secondary">查看</a>
                            <a href="#" class="btn btn-small">编辑</a>
                            <% if (user.isStatus()) { %>
                                <a href="#" class="btn btn-small btn-danger">禁用</a>
                            <% } else { %>
                                <a href="#" class="btn btn-small btn-secondary">启用</a>
                            <% } %>
                        </td>
                    </tr>
                    <% 
                            }
                        } else {
                    %> 
                    <tr>
                        <td colspan="8" style="text-align: center; padding: 20px;">暂无用户数据</td>
                    </tr>
                    <% 
                        }
                    %> 
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>