drop table if exists TAGS;

create table TAGS
(
    id            int primary key auto_increment not null,
    name          varchar(255) not null,
    user_id       int not null,
    created_on    timestamp,
    updated_on    timestamp
);

alter table TAGS
    add foreign key (user_id) references USERS (id);

create table COFFEE_TAG
(
    coffee_id   int not null,
    foreign key (coffee_id) references COFFEES (id),
    tag_id      int not null references TAGS (id),
    foreign key (tag_id) references TAGS (id)
);

