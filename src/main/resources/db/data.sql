delete from pairs;
delete from users;
delete from teams;
delete from users_opponents;

insert into teams(name)
values ('Red'),
       ('Green'),
       ('Blue');

insert into users(name, surname, team_id, is_chosen, last_duel)
values ('Vladislav', 'Nemiro', 3, false, '2023-01-01 00:00:00.000000'),
       ('Sergei', 'Losev', 3, false, '2023-01-01 00:00:00.000000'),
       ('Polina', 'Belkevich', 3, false, '2023-01-01 00:00:00.000000'),
       ('Andrey', 'Pshtyr', 3, false, '2023-01-01 00:00:00.000000'),
       ('Egor', 'Makariev', 3, false, '2023-01-01 00:00:00.000000'),

       ('Vadim', '-', 2, false, '2023-01-01 00:00:00.000000'),
       ('Uladzimir', 'Krul', 2, false, '2023-01-01 00:00:00.000000'),
       ('Pavel', 'Paliakou', 2, false, '2023-01-01 00:00:00.000000'),
       ('Zilya', 'Nurislamova', 2, false, '2023-01-01 00:00:00.000000'),

       ('Fedor', 'Ermakov', 1, false, '2023-01-01 00:00:00.000000'),
       ('Mikhail', 'Apenko', 1, false, '2023-01-01 00:00:00.000000'),
       ('Valerii', 'Uskov', 1, false, '2023-01-01 00:00:00.000000'),
       ('Roman', 'Sokolov', 1, false, '2023-01-01 00:00:00.000000');