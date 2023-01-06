create table teams
(
    id   bigserial   not null,
    name varchar(30) not null,
    constraint pk_team primary key (id)
);