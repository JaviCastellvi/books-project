drop database if exists booksdb;
create database booksdb;
 
use booksdb;
 
create table users (
	username	varchar(20) not null primary key,
	userpass	char(32) not null,
	name		varchar(70) not null,
	email		varchar(255) not null
);
 
create table user_roles (
	username			varchar(20) not null,
	rolename 			varchar(20) not null,
	foreign key(username) references users(username) on delete cascade,
	primary key (username, rolename)
);


create table autores (
	idautor int not null auto_increment primary key,
	autor	 varchar(70)
);

create table libro (
	idlibro			int not null auto_increment primary key,
	titulo	 			varchar(100) not null,
	autor	 			varchar(70) not null,
	idioma				varchar(100) not null,
	edicion				varchar(100) not null,
	fecha_edicion		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	fecha_impresion		timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	editorial			varchar(100) not null,
	idautor				int not null,
	foreign key(idautor) 	references autores(idautor)
		
);

create table critica (

	id 					int not null auto_increment primary key,
	username			varchar(20) not null,
	name				varchar(70) not null,
	libro				integer not null,
	texto				varchar(500) not null,
	Last_modified timestamp default current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	foreign key(username) references users(username) on delete cascade,
	foreign key(libro) references libro(idlibro) on delete cascade
);






