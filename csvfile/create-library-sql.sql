CREATE DATABASE BookLibrary;
USE BookLibrary;

CREATE TABLE BOOK (
  isbn        character(10) not null,
  Title      varchar(250) not null, 
  CONSTRAINT pk_Book primary key (isbn)
);

CREATE TABLE BOOK_AUTHORS (
  isbn        character(10) not null,
  Author_name      varchar(100) not null, 
  CONSTRAINT pk_book_authors primary key (isbn, Author_name),
  CONSTRAINT fk_book_authors_book foreign key(isbn) references BOOK(isbn)
);

CREATE TABLE LIBRARY_BRANCH (
  branch_id        int not null,
  branch_name      varchar(30) not null, 
  address     varchar(50) not null, 
  CONSTRAINT pk_library_branch primary key (branch_id)
);

CREATE TABLE BOOK_COPIES (
  book_id        character(10) not null,
  branch_id      int not null, 
  no_of_copies int ,
  CONSTRAINT pk_book_copies primary key (book_id, branch_id),
  CONSTRAINT fk_book_copies_book foreign key(book_id) references BOOK(isbn),
  CONSTRAINT fk_book_copies_library_branch foreign key(branch_id) references LIBRARY_BRANCH(branch_id)
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


select isbn, title,author_name, branch_id, branch_name, no_of_copies from ((((select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn = '0393045218' or isbn = '0425176428' or isbn = '0679425608') as  b) inner join book_copies as bc on  bc.book_id = b.isbn)natural join library_branch);

select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn like '0393045218' ;

 select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn like '0393%42%' ;
 
 select * from BOOK;
 select * from BOOK_AUTHORS;
 select * from LIBRARY_BRANCH;
 select * from BOOK_COPIES;
 select * from BORROWER;
 
 select  count(*), current_time() from BOOK_COPIES;
  select count(*) from BORROWER;
   select isbn, author_name from BOOK_AUTHORS  where isbn='1569871213';