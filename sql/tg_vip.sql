/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.2.201
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 192.168.2.201:3306
 Source Schema         : number_admin_dev

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 16/09/2024 15:18:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tg_vip
-- ----------------------------
DROP TABLE IF EXISTS `tg_vip`;
CREATE TABLE `tg_vip`  (
  `id` bigint(20) UNSIGNED NOT NULL COMMENT '主键ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '内容',
  `status` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态 0-未领取,1-领取未确定 2-已确定',
  `take_time` datetime(0) NULL DEFAULT NULL COMMENT '领取时间',
  `take_user_id` bigint(20) NULL DEFAULT NULL COMMENT '领取用户id',
  `deleted` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '0-正常，1-删除',
  `version` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '乐观锁',
  `create_by` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_by` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `gmt_create` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `gmt_modified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
