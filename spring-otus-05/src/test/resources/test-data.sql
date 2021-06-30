select 1;
insert into GENR (ID, NAME) values
(1, 'GENR_1'),
(2, 'GENR_2'),
(3, 'GENR_3');


insert into AUTHORS (id, name) values
(1, 'Alexander Pushkin'),
(2, 'Mikhail Bulgakov'),
(3, 'Ilya Petrov');

insert into BOOKS (id, name, author_id) values
 (1, 'book', 1),
 (2, 'book2', 2),
 (3, 'book3', 3);
insert into BOOK_GENR(book_id, genre_id) values
(1, 1),
(2, 1),
(2, 2);
