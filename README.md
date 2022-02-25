<h1 style="text-align:center">
    Arco Design Server
</h1>

<p style="text-align:center">
    <img src="https://img.shields.io/badge/Spring%20Boot-2.3.7-%236db33f?logo=Spring" alt="Spring Boot">
    <img src="https://img.shields.io/badge/MyBatis--Plus-3.5.1-%231e90ff" alt="MyBatis-Plus">
    <img src="https://img.shields.io/badge/Redis-6.2.6-%23d92b21?logo=Redis" alt="Redis">
    <img src="https://img.shields.io/badge/Sa--Token-1.29.0-%2344cc11" alt="Sa-Token">
    <img src="https://img.shields.io/badge/-%E9%98%BF%E9%87%8C%E4%BA%91%E7%9F%AD%E4%BF%A1-%23ff6a00" alt="阿里云短信">
    <img src="https://img.shields.io/badge/-%E6%94%AF%E4%BB%98%E5%AE%9D%E6%B2%99%E7%AE%B1-%23226bf3" alt="支付宝沙箱">
    <img src="https://img.shields.io/badge/-%E7%BD%91%E6%98%93%E9%82%AE%E7%AE%B1-%23ff3333" alt="网易邮箱">
<p>

<p style="text-align:center">
基于Spring Boot开发，提供技术框架的基础封装，减少开发工作，让您只需关注业务。
</p>

## ✨ 特点

- 使用Knife4j生成Api文档
- 引入MyBatis-plus，简化单表CRUD操作
- 集成Sa-Token，让鉴权变得简单、优雅
- 内置参数校验、全局异常处理、自定义异常、自定义统一响应体
- 整合阿里云短信业务、网易163邮箱服务、支付宝沙箱服务

## 🌈 目录结构

```sh
src                            # 源码目录
├── common                     # 项目通用类库
│   ├── annotation                 # 自定义注解
│   ├── constant                   # 公共常量
│   ├── domain                     # 全局 javabean
│   ├── exception                  # 自定义异常
│   ├── mapper                     # 基础 mapper
│   └── properties                 # 参数配置类
├── config                     # 项目配置信息
├── handler                    # 全局处理器
├── injector                   # 全局注入器
├── interceptor                # 全局连接器
├── listener                   # 全局监听器
├── module                     # 业务模块
│   ├── user                      # 用户模块
│   │   ├── controller                # 控制层
│   │   ├── domin                     # 实体类
│   │   ├── mapper                    # 数据访问层
│   │   └── service                   # 业务逻辑层
│   └── role                      # 角色模块
├── third                      # 三方服务，比如 redis, oss，微信sdk等等
├── util                       # 全局工具类
└── Application.java           # 启动类

resources                      #资源目录
├── mapper                     # 自定义mapper文件
├── static                     # 静态文件
├── templates                  # thymeleaf模板
├── application.yml            # 项目配置文件
├── application-dev.yml        # 开发环境配置文件
├── application-prod.yml       # 生产环境配置文件

test                           # 单元测试目录
```

## 🪂 项目安装

```sh
# 克隆项目
git clone https://gitee.com/mayingfa/arco-design-server.git

# 进入项目目录
cd arco-design-server

# 安装依赖
mvn install
```

## 🧩 Spring Boot 生态圈

- Spring Boot  <https://spring.io/projects/spring-boot>
- MyBatis <https://mybatis.org/mybatis-3/zh/index.html>
- MyBatis-Plus <https://baomidou.com>
- Sa-Token <https://sa-token.dev33.cn>
- Elasticsearch <https://www.elastic.co>
- RocketMQ <https://rocketmq.apache.org>
- Redis <https://redis.io>
