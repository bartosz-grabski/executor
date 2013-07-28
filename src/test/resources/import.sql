--CREATING USER ROLES--
INSERT INTO authorities (authority_id,authority) VALUES (1,'ROLE_ADMIN')
INSERT INTO authorities (authority_id,authority) VALUES (2,'ROLE_USER')
INSERT INTO authorities (authority_id,authority) VALUES (3,'ROLE_DOMAIN')

--CREATE DOMAINS--
INSERT INTO domain(exercise_id, description, title) VALUES (1, 'domain 1', 'domainTitle 1')
INSERT INTO domain(exercise_id, description, title) VALUES (2, 'domain 2', 'domainTitle 2')

--CREATE GROUPS--
INSERT INTO groups( group_id, description, title, domain_id) VALUES (1, 'group 1', 'groupTitle 1', 1)
INSERT INTO groups( group_id, description, title, domain_id) VALUES (2, 'group 2', 'groupTitle 2', 2)

--CREATING USERS--
INSERT INTO users(user_id, email, enabled, password, username, domain_id) VALUES (1, 'admin@ideafactory.com', true, 'admin', 'admin', 1)
INSERT INTO users(user_id, email, enabled, password, username, domain_id) VALUES (2, 'user@ideafactory.com', true, 'user', 'user', 1)
INSERT INTO users(user_id, email, enabled, password, username, domain_id) VALUES (3, 'domain@ideafactory.com', true, 'domain', 'domain', 1)

-- JOINING USERS WITH GROUPS--    
INSERT INTO users_groups(users_user_id, groups_group_id) VALUES (1, 1)
INSERT INTO users_groups(users_user_id, groups_group_id) VALUES (2, 1)
INSERT INTO users_groups(users_user_id, groups_group_id) VALUES (3, 1)

--JOINING ROLES WITH USERS--
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (1 , 1)
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (2 , 2)
INSERT INTO user_authorities (user_id ,authority_id ) VALUES (3 , 3)

--CREATE PROBLEMS--
INSERT INTO problem (problem_id,content,name, user_id) VALUES (1, 'ProblemContent 1', 'ProblemName 1', 1)
INSERT INTO problem (problem_id,content,name, user_id) VALUES (2, 'ProblemContent 2', 'ProblemName 2', 1)
INSERT INTO problem (problem_id,content,name, user_id) VALUES (3, 'ProblemContent 3', 'ProblemName 3', 1)

--CREATE EXERCISES--
INSERT INTO exercise(exercise_id, deadline, problem_id) VALUES (1, '2013-06-03 23:20:39.394', 1)
INSERT INTO exercise(exercise_id, deadline, problem_id) VALUES (2, '2013-06-03 23:20:39.394', 2)
INSERT INTO exercise(exercise_id, deadline, problem_id) VALUES (3, '2013-06-03 23:20:39.394', 3)

--CREATE RESULTSTATUS--
insert into result_status ( result_status_id,resultstatus ) values (1 , 1)

--CREATE RESULTS--
insert into result (result_id, score, status) values (1, 0, 1)
insert into result (result_id, score, status) values (2, 0, 1)
insert into result (result_id, score, status) values (3, 0, 1)
--CREATE SUBMITS--
INSERT INTO submit ( submit_id, commit_date, file_path, exercise_id, result_id, user_id) values (1, '2013-06-03 23:20:39.394', 'submit_path_2',1, 1, 1)
INSERT INTO submit ( submit_id, commit_date, file_path, exercise_id, result_id, user_id) values (2, '2013-06-03 23:20:39.394', 'submit_path_2',1, 2, 2)
INSERT INTO submit ( submit_id, commit_date, file_path, exercise_id, result_id, user_id) values (3, '2013-06-03 23:20:39.394', 'submit_path_3',1, 3, 1)






