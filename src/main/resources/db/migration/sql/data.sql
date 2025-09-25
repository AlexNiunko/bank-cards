insert into role (name)
values ('USER'),
       ('ADMIN');


-- $2a$10$j6.P987YcDW1.NdPIQ1JGuRSLW1bgcYZFF8zh5qfTt.JMFgPTnx4a --- 123
-- $2a$10$ixQdwG5PCGr6VnVl4fGd1.WKhRjK2G1ZXghCbH954ysYGmwCqtZMa --- 456
-- $2a$10$kTs.2LnpH3SP0otC8yteIeNhZEL6LfYqxiRMKM3mt1iyw5PLZQCAS --- 789


insert into users (login, password, firstname, lastname, birth_date, phone_number,status)
values ('alex@gmail.com', '$2a$10$j6.P987YcDW1.NdPIQ1JGuRSLW1bgcYZFF8zh5qfTt.JMFgPTnx4a', 'alex',
        'niunko', '1989-02-09', '375298823898','ACTIVE'),
       ('ivan@mail.ru', '$2a$10$ixQdwG5PCGr6VnVl4fGd1.WKhRjK2G1ZXghCbH954ysYGmwCqtZMa', 'ivan',
        'ivanov', '1999-09-09', '375338459562','ACTIVE'),
       ('sveta@gmail.com', '$2a$10$kTs.2LnpH3SP0otC8yteIeNhZEL6LfYqxiRMKM3mt1iyw5PLZQCAS', 'sveta',
        'svetikova', '1989-05-19', '375296589123','ACTIVE');


insert into user_roles (role_id, user_id)
VALUES (2, 1),
       (1, 2),
       (2, 3);

insert into card (system, expiration_time, currency, card_number, user_id,balance,status)
values ('VISA', '2026-11-29T14:00:00', 'EUR', '1234567891012134', 2,100.0,'ACTIVE'),
       ('MASTERCARD', '2026-09-15T16:00:00', 'USD', '1111222234567889', 2,200.0,'BLOCKED'),
       ('MASTERCARD', '2026-12-27T17:00:00', 'RUB', '9999888877771111', 2,150.0,'ACTIVE'),
       ('VISA', '2027-10-17T12:00:00', 'USD', '8587455621543256', 3,50.0,'BLOCKED'),
       ('MIR', '2027-10-17T18:00:00', 'RUB', '3298456521238789', 3,50.0,'ACTIVE');
