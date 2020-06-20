## Warsztaty_2

# Introduction
Warsztaty_2 is a next project which was created during Coders Lab lessons.
The project simulates application to manage programming school.
It implements the design pattern Data Access Object to provide communication between users and database.

# Before start
Here you can find few steps which are necessary to run programs properly.
1. Before you start using the application, you have to create database:
    ```sql
    CREATE DATABASE warsztaty2
      CHARACTER SET utf8mb4
      COLLATE utf8mb4_unicode_ci;
    ``` 
2. After that you have to create tables in the following order:
   * user_groups - table which keeps information about student's groups 
    ```sql
    CREATE TABLE user_groups (
        id int auto_increment primary key,
        name varchar(255) not null
    );
   ```
   * users - table which keeps information about students
   ```sql
   CREATE TABLE users (
       id int auto_increment primary key,
       username varchar(255) not null,
       email varchar(255) unique not null,
       password varchar(255) not null,
       user_groups int null,
       foreign key (user_groups) references user_groups(id)
       on update cascade on delete cascade
   );
   ```
   
   * exercises - table which keeps information about exercises which are homeworks for students
   ```sql
   CREATE TABLE exercises (
       id int auto_increment primary key,
       title varchar(255) not null,
       description text not null
   );
   ```
   
   * solutions - table which keeps information about solutions given by students
    ```sql
   CREATE TABLE solutions (
       id int auto_increment primary key,
       created datetime not null,
       updated datetime,
       description text,
       exercise_id int,
       user_id int,
       foreign key (exercise_id) references exercises(id),
       foreign key (user_id) references users(id)
   );
    ```

3. At the end you have to set attributes in **DBUtil** class:
    ```java
   private static final String DB_USER = ""; //Username to get access to database
   private static final String DB_PASS = ""; //Password to your database
    ```
	
#Description

Each class is a kind of administrative programme and delivers particular functionality:
* **Program 1** allow to add/edit/delete students
* **Program 2** help to add/edit/delete exercises for students
* **Program 3** allow to add/edit/delete groups 
* **Program 4** allow to add/view exercises
* **UserProgram** allow to add/view solutions which belong to particular student 
(his/her email must be given as an argument in function *main()* in your console or in your IDE before run the program). 