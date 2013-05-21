--CREATING USER ROLES--
INSERT INTO authorities (authority_id,authority) VALUES (1,'ROLE_ADMIN')
INSERT INTO authorities (authority_id,authority) VALUES (2,'ROLE_USER')
INSERT INTO authorities (authority_id,authority) VALUES (3,'ROLE_DOMAIN')

--CREATING USERS--
INSERT INTO users (user_id ,username , password, enabled , email) VALUES (1 , 'admin' , 'admin' , true , 'admin@ideafactory.com')
INSERT INTO users (user_id ,username , password, enabled , email) VALUES (2 , 'user' , 'user' , true , 'user@ideafactory.com')
INSERT INTO users (user_id ,username , password, enabled , email) VALUES (3 , 'domain' , 'domain' , true , 'domain@ideafactory.com')

--JOINING ROLES WITH USERS--
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (1 , 1)
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (2 , 2)
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (3 , 3)