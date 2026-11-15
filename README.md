# community · 社区服务平台

基于 JSP + Servlet 的校园社区服务平台，实现用户注册登录、服务发布、预约、评价、投诉与管理后台等功能。

## 技术栈

JSP + Servlet + Maven + MySQL

## 功能

- 用户：注册 / 登录 / 密码修改 / 个人中心
- 服务：发布 / 列表 / 详情 / 我的服务
- 预约：创建 / 详情 / 列表
- 评价与投诉
- 管理后台：用户管理、服务管理、预约管理、评价审核、投诉处理

## 运行

```bash
mvn clean package
# 部署 target/ 下生成的 war 包到 Tomcat
```

## 数据库

`database_init.sql` 为建库与初始化脚本。

## 说明

- `target/` 已通过 `.gitignore` 排除
