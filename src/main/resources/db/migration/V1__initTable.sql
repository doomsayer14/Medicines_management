create table medicine
(
    id                bigint not null auto_increment,
    compound          varchar(2048) not null,
    contraindications varchar(2048) not null,
    name              varchar(200) unique not null,
    price             double precision not null,
    terms_of_use      varchar(2048) not null,
    primary key (id)
) engine = InnoDB