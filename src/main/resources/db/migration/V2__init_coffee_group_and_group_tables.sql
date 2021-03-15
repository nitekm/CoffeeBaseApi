drop table if exists coffee_groups;
drop table if exists coffee_coffee_group;

create table coffee_groups (
    id int primary key auto_increment not null,
    name varchar(255) not null,
    group_type varchar(10)
);

create table coffee_coffee_group (
    coffee_id int not null,
    coffee_group_id int not null,
    foreign key (coffee_id) references coffees (id),
    foreign key (coffee_group_id) references coffee_groups (id)
);