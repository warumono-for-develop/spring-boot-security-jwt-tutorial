INSERT INTO USERS 
	(id, username, password) 
VALUES 
	(1, 'admin@me.com', '$2a$10$bnC26zz//2cavYoSCrlHdecWF8tkGfPodlHcYwlACBBwJvcEf0p2G'), 
	(2, 'staff@me.com', '$2a$10$bnC26zz//2cavYoSCrlHdecWF8tkGfPodlHcYwlACBBwJvcEf0p2G'), 
	(3, 'user@me.com', '$2a$10$bnC26zz//2cavYoSCrlHdecWF8tkGfPodlHcYwlACBBwJvcEf0p2G');

INSERT INTO USER_ROLES 
	(user_id, role) 
VALUES 
	(1, 'ADMIN'), 
	(1, 'STAFF'), 
	(1, 'USER'), 
	(2, 'STAFF'), 
	(2, 'USER'), 
	(3, 'USER');