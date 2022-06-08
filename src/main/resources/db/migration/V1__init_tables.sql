drop table if exists COFFEES;
drop table if exists USERS;

create table COFFEES
(
    id            int primary key auto_increment not null,
    name          varchar(255) not null,
    origin        varchar(255),
    roaster       varchar(255),
    rating        int,
    imageUrl      varchar(255),
    favourite     bit,
    created_on    timestamp    not null,
    updated_on    timestamp,
    plain_user_id varchar(255) not null,
    user_id       int
);

create table USERS
(
    id       int primary key auto_increment not null,
    user_id  varchar(255) not null,
    username varchar(255) not null,
    email    varchar(255)
);

alter table COFFEES
    add foreign key (user_id) references USERS (id);