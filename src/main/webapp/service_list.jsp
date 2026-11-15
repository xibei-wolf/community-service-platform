<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.community.model.ServiceInfo" %>
<html>
<head>
    <title>服务列表 - 社区便民服务平台</title>
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
        .search-bar {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .search-bar input, .search-bar select {
            padding: 8px;
            margin-right: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #5D83A6;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            margin: 0 5px;
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
        .service-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
        }
        .service-card {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .service-card h3 {
            margin-top: 0;
            color: #333;
        }
        .service-info {
            color: #666;
            margin: 10px 0;
        }
        .service-price {
            color: #f44336;
            font-weight: bold;
            font-size: 18px;
            margin: 10px 0;
        }
        .service-status {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
            margin-bottom: 10px;
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
    </style>
</head>
<body>
    <div class="header">
        <h1>社区便民服务平台</h1>
        <p>为社区居民提供便捷的生活服务</p>
    </div>
    <div class="container">
        <div class="search-bar">
            <form action="service" method="get">
                <input type="hidden" name="action" value="list">
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
                    <option value="approved">已通过</option>
                </select>
                <button type="submit" class="btn">搜索</button>
                <a href="service?action=publish" class="btn btn-secondary">发布服务</a>
            </form>
        </div>
        
        <div class="service-grid">
            <% 
                List<ServiceInfo> services = (List<ServiceInfo>) request.getAttribute("services");
                if (services != null && !services.isEmpty()) {
                    for (ServiceInfo service : services) {
            %>
            <div class="service-card">
                <h3><%= service.getTitle() %></h3>
                <div class="service-status 
                    <%= service.getStatus().equals("pending") ? "status-pending" : 
                       service.getStatus().equals("approved") ? "status-approved" : "status-rejected" %>">
                    <%= service.getStatus().equals("pending") ? "待审核" : 
                       service.getStatus().equals("approved") ? "已通过" : "已拒绝" %>
                </div>
                <div class="service-info">分类：<%= service.getCategory() %></div>
                <div class="service-info">提供者：<%= service.getUser().getUsername() %></div>
                <div class="service-info"><%= service.getDescription() %></div>
                <div class="service-price">价格：¥<%= service.getPrice() %></div>
                <div class="service-info">发布时间：<%= service.getCreateTime() %></div>
                <a href="service?action=detail&id=<%= service.getId() %>" class="btn">查看详情</a>
                <a href="appointment?action=create&id=<%= service.getId() %>" class="btn btn-secondary">预约服务</a>
            </div>
            <% 
                    }
                } else {
            %>
            <div style="grid-column: 1 / -1; text-align: center; padding: 50px;">
                <h3>暂无服务信息</h3>
            </div>
            <% 
                }
            %>
        </div>
    </div>
</body>
</html>