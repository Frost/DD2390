drop table if exists securities;
drop table if exists orders;
drop table if exists trades;

create table securities (
	id int NOT NULL AUTO_INCREMENT,
       name varchar(64),
	PRIMARY KEY (id)
);

create table orders (
	id int NOT NULL AUTO_INCREMENT,
       name varchar(64),
       type varchar(64),
       price float,
       amount int,
       uid varchar(64),
	PRIMARY KEY (id)
);

create table trades (
	id int NOT NULL AUTO_INCREMENT,
       name varchar(64),
       price float,
       amount int,
       dt datetime,
       buyer varchar(64),
       seller varchar(64),
	PRIMARY KEY (id)
);
