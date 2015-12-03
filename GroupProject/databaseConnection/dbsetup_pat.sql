use c_cs108_mateog;

DROP TABLE IF EXISTS Choices;
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
	username2 VARCHAR(64),
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
     description TEXT NOT NULL DEFAULT 'Another fun quiz',
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
CREATE TABLE Choices (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	questionId INT NOT NULL,
	choice VARCHAR(255) NOT NULL,
	choiceIndex INT NOT NULL,
	isCorrect BOOLEAN NOT NULL
);

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
INSERT INTO Accounts (username, password, isAdmin, suspended) VALUES("pat", "patpatpat", 1, 0);
INSERT INTO Accounts (username, password, isAdmin, suspended) VALUES("eric", "1234", 0, 0);
INSERT INTO Accounts (username, password, isAdmin, suspended) VALUES("mateo", "mateo", 1, 0);
INSERT INTO Accounts (username, password, isAdmin, suspended) VALUES("aaron", "passw0rd", 1, 0);

-- Sample quiz
INSERT INTO Quizzes (name, randomOrder, singlePage, immediateCorrection, practiceModeAllowed, takenCounter, createdBy, description, createdDate) VALUES ("TV Shows Trivia", 0, 1, 0, 0, 0, "pat", "A must-take quiz if you think you know more about TV shows than any of your friends", "2015-11-23 06:15:00");
INSERT INTO Quizzes (name, randomOrder, singlePage, immediateCorrection, practiceModeAllowed, takenCounter, createdBy, description, createdDate) VALUES ("CS 108 Quiz", 0, 1, 0, 0, 0, "eric", "Preparing for a final for CS 108? Well there isn't one!", "2015-11-24 17:10:00");
INSERT INTO Quizzes (name, randomOrder, singlePage, immediateCorrection, practiceModeAllowed, takenCounter, createdBy, description, createdDate) VALUES ("Another Awesome Quiz", 0, 1, 0, 0, 0, "pat", "Just another fun quiz", "2015-11-25 16:15:00");

-- Sample question
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Response", 1, "Who is your daddy?", "Pat", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Response", 1, "What is 5 + 1?", "6", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Blank", 1, "@@@@ killed Lord Tywin Lannister? (First name)", "Tyrion", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Picture", 1, "Who is this actress? (First name)", "Lisa", "https://upload.wikimedia.org/wikipedia/en/f/f6/Friendsphoebe.jpg");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "MultipleChoice", 1, "Who plays Rachel Green in Friends?", "", "");

INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (9, "Lisa Kudrow", 0, 0, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (9, "Jennifer Aniston", 1, 1, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (9, "Courtney Cox", 2, 0, '');
INSERT INTO Answers(questionID, answer, answerIndex, correct, prompt) VALUES (9, "Sigourney Weaver", 3, 0, '');
