drop table if exists COFFEES;
create table COFFEES
(

    id         int primary key auto_increment not null,
    name       varchar(255)                        not null,
    origin     varchar(255),
    roaster    varchar(255),
    rating     int,
    imageUrl   varchar(255),
    favourite  bit,
    created_on datetime                       not null,
    updated_on datetime
)