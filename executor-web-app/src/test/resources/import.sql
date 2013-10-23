--CREATING USER ROLES--
INSERT INTO authorities (authority_id,authority) VALUES (1,'ROLE_ADMIN')
INSERT INTO authorities (authority_id,authority) VALUES (2,'ROLE_USER')
INSERT INTO authorities (authority_id,authority) VALUES (3,'ROLE_DOMAIN')

--CREATE DOMAINS--
INSERT INTO domain(domain_id, description, title, password) VALUES (1, 'domain 1', 'domainTitle 1', 'password 1')
INSERT INTO domain(domain_id, description, title, password) VALUES (2, 'domain 2', 'domainTitle 2', 'password 2')

--CREATE GROUPS--
INSERT INTO groups( group_id, description, title, domain_id, password) VALUES (1, 'group 1', 'groupTitle 1', 1, 'password_1')
INSERT INTO groups( group_id, description, title, domain_id, password) VALUES (2, 'group 2', 'groupTitle 2', 2, 'password_2')

--CREATING USERS--
INSERT INTO users(user_id, email, enabled, password, username) VALUES (1, 'admin@ideafactory.com', true, 'admin', 'admin')
INSERT INTO users(user_id, email, enabled, password, username) VALUES (2, 'user@ideafactory.com', true, 'user', 'user')
INSERT INTO users(user_id, email, enabled, password, username) VALUES (3, 'domain@ideafactory.com', true, 'domain', 'domain')

-- JOINING USERS WITH GROUPS--    
INSERT INTO user_group(user_id, group_id) VALUES (1, 1)
INSERT INTO user_group(user_id, group_id) VALUES (2, 1)
INSERT INTO user_group(user_id, group_id) VALUES (3, 1)

--JOINING ROLES WITH USERS--
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (1 , 1)
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (2 , 2)
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (3 , 3)

--CREATE PROBLEMS--
INSERT INTO problem (problem_id,content,name, user_id, active) VALUES (1, null, 'ProblemName 1', 1, true)
INSERT INTO problem (problem_id,content,name, user_id, active) VALUES (2, null, 'ProblemName 2', 1, true)
INSERT INTO problem (problem_id,content,name, user_id, active) VALUES (3, null, 'ProblemName 3', 1, true)

--CREATE EXERCISES--
INSERT INTO exercise(exercise_id, title, deadline, problem_id) VALUES (1, 'exercise title 1', '2013-06-03 23:20:39.394', 1)
INSERT INTO exercise(exercise_id, title, deadline, problem_id) VALUES (2, 'exercise title 2', '2013-06-03 23:20:39.394', 2)
INSERT INTO exercise(exercise_id, title, deadline, problem_id) VALUES (3, 'exercise title 3','2013-06-03 23:20:39.394', 3)

--CREATE RESULTSTATUS--
insert into result_status ( result_status_id,resultstatus ) values (1 , 1)

--CREATE RESULTS--
insert into result (result_id, score, status) values (1, 0, 1)
insert into result (result_id, score, status) values (2, 0, 1)
insert into result (result_id, score, status) values (3, 0, 1)
--CREATE SUBMITS--
INSERT INTO submit ( submit_id, commit_date, file_name, exercise_id, result_id, user_id,content, language) values (1, '2013-06-03 23:20:39.394', 'submit_name_2',1, 1, 1,null, 'language_1')
INSERT INTO submit ( submit_id, commit_date, file_name, exercise_id, result_id, user_id,content, language) values (2, '2013-06-03 23:20:39.394', 'submit_name_2',1, 2, 2,null, 'language_2')
INSERT INTO submit ( submit_id, commit_date, file_name, exercise_id, result_id, user_id,content, language) values (3, '2013-06-03 23:20:39.394', 'submit_name_3',1, 3, 1,null, 'language_3')


--CREATE INSTITUTIONS--
INSERT INTO institution (institution_id, email, password, enabled) VALUES (1, 'existing@institution.com', 'password', true)
