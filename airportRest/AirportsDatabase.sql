--Table definitions.


CREATE TABLE states(
	pk_idState int not null AUTO_INCREMENT,
	name varchar(100) not null,
	isocode3 varchar(3),
	primary key(pk_idState)
);

CREATE TABLE provinces(
	pk_idProvince int not null AUTO_INCREMENT,
	name varchar(100) not null,
	fk_idState int not null,
	primary key(pk_idProvince),
	foreign key(fk_idState) references states(pk_idState)
);

CREATE TABLE cities(
	pk_idCity int not null AUTO_INCREMENT,
	name varchar(100) not null,
	fk_idProvince int not null,
	primary key(pk_idCity),
	foreign key(fk_idProvince) references provinces(pk_idProvince)
);

CREATE TABLE airports(
	pk_idAirport int not null AUTO_INCREMENT,
	name varchar(100) not null,
	IATA_code varchar(3) not null UNIQUE,
	fk_idCity int not null,
	primary key(pk_idAirport),
	foreign key(fk_idCity) references cities(pk_idCity)
);

CREATE TABLE cabins(
	pk_idCabin int not null AUTO_INCREMENT,
	name varchar(100) not null,
	primary key(pk_idCabin)
);

CREATE TABLE routes(
	pk_idRoute int not null AUTO_INCREMENT,
	fk_departureAirport int not null,
	fk_arrivalAirport int not null,
	primary key(pk_idRoute),
	foreign key(fk_departureAirport) references airports(pk_idAirport),
	foreign key(fk_arrivalAirport) references airports(pk_idAirport)
);

CREATE TABLE pricesPerRoutePerCabin(
	pk_idPrice int not null AUTO_INCREMENT,
	fk_idRoute int not null,
	fk_idCabin int not null,
	price float(11,2) not null default 0.0,
	vigencyFrom date not null,
	vigencyTo date not null,
	primary key(pk_idPrice),
	foreign key(fk_idRoute) references routes(pk_idRoute),
	foreign key(fk_idCabin) references cabins(pk_idCabin)
);





