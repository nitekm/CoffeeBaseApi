drop table if exists COFFEES;
create table COFFEES
(

    id         int primary key auto_increment not null,
    name       varchar                        not null,
    origin     varchar,
    roaster    varchar,
    rating     int,
    imageUrl   varchar,
    favourite  bit,
    created_on datetime                       not null,
    updated_on datetime
)