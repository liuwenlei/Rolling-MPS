create table user_tb(
	userid int primary key auto_increment,
	username char(20),
	account char(20),
	password char(20),
	status char(20)
);
insert into user_tb values(1001,"刘文磊","2013303039","kkk1234521154","管理员");
insert into user_tb values(1002,"刘1","2013303032","kkk1234521154","计划员");
insert into user_tb values(1003,"刘2","2013303033","kkk1234521154","计划员");
insert into user_tb values(1004,"刘3","2013303034","kkk1234521154","计划员");


create table customer_tb(
	customerid int primary key auto_increment,
	customername char(20),
	contacts char(20),
	tel char(20),
	email char(20),
	address char(50)
);

insert into customer_tb values(2001,"西安第一汽车厂","李1","18829236368","2286790590@qq.com","长安东大");
insert into customer_tb values(2002,"西安第二汽车厂","李2","18829236368","2286790590@qq.com","长安东大");
insert into customer_tb values(2003,"西安第三汽车厂","李3","18829236368","2286790590@qq.com","长安东大");
insert into customer_tb values(2004,"西安第四汽车厂","李4","18829236368","2286790590@qq.com","长安东大");