```sql
-- ==========================================
-- 智能议价系统数据库初始化脚本 (W9 底座)
-- ==========================================

-- 1. 商品表
CREATE TABLE IF NOT EXISTS `item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '商品名称',
  `description` text COMMENT '商品描述',
  `list_price` decimal(10,2) NOT NULL COMMENT '挂牌价',
  `floor_price` decimal(10,2) NOT NULL COMMENT '底价(对买家不可见)',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '1-在售, 2-议价中, 3-已售',
  `seller_id` bigint NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 2. 议价会话表 (状态机核心)
CREATE TABLE IF NOT EXISTS `negotiation` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `item_id` bigint NOT NULL,
  `buyer_id` bigint NOT NULL,
  `seller_id` bigint NOT NULL,
  `buyer_limit` decimal(10,2) NOT NULL COMMENT '买家心理价位',
  `current_round` int NOT NULL DEFAULT '0' COMMENT '当前轮次',
  `max_round` int NOT NULL DEFAULT '10' COMMENT '最大轮次',
  `status` varchar(20) NOT NULL DEFAULT 'INIT' COMMENT 'INIT/BARGAINING/AGREED/FAILED',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='议价会话表';

-- 3. 逐轮出价记录表
CREATE TABLE IF NOT EXISTS `negotiation_offer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `negotiation_id` bigint NOT NULL,
  `round` int NOT NULL COMMENT '轮次',
  `offer_side` varchar(10) NOT NULL COMMENT 'BUYER/SELLER',
  `offer_price` decimal(10,2) NOT NULL COMMENT '出价金额',
  `agent_thought` text COMMENT 'Agent思考过程',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_negotiation_id` (`negotiation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='逐轮出价记录表';

-- 4. 交易订单表 (HITL双确认)
CREATE TABLE IF NOT EXISTS `trade_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_no` varchar(64) NOT NULL,
  `negotiation_id` bigint NOT NULL,
  `item_id` bigint NOT NULL,
  `buyer_id` bigint NOT NULL,
  `seller_id` bigint NOT NULL,
  `final_price` decimal(10,2) NOT NULL,
  `buyer_confirm` tinyint NOT NULL DEFAULT '0' COMMENT '0-未确认, 1-已确认',
  `seller_confirm` tinyint NOT NULL DEFAULT '0' COMMENT '0-未确认, 1-已确认',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '0-待确认, 1-已完成, 2-已取消',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  UNIQUE KEY `uk_negotiation_id` (`negotiation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易订单表';