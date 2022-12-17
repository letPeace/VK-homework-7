
INSERT INTO security.users
VALUES (1, 'manager', 'manager'),
       (2, 'guest', 'guest');

INSERT INTO security.roles
VALUES (1, 'manager'),
       (2, 'guest');

INSERT INTO security.users_roles
VALUES (1, 1),
       (1, 2),
       (2, 2);
