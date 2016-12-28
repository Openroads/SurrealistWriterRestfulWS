drop DATABASE surrealist_writer_db;
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