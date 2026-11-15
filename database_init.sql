-- 创建数据库
CREATE DATABASE IF NOT EXISTS community_platform DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE community_platform;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user', -- user/admin
    status INT NOT NULL DEFAULT 1, -- 1:正常 0:禁用
    register_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建服务信息表
CREATE TABLE IF NOT EXISTS service_info (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    contact_info VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pending', -- pending/approved/rejected, completed
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 创建预约表
CREATE TABLE IF NOT EXISTS complaints (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    service_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pending', -- pending/processed/resolved
    response TEXT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    process_time TIMESTAMP NULL,
    process_content TEXT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (service_id) REFERENCES service_info(id)
);

-- 创建评价表
CREATE TABLE IF NOT EXISTS reviews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    service_id INT NOT NULL,
    user_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (service_id) REFERENCES service_info(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 创建投诉表
CREATE TABLE IF NOT EXISTS complaints (
    id INT PRIMARY KEY AUTO_INCREMENT,
    service_id INT NOT NULL,
    user_id INT NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pending', -- pending/processed/resolved
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    process_time TIMESTAMP NULL,
    process_content TEXT NULL,
    FOREIGN KEY (service_id) REFERENCES service_info(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 插入管理员账号
INSERT INTO user (username, password, email, phone, role, status) VALUES
('admin', 'admin123', 'admin@example.com', '13800138000', 'admin', 1) ON DUPLICATE KEY UPDATE username='admin';

-- 插入测试用户
INSERT INTO user (username, password, email, phone, role, status) VALUES
('user1', '123456', 'user1@example.com', '13800138001', 'user', 1) ON DUPLICATE KEY UPDATE username='user1';

-- 插入测试服务信息
INSERT INTO service_info (title, category, description, location, price, contact_info, status, user_id) VALUES
('专业家政保洁', '家政服务', '提供专业的家庭保洁服务，包括日常清洁、深度清洁等', '北京市朝阳区', 50.00, '13800138001', 'pending', 2) ON DUPLICATE KEY UPDATE title='专业家政保洁';

INSERT INTO service_info (title, category, description, location, price, contact_info, status, user_id) VALUES
('电器维修服务', '维修服务', '专业维修各种家用电器，包括空调、洗衣机、冰箱等', '北京市海淀区', 80.00, '13800138001', 'pending', 2) ON DUPLICATE KEY UPDATE title='电器维修服务';

INSERT INTO service_info (title, category, description, location, price, contact_info, status, user_id) VALUES
('二手自行车出售', '二手物品', '九成新自行车，适合城市通勤，价格优惠', '北京市东城区', 150.00, '13800138001', 'pending', 2) ON DUPLICATE KEY UPDATE title='二手自行车出售';

INSERT INTO service_info (title, category, description, location, price, contact_info, status, user_id) VALUES
('生活用品配送', '生活用品配送', '提供日常生活用品配送服务，30分钟内送达', '北京市西城区', 5.00, '13800138001', 'pending', 2) ON DUPLICATE KEY UPDATE title='生活用品配送';
