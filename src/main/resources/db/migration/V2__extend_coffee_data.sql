alter table COFFEES
    add column processing      varchar(255),
    add column roast_profile   varchar(255),
    add column region          varchar(255),
    add column continent       varchar(255),
    add column farm            varchar(255),
    add column crop_height     double,
    modify column rating double;
