
--0：工作日；1：节假日
create table workday(
	tday date not null,
	flag integer not null,
);

--可以客户上报记录
CREATE TABLE kykhsb (
  sbxh serial NOT NULL,
  khbh char(20)  NOT NULL,
  sbsj datetime year to second default current year to second,
  pfkhbz int DEFAULT 0,
  ljkhbz int DEFAULT 0,
  sbzt int,
  primary key(sbxh)
) ;



CREATE TABLE report_record (
  id serial NOT NULL,
  report_date char(10)  NOT NULL,
  report_feature char(10) NOT NULL,
  start_time char(10) NOT NULL,
  end_time char(10) NOT NULL,
  suc_count int DEFAULT 0,
  status int DEFAULT 0,
  primary key(id)
) ;




CREATE TABLE task_schedule_job (
  job_id serial NOT NULL,
  create_time char(20)  DEFAULT NULL,
  update_time char(20)  DEFAULT NULL,
  job_name char(50) DEFAULT NULL,
  job_group char(50) DEFAULT NULL,
  job_status char(10) DEFAULT NULL,
  cron_expression char(30) NOT NULL,
  description char(100) DEFAULT NULL,
  bean_class char(127) DEFAULT NULL,
  method_name char(50) NOT NULL,
  spring_id char(50) DEFAULT NULL,
  is_concurrent char(10) DEFAULT NULL,
  params char(255)
) ;

INSERT INTO task_schedule_job VALUES ('15', '2016-07-31 23:57:17', '2016-07-31 23:57:17', '1001', 'TASK', '0', '0 0 * * * ?', '定时上报1001', null, 'task1001', 'task100403','1', '{last:5}');
INSERT INTO task_schedule_job VALUES ('16', '2016-07-31 23:57:17', '2016-07-31 23:57:17', '1002', 'TASK', '0', '0 0 * * * ?', '定时上报1002', null, 'task1002', 'task100403','1', '{last:5}');
INSERT INTO task_schedule_job VALUES ('17', '2016-07-31 23:57:17', '2016-07-31 23:57:17', '3006', 'TASK', '0', '0 0 * * * ?', '定时上报3006', null, 'task3006', 'task100405','1', null);
INSERT INTO task_schedule_job VALUES ('18', '2016-07-31 23:57:17', '2016-07-31 23:57:17', '3007', 'TASK', '0', '0 0 * * * ?', '定时上报3007', null, 'task3007', 'task100405','1', null);



--用于筛选界面显示的条目
CREATE TABLE tx405item (
  id serial NOT NULL,
  bankid int ,
  account_name char(50)  DEFAULT NULL,
  card_number char(32)  DEFAULT NULL,
  account_number char(50)  DEFAULT NULL,
  account_serial char(50) DEFAULT NULL,
  account_type char(10) DEFAULT NULL,
  account_status char(10) DEFAULT NULL,
  pay_count int DEFAULT 0,
  pay_amount decimal DEFAULT 0.0,
  pey_count int DEFAULT 0,
  pey_amount decimal DEFAULT 0.0,
  exe_date char(10) DEFAULT NULL,
  feature char(10) DEFAULT NULL,
  file_path char(255) DEFAULT NULL,
  status int DEFAULT 0,
  primary key(id)
) ;
CREATE INDEX item_idx_1 ON tx405item(exe_date,feature);


--用于筛选界面显示的条目明细
CREATE TABLE tx405detail (
  id serial NOT NULL,
  item_id long  NOT NULL,
  trans_flow char(32),
  trans_time char(14),
  trans_count char(10),
  trs_code char(10),
  acct_no char(32),
  acct_name char(50),
  card_no char(32),
  vch_type char(10),
  vch_code char(32),
  borrow_sign char(2),
  trs_type char(50),
  trs_amount decimal,
  trs_balance decimal,
  op_bank_no char(20),
  op_bank_name char(50),
  op_acct_no char(32),
  op_acct_name char(50),
  primary key(id)
);


--柜员，每个网点分配一个柜员，网点号即为登陆id
CREATE TABLE teller (
	userid int NOT NULL unique,
	username char(32),
	password char(32),
	name char(50),
	telephone char(20),
	email char(50),
	roleid int,
	status int
);

insert into teller values(130000000,'保定银行股份有限公司','e10adc3949ba59abbe56e057f20f883e','刘浩','13800138000','130000000@bdbank.com',1000,0);

--用户菜单
CREATE TABLE menu (
	menuid int,
	title char(50),
	url char(50),
	status int
);

insert into menu values(1,'导出账户所有交易','trans01',0);
insert into menu values(2,'导出账户所有余额','trans02',0);
insert into menu values(1000,'定时任务列表','trans01',0);
insert into menu values(100401,'案件举报','tx100401',0);
insert into menu values(1004051,'可疑事件上报','trans01',0);
insert into menu values(1004052,'可疑事件筛选','taskmanage',0);


--角色菜单表
CREATE TABLE role_menu (
	roleid int,
	menuid int
);

insert into role_menu values(1000,1000);
insert into role_menu values(1000,100401);
insert into role_menu values(1000,1004051);
insert into role_menu values(1000,1004052);


--柜员操作记录
create table optrecord(
	userid int,
	optdate char(10),
	opttime char(10)
);