CREATE DATABASE `book_manage` DEFAULT CHARACTER SET utf8;
use book_manage;

create table admin
(
    id       int auto_increment
        primary key,
    username varchar(255) not null,
    nickname varchar(255) not null,
    password varchar(255) not null
);

create table book
(
    bid    int auto_increment
        primary key,
    title  varchar(255)   null,
    `desc` varchar(255)   null,
    price  decimal(10, 2) null
);

create definer = root@localhost trigger del_book
    before delete
    on book
    for each row
    DELETE FROM borrow WHERE bid = old.bid;

create table student
(
    sid   int auto_increment
        primary key,
    name  varchar(255)                   not null,
    sex   enum ('男', '女') default '男' not null,
    grade int                            not null
);

create table borrow
(
    id   int auto_increment
        primary key,
    sid  int      null,
    bid  int      null,
    time datetime null,
    constraint f_bid
        unique (bid),
    constraint unique_sid_bid
        unique (sid, bid),
    constraint f_bid
        foreign key (bid) references book (bid),
    constraint f_sid
        foreign key (sid) references student (sid)
);

create definer = root@localhost trigger del
    before delete
    on student
    for each row
    DELETE FROM borrow WHERE sid = old.sid;

