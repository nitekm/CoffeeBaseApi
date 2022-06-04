drop table if exists users;

create table users (
    id int primary key auto_increment not null,
    username varchar(255) not null,
    password varchar(255),
    email varchar(255)
);

alter table coffees add foreign key (user_id) references users (id);
alter table coffees add column plain_user_id varchar(255);