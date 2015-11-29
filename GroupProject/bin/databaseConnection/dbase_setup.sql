use c_cs108_mateog;

DROP TABLE IF EXISTS Challenges;
DROP TABLE IF EXISTS FriendRequests;
DROP TABLE IF EXISTS Notes;

DROP TABLE IF EXISTS Accounts;
DROP TABLE IF EXISTS Friends;


-- Represents all accounts on the website.
CREATE TABLE Accounts (
	-- A numerical ID for this account (NOT THE USERNAME).
	ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	-- The username of the account, to be unique.
	Username VARCHAR(255) NOT NULL UNIQUE,
	-- The password hash SHA1 of the account, accounting the salt we use
	-- to secure passwords.
	Password CHAR(40),
	-- Whether or not the user is an admin. Defaults to false.
	IsAdmin BOOLEAN DEFAULT FALSE
);

-- Represents friend relationships. Hold 2 entries for each friend relationship for convenience.
-- For example, if entry (A, B) exists in the table, there MUST be a corresponding (B, A) in the
-- table.
CREATE TABLE Friends (
	-- The ID of the first user in the friendship.
	UserID INT NOT NULL,
	FOREIGN KEY (UserID) REFERENCES Accounts(ID),
	-- The ID of the second user in the friendship.
	FriendID INT NOT NULL,
	FOREIGN KEY (FriendID) REFERENCES Accounts(ID)
);

-- Represents all pending quiz challenges. Remove when a user accepts or declines the challenge.
CREATE TABLE Challenges (
  	ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	-- The ID of the issuer of the request.
	SenderID INT NOT NULL,
	FOREIGN KEY (SenderID) REFERENCES Accounts(ID),
	-- The ID of the receiver of the request.
	ReceiverID INT NOT NULL,
	FOREIGN KEY (ReceiverID) REFERENCES Accounts(ID),
	-- The date of when the request was made.
	SentDate DATETIME,
	-- The ID of the quiz being sent as a challenge.
	QuizID INT NOT NULL,
	FOREIGN KEY (QuizID) REFERENCES Quizzes(ID)
);

-- Represents all notes that are sent between users. Notes exist forever but can be set as "seen".
-- A "seen" notice will exist and can be reviewed but won't be annoying and appear on the newsfeed.
CREATE TABLE Notes (
  -- The ID to identify this note by
  ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  -- The ID of the issuer of the request.
  ID  INT NOT NULL,
  FOREIGN KEY (SenderID) REFERENCES Accounts(ID),
  -- The ID of the receiver of the request.
  ReceiverID INT NOT NULL,
  FOREIGN KEY (ReceiverID) REFERENCES Accounts(ID),
  -- The date of when the request was made.
  SentDate DATETIME,
  -- The text contents of the actual note.
  Contents VARCHAR(2000),
  -- The text of the subject
  Subject VARCHAR(100),
  -- Whether or not the note has been seen. All created notes are defaulted to not seen and thus
  -- this field does not need to be included.
  Seen BOOLEAN DEFAULT FALSE
);

-- Represents all pending friend requests. Only create one if a user requests to be a friend with
-- someone is not friends with them. If a user requests to be a friend with a user that has initiated
-- a friend request with them pending, DO NOT issue dual requests; simply remove the pending request
-- and make them friends. DO NOT issue friend requests from the same user to himself. Remove the
-- request once another user has Accepted or Declined
CREATE TABLE FriendRequests (
  ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  -- The ID of the issuer of the request.
  SenderID INT NOT NULL,
  FOREIGN KEY (SenderID) REFERENCES Accounts(ID),
  -- The ID of the receiver of the request.
  ReceiverID INT NOT NULL,
  FOREIGN KEY (ReceiverID) REFERENCES Accounts(ID),
  -- The date of when the request was made.
  SentDate DATETIME
);
