delete from pairs;
delete from users;
delete from teams;
delete from users_opponents;

insert into teams(id, name)
values (1, 'Red'),
       (2, 'Green'),
       (3, 'Blue');



insert into users(id, name, surname, team_id, is_chosen, last_duel)
values (1, 'Vladislav', 'Nemirov', 3, false, '2023-01-01 00:00:00.000000'),
       (2, 'Sergey', 'Losev', 3, false, '2023-01-01 00:00:00.000000'),
       (3, 'Polina', 'Belkevich', 3, false, '2023-01-01 00:00:00.000000'),
       (4, 'Andrey', 'Pshtyr', 3, false, '2023-01-01 00:00:00.000000'),
       (5, 'Egor', 'Makariev', 3, false, '2023-01-01 00:00:00.000000'),

       (6, 'Vadim', '-', 2, false, '2023-01-01 00:00:00.000000'),
       (7, 'Uladzimir', 'Krul', 2, false, '2023-01-01 00:00:00.000000'),
       (8, 'Pavel', 'Paliakou', 2, false, '2023-01-01 00:00:00.000000'),
       (9, 'Zilya', 'Nurislamova', 2, false, '2023-01-01 00:00:00.000000'),

       (10, 'Fedor', 'Ermakov', 1, false, '2023-01-01 00:00:00.000000'),
       (11, 'Mikhail', 'Apenko', 1, false, '2023-01-01 00:00:00.000000'),
       (12, 'Valerii', 'Uskov', 1, false, '2023-01-01 00:00:00.000000'),
       (13, 'Roman', 'Sokolov', 1, false, '2023-01-01 00:00:00.000000');