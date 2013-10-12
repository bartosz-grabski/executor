--CREATE USER
INSERT INTO users(user_id, email, enabled, password, username) VALUES (1, 'admin@ideafactory.com', true, 'admin', 'admin')

--CREATE PROBLEM
INSERT INTO problem (problem_id, content ,name, user_id) VALUES (1, null, 'ProblemName 1', 1)

--CREATE EXERCISE
INSERT INTO exercise(exercise_id, deadline, problem_id) VALUES (1, '2013-06-03 23:20:39.394', 1)

--CREATE RESULTS
insert into result (result_id, score, status) values (1, 0, 1)
insert into result (result_id, score, status) values (2, 0, 1)

--CREATE SUBMITS--
INSERT INTO submit ( submit_id, commit_date, file_name, exercise_id, result_id, user_id,content, language) values (1, '2013-06-03 23:20:39.394', 'submit_name_1',1, 1, 1,null, 'C')

--CREATE TESTS--
INSERT INTO test (test_id, input, output) values (1, 'dupa', 'cycki')
INSERT INTO test (test_id, input, output) values (2, 'dupa1', 'cycki1')

insert into exercise_test (exercise_exercise_id, tests_test_id) values (1,1)
insert into exercise_test (exercise_exercise_id, tests_test_id) values (1,2)