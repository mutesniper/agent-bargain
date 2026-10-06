#  智能议价系统 (Agent Bargain Platform)

## 项目简介
一个简化版二手交易平台，核心验证 "AI Agent 代表人类进行有边界的自主协商" 机制。
买家 Agent 与卖家 Agent 基于各自授权信息（心理价位/底价）自动完成多轮议价；
模型的任何越界行为均被 Java 代码层强制拦截；议价结果必须经过双方人工确认（HITL）才生成订单。
本项目采用**极简后端架构**，将 100% 精力投入 Agent 编排、硬边界校验与全链路可观测性。

## 技术栈
- **极简后端**：Java 17, Spring Boot 3.x, MyBatis-Plus, MySQL
- **实时交互**：WebSocket (STOMP)
- **Agent 核心**：Spring AI Alibaba, 通义千问/DeepSeek API
- **深度机制 (手写)**：自研状态机 (Orchestrator), Java AOP (全链路追踪), 策略模式 (硬边界拦截)

## 目录结构规划
```text
agent-bargain/
├── sql/                # 数据库初始化脚本 (4张核心表)
├── src/main/java/
│   ├── common/         # 全局通用组件
│   ├── item/           # 商品域 (极简 CRUD)
│   ├── negotiation/    # 议价核心域 (状态机/Agent编排/硬校验)
│   └── order/          # 订单域 (HITL双确认)
└── README.md
```

## 如何启动

1. 确保本地已安装 JDK 17+, MySQL 8.0+
2. 执行 `sql/init.sql` 初始化数据库表结构
3. 配置 `application.yml` 中的数据库连接与大模型 API Key
4. 运行 `BargainApplication.java` 启动服务