drop table if exists bostader;

create table bostader (
lan varchar(64),
objekttyp varchar(64),
adress varchar(64),
area float,
rum int,
pris float,
avgift float
);

insert into bostader values ('Stockholm','BostadsrÃ¤tt','Polhemsgatan 1',30,1,1000000,1234);
insert into bostader values ('Stockholm','BostadsrÃ¤tt','Polhemsgatan 2',60,2,2000000,2345);
insert into bostader values ('Stockholm','Villa','Storgatan 1',130,5,1000000,3456);
insert into bostader values ('Stockholm','Villa','Storgatan 2',160,6,1000000,3456);
insert into bostader values ('Uppsala','BostadsrÃ¤tt','GrÃ¶na gatan 1',30,1,500000,1234);
insert into bostader values ('Uppsala','BostadsrÃ¤tt','GrÃ¶na gatan 2',60,2,1000000,2345);
insert into bostader values ('Uppsala','Villa','KungsÃ¤ngsvÃ¤gen 1',130,5,1000000,3456);
insert into bostader values ('Uppsala','Villa','KungsÃ¤ngsvÃ¤gen 2',160,6,1000000,3456);

SELECT * FROM bostader
