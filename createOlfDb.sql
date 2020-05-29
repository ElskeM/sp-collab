connect 'jdbc:derby://localhost:50000/OlfDb; create=true';

drop table tblorderrows;
drop table tblcustomerorder;
drop table tblcustomer;
drop table tblarticle;

create table tblarticle (
    artnr int,
    description varchar(255),
    name varchar(255),
    price double not null,
    stock int not null,
    primary key(artnr)
);

create table tblcustomer (
    cnr int,
    address varchar(255),
    city varchar(255),
    discount double not null,
    firstname varchar(255),
    lastname varchar(255),
    zipcode varchar(255),
    primary key(cnr)
);

create table tblcustomerorder (
    ordernr int,
    orderdate date,
    dispatchDate date,
    total double not null with default 0,
    cnr int references tblcustomer(cnr),
    primary key(ordernr)
);

create table tblorderrows (
    ordernr int references tblcustomerorder(ordernr),
    antal int,
    artnr int references tblarticle(artnr),
    primary key (ordernr,artnr)
);

insert into tblarticle (artnr, description, name, price, stock) values
(10000, 'En rejäl bult', 'Bult',15,35),
(10001, 'Passar till art:10000', 'Mutter',15,200),
(10002, 'Svensk Mora', 'Kniv',25,250),
(10003, 'Gammaldags vedklyv', 'Yxa',65,35),
(10004, 'Jämnar ut träytor', 'Hyvel',45,20),
(10005, 'Standardskruv', 'Skruv',1,100),
(10006, 'Extra lång spik', 'Spik',1,300);

insert into tblcustomer (cnr, firstname, lastname, address, zipcode, city, discount) values
(111,'Eric','Johnson', 'Gitarrgatan 1', '413 44', 'Göteborg',0.5),
(112,'Steve','Morse', 'Gitarrgatan 2', '413 66', 'Stockholm',0.2),
(116,'John','Petrucci', 'Gibsongatan 12', '435 71', 'Göteborg',0.2);

insert into tblcustomerorder (ordernr, cnr, orderdate, dispatchDate) values
(2,116,'2006-01-01','2006-01-01'),
(3,116,'2006-01-06','2006-01-06'),
(4,112,'2006-01-10','2006-01-10'),
(5,111,'2006-01-14','2006-01-14'),
(6,112,'2006-01-22','2006-01-19'),
(8,116,'2006-01-01','2006-01-01'),
(9,116,'2006-03-23','2006-03-23');

insert into tblorderrows (ordernr, artnr, antal) values
(2,10000,12),
(3,10000,50),
(3,10004,1),
(4,10002,2),
(5,10005,3),
(5,10002,2),
(6,10004,3),
(6,10002,2),
(2,10002,1),
(8,10004,2),
(8,10002,3),
(9,10005,2),
(9,10001,20);



