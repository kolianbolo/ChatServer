CREATE DATABASE IF NOT EXISTS chat  CHARACTER SET utf8;
USE chat;
CREATE TABLE IF NOT EXISTS `users`( `id` INT PRIMARY KEY AUTO_INCREMENT, 
                                    `name` VARCHAR(255) NOT NULL,
                                    `password` TEXT NOT NULL);

CREATE TABLE IF NOT EXISTS `sessions`(  `id` INT PRIMARY KEY AUTO_INCREMENT, 
                                        `user` INT NOT NULL,
                                        `uuid` VARCHAR(36) NOT NULL,
                                        `timestamp` TIMESTAMP NOT NULL,
                                        FOREIGN KEY (`user`) REFERENCES `users`(`id`));

CREATE TABLE IF NOT EXISTS `messages`(  `id` INT PRIMARY KEY AUTO_INCREMENT, 
                                        `sender` INT NOT NULL,
                                        `recipient` INT NOT NULL, 
                                        `message` TEXT NOT NULL, 
                                        `timestamp` TIMESTAMP NOT NULL,
                                        `status` TINYINT NOT NULL,
                                        FOREIGN KEY (`sender`) REFERENCES `users`(`id`),
                                        FOREIGN KEY (`recipient`) REFERENCES `users`(`id`));
