CREATE DATABASE M295;
Use M295;

CREATE table TEAMMEMBER (
    memberId int NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    firstname varchar(255) NOT NULL,
    joinDate datetime NOT NULL,
    team int NOT NULL,
    PRIMARY KEY (memberId)
    );


CREATE table TEAM (
    teamId int NOT NULL AUTO_INCREMENT,
    teamName varchar(255) NOT NULL,
    budget int NOT NULL,
    PRIMARY KEY (teamId)
    );

CREATE table SOFTWARE (
    softwareId varchar(3) NOT NULL,
    softwareName varchar(255) NOT NULL,
    softwareVersion varchar(255) NOT NULL,
    team int NOT NULL,
    project int NOT NULL,
    status varchar(50) NOT NULL,
    PRIMARY KEY (softwareId)
    );



CREATE table PROJECT (
    projectId int NOT NULL AUTO_INCREMENT,
    projectName varchar(255) NOT NULL,
    PRIMARY KEY (projectId)
    );


ALTER TABLE TEAMMEMBER ADD CONSTRAINT FK_TEAMMEMBER FOREIGN KEY (team) REFERENCES TEAM(teamId);
ALTER TABLE SOFTWARE ADD CONSTRAINT FK_TEAM FOREIGN KEY (team) REFERENCES TEAM(teamId);
ALTER TABLE SOFTWARE ADD CONSTRAINT FK_PROJECT FOREIGN KEY (project) REFERENCES PROJECT(projectId);

