# MathWhiz-_Lab-2-_IT24018





# Jahidul Islam – MATH WHIZ

## Project Description

MATH WHIZ is a web-based mathematical game developed using Java Servlet and JDBC. Players answer four mathematical questions based on prime numbers, modulus operations, and trigonometry. The application calculates scores automatically and stores player results in a MySQL database.

## Project Objectives

- Develop a mathematical game using Java.
- Implement reusable mathematical methods.
- Use Java Servlet for server-side processing.
- Connect Java applications with MySQL using JDBC.
- Store player scores in a database.
- Display the top 10 scores in a leaderboard.

## Technologies Used

- Java
- Java Servlet
- JSP
- JDBC
- MySQL
- Apache Tomcat
- HTML
- CSS
- IntelliJ IDEA

## Software Requirements

- JDK 21
- IntelliJ IDEA
- Apache Tomcat 10
- MySQL Server
- MySQL Connector/J
- Web Browser

## Database Configuration

Database Name: `math_whiz_db`

Table Name: `game_scores`

Database Connection:

- URL: `jdbc:mysql://localhost:3306/math_whiz_db`
- Username: `root`
- Password: Your MySQL password

## Database Creation SQL

```sql
CREATE DATABASE math_whiz_db;

USE math_whiz_db;

CREATE TABLE game_scores (
    id INT PRIMARY KEY AUTO_INCREMENT,
    player_name VARCHAR(100),
    score INT,
    difficulty VARCHAR(20),
    played_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## How to Run the Project

1. Clone or download the GitHub repository.
2. Open the project in IntelliJ IDEA.
3. Configure Apache Tomcat Server.
4. Create the MySQL database using the provided SQL file.
5. Configure the database username and password.
6. Add MySQL Connector/J to the project.
7. Run the project using Tomcat.
8. Open the application in your browser.

## Game Rules and Scoring System

The game contains four mathematical questions.

| Question | Topic | Marks |
|---|---|---:|
| Q1 | Prime Number | 2 |
| Q2 | Modulus Operation | 2 |
| Q3 | Trigonometry | 3 |
| Q4 | Trigonometry | 3 |
| **Total** | **All Questions** | **10** |

Difficulty Level: NORMAL

Maximum Score: 10

## Mathematical Functions

- `isPrime(int number)` – Checks whether a number is prime.
- `calculateMod(int a, int b)` – Calculates the remainder.
- `calculateSin(double degree)` – Calculates sine.
- `calculateCos(double degree)` – Calculates cosine.
- `calculateTan(double degree)` – Calculates tangent.

## Leaderboard

The application displays the top 10 players according to their scores. Higher scores appear first.

## Application Screenshots

### 1. Welcome Page

![Welcome Page](screenshots/home.png)

### 2. Game Page

![Game Page](screenshots/game.png)

### 3. Result Page

![Result Page](screenshots/result.png)

### 4. Leaderboard

![Leaderboard](screenshots/leaderboard.png)

## GitHub Project Link

Add your GitHub repository link here.

## Developer

**Jahidul Islam**

Department of Information and Communication Technology (ICT)

Mawlana Bhashani Science and Technology University (MBSTU)
