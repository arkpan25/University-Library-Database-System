CREATE DATABASE BookLibrary;
USE BookLibrary;

CREATE TABLE BOOK (
  isbn        character(10) not null,
  Title      varchar(300) not null, 
  CONSTRAINT pk_Book primary key (isbn)
);

CREATE TABLE BOOK_AUTHORS (
  isbn        character(10) not null,
  Author_name      varchar(150) not null, 
  CONSTRAINT pk_book_authors primary key (isbn, Author_name),
  CONSTRAINT fk_book_authors_book foreign key(isbn) references BOOK(isbn)
);



CREATE TABLE BORROWER (
  card_no  int not null,
  fname    varchar(25) not null,
  lname    varchar(25) not null,
  address  varchar(100) not null,  
  phone    char(14) not null, 
  CONSTRAINT pk_borrower primary key (card_no)
);

select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn = '0393045218' and isbn = '0425176428' or isbn = '0679425608' ;



select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn like '0393045218' ;

 select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn like '0393%42%' ;
 
 select * from BOOK;
 select * from BOOK_AUTHORS;
 
 select * from BORROWER;