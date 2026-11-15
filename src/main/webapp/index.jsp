<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>社区便民服务平台</title>
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
            padding: 20px;
            text-align: center;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .intro {
            text-align: center;
            margin: 50px 0;
        }
        .features {
            display: flex;
            justify-content: space-around;
            margin: 50px 0;
        }
        .feature {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            width: 200px;
        }
        .actions {
            text-align: center;
            margin: 50px 0;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 0 10px;
            background-color: #5D83A6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
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
    </style>
</head>
<body>
    <div class="header">
        <h1>社区便民服务平台</h1>
        <p>为社区居民提供便捷的生活服务</p>
    </div>
    <div class="container">
        <div class="intro">
            <h2>欢迎来到社区便民服务平台</h2>
            <p>我们提供家政、维修、二手物品交易、生活用品配送等各类便民服务</p>
        </div>
        <div class="features">
            <div class="feature">
                <h3>家政服务</h3>
                <p>专业的家政服务人员，让您的生活更轻松</p>
            </div>
            <div class="feature">
                <h3>维修服务</h3>
                <p>快速响应的维修服务，解决您的燃眉之急</p>
            </div>
            <div class="feature">
                <h3>二手交易</h3>
                <p>安全可靠的二手物品交易平台</p>
            </div>
            <div class="feature">
                <h3>生活用品配送</h3>
                <p>便捷的生活用品配送服务，送货上门</p>
            </div>
        </div>
        <div class="actions">
            <a href="login.jsp" class="btn">登录</a>
            <a href="register.jsp" class="btn btn-secondary">注册</a>
        </div>
    </div>
</body>
</html>