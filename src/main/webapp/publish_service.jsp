<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.User" %>
<html>
<head>
    <title>发布服务 - 社区便民服务平台</title>
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
        .form-container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #f9f9f9;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .form-group textarea {
            height: 120px;
            resize: vertical;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #5D83A6;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 10px;
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
        .error-message {
            color: #f44336;
            margin-bottom: 20px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 4px;
        }
        h2 {
            color: #333;
            margin-top: 0;
            margin-bottom: 20px;
            text-align: center;
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
            <a href="service?action=publish" style="background-color: #555; padding-left: 10px;">发布服务</a>
            <a href="user?action=logout">退出登录</a>
        </div>
        <div class="main-content">
            <div class="form-container">
                <h2>发布服务</h2>
                
                <% if (request.getAttribute("error") != null) { %>
                    <div class="error-message">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
                
                <form action="service?action=add" method="post">
                    <div class="form-group">
                        <label for="title">服务标题</label>
                        <input type="text" id="title" name="title" required placeholder="请输入服务标题">
                    </div>
                    
                    <div class="form-group">
                        <label for="category">服务类别</label>
                        <select id="category" name="category" required>
                            <option value="">请选择服务类别</option>
                            <option value="维修服务">维修服务</option>
                            <option value="家政服务">家政服务</option>
                            <option value="教育培训">教育培训</option>
                            <option value="健康医疗">健康医疗</option>
                            <option value="生活服务">生活服务</option>
                            <option value="其他服务">其他服务</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">服务描述</label>
                        <textarea id="description" name="description" required placeholder="请详细描述您的服务内容、服务流程、注意事项等"></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="location">服务地点</label>
                        <input type="text" id="location" name="location" required placeholder="请输入服务地点">
                    </div>
                    
                    <div class="form-group">
                        <label for="price">服务价格</label>
                        <input type="number" id="price" name="price" step="0.01" required placeholder="请输入服务价格">
                    </div>
                    
                    <div class="form-group">
                        <label for="contactInfo">联系方式</label>
                        <input type="text" id="contactInfo" name="contactInfo" required placeholder="请输入您的联系方式（电话、微信等）">
                    </div>
                    
                    <div style="text-align: center;">
                        <button type="submit" class="btn">发布服务</button>
                        <a href="service?action=list" class="btn btn-secondary">返回服务列表</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>