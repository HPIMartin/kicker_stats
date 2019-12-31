create table players
(
   id UUID primary key NOT null,
   name varchar (255) NOT null,
   mail varchar (255) NOT null,
   password varchar (255) NOT null,
   isAdmin boolean NOT null
);

INSERT INTO players (id, name, mail, password, isAdmin) VALUES ("0000000-0000-0000-0000-000000000001", "Super Admin", "admin@schoenberg.dev", "28d977e1f53e5cb1250ddc22a8472bb519a6735e81fc9f28927a2465afa84d3f", true) ON CONFLICT DO NOTHING;
