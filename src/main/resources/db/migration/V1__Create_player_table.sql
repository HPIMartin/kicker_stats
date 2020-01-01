create table players
(
   id UUID primary key NOT NULL,
   name varchar (255) NOT NULL,
   mail varchar (255) NOT NULL UNIQUE,
   password varchar (255) NOT NULL,
   isAdmin boolean NOT NULL
);

INSERT INTO players (id, name, mail, password, isAdmin) VALUES ("0000000-0000-0000-0000-000000000001", "Super Admin", "admin@schoenberg.dev", "f38601176e77ab5945e54cbe71d7b18e7e8fbea024e895d427c9e1927e640037", true) ON CONFLICT DO NOTHING;
