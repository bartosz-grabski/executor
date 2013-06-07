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

--CREATE PROBLEMS--
INSERT INTO problem (problem_id,content,name, user_id) VALUES (1, 'ProblemContent 1', 'ProblemName 1', 1)


--CREATE RESULTSTATUS--
insert into resultstatus ( result_status_id,resultstatus ) values (1 , 1)

--CREATE RESULTS--
insert into result (result_id, score, status) values (1, 0, 1)
insert into result (result_id, score, status) values (2, 0, 1)
insert into result (result_id, score, status) values (3, 0, 1)
--CREATE SUBMITS--
INSERT INTO submit ( submit_id, commit_date, file_path, problem_id, result_id, user_id) values (1, '2013-06-03 23:20:39.394', 'submit_path_2',1, 1, 1)
INSERT INTO submit ( submit_id, commit_date, file_path, problem_id, result_id, user_id) values (2, '2013-06-03 23:20:39.394', 'submit_path_2',1, 2, 2)
INSERT INTO submit ( submit_id, commit_date, file_path, problem_id, result_id, user_id) values (3, '2013-06-03 23:20:39.394', 'submit_path_3',1, 3, 1)





