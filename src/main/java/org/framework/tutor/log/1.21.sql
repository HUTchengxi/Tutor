#创建数据库
create database tutor;

use tutor;

#创建用户表
create table user_main(
  id int auto_increment comment "用户唯一标识",
  identity int default 0 comment "0普通用户，-1admin，1家教，-2咱不验证用户",
  username varchar(20) not null comment "用户名",
  password varchar(12) not null comment "密码",
  nickname varchar(12) not null comment "昵称",
  sex int default 0 comment "0为女，1为男",
  age int default 0 comment "年龄",
  telephone char(11) comment "手机号码",
  email varchar(30) comment "邮箱",
  info varchar(200) default "这位童鞋很懒，什么都没留下" comment "个人描述",
  imgsrc varchar(50) default "images/default/user_face.jpg" comment "用户的个人头像",
  regtime datetime default now() comment "注册的时间",
  key(id),
  primary key(username)
);

#insert into user_main(username,password,nickname) values("chengxi","chengxi","成兮");



#用户注册验证表
create table user_vali(
  id int auto_increment comment "唯一标识",
  username varchar(20) not null comment "用户名",
  valicode int comment "邮箱验证码，保留一天有效期，过期注册作废",
  regtime datetime default now() comment "注册的时间",
  status int not null comment "0邮箱验证，1手机号码验证",
  resend int default 1 comment "用户是否屏蔽验证弹出消息，1表示不屏蔽，0表示屏蔽",
  key(id),
  primary key(username),
  foreign key(username) references user_main(username)
);

#用户登录记录表
create table user_log(
  id int primary key auto_increment comment "唯一标识",
  username varchar(20) not null comment "用户名",
  logtime datetime default now() comment "登录的时间",
  logcity varchar(100) not null comment "登录的城市",
  logip varchar(15) not null comment "登录的ip地址",
  logsys varchar(10) not null comment "电脑的操作系统",
);

#用户的课程记录
create table course_log(
  id int auto_increment comment "唯一标识",
  cid int comment "对应的课程id",
  username varchar(20) not null comment "用户名",
--   ctype varchar(20) not null comment "课程类型",
--   cname varchar(50) not null comment "课程名称",
  logtime datetime default now() comment "浏览时间",
  primary key(cid),
  foreign key(cid) references course_main(id)
);


#用户课程收藏表
create table course_collect(
  id int auto_increment comment "唯一标识",
  cid int comment "收藏的课程的id",
  username varchar(20) not null comment "用户名",
  coltime datetime default now() comment "收藏的时间",
  descript varchar(50) comment "收藏笔记",
  key(id),
  primary key(cid, username),
  foreign key(cid) references course_main(id)
);


#课程表
create table course_main(
  id int auto_increment comment "唯一标识",
  username varchar(20) not null comment "家教老师名",
  name varchar(50) not null comment "课程名称",
  imgsrc varchar(50) not null comment "课程封面图片",
  stype int default 4 comment "主类别：1小学，2初中，3高中，4其他兴趣",
  ctype varchar(20) not null comment "类别名称",
  jcount int not null comment "课程总人数",
  hcount int not null comment "课程点击量",
  ccount int not null comment "课程评论量",
  descript varchar(500) not null comment "课程描述信息",
  price decimal(6,1) not null comment "课程报名费用",
  ptime datetime default now() comment "课程发布时间",
  stime datetime not null comment "课程开课时间",
  total int not null comment "课程总天数",
  key(id),
  primary key(username, id),
  foreign key(username) references user_main(username)
);

#课程章节目录表
create table course_chapter(
  id int auto_increment comment "唯一标识",
  cid int not null comment "课程id",
  ord int not null comment "课程章节号",
  title varchar(50) not null comment "课程章节标题",
  descript varchar(500) not null comment "课程章节概要",
  primary key(id, cid),
  foreign key(cid) references course_main(id)
);
insert into course_chapter(cid, ord, title, descript) values(1,1,"目录1","是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的");
insert into course_chapter(cid, ord, title, descript) values(1,2,"目录2","是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的");
insert into course_chapter(cid, ord, title, descript) values(1,3,"目录3","是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的");
insert into course_chapter(cid, ord, title, descript) values(1,4,"目录4","是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的");
insert into course_chapter(cid, ord, title, descript) values(1,5,"目录5","是多少啊嗲是的吧骚i到死电脑i俺倒是你都啊是电脑上电脑sand爬山的哪怕是的阿斯顿那时的哪怕是的");


#课程订单表
create table course_order(
  id int auto_increment comment "唯一标识",
  cid int not null comment "课程id",
  username varchar(20) not null comment "用户",
  state int not null comment "订单状态，0购物车，1已订购",
  otime datetime default now() comment "订单更新时间",
  key(id),
  primary key(cid, username),
  foreign key(username) references user_main(username),
  foreign key(cid) references course_main(id)
);

#教师课程评价回复表
create table course_treply(
  id int auto_increment comment "唯一标识",
  cid int not null comment "课程id",
  cmid int not null comment "对应的评价id",
  tname varchar(20) not null comment "家教老师用户名",
  info varchar(500) not null comment "回复内容",
  key(id),
  primary key(cid, tname, cmid),
  foreign key(cid) references course_main(id),
  foreign key(tname) references user_main(username),
  foreign key(cmid) references course_command(id)
);


#课程评论表
create table course_command(
  id int auto_increment comment "唯一标识",
  cid int not null comment "课程id",
  username varchar(20) not null comment "评论用户",
  info varchar(500) not null comment "评论内容",
  ctime datetime default now() comment "评论时间",
  score int default 5 comment "评价星级 1-5",
  key(id),
  primary key(cid, username, id),
  foreign key(cid) references course_main(id),
  foreign key(username) references user_main(username)
);

#用户签到记录表
create table user_sign(
  id int auto_increment comment "唯一标识",
  username varchar(20) not null comment "用户名",
  stime datetime default now() comment "签到时间",
  key(id),
  primary key(id, username),
  foreign key(username) references user_main(username)
);


#我的通知表
create table user_message(
  id int auto_increment comment "唯一标识",
  identity int default 0 comment "是否私信通知，0表示否，1表示是",
  suser varchar(20) not null comment "发送的管理员用户名，模拟admin",
  username varchar(20) comment "发送私信通知的用户名",
  title varchar(50) not null comment "通知的标题",
  descript text not null comment "通知的信息",
  stime datetime default now() comment "通知发送的时间",
  status int default 0 comment "0表示未读，1表示已读",
  primary key(id),
  #后续创建了管理员表就将suser设置成一个外键
  foreign key(username) references user_main(username)
);


#用户密保表
create table user_secret(
  id int primary key auto_increment comment "唯一标识",
  username varchar(20) not null comment "对用用户名",
  question varchar(20) not null comment "密保问题，不超过20个字",
  answer varchar(20) not null comment "密保答案，不超过20个字",
  msg varchar(20) default "无" comment "提示信息",
  foreign key(username) references user_main(username)
);


#家教_管理员通知表
create table tutor_message(
  id int auto_increment comment "唯一标识",
  identity int default 0 comment "0表示所有，1表示指定接收信息的家教",
  admin varchar(20) not null comment "管理员用户名",
  tuser varchar(20) comment "接收的家教用户名",
  title varchar(50) not null comment "消息的标题",
  message text not null comment "通知数据",
  status int defualt 0 comment "是否已读",
  alert int default 0 comment "是否以弹出提示",
  primary key(id),
  foreign key(tuser) REFERENCES user_main(username)
);

#家教后台管理的链接汇总表
create table tutorsys_btns(
  id int auto_increment comment "唯一标识",
  name varchar(20) not null comment "链接名称",
  url varchar(200) not null comment "链接url",
  ord int not null comment "链接优先级",
  primary key(id)
);

#家教_链接中间表
create table tutor_btns(
  id int auto_increment comment "唯一标识",
  tname varchar(20) not null comment "家教用户名",
  bid int not null comment "链接id",
  primary key(id),
  foreign key(tname) references user_main(username),
  foreign key(bid) references tutorsys_btns(id)
)


#更新版本类型表
create table publish_type(
  id int auto_increment comment "唯一标识",
  name varchar(10) not null comment "版本类型名称",
  ord int default 100 comment "越低的越靠前",
  primary key(id)
);


#网站历史更新记录表
create table publish_log(
  id int auto_increment comment "唯一标识",
  pversion varchar(15) not null comment "版本号",
  typeid int default 0 comment "版本类型",
  ptime datetime default now() comment "版本发布时间",
  descript varchar(100) not null comment "更新内容描述",
  primary key(id, pversion),
  foreign key(typeid) references publish_type(id)
);


#用户评论点赞表
create table command_star(
  id int auto_increment comment "唯一标识",
  username varchar(20) not null comment "点赞/踩的用户名",
  cmid int not null comment "评论对应的id",
  score int not null comment "点赞为1，踩为-1",
  primary key(id),
  foreign key(username) references user_main(username),
  foreign key(cmid) references course_command(id)
);



#论坛帖子表
create table bbs_card(
  id int auto_increment comment "唯一标识",
  username varchar(20) not null comment "发帖人用户名",
  title varchar(20) not null comment "帖子标题",
  descript varchar(200) not null comment "帖子内容",
  imgsrc varchar(50) default "/images/default/discuss.jpg",
  crttime datetime default now() comment "发帖时间",
  viscount int default 0 comment "访问人数",
  comcount int default 0 comment "评论数",
  colcount int default 0 comment "收藏人数",
  primary key(id),
  foreign key(username) references user_main(username)
);

#帖子答案表
create table bbs_card_answer(
  id int auto_increment comment "唯一标识",
  cardid int not null comment "帖子id",
  username varchar(20) not null comment "答案制作者用户名",
  answer text not null comment "答案",
  crtime datetime default now() comment "回答时间",
  gcount int default 0 comment "点赞数",
  bcount int default 0 comment "踩数",
  primary key(id),
  foreign key(cardid) references bbs_card(id),
  foreign key(username) references user_main(username)
);



#用户帖子收藏表
create table bbs_card_collect(
  id int primary key auto_increment comment "唯一标识",
  username varchar(20) not null comment "收藏的用户名",
  cardid int not null comment "收藏的帖子id",
  coltime datetime default now() comment "收藏时间",
  foreign key(username) references user_main(username),
  foreign key(cardid) references bbs_card(id)
);




#用户帖子评论表
create table bbs_card_answer_command(
  id int primary key auto_increment comment "唯一标识",
  username varchar(20) not null comment "评论的用户名",
  cardid int not null comment "评论的帖子id",
  aid int not null comment "评论的帖子答案id",
  floor int not null comment "当前占楼",
  repfloor int comment "若是回复则回复floor，不是则为null",
  comment varchar(200) not null comment "评论信息",
  comtime datetime default now() comment "评论时间",
  gcount int default 0 comment "点赞数",
  bcount int default 0 comment "踩数",
  foreign key(username) references user_main(username),
  foreign key(cardid) references bbs_card(id),
  foreign key(aid) references bbs_card_answer(id)
);




#发表帖子的背景图片表
create table common_imgsrc(
  id int primary key auto_increment comment "唯一标识",
  imgsrc varchar(100) not null comment "图片位置，相对于项目，绝对于网址"
);



#帖子回答star记录表
create table bbs_card_answer_star(
  id int primary key auto_increment comment "唯一标识",
  aid int not null comment "回答标识",
  username varchar(20) not null comment "用户名",
  score int not null comment "0踩，1赞",
  stime datetime default now() comment "star时间",
  foreign key(aid) references bbs_card_answer(id),
  foreign key(username) references user_main(username)
);


#课程概述表
create table course_summary(
  id int primary key auto_increment comment "唯一标识",
  username varchar(20) not null comment "用户标识",
  cid int not null comment "课程标识",
  title varchar(50) not null comment "概述标题",
  descript varchar(200) not null comment "概述内容",
  foreign key(username) references user_main(username),
  foreign key(cid) references course_main(id)
);

#课程下线申请表
create table course_delete_req(
  id int primary key auto_increment comment "唯一标识",
  username varchar(20) not null comment "用户标识",
  cid int not null comment "课程标识",
  reqtime datetime default now() comment "申请时间",
  descript text not null comment "申请原因",
  foreign key(username) references user_main(username),
  foreign key(cid) references course_main(id)
);


#评论删除待审表 家教用
create table course_command_delete_req(
  id int primary key auto_increment comment "唯一标识",
  reqer varchar(20) not null comment "防止sql恶意注入，校验申请用户名",
  cid int not null comment "要删除的评论id",
  reqtime datetime default now() comment "申请时间",
  foreign key(reqer) references user_main(username),
  foreign key(cid) references course_command(cid)
);


#课程订单管理表
create table course_order_manager(
  id int auto_increment comment "唯一标识",
  code varchar(20) not null comment "订单编号，内置算法生成",
  oid int not null comment "对应的订单id",
  tutorstatus int default 0 comment "家教的处理状态：0初始状态，1已经接收，2开始教学，3教学完成，4完成订单，-1申请撤销，-2订单异常复审申请",
  userstatus int default 0 comment "用户的处理状态 0初始状态，1正在听课，2听课完成，3完成订单，-1申请退款，-2订单异常复审申请",
  foreign key(oid) references course_order(id),
  primary key(code),
  key(id)
);


#系统邮件表
create table sys_email(
  id int primary key auto_increment comment "唯一标识",
  sendto varchar(20) not null comment "接收者",
  theme varchar(100) not null comment "邮件主题",
  email text not null comment "邮件内容",
  sendtime datetime default now() comment "发送/更新时间",
  status int not null comment "邮件状态：1发送，-1保存",
  foreign key(sendto) references user_main(sendto)
);


#课程下线申请响应表
create table course_delete_resp(
  id int primary key auto_increment comment "唯一标识",
  reqid int not null comment "课程下线申请标识",
  status int default 0 comment "0待处理，1同意，2不同意",
  response varchar(200) not null comment "响应标注信息",
  resptime datetime default now() comment "响应时间"
);


#通知删除中间表
create table user_message_delete(
  id int primary key auto_increment comment "唯一标识",
  mid int not null comment "通知标识id",
  username varchar(20) not null comment "删除通知的用户名",
  status int not null comment "-1表示删除，1表示已读",
  foreign key(mid) references user_message(id),
  foreign key(username) references user_main(username)
);



#家教老师标签表
#实名认证表
#课程表
#课程评论表
#课程报名表
#科目类别表

#今日我的课程收藏多少次
select count(*) from course_collect where cid in (select id from course_main where username="chengxi") and coltime like '2018-03-22%';

#今日我的课程评论了多少次
select count(*) from course_command where cid in (select id from course_main where username=#{username}) and ctime like CONCAT('%', #{now}, '%')

#今日我的课程下单了多少次
select count(*) from course_order where cid in (select id from course_main where username=#{username}) and otime like CONCAT('%', #{NOW}, '%')


#修改了user表的主键为username

