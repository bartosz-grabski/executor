--CREATING USER ROLES--
INSERT INTO authorities (authority) VALUES ('ROLE_ADMIN')
INSERT INTO authorities (authority) VALUES ('ROLE_USER')
INSERT INTO authorities (authority) VALUES ('ROLE_DOMAIN')

--CREATING SAMPLE INSTITUTION- password executor-

INSERT INTO institution (email, enabled, password) values ('executor@agh.pl', TRUE, '0e2a2b4ff14ced3517d7dcb6e684672131d4796d2377f92e30085f67691b138a')