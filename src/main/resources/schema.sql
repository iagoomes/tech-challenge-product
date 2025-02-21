drop table if exists TB_PRODUCT;
create table TB_PRODUCT (
    id int auto_increment primary key,
    name varchar(255) not null,
    description text not null,
    price decimal(10, 2) not null,
    stockQuantity int not null
);