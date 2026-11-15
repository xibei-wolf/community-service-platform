<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>预约服务 - 社区便民服务平台</title>
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
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input, .form-group textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
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
        .error {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>预约服务</h1>
    </div>
    <div class="container">
        <h2>填写预约信息</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        <form action="appointment?action=add" method="post">
            <input type="hidden" name="serviceId" value="<%= request.getAttribute("serviceId") %>" />
            <div class="form-group">
                <label for="appointmentTime">预约时间</label>
                <input type="datetime-local" id="appointmentTime" name="appointmentTime" required />
            </div>
            <div class="form-group">
                <label for="notes">备注信息</label>
                <textarea id="notes" name="notes" rows="4" placeholder="请输入备注信息（可选）"></textarea>
            </div>
            <div>
                <button type="submit" class="btn">提交预约</button>
                <a href="appointment?action=list" class="btn btn-secondary">取消</a>
            </div>
        </form>
    </div>
</body>
</html>