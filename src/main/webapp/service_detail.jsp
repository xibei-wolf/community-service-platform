<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.community.model.ServiceInfo" %>
<%@ page import="com.community.model.User" %>
<html>
<% 
    // 在页面顶部声明currentUser变量，使其在整个页面中可用
    User currentUser = (User) session.getAttribute("user");
%>
<head>
    <title>服务详情 - 社区便民服务平台</title>
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
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            margin-top: 20px;
            border-radius: 8px;
        }
        .service-detail {
            margin-bottom: 30px;
        }
        .service-detail h2 {
            color: #333;
            margin-top: 0;
            padding-bottom: 10px;
            border-bottom: 1px solid #ddd;
        }
        .service-info {
            margin: 15px 0;
            line-height: 1.6;
        }
        .service-info label {
            font-weight: bold;
            color: #555;
            display: inline-block;
            width: 120px;
        }
        .service-description {
            margin: 20px 0;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
            line-height: 1.6;
        }
        .service-price {
            color: #f44336;
            font-weight: bold;
            font-size: 24px;
            margin: 20px 0;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #5D83A6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
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
        .service-status {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
            margin-left: 10px;
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
        <% 
            ServiceInfo service = (ServiceInfo) request.getAttribute("service");
            if (service == null) {
                response.sendRedirect("service_list.jsp");
                return;
            }

        %>
        
        <div class="service-detail">
            <h2>
                <%= service.getTitle() %>
                <span class="service-status 
                    <%= service.getStatus().equals("pending") ? "status-pending" : 
                       service.getStatus().equals("approved") ? "status-approved" : "status-rejected" %>">
                    <%= service.getStatus().equals("pending") ? "待审核" : 
                       service.getStatus().equals("approved") ? "已通过" : "已拒绝" %>
                </span>
            </h2>
            
            <div class="service-info">
                <label>分类：</label><%= service.getCategory() %>
            </div>
            
            <div class="service-info">
                <label>提供者：</label><%= service.getUser() != null ? service.getUser().getUsername() : "未知" %>
            </div>
            
            <div class="service-info">
                <label>服务地点：</label><%= service.getLocation() %>
            </div>
            
            <div class="service-price">
                价格：¥<%= service.getPrice() %>
            </div>
            
            <div class="service-info">
                <label>发布时间：</label><%= service.getCreateTime() %>
            </div>
            
            <div class="service-info">
                <label>联系方式：</label><%= service.getContactInfo() %>
            </div>
            
            <div class="service-description">
                <label style="display: block; margin-bottom: 10px;">服务描述：</label>
                <%= service.getDescription() %>
            </div>
            
            <div style="margin-top: 30px;">
            <a href="service?action=list" class="btn btn-secondary">返回列表</a>
            <% if (service.getStatus().equals("approved")) { %>
                <a href="appointment?action=create&id=<%= service.getId() %>" class="btn">预约服务</a>
            <% } %>
        </div>
    </div>
    
    <!-- 评价部分 -->
    <div class="container" style="margin-top: 30px;">
        <h3>服务评价</h3>
        
        <!-- 评价列表 -->
        <div class="reviews">
            <% 
                // 获取该服务的评价
                com.community.service.ReviewService reviewService = new com.community.service.ReviewService();
                java.util.List<com.community.model.Review> reviews = reviewService.getReviewsByServiceId(service.getId());
                
                if (reviews != null && !reviews.isEmpty()) {
                    for (com.community.model.Review review : reviews) {
                        com.community.model.User reviewer = new com.community.service.UserService().getUserById(review.getUserId());
            %>
            <div style="background-color: #f9f9f9; padding: 15px; margin: 10px 0; border-radius: 8px;">
                <div style="display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <strong><%= reviewer != null ? reviewer.getUsername() : "匿名用户" %></strong>
                    </div>
                    <div style="color: #ff9800;">
                        <% for (int i = 0; i < review.getRating(); i++) { %>
                            ★
                        <% } %>
                        <% for (int i = 0; i < (5 - review.getRating()); i++) { %>
                            ☆
                        <% } %>
                    </div>
                </div>
                <div style="margin-top: 10px; color: #555;">
                    <%= review.getComment() != null ? review.getComment() : "无评价内容" %>
                </div>
                <div style="margin-top: 10px; font-size: 12px; color: #999;">
                    <%= review.getCreateTime() %>
                </div>
            </div>
            <% 
                    }
                } else {
            %>
            <div style="text-align: center; padding: 20px; color: #666;">
                暂无评价
            </div>
            <% 
                }
            %>
        </div>
        
        <!-- 添加评价表单 -->
        <% 
            if (currentUser != null) { 
                // 检查用户是否已经评价过该服务
                boolean hasReviewed = false;
                for (com.community.model.Review review : reviews) {
                    if (review.getUserId() == currentUser.getId()) {
                        hasReviewed = true;
                        break;
                    }
                }
                
                if (!hasReviewed) { 
            %>
        <div style="margin-top: 30px; padding: 20px; background-color: #f9f9f9; border-radius: 8px;">
            <h4>添加评价</h4>
            <form action="review?action=add" method="post">
                <input type="hidden" name="serviceId" value="<%= service.getId() %>">
                
                <div style="margin: 15px 0;">
                    <label style="display: block; margin-bottom: 5px;">评分：</label>
                    <div style="color: #ff9800;">
                        <input type="radio" name="rating" value="1" required>★                        <input type="radio" name="rating" value="2" required>★★
                        <input type="radio" name="rating" value="3" required>★★★                        <input type="radio" name="rating" value="4" required>★★★★
                        <input type="radio" name="rating" value="5" required>★★★★★                    </div>
                </div>
                
                <div style="margin: 15px 0;">
                    <label style="display: block; margin-bottom: 5px;">评价内容：</label>
                    <textarea name="comment" rows="5" cols="50" style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; resize: vertical;"></textarea>
                </div>
                
                <button type="submit" class="btn">提交评价</button>
            </form>
        </div>
        <% 
                } else {
            %>
        <div style="margin-top: 20px; padding: 10px; background-color: #e3f2fd; border-radius: 4px; text-align: center;">
            <p style="color: #1976d2;">您已经评价过该服务，感谢您的反馈！</p>
        </div>
        <% 
                }
            }
        %>
    </div>
    
    <!-- 投诉部分 -->
    <div class="container" style="margin-top: 30px;">
        <h3>提交投诉</h3>
        
        <% 
            if (currentUser != null) { 
        %>
        <div style="margin-top: 20px; padding: 20px; background-color: #f9f9f9; border-radius: 8px;">
            <h4>投诉该服务</h4>
            <form action="complaint?action=add" method="post">
                <input type="hidden" name="serviceId" value="<%= service.getId() %>">
                
                <div style="margin: 15px 0;">
                    <label style="display: block; margin-bottom: 5px;">投诉标题：</label>
                    <input type="text" name="title" required style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px;">
                </div>
                
                <div style="margin: 15px 0;">
                    <label style="display: block; margin-bottom: 5px;">投诉内容：</label>
                    <textarea name="content" rows="5" cols="50" required style="width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; resize: vertical;"></textarea>
                </div>
                
                <button type="submit" class="btn">提交投诉</button>
            </form>
        </div>
        <% 
            } else { 
        %>
        <div style="margin-top: 20px; padding: 10px; background-color: #fff3e0; border-radius: 4px; text-align: center;">
            <p style="color: #e65100;">请先登录后再提交投诉</p>
        </div>
        <% 
            } 
        %>
    </div>
</div>
</body>
</html>