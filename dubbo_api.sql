# Host: 127.0.0.1  (Version 5.7.17-log)
# Date: 2019-06-04 16:29:48
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "app_acct_binding"
#

DROP TABLE IF EXISTS `app_acct_binding`;
CREATE TABLE `app_acct_binding` (
  `ID` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '绑定编码，自增ID',
  `LION_ID` varchar(256) NOT NULL COMMENT '奇瑞账号',
  `BIND_ID` varchar(256) NOT NULL COMMENT '合作方账号',
  `BIND_SRC` varchar(256) NOT NULL COMMENT '合作方',
  `STATUS` char(1) NOT NULL COMMENT '绑定状态',
  `START_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '生效日期',
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '失效日期',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000100 DEFAULT CHARSET=utf8;

#
# Structure for table "app_exce_log"
#

DROP TABLE IF EXISTS `app_exce_log`;
CREATE TABLE `app_exce_log` (
  `EXCE_ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '异常编码',
  `MODULE` varchar(32) NOT NULL COMMENT '模块名',
  `LOG_LEVEL` varchar(20) DEFAULT NULL COMMENT '日志级别',
  `SERI_NUM` varchar(200) DEFAULT NULL COMMENT '流水号',
  `LOG_DATE` datetime NOT NULL COMMENT '日志产生时间',
  `REQUEST_IP` varchar(32) DEFAULT NULL COMMENT '请求IP',
  `SERVER_IP` varchar(32) DEFAULT NULL COMMENT '服务IP',
  `THREAD_NAME` varchar(32) DEFAULT NULL COMMENT '线程号',
  `TRANC_NUM` varchar(32) DEFAULT NULL COMMENT '事务流水',
  `APP_ID` bigint(12) DEFAULT NULL COMMENT '应用编码',
  `PROCESS_CODE` varchar(32) DEFAULT NULL COMMENT '请求方法',
  `EXCE_CODE` bigint(10) DEFAULT NULL COMMENT '异常编码',
  `EXCE_MSG` varchar(200) DEFAULT NULL COMMENT '异常信息',
  `EXCE_STACK` varchar(5000) DEFAULT NULL COMMENT '异常明细',
  `EXCE_TYPE` varchar(32) DEFAULT NULL COMMENT '异常类型',
  `ALARM_STRATEGY` varchar(32) DEFAULT NULL COMMENT '告警策略',
  PRIMARY KEY (`EXCE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10000000000000100000 DEFAULT CHARSET=utf8;

#
# Structure for table "app_info"
#

DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info` (
  `APP_ID` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '应用ID，提供给一个外部接入对象',
  `APP_NAME` varchar(256) DEFAULT NULL COMMENT '分配应用的名称',
  `APP_PLATFORM` varchar(20) DEFAULT NULL COMMENT '应用平台：咪咕，K-Radio',
  `APP_STATUS` varchar(2) NOT NULL COMMENT '应用状态 U：use I：Invalid',
  `CREATE_ID` varchar(12) DEFAULT NULL COMMENT '创建人',
  `CREATE_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `APP_REDIRECTURI` varchar(1024) DEFAULT NULL COMMENT '回调地址',
  `APP_KEY` varchar(32) DEFAULT NULL COMMENT '应用秘钥，对称加密',
  `RSA_PUBLIC` varchar(1024) DEFAULT NULL COMMENT 'RSA公钥，非对称',
  `RSA_PRIVATE` varchar(1024) DEFAULT NULL COMMENT 'RSA私钥，非对称',
  `GRANT_TYPES` varchar(128) DEFAULT NULL COMMENT '授权类型：密码模式（resource owner password credentials）授权码模式（authorization code）简化模式（implicit）客户端模式（client credentials）',
  `SIGN_METHOD` varchar(128) DEFAULT NULL COMMENT '签名加密方法:对称加密AES 非对称加密RSA',
  `ENCRYPT_METHOD` varchar(32) DEFAULT NULL COMMENT '业务参数加密方法:对称加密AES 非对称加密RSA',
  `ENCRYPT_TYPE` varchar(10) DEFAULT NULL COMMENT '加密类型:A:整体加密S:单个参数加密N:不加密',
  `SCOPE` varchar(20) DEFAULT NULL COMMENT '资源的授权范围',
  PRIMARY KEY (`APP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000100 DEFAULT CHARSET=utf8;

#
# Structure for table "app_int"
#

DROP TABLE IF EXISTS `app_int`;
CREATE TABLE `app_int` (
  `APP_ID` bigint(12) NOT NULL COMMENT '应用ID，提供给一个外部接入对象',
  `PROCESS_CODE` varchar(256) NOT NULL COMMENT '服务编号',
  `STATUS` char(1) NOT NULL COMMENT '服务状态',
  `CALL_SUM` int(12) DEFAULT NULL COMMENT '调用次数',
  `CYCLE` varchar(20) DEFAULT NULL COMMENT '周期:day：天 hour：小时',
  `STRATEGY` varchar(20) DEFAULT NULL COMMENT '策略:warn：预警 refuse:拒绝',
  `START_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '生效日期',
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '失效日期',
  PRIMARY KEY (`APP_ID`,`PROCESS_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "app_log"
#

DROP TABLE IF EXISTS `app_log`;
CREATE TABLE `app_log` (
  `LOG_ID` bigint(12) NOT NULL AUTO_INCREMENT COMMENT '日志编码',
  `MODULE` varchar(32) NOT NULL COMMENT '模块名',
  `APP_ID` bigint(12) DEFAULT NULL COMMENT '应用编码',
  `FORMNUM` varchar(200) DEFAULT NULL COMMENT '受理流水号',
  `USERNAME` varchar(50) DEFAULT NULL COMMENT '用户名',
  `OPERATION` varchar(50) DEFAULT NULL COMMENT '操作方式,业务类型',
  `METHOD` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `PARAMS` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `TIME` bigint(20) DEFAULT NULL COMMENT '执行时长(毫秒)',
  `IP` varchar(64) DEFAULT NULL COMMENT '请求IP地址',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100000000100 DEFAULT CHARSET=utf8;

#
# Structure for table "app_master_order"
#

DROP TABLE IF EXISTS `app_master_order`;
CREATE TABLE `app_master_order` (
  `ORDER_ID` varchar(14) NOT NULL COMMENT '订单编码',
  `ORDER_TYPE` varchar(20) NOT NULL COMMENT '订单类型',
  `BUYER_ID` varchar(20) NOT NULL COMMENT '客户编码',
  `ORDER_SOURCE` varchar(20) NOT NULL COMMENT '订单来源:美团，途虎,自营',
  `ORDER_AMOUNT` decimal(8,2) NOT NULL COMMENT '价格',
  `STATUS` char(1) NOT NULL COMMENT '订单状态',
  `PAY_STATUS` char(1) NOT NULL COMMENT '支付状态',
  `PAY_CHENNEL` varchar(20) DEFAULT NULL COMMENT '支付渠道',
  `RECDATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '受理日期',
  `END_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '失效日期',
  PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "app_order_detail"
#

DROP TABLE IF EXISTS `app_order_detail`;
CREATE TABLE `app_order_detail` (
  `DETAIL_ID` varchar(14) NOT NULL COMMENT '明细编码',
  `ORDER_ID` varchar(14) NOT NULL COMMENT '订单编码',
  `PRODUCT_ID` varchar(256) NOT NULL COMMENT '商品编码',
  `PRODUCT_NAME` varchar(256) NOT NULL COMMENT '商品名称',
  `PRODUCT_PRICE` decimal(8,2) NOT NULL COMMENT '商品价格（标价）',
  `PRICE` decimal(8,2) NOT NULL COMMENT '售价',
  `STATUS` char(1) NOT NULL COMMENT '状态',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`DETAIL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for table "dubbo_s_acct"
#

DROP TABLE IF EXISTS `dubbo_s_acct`;
CREATE TABLE `dubbo_s_acct` (
  `ACCT_ID` decimal(18,0) NOT NULL DEFAULT '0' COMMENT 'ID',
  `NICKNAME` varchar(25) DEFAULT NULL COMMENT '昵称',
  `ACCT_NAME` varchar(25) DEFAULT NULL COMMENT '账户名',
  `PHONE` varchar(20) NOT NULL,
  `PASSWORD` varchar(200) NOT NULL DEFAULT '' COMMENT '密码',
  `IMAGE` varchar(100) DEFAULT NULL,
  `REAL_NAME` varchar(20) DEFAULT NULL,
  `EMERGENCY_PHONE` varchar(20) DEFAULT NULL,
  `ID_CARD` varchar(18) DEFAULT NULL,
  `REVIEW_STATUS` char(1) DEFAULT NULL COMMENT '1 已审核\n            0 未审核',
  `BLOOD_TYPE` char(2) DEFAULT NULL COMMENT 'A:1 B:2 AB:3 O:0',
  `CREATE_TIME` datetime DEFAULT NULL,
  `UPDATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ACCT_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
