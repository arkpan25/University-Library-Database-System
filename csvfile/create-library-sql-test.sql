CREATE DATABASE BookLibraryTest;
USE BookLibraryTest;
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
  card_no  char(8) not null,
  ssn     char(11) not null,
  fname    varchar(25) not null,
  lname    varchar(25) not null,
  address  varchar(100) not null,  
  phone    char(14), 
  CONSTRAINT pk_borrower primary key (card_no)
);

CREATE TABLE BOOK_LOANS (
  loan_id  int not null,
  isbn    character(10) not null,
  branch_id   int not null, 
  card_no  char(8) not null, 
  date_out    date not null,  
  due_date date  not null, 
  date_in date, 
  CONSTRAINT pk_book_loans primary key (loan_id),
  CONSTRAINT fk_book_loans_book foreign key(isbn) references BOOK(isbn),
  CONSTRAINT fk_book_loans_library_branch foreign key(branch_id) references LIBRARY_BRANCH(branch_id),
  CONSTRAINT fk_book_loans_borrower foreign key(card_no) references BORROWER(card_no)
);

CREATE TABLE  FINES(
 loan_id  int not null,
 fine_amt decimal(10,2) not null,
 paid int not null,
 CONSTRAINT pk_fines primary key(loan_id)
 #'CONSTRAINT fk_fines_book_loans foreign key (loan_id) references BOOK_LOANS(loan_id)'
);

INSERT INTO BOOK_LOANS VALUES('1','0195153448','1','1',current_date(),date_add(current_date(), interval 14 day), NULL);

INSERT INTO BOOK_LOANS VALUES('2','0195153448','1','1',current_date(),date_add(current_date(), interval 14 day), NULL);


select date_add(current_date(), interval 14 day);

UPDATE BOOK_COPIES set no_of_copies = '4' where book_id = '0195153448';

select count(*) from book_loans where isbn = '0195153448' and branch_id ='1' and date_in is null;



select no_of_copies from book_copies where book_id = '0195153448' and branch_id ='1';

select no_of_copies - count(*) from book_loans as l, book_copies as b where book_id = isbn and isbn = '0195153448' and b.branch_id ='1' and l.branch_id ='1' and date_in is null;

select count(*) as result from book_loans  where card_no = '1' and date_in is null ;

select book_id, branch_id, no_of_copies, checkout, no_of_copies - checkout from book_copies natural join (select isbn as book_id, branch_id, count(*) as checkout from book_loans where date_in is null group by isbn, branch_id) as ibc;

select bc.book_id, bc.branch_id, no_of_copies, checkout, no_of_copies - checkout as available from (book_copies as bc left join ((select isbn as book_id, branch_id, count(*) as checkout from book_loans where date_in is null group by isbn, branch_id) as ibc) on ibc.book_id = bc.book_id and ibc.branch_id = bc.branch_id) where bc.book_id = '0195153448' and bc.branch_id ='1';

select no_of_copies, checkout, no_of_copies - checkout as available from (book_copies as bc left join ((select isbn as book_id, branch_id, count(*) as checkout from book_loans where date_in is null group by isbn, branch_id) as ibc) on ibc.book_id = bc.book_id and ibc.branch_id = bc.branch_id) where bc.book_id = '0195153448' and bc.branch_id ='1';


select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn = '0393045218' and isbn = '0425176428' or isbn = '0679425608' ;

select isbn, title, author_name,branch_id,branch_name,no_of_copies 
from ((((book natural join book_authors) natural join 
( select book_id as isbn, branch_id, no_of_copies from book_copies) as bc) 
natural join library_branch) as bf)
left join (book_loans as bl) on bf.isbn = bl.isbn and bf.branch_id = bl.branch_id;


select be.no_of_copies, be.checkout, no_of_copies - checkout as available 

#'search book'
select distinct bd.isbn,bd.title, bd.author_name,bd.branch_id,bd.branch_name,bd.no_of_copies, checkout, no_of_copies - checkout as available from ((select isbn, title, author_name,branch_id,branch_name,no_of_copies 
from ((book natural join book_authors)  natural join 
( select book_id as isbn, branch_id, no_of_copies from book_copies) as bc) 
natural join library_branch) as bd )
left join ((select isbn, branch_id, count(*) as checkout from book_loans where date_in is null group by isbn, branch_id) as be) on bd.isbn = be.isbn and bd.branch_id = be.branch_id;



select isbn, title, author_name,branch_id,branch_name,no_of_copies 
from (((book natural join book_authors) natural join 
( select book_id as isbn, branch_id, no_of_copies from book_copies) as bc) 
natural join library_branch) ;

select isbn, title,branch_id from 
(book natural join ( select book_id as isbn, branch_id, no_of_copies from book_copies) as bc) 
natural join ; 

select isbn, title,author_name, branch_id, branch_name, no_of_copies from ((((select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn = '0393045218' or isbn = '0425176428' or isbn = '0679425608') as  b) inner join book_copies as bc on  bc.book_id = b.isbn)natural join library_branch);

select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn like '0393045218' ;

 select isbn, title,author_name from BOOK natural join BOOK_AUTHORS  where  isbn like '0393%42%' ;

 #'fine for book still out, fine have already existed, due date past'
 update book_loans as bl, Fines as f set f.fine_amt = DATEDIFF(current_date, bl.due_date) *0.25
 where f.loan_id = bl.loan_id and bl.date_in is null and f.paid = 0 
 and f.fine_amt <> DATEDIFF(current_date, bl.due_date) 
 and DATEDIFF(current_date, bl.due_date) > 0 ; 
 
  #'fine for book still out, fine have not  existed, due date past'
 Insert into fines  select  bl.loan_id, DATEDIFF(current_date, bl.due_date) *0.25, 0
 from  book_loans as bl left join Fines as f on bl.loan_id = f.loan_id
 where f.loan_id is null and bl.date_in is null and DATEDIFF(current_date, bl.due_date) > 0 ; 
 
 #'fine for book return, fine have already existed, due date past'
 update book_loans as bl, Fines as f set f.fine_amt = DATEDIFF(bl.date_in, bl.due_date) *0.25
 where f.loan_id = bl.loan_id  and bl.date_in is not null and f.paid = 0 
 and f.fine_amt <> DATEDIFF(bl.date_in, bl.due_date) *0.25 and DATEDIFF(bl.date_in, bl.due_date) > 0 ; 
 
  #'updatePayFineCmd'
 update book_loans as bl natural join  Fines as f 
 set f.paid = 0
 where f.loan_id = bl.loan_id  and bl.date_in is not null and f.paid = 1 and bl.card_no = 'ID000888'; 
 
  update book_loans as bl natural join  Fines as f 
 set f.paid = 1
 where f.loan_id = bl.loan_id  and bl.date_in is not null and f.paid = 0 and bl.card_no = 'ID000888'; 
 
 
  #'fine for book return, fine have not  existed, due date past'
  Insert into fines(loan_id, fine_amt, paid) 
 select  bl.loan_id, DATEDIFF(bl.date_in, bl.due_date) *0.25, 0
 from  book_loans as bl left join Fines as f on bl.loan_id = f.loan_id
 where  f.loan_id is null and bl.date_in is not null and DATEDIFF(bl.date_in, bl.due_date) > 0 ; 
 
 
 #'display all fines by card_no, filter out previously paid fines'
 select card_no, sum(fine_amt) as sum
 from fines natural join book_loans
 where paid = 0
 group by card_no;
 
 #'check allow payment fine'
 select count(*) as sum
 from fines natural join book_loans
 where paid = 0 and date_in is null and card_no = 'ID000888'
 group by card_no;
 
#0
 select count(*) as __num from book_loans  where card_no = 'ID000001' and date_in is null ;
 
 #Empty set
  select count(*) as sum
 from fines natural join book_loans
 where paid = 0 and date_in is null and card_no = 'ID000888'
 group by card_no;
 
 update  Fines as f  set fine_amt = 0.1 ;
 
  insert into  Fines values(6,4,0);
  delete from book_loans;
  delete from fines;
  
  insert into book_loans values('7',	'0060812508',	'1',	'ID000360', '2015-09-25',	'2015-10-08',	'2015-10-20');
  
  insert into book_loans values('1',	'0812932773',	'5',	'ID000887',	'2015-09-24',	'2015-10-07',	'2015-10-07');
  
  select * from book_loans;    
  select * from fines;
  
  
 CREATE TABLE  FINES(
 loan_id  int not null,
 fine_amt decimal(10,2) not null,
 paid int not null,
 CONSTRAINT pk_fines primary key(loan_id)
 #'CONSTRAINT fk_fines_book_loans foreign key (loan_id) references BOOK_LOANS(loan_id)'
);
 
 select * from BOOK;
 select * from BOOK_AUTHORS;
 select * from LIBRARY_BRANCH;
 select * from BOOK_COPIES;
 select * from BORROWER;