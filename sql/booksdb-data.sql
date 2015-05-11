source booksdb-schema.sql;

insert into users values('admin', MD5('admin'), 'Administrador', 'admin@acme.com');
insert into user_roles values ('admin', 'administrador');
insert into users values('test', MD5('test'), 'Tester', 'test@acme.com');
insert into user_roles values ('test', 'registrado');


insert into autores (autor)  values ('Santigago Posteguillo');
insert into autores (autor)  values ('Marti Gironell');
insert into autores (autor)  values ('John Ronald Reuel Tolkien');
insert into autores (autor)  values ('George R.R. Martin');
insert into autores (autor)  values ('Christian Jacq');


INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('Circo Maximo', 'Santigago Posteguillo', 'Castellano', 'Primera', 'Planeta',1);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('El Primer Heroi', 'Marti Gironell', 'Catalan', 'Primera', 'Ediciones B, S.A.',2);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('Los Asesinos Del Emperador', 'Santigago Posteguillo', 'Castellano', 'Primera', 'Planeta',1);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('El Hobbit: La Desolacion De Smaug', 'John Ronald Reuel Tolkien', 'Castellano', 'Primera', 'Minotauro',3);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('Juego De Tronos Vol.1', 'George R.R. Martin', 'Castellano', 'Primera', 'GIGAMESH OMNIUM',4);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('Juego De Tronos Vol.2', 'George R.R. Martin', 'Castellano', 'Segunda', 'GIGAMESH OMNIUM',4);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('Juego De Tronos Vol.3', 'George R.R. Martin', 'Castellano', 'Primera', 'GIGAMESH OMNIUM',4);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('Juego De Tronos Vol.4', 'George R.R. Martin', 'Castellano', 'Primera', 'GIGAMESH OMNIUM',4);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('Juego De Tronos Vol.5', 'Christian Jacq', 'Ingles', 'Primera', 'GIGAMESH OMNIUM',5);
INSERT INTO libro(titulo, autor, idioma, edicion, editorial, idautor) VALUES('El Origen De Los Dioses', 'Santigago Posteguillo','Castellano', 'Primera', 'MR',1);


INSERT INTO critica(username, name, libro, texto) VALUES('admin', 'Administrador', 1, 'Circo M�ximo es la historia de Trajano y su gobierno, guerras y traiciones, lealtades insobornables e historias de amor imposibles. Hay una vestal, un juicio, inocentes acusados, un abogado brillante, mensajes cifrados, fortalezas inexpugnables, dos aurigas rivales, gladiadores y tres carreras de cuadrigas. Hay un caballo especial, diferente a todos, leyes antiguas olvidadas, sacrificios humanos, amargura y terror, pero tambi�n destellos de nobleza y esperanza.');
INSERT INTO critica(username, name, libro, texto) VALUES('test', 'Tester', 2, 'El llibre m�s esperat de Mart� Gironell. Una aventura fundacional plena de reptes i amenaces, en la qual el lector descobrir� un m�n nou i fascinant.  Fa m�s de cinc mil anys, un home va ser capa� d�anar m�s enll� de la seva pr�pia terra. Ynats� �s escollit pels d�us per protegir el seu poblat i trobar el remei a un mal que assola la seva comunitat, el Clan dels Cavalls.');
INSERT INTO critica(username, name, libro, texto) VALUES('test', 'Tester', 3, '18 de septiembre del a�o 96 d. C. Un plan perfecto. Un d�a dise�ado para escribir la Historia, pero cuando todo sale mal la Historia ya no se escribe? se improvisa: una guerra civil, las fieras del Coliseo, la guardia pretoriana, traiciones, envenenamientos, delatores y poetas, combates en la arena, ejecuciones sumar�simas, el �ltimo disc�pulo de Cristo, el ascenso y ca�da de una dinast�a imperial, locura y esperanza, la erupci�n de Vesubio, un pu�ado de gladiadores.');
INSERT INTO critica(username, name, libro, texto) VALUES('admin', 'Administrador', 4, 'El tercero de los vol�menes escritos, ilustrados y dise�ados por los equipos de Weta y el departamento art�stico de El Hobbit incluye m�s de mil im�genes del rodaje, dise�os digitales y fotogramas de la pel�cula adem�s de comentarios personales de los artistas, los directores de la pel�cula y el reparto que nos revelar�n las historias que se esconden detr�s de las c�maras.');
INSERT INTO critica(username, name, libro, texto) VALUES('admin', 'Administrador', 5, 'En un mundo cuyas estaciones pueden durar decenios y en el que retazos de una magia inmemorial y olvidada surgen en los rincones m�s sombr�os y maravillosos, la traici�n y la lealtad, la compasi�n y la sed de venganza, el amor y el poder hacen del juego de tronos una poderosa trampa que atrapar� en sus fauces a los personajes... y al lector.');
INSERT INTO critica(username, name, libro, texto) VALUES('test', 'Tester', 6, 'En un mundo cuyas estaciones pueden durar decenios y en el que retazos de una magia inmemorial y olvidada surgen en los rincones m�s sombr�os y maravillosos, la traici�n y la lealtad, la compasi�n y la sed de venganza, el amor y el poder hacen del juego de tronos una poderosa trampa que atrapar� en sus fauces a los personajes... y al lector.');
INSERT INTO critica(username, name, libro, texto) VALUES('test', 'Tester', 7, 'Canci�n de hielo y fuego: Libro cuarto. La novela r�o m�s espectacular jam�s escrita. Mientras los vientos del oto�o desnudan los �rboles, las �ltimas cosechas se pudren en los pocos campos que no han sido devastados por la guerra, y por los r�os te�idos de rojo bajan cad�veres de todos los blasones y estirpes. Y aunque casi todo Poniente yace extenuado, en diversos rincones florecen nuevas e inquietantes intrigas que ans�an nutrirse de los despojos de un reino moribundo.');
INSERT INTO critica(username, name, libro, texto) VALUES('admin', 'Administrador', 8, 'Canci�n de hielo y fuego: Libro quinto La novela r�o m�s espectacular jam�s escrita Daenerys Targaryen intenta mitigar el rastro de sangre y fuego que dej� en las Ciudades Libres al erradicar la esclavitud en Meereen. Mientras, un enano parricida, un pr�ncipe de inc�gnito, un capit�n implacable y un enigm�tico caballero acuden a la llamada de los dragones desde el otro lado del mar Angosto, ajenos al peligro que se cierne sobre el Norte, y que solo las menguadas huestes.');
INSERT INTO critica(username, name, libro, texto) VALUES('test', 'Tester', 9, 'Canci�n de hielo y fuego: Libro quinto La novela r�o m�s espectacular jam�s escrita Daenerys Targaryen intenta mitigar el rastro de sangre y fuego que dej� en las Ciudades Libres al erradicar la esclavitud en Meereen. Mientras, un enano parricida, un pr�ncipe de inc�gnito, un capit�n implacable y un enigm�tico caballero acuden a la llamada de los dragones desde el otro lado del mar Angosto, ajenos al peligro que se cierne sobre el Norte, y que solo las menguadas huestes.');
INSERT INTO critica(username, name, libro, texto) VALUES('admin', 'Administrador', 10, 'Rebasa las barreras de los g�neros como si nunca hubieran existido: Danza de dragones marca su consagraci�n definitiva entre los m�s grandes creadores de la historia de la literatura, m�s all� de cualquier distinci�nde etiquetas.');


