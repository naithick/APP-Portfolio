# Library Management System

## Description
A simple console-based Library Management System using Java and MySQL. You can add books, view books, issue books, return books, and delete books.

## Features
- Add new books
- View all books
- Issue books
- Return books
- Delete books
- Database using MySQL

## Tech Stack
- Java
- MySQL
- JDBC

## Setup

1. Create database:
```sql
CREATE DATABASE library_db;
```

2. Update password in `DatabaseManager.java`:
```java
private static final String PASS = "your_password";
```

3. Download MySQL Connector JAR and add to classpath

## How to Run

```bash
javac -cp ".;mysql-connector-java-8.0.33.jar" src/*.java
java -cp ".;src;mysql-connector-java-8.0.33.jar" LibraryManagementSystem
```

## What I Learned
- JDBC connectivity
- SQL operations (INSERT, SELECT, UPDATE, DELETE)
- PreparedStatement
- Exception handling
- Scanner for user input
