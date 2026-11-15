<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.ServiceInfo" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>服务管理 - 社区便民服务平台</title>
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
            background-color: #555;
        }
        .btn-secondary:hover {
            background-color: #777;
        }
        .btn-warning {
            background-color: #ff9800;
        }
        .btn-warning:hover {
            background-color: #f57c00;
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
        .service-status {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-pending {
            background-color: #ffeb3b;
            color: #333;
        }
        .status-approved {
            background-color: #5D83A6;
            color: white;
        }
        .status-rejected {
            background-color: #f44336;
            color: white;
        }
        .actions {
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>社区便民服务平台 - 服务管理</h1>
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
            <div class="search-bar">
            <form action="service" method="get">
                <input type="hidden" name="action" value="adminList">
                <input type="text" name="keyword" placeholder="搜索服务名称或描述">
                <select name="category">
                    <option value="">所有分类</option>
                    <option value="家政服务">家政服务</option>
                    <option value="维修服务">维修服务</option>
                    <option value="二手物品">二手物品</option>
                    <option value="生活用品配送">生活用品配送</option>
                </select>
                <select name="status">
                    <option value="">所有状态</option>
                    <option value="pending">待审核</option>
                    <option value="approved">已通过</option>
                    <option value="rejected">已拒绝</option>
                </select>
                <button type="submit" class="btn">搜索</button>
                <a href="admin?action=dashboard" class="btn btn-secondary">返回管理首页</a>
            </form>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>服务ID</th>
                    <th>服务名称</th>
                    <th>状态</th>
                    <th>分类</th>
                    <th>提供</th>
                    <th>价格</th>
                    <th>发布时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<ServiceInfo> services = (List<ServiceInfo>) request.getAttribute("services");
                    if (services != null && !services.isEmpty()) {
                        for (ServiceInfo service : services) {
                %>
                <tr>
                    <td><%= service.getId() %></td>
                    <td><%= service.getTitle() %></td>
                    <td>
                        <span class="service-status status-<%= service.getStatus() %>">
                            <%= service.getStatus().equals("pending") ? "待审核" : (service.getStatus().equals("approved") ? "已通过" : "已拒绝") %>
                        </span>
                    </td>
                    <td><%= service.getCategory() %></td>
                    <td><%= service.getUserId() %></td>
                    <td>¥<%= service.getPrice() %></td>
                    <td><%= service.getCreateTime() %></td>
                    <td class="actions">
                        <% if (service.getStatus().equals("pending")) { %>
                            <a href="admin?action=approveService&id=<%= service.getId() %>" class="btn">通过</a>
                            <a href="admin?action=rejectService&id=<%= service.getId() %>" class="btn btn-warning">拒绝</a>
                        <% } %>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="8" style="text-align: center; padding: 20px;">暂无服务记录</td>
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