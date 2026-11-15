<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.Complaint" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>投诉管理 - 社区便民服务平台</title>
    <style>
        /* 全局样式 */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }

        /* 头部样式 */
        .header {
            background-color: #5D83A6;
            color: white;
            padding: 15px;
            text-align: center;
        }

        /* 容器样式 */
        .container {
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-top: 20px;
            border-radius: 8px;
        }

        h2 {
            color: #333;
            margin-top: 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #ddd;
        }

        /* 导航栏样式 */
        .nav {
            background-color: #2196F3;
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
        }

        .nav a {
            color: white;
            text-decoration: none;
            margin-right: 20px;
            padding: 8px 16px;
            border-radius: 4px;
        }

        .nav a:hover {
            background-color: #0b7dda;
        }

        /* 投诉项样式 */
        .complaint-item {
            margin: 20px 0;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
        }

        .complaint-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }

        .complaint-title {
            font-weight: bold;
            color: #333;
            font-size: 16px;
        }

        /* 状态标签样式 */
        .complaint-status {
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

        .status-resolved {
            background-color: #5D83A6;
            color: white;
        }

        .status-rejected {
            background-color: #f44336;
            color: white;
        }

        /* 投诉信息样式 */
        .complaint-info {
            margin: 10px 0;
            color: #555;
        }

        .complaint-content {
            margin: 15px 0;
            padding: 15px;
            background-color: white;
            border-radius: 4px;
            border-left: 3px solid #2196F3;
        }

        .complaint-response {
            margin: 15px 0;
            padding: 15px;
            background-color: #e8f5e9;
            border-radius: 4px;
            border-left: 3px solid #5D83A6;
        }

        .response-label {
            font-weight: bold;
            color: #388e3c;
            margin-bottom: 5px;
            display: block;
        }

        /* 按钮样式 */
        .btn {
            display: inline-block;
            padding: 8px 16px;
            background-color: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
            border: none;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #0b7dda;
        }

        .btn-success {
            background-color: #5D83A6;
        }

        .btn-success:hover {
            background-color: #4A6B8A;
        }

        .btn-danger {
            background-color: #f44336;
        }

        .btn-danger:hover {
            background-color: #da190b;
        }

        .btn-primary {
            background-color: #2196F3;
        }

        .btn-primary:hover {
            background-color: #0b7dda;
        }

        .action-form {
            display: inline;
        }

        /* 回复表单样式 */
        .response-form {
            margin-top: 15px;
            padding: 15px;
            background-color: #fff3e0;
            border-radius: 4px;
            border-left: 3px solid #ff9800;
        }

        .response-form textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            resize: vertical;
            min-height: 100px;
            margin: 10px 0;
        }

        /* 空数据提示样式 */
        .empty-message {
            text-align: center;
            padding: 40px;
            color: #666;
            background-color: #f9f9f9;
            border-radius: 8px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>社区便民服务平台 - 管理员后台</h1>
        <p>投诉管理</p>
    </div>

    <div class="container">
        <!-- 导航栏 -->
        <div class="nav">
            <a href="${pageContext.request.contextPath}/admin/admin_center.jsp">管理中心</a>
            <a href="${pageContext.request.contextPath}/admin/service_admin_list.jsp">服务管理</a>
            <a href="${pageContext.request.contextPath}/admin/user_admin_list.jsp">用户管理</a>
            <a href="${pageContext.request.contextPath}/complaint?action=adminList">投诉管理</a>
        </div>

        <h2>投诉列表</h2>

        <% 
            // 获取投诉列表数据
            List<Complaint> complaints = (List<Complaint>) request.getAttribute("complaints");
            if (complaints != null && !complaints.isEmpty()) {
                for (Complaint complaint : complaints) {
        %>
                    <!-- 单个投诉项 -->
                    <div class="complaint-item">
                        <div class="complaint-header">
                            <div class="complaint-title"><%= complaint.getTitle() %></div>
                            <div class="complaint-status <%= 
                                complaint.getStatus().equals("pending") ? "status-pending" : 
                                complaint.getStatus().equals("resolved") ? "status-resolved" : "status-rejected" 
                            %>">
                                <%= 
                                    complaint.getStatus().equals("pending") ? "待处理" : 
                                    complaint.getStatus().equals("resolved") ? "已解决" : "已驳回" 
                                %>
                            </div>
                        </div>

                        <div class="complaint-info">
                            <strong>投诉ID：</strong><%= complaint.getId() %> | 
                            <strong>用户ID：</strong><%= complaint.getUserId() %> | 
                            <strong>服务ID：</strong><%= complaint.getServiceId() %>
                        </div>

                        <div class="complaint-info">
                            <strong>提交时间：</strong><%= complaint.getCreateTime() %> | 
                            <strong>更新时间：</strong><%= complaint.getUpdateTime() %>
                        </div>

                        <div class="complaint-content">
                            <%= complaint.getContent() %>
                        </div>

                        <% 
                            // 显示管理员回复（如果有）
                            if (complaint.getResponse() != null && !complaint.getResponse().isEmpty()) { 
                        %>
                            <div class="complaint-response">
                                <span class="response-label">管理员回复：</span>
                                <%= complaint.getResponse() %>
                            </div>
                        <% } %>

                        <% 
                            // 待处理状态显示处理表单
                            if (complaint.getStatus().equals("pending")) { 
                        %>
                            <div class="response-form">
                                <h4>处理投诉</h4>
                                
                                <!-- 标记为已解决表单 -->
                                <form class="action-form" action="${pageContext.request.contextPath}/complaint?action=resolve" method="post">
                                    <input type="hidden" name="id" value="<%= complaint.getId() %>">
                                    <div>
                                        <label for="resolve-response-<%= complaint.getId() %>">解决回复：</label>
                                        <textarea id="resolve-response-<%= complaint.getId() %>" name="response" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-success">标记为已解决</button>
                                </form>

                                <!-- 驳回投诉表单 -->
                                <form class="action-form" action="${pageContext.request.contextPath}/complaint?action=reject" method="post">
                                    <input type="hidden" name="id" value="<%= complaint.getId() %>">
                                    <div>
                                        <label for="reject-response-<%= complaint.getId() %>">驳回回复：</label>
                                        <textarea id="reject-response-<%= complaint.getId() %>" name="response" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-danger">驳回投诉</button>
                                </form>
                            </div>
                        <% } %>
                    </div>
        <% 
                }
            } else { 
        %>
                <!-- 空数据提示 -->
                <div class="empty-message">
                    <h3>暂无投诉记录</h3>
                    <p>目前没有用户提交的投诉</p>
                </div>
        <% } %>
    </div>
</body>
</html>