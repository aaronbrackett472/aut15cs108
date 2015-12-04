use c_cs108_mateog;

DROP TABLE IF EXISTS Answers;
DROP TABLE IF EXISTS Questions;
DROP TABLE IF EXISTS History;
DROP TABLE IF EXISTS Quizzes;
DROP TABLE IF EXISTS Achievement;
DROP TABLE IF EXISTS Friendship;
DROP TABLE IF EXISTS Accounts;

CREATE TABLE Accounts (
	username VARCHAR(64) NOT NULL PRIMARY KEY,
	password VARCHAR(64) NOT NULL,
	isAdmin BOOL NOT NULL DEFAULT 0,
	suspended BOOL NOT NULL DEFAULT 0,
	suspensionEnd TIMESTAMP
);

CREATE TABLE Friendship (
	username1 VARCHAR(64),
	username2 VARCHAR(64)
);

CREATE TABLE Achievement (
	username VARCHAR(64),
	achivementName VARCHAR(64),
	achivementDescription VARCHAR(255),
	image VARCHAR(255),
	dateAcquired TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Quizzes (
     id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(64),
     randomOrder BOOL,
     singlePage BOOL,
     immediateCorrection BOOL,
     practiceModeAllowed BOOL,
     takenCounter INT NOT NULL DEFAULT 0,
     createdBy VARCHAR(64) NOT NULL,
     description TEXT,
     createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE History (
	username VARCHAR(64) NOT NULL,
	score INT,
	maxScore INT,
	quizId INT NOT NULL,
	dateTaken TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Represents all questions in the website
CREATE TABLE Questions (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	quizId INT NOT NULL,
	type VARCHAR(255) NOT NULL,
	score INT NOT NULL,
	question VARCHAR(255),
	correctAnswer VARCHAR(255),
	imageUrl VARCHAR(255)
);

-- Represents all questions in the website

CREATE TABLE Answers (
     id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
     questionID INT, 
     answer VARCHAR(64),
     answerIndex INT, 
     correct BOOL,
     prompt VARCHAR(64),
     createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample user
INSERT INTO Accounts (username, password, isAdmin, suspended) VALUES("pat", "58b6af29938a3941eeeab30085787170d574e2df", 1, 0);
INSERT INTO Accounts (username, password, isAdmin, suspended) VALUES("pat2", "58b6af29938a3941eeeab30085787170d574e2df", 0, 0);
INSERT INTO Accounts (username, password, isAdmin, suspended) VALUES("eric", "7110eda4d09e062aa5e4a390b0a572ac0d2c0220", 0, 0);

-- Sample quiz
INSERT INTO Quizzes (name, randomOrder, singlePage, immediateCorrection, practiceModeAllowed, takenCounter, createdBy, description, createdDate) VALUES ("TV Shows Trivia", 0, 1, 0, 0, 0, "pat", "A must-take quiz if you think you know more about TV shows than any of your friends", "2015-11-23 06:15:00");

-- Sample question
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Question-Response", 1, "Who is your daddy?", "", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Question-Response", 1, "What is 5 + 1?", "", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Fill in the Blank", 1, "@@@@ killed Lord Tywin Lannister?", "", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Picture Response", 1, "Who is this actress?", "", "https://upload.wikimedia.org/wikipedia/en/f/f6/Friendsphoebe.jpg");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Multiple Choice", 1, "Who plays Rachel Green in Friends?", "", "");


INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (1, "Pat", 0, 1, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (3, "6", 0, 1, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (5, "Tyrion", 0, 1, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (5, "Tyrion Lannister", 0, 1, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (5, "The Imp", 0, 1, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (7, "Lisa Kudrow", 0, 1, '');

INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (9, "Lisa Kudrow", 0, 0, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (9, "Jennifer Aniston", 1, 1, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (9, "Courtney Cox", 2, 0, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (9, "Sigourney Weaver", 3, 0, '');
