drop table if exists TAGS;

create table TAGS
(
    id            int primary key auto_increment not null,
    name          varchar(255) not null,
    coffee_id     int,
    created_on    timestamp,
    updated_on    timestamp
);

alter table TAGS
    add foreign key (coffee_id) references COFFEES (id);