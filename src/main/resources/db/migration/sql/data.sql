insert into role (name)
values ('USER'),
       ('ADMIN');
--
--     $2a$10$8QcNeAD/HW9zBdYx4iviQeS9OrTGCdgtFqAMXB5QDPXtKnGwQnkFW
-- $2a$10$Ts16vXo8FuaFJM3N6zE0JONU1rcrfS34E.cFOsbq/VEE/h2FIovWO

insert into users (login, password, firstname, lastname, birth_date, phone_number)
values ('alex@gmail.com', '$2a$10$8QcNeAD/HW9zBdYx4iviQeS9OrTGCdgtFqAMXB5QDPXtKnGwQnkFW', 'alex',
        'niunko', '1989-02-09', '357298823898');

insert into user_roles (role_id, user_id)
VALUES (2, 1)