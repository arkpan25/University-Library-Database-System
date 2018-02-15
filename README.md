# University-Library-Database-System
# Library Mangement System by Fangzhou
# Description
This SQL programming project involves the creation of a database host application that
interfaces with a backend SQL database implementing a Library Management System. Users of
the system are understood to be librarians (not book borrowers).

Following is a brief description about the working of the system:

- Search a Book - Allows the user to search any book given any combination of Book id and/or Book Title and /or Book Author. 
- Checkout a Book ¨C Allows a user to check-out the book from a branch based on its availability and book borrowers credibility. 
- Checkin a Book - Allows a user to check-in the book. This feature first searches for all the book loans the borrower has taken which are not checked-in yet and then allows the user to check in the selected the book. 
- Add New Borrower - Allows the user to add a new borrower to the library loan system. 
- Fine - This feature allows two types of fine computation. 

## Installation
we could import this file system into eclipse. 
also we should add mysql-connector-java-5.1.45-bin.jar as referenced Libraries.
Then we could select LibraryView.java to compile and build in eclipse.
Then we could export jar by select export type as java. Runable JAR file.

It should include any technical dependencies (language, frameworks, platform, OS,
software libraries, software versions, etc.).
language : java 
compile : JavaSE-1.8 
frameworks : Swing 
platform : windows 10, Linux 
software libraries : mysql-connector-java-5.1.38-bin.jar 
database : MySQL

## design architecture
![](/class digram layer.png)

## design mode
- 1. model

The model directly manages the data, logic and rules of the application. The model stores data
that is retrieved according to commands from the controller and displayed in the view.
We could divide this system into nine function modules: BookSearch, BookCheckOut,
BookCheckIn, BorrowerManagement, Fines, SqlGenerator, DataBaseController ,
DataBaseBuilder, DataReader. The first five model could directly interact with LibraryView.
BookSearch is responsible for Searching book.
BookCheckOut is responsible for checking out book.
BookCheckIn is responsible for checking in book.
BorrowerManagement is responsible for adding new borrower.
Fines is responsible for make a payment for particular user and update fines.
DataBaseController provide api to access and retrieve data from MySql.
SqlGenerator is responsible for providing sql statement for other models.
DataBaseBuilder is responsible for parse information for inserting value into tables of database.
DataReader is responsible for retreiving information from csv files.
- 2. View

A view can be any output representation of information, A view generates an output presentation
to the user based on changes in the model. There are five views in our system.
BookCheckOutJPanel, BookCheckInJPanel, BorrowerMangementJPanel, FinesJPanel,
BookSearchJPanel. And LibraryView composites all views. Users interact on these views, they
send event or message to Librarycontroller, indirectly call method in model layer.
- 3. Controller

The LibraryController accepts input and converts it to commands for the model or view. We use
controller in our system. This could make our model reusable.

## concerns about this project
**Why not keep SqlStatement in BookSearch, BookCheckOut, BookCheckIn, BorrowerManagement, Fines ?**

If SqlStatements is included in these five models, it could make them care about more unrelated
things to becoming lower cohesive. According to GRASP pattern, we could use pure
Fabrication to create new class SqlGenerator, which could manage all the sql statement make
BookSearch, BookCheckOut, BookCheckIn, BorrowerManagement, Fines keeping high
cohesive. In this case, it make code manageable, If database schema changing, we could just
looking for codes in SqlGenerator. Otherwise, we have to look through the entire code of our
project.

**Why not keep SqlStatement in databaseController?**

If we keep SqlStatement in databaseController, It could make databaseController be very busy
and make the system not easy to maintain. If we divide it into databaseController and
SqlGenerator, then we could easily change MySql into any other Database, because most of sql
statement in different database is uniform.





