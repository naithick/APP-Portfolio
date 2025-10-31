# Student Registration Form

## Description
A GUI application for student registration using Java Swing and MySQL. It has a form to enter student details and save them to database.

## Features
- Student registration form with GUI
- Email and phone validation
- Radio buttons for gender
- Dropdown for course selection
- Save data to MySQL
- View all students in table

## Tech Stack
- Java Swing
- MySQL
- JDBC

## Setup

1. Create database:
```sql
CREATE DATABASE student_db;
```

2. Update password in `StudentDatabase.java`:
```java
private static final String PASS = "your_password";
```

3. Download MySQL Connector JAR

## How to Run

```bash
javac -cp ".;mysql-connector-java-8.0.33.jar" src/*.java
java -cp ".;src;mysql-connector-java-8.0.33.jar" StudentRegistrationForm
```

## Form Fields
- Name
- Email (must be valid format)
- Phone (10 digits)
- Date of Birth
- Gender (Male/Female/Other)
- Course (dropdown)

## What I Learned
- Java Swing GUI components
- GridBagLayout for form layout
- JTable for displaying data
- Input validation using regex
- JDBC database operations
- Event handling in Java
