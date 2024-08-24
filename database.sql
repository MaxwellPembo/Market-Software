# Author : Maxwell Pembo
# Date : 03-29-2023
# 
# This File is used to create this projects database and fill
# it with data/test cases.

-- use mpembo;
 
Drop table if exists InvoiceItem; 
Drop table if exists Invoice;
Drop table if exists Item;
Drop table if exists Store;
Drop table if exists Email;
Drop table if exists Person;
Drop table if exists Address;


create table Address(
addressId int not null primary key auto_increment,
street varchar(25),
city varchar(15),
state varchar(2),
zip varchar(6),
country varchar (2)
);

 create table Person(
 personId int not null primary key auto_increment,
 personCode varchar(15),
 firstName varchar(15),
 lastName varchar(15),
 addressId int not null,
 foreign key (addressId) references Address(addressId)
 );
  
create table Email(
emailId int not null primary key auto_increment,
email varchar(30),
personId int not null,
foreign key (personId) references Person(personId)
);

create table Store(
storeId int not null primary key auto_increment,
storeCode varchar(10),
personId int not null,
addressId int not null,
foreign key (addressId) references Address(addressId),
foreign key (personId) references Person(personId)
);

create table Item(
itemId int not null primary key auto_increment,
code varchar (15),
name varchar (30),
eps char,
model varchar(15),
unit varchar(15),
untitprice double,
hourlyRate double
);

create table Invoice(
invoiceId int not null primary key auto_increment,
invoiceCode varchar(10),
date varchar(15),
coustomerId int not null,
salespersonId int not null,
addressId int not null,
foreign key (coustomerId) references Person(personId),
foreign key (salespersonId) references Person(personId),
foreign key (addressId) references Address(addressId)
);


create table InvoiceItem(
invoiceItemId int not null primary key auto_increment,
pl char,
startDate varchar(15),
endDate varchar(15),
monthyfee int,
purchasePrice int,
quantityPurchased int,
hoursBilled double,
invoiceId int not null,
itemId int not null,
foreign key (itemId) references Item(itemId),
foreign key (invoiceId) references Invoice(invoiceId)
);


Insert into Address(addressId, street, city, state, zip, country) values
	(1, "4016 Driftwood Dr", "Grand Island", "NE", "68803", "US"),
    (2, "5803 Jhonec Rd", "Grand Island", "NE", "68808","US"),
    (3, "6284 Megadome Dr", "Martial Town", "IA", "43275","US"),
    (4, "6845 Utraville Av", "Lincoln", "NE", "76543", "US"),
    (5, "7440 Deirs Av", "Grand Island", "NE", "16426","US"),
    (96, "0000 NowhereVille", "Clevland", "OI","61842", "US"),
    (100, "3984 HueMongus Rd", "Los Angeles", "CA","16425", "US"),
    (200, "3782 Amongus Sus Rd", "Omaha", "NE","94675", "US");
    
Insert into Person(personId, personCode, firstName, lastName, addressId) values
	(1, "D8dsr", "Maxwell", "Pembo", 1),
    (2, "H7Ger", "Jackson", "Pembo", 1),
    (3, "H5Se6", "Ray", "Ericson", 2),
    (4, "H7D42", "Levi", "Ames", 5),
    (8, "I89G6", "Chino", "Moreno", 100),
    (9, "H47De", "Light", "Yagimi", 96),
    (80, "G5K4h", "Askua", "Langley", 1);
    
Insert into Email(emailId, email, personId) values
	(1, "middlemaxman@gmail.com",1),
    (2, "mpembo2@unl.edu",1),
    (3, "narutoLVR@gmail.com",3),
    (4, "urmother@yahoo.com",8),
    (5, "unicornman@hotmail.com",80);
    
Insert into Store(storeId, storeCode, personId, addressId) values
	(1,"HEESL",1, 1),
    (2,"GEGEG",4,100),
    (3,"UG834",9,200),
    (4,"HEODE",2,3),
    (6,"URGES",8,96);
    
Insert into Item(itemId, code, name, eps, model, unit, untitprice, hourlyRate) values
	(1, "74Dke", "PowerHoe", "E", "MegaHoe", null, null, null),
    (2, "83940", "Tractor", "E", "JhoneDearist", null, null, null),
    (3, "ENJS9", "Truck", "E", "Bord F-360", null, null, null),
    (4, "3925d", "Rake", "E", "PowerRaker 5000", null, null, null),
    (5, "HI903", "Corn seeds", "P", null, "Packet", 30, null),
	(6, "72932", "Barn Wood", "P", null, "Plank", 10, null),
	(7, "26494", "Fertilizer", "P", null, "bag", 25, null),
	(8, "39245", "Hog Wash", "P", null, "Bottle", 50, null),
	(9, "28340", "Barn Construction", "S", null, null, null, 150),
	(10, "73246", "Pig cuddling", "S", null, null, null, 10),
    (11, "32740", "Barn security", "S", null, null, null, 20);
    
    
Insert into Invoice(invoiceId, invoiceCode, date, coustomerId, salespersonId, addressId) values
	(1, "INV001", "2023-12-01",4, 1, 1),
    (2, "INV002", "2023-2-28",3, 9, 100),
    (3, "INV003", "2022-11-02",80, 2, 96),
    (4, "INV004", "2022-12-25",8, 1, 1);
    
    
Insert into InvoiceItem(invoiceItemId, pl, startDate, endDate, monthyfee,
	purchasePrice, quantityPurchased, hoursBilled, invoiceId, itemId) values
		(1,"P",null,null,null, 15000, null, null, 1, 3),
        (2,"P",null,null,null, 2000, null, null, 1, 1),
        (3,"L","2022-2-01","2022-08-01",1000, null, null, null, 2, 4),
        (4,"L","2022-2-01","2022-08-01",1000, null, null, null, 2, 2),
        (5,null,null,null,null, null, 100, null, 3, 6),
        (6,null,null,null,null, null, 35, null, 3, 8),
        (7,null,null,null,null, null, 120, null, 1, 7),
        (8,null,null,null,null, null, 28, null, 4, 5),
        (9,null,null,null,null, null, null, 8, 4, 10),
        (10,null,null,null,null, null, null, 4, 4, 11),
        (11,null,null,null,null, null, null, 5.5, 2, 9);




