CREATE DATABASE IF NOT EXISTS M295;
USE M295;

CREATE TABLE IF NOT EXISTS TEAMMEMBER
(
    `memberId`  INT          NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(255) NOT NULL,
    `firstname` VARCHAR(255) NOT NULL,
    `joinDate`  DATETIME     NOT NULL,
    `team`      INT          NOT NULL,
    PRIMARY KEY (memberId)
);


CREATE TABLE IF NOT EXISTS TEAM
(
    `teamId`   INT          NOT NULL AUTO_INCREMENT,
    `teamName` VARCHAR(255) NOT NULL,
    `budget`   INT          NOT NULL,
    PRIMARY KEY (teamId)
);

CREATE TABLE IF NOT EXISTS SOFTWARE
(
    `softwareId`      VARCHAR(3)   NOT NULL,
    `softwareName`    VARCHAR(255) NOT NULL,
    `softwareVersion` VARCHAR(255) NOT NULL,
    `team`            INT          NOT NULL,
    `project`         INT          NOT NULL,
    `status`          VARCHAR(50)  NOT NULL,
    PRIMARY KEY (softwareId)
);



CREATE TABLE IF NOT EXISTS PROJECT
(
    `projectId`   INT          NOT NULL AUTO_INCREMENT,
    `projectName` VARCHAR(255) NOT NULL,
    PRIMARY KEY (projectId)
);


ALTER TABLE TEAMMEMBER
    ADD CONSTRAINT FK_TEAMMEMBER FOREIGN KEY (team) REFERENCES TEAM (teamId);
ALTER TABLE SOFTWARE
    ADD CONSTRAINT FK_TEAM FOREIGN KEY (team) REFERENCES TEAM (teamId);
ALTER TABLE SOFTWARE
    ADD CONSTRAINT FK_PROJECT FOREIGN KEY (project) REFERENCES PROJECT (projectId);

