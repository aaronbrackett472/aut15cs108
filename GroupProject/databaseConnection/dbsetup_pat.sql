use c_cs108_mateog;

DROP TABLE IF EXISTS Choices;
DROP TABLE IF EXISTS Questions;

-- Represents all questions in the website
CREATE TABLE Questions (
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	quizId BIGINT NOT NULL,
	FOREIGN KEY (quizId) REFERENCES quizzes(id),
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
	FOREIGN KEY (questionId) REFERENCES Questions(id),
	choice VARCHAR(255) NOT NULL,
	choiceIndex INT NOT NULL,
	isCorrect BOOLEAN NOT NULL
);

-- Sample question
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Response", 1, "Who is your daddy?", "Pat", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Response", 1, "What is 5 + 1?", "6", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Blank", 1, "@@@@ killed Lord Tywin Lannister? (First name)", "Tyrion", "");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "Picture", 1, "Who is this actress? (First name)", "Lisa", "https://upload.wikimedia.org/wikipedia/en/f/f6/Friendsphoebe.jpg");
INSERT INTO Questions(quizId, type, score, question, correctAnswer, imageUrl) VALUES (1, "MultipleChoice", 1, "Who plays Rachel Green in Friends?", "", "");

INSERT INTO Choices(questionId, choice, choiceIndex, isCorrect) VALUES (5, "Lisa Kudrow", 0, 0);
INSERT INTO Choices(questionId, choice, choiceIndex, isCorrect) VALUES (5, "Jennifer Aniston", 1, 0);
INSERT INTO Choices(questionId, choice, choiceIndex, isCorrect) VALUES (5, "Courtney Cox", 2, 0);
INSERT INTO Choices(questionId, choice, choiceIndex, isCorrect) VALUES (5, "Sigourney Weaver", 3, 0);