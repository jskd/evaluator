# Users schema

# --- !Ups

CREATE TABLE users (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    pseudo varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    admin boolean DEFAULT FALSE,
    PRIMARY KEY (id),
    UNIQUE KEY (pseudo)
);

INSERT INTO users (pseudo, password, admin) VALUES ('admin', 'admin', 1);
INSERT INTO users (pseudo, password, admin) VALUES ('user', 'user', 0);

# --- !Downs

DROP TABLE users;
