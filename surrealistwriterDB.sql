CREATE DATABASE IF NOT EXISTS surrealist_writer_db DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
use surrealist_writer_db;

CREATE TABLE IF NOT EXISTS user(
  userID int(4) NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  hash_password char(64) NOT NULL,
  register_date datetime NOT NULL,
  PRIMARY KEY (userID)
);
ALTER TABLE user AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS game(
 gameID int(4) NOT NULL AUTO_INCREMENT,
 current_text TEXT NULL,
 color_text TEXT NULL,
 round_amount int(3) NOT NULL,
 max_word int(3) NOT NULL,
 -- status about game, running/unavailable(0) - user cannot join to the game, available(1) - user can join to game
 status int(1) NOT NULL,
 PRIMARY KEY (gameID)
);
ALTER TABLE game AUTO_INCREMENT = 1;

CREATE TABLE IF NOT EXISTS gameUser(
 gameID int(4) NOT NULL,
 userID int(4) NOT NULL,
 color int(8) NOT NULL,
 PRIMARY KEY(gameID,userID),
 CONSTRAINT fk_game FOREIGN KEY(gameID) REFERENCES game(gameID),
 CONSTRAINT fk_user FOREIGN KEY(userID) REFERENCES user(userID)
 
);

CREATE TABLE IF NOT EXISTS room(
 roomID int(4) NOT NULL AUTO_INCREMENT,
 adminID int(4) NOT NULL,
 name varchar(40) NOT NULL,
 -- if password is null room is public,everyone can join to room
 password varchar(15) NULL,
 max_players int(2) NOT NULL,
 PRIMARY KEY(roomID),
 CONSTRAINT foreignkey_user FOREIGN KEY(adminID) REFERENCES user(userID)
);

CREATE TABLE IF NOT EXISTS matches(
 gameID int (4) NOT NULL,
 roomID int (4) NOT NULL,
 -- status about match - finished(0) - not displayed to user,current(1)-to show in online game
 status int(1) NOT NULL, 
 PRIMARY KEY (gameID,roomID),
 CONSTRAINT fk_gameid FOREIGN KEY(gameID) REFERENCES game(gameID),
 CONSTRAINT fk_roomid FOREIGN KEY(roomID) REFERENCES room(roomID)
 
);

CREATE TABLE IF NOT EXISTS gameScore(
 gameID int(4) NOT NULL,
 userID int(4) NOT NULL,
 score int(4) NOT NULL,
 PRIMARY KEY(gameID,userID),
 CONSTRAINT f_game FOREIGN KEY(gameID) REFERENCES game(gameID),
 CONSTRAINT f_user FOREIGN KEY(userID) REFERENCES user(userID)

);

DELIMITER //
CREATE TRIGGER make_match
AFTER INSERT ON game
FOR EACH ROW BEGIN
	set @roomid = (select max(roomID) from room);
	set @gameid = (select max(gameID) from game);
	INSERT INTO matches(gameID,roomID,status) values(@gameID,@roomID,1);
END //
DELIMITER ;