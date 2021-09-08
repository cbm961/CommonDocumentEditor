CREATE TABLE IF NOT EXISTS documents (
         name        VARCHAR(30)   NOT NULL DEFAULT '',
         delete_count     INT UNSIGNED  NOT NULL DEFAULT 1,
         content		TEXT,
         PRIMARY KEY  (name)
       );

CREATE TABLE IF NOT EXISTS users (
         name        VARCHAR(100)   NOT NULL DEFAULT '',
         password     VARCHAR(30)   NOT NULL DEFAULT '' ,
         email		    VARCHAR(100)   NOT NULL DEFAULT '',
         PRIMARY KEY  (name)
       );
CREATE TABLE IF NOT EXISTS users_documents (
username VARCHAR(100) NOT NULL DEFAULT '',
documentname VARCHAR(30) NOT NULL DEFAULT '',

PRIMARY KEY(username, documentname)
);

INSERT INTO documents (name, content) VALUES ("doc1", "a la peche moule moule moule je nveux plus aller maman");
INSERT INTO documents (name, content) VALUES ("doc2", "car les gens de la ville ville ville m'ont vole ma peche maman");
INSERT INTO documents (name, content) VALUES ("doc3", "au clair de la lune mon ami pierro");
INSERT INTO documents (name, content) VALUES ("doc4", "pretes moi ta plume pour ecrire un mot");

INSERT INTO users (name, password,email ) VALUES ("claraboumansour", "Clara_pass", "claraboumansour@gmail.com");
INSERT INTO users (name, password,email ) VALUES ("kinansolh", "Kinan_pass", "kinanSolh@gmail.com");
INSERT INTO users (name, password,email ) VALUES ("josephibrahim", "Joseph_pass", "JosephIbrahim@gmail.com");



INSERT INTO users_documents (username, documentname) VALUES ("claraboumansour", "doc1");
INSERT INTO users_documents (username, documentname) VALUES ("claraboumansour", "doc2");
INSERT INTO users_documents (username, documentname) VALUES ("kinansolh", "doc2");
INSERT INTO users_documents (username, documentname) VALUES ("kinansolh", "doc3");
INSERT INTO users_documents (username, documentname) VALUES ("josephibrahim", "doc3");
INSERT INTO users_documents (username, documentname) VALUES ("josephibrahim", "doc4");




