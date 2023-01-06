create table if not exists users
(
    id        bigserial   not null,
    name      varchar(30) not null,
    surname   varchar(30) not null,
    team_id   int,
    is_chosen boolean     not null,
    last_duel timestamp   not null,

    constraint pk_user primary key (id),
    constraint fk_team_user foreign key (team_id) references teams (id)
        on delete set null
);