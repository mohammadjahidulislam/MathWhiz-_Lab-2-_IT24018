# JAHID – MATH WHIZ

A web-based mathematical game built with **Java Servlet, JSP and JDBC (MySQL)**.
The player answers four questions (prime number, modulus, two trigonometry questions) and the score is saved in a database. A Top 10 leaderboard is shown.

## Project Objectives
- Write reusable Java methods for mathematical calculations.
- Apply conditional logic to calculate the score (10 marks).
- Understand Servlet request/response processing.
- Use JDBC with `PreparedStatement` to save and read data.
- Display a sorted Top 10 leaderboard.

## Technologies Used
- Java 17+, Servlet API (Jakarta EE 9), JSP
- JDBC, MySQL 8
- Maven, Jetty 11 (`jetty-maven-plugin`)
- HTML, CSS

## Software Requirements
- JDK 17 or newer
- IntelliJ IDEA (Maven support included)
- MySQL Server 8 (running on `localhost:3306`)

## Project Structure
```
MathWhiz/
├── src/main/java/
│   ├── controller/GameServlet.java
│   ├── model/GameScore.java, Quiz.java, QuestionResult.java
│   ├── service/MathService.java
│   ├── dao/ScoreDAO.java, DBUtil.java
│   └── util/Html.java
├── src/main/resources/db.properties
├── src/main/webapp/
│   ├── index.jsp, game.jsp, result.jsp, leaderboard.jsp
│   ├── css/style.css
│   └── WEB-INF/web.xml
├── src/test/java/service/MathServiceTest.java
├── database/math_whiz_db.sql
└── README.md
```

## Database Configuration
Edit `src/main/resources/db.properties` and set your MySQL user and password:
```
db.url=jdbc:mysql://localhost:3306/math_whiz_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Dhaka
db.user=root
db.password=YOUR_PASSWORD
```
The database and table are also created automatically on first use, but you can create them manually with the SQL below.

## Database Creation SQL
```sql
CREATE DATABASE IF NOT EXISTS math_whiz_db;
USE math_whiz_db;

CREATE TABLE IF NOT EXISTS game_scores (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    player_name VARCHAR(100) NOT NULL,
    score       INT          NOT NULL,
    difficulty  VARCHAR(20)  NOT NULL DEFAULT 'NORMAL',
    played_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```
(also available in `database/math_whiz_db.sql`)

## Steps to Run
1. Start MySQL and set the password in `db.properties`.
2. Open the `MathWhiz` folder in IntelliJ IDEA (File → Open → select `pom.xml` → Open as Project).
3. Wait for Maven to download dependencies.
4. Open the Maven panel → **Plugins → jetty → jetty:run** (or run `mvn jetty:run`).
5. Open <http://localhost:8080/> in the browser.

To stop the server press the red **Stop** button in IntelliJ. To use another port: `mvn jetty:run -Djetty.http.port=9090`.

## Game Rules and Scoring
| Question | Area | Marks |
|----------|------|-------|
| Q1 | Prime number (1 = prime, 0 = not prime) | 2 |
| Q2 | Modulus (integers only, divisor never zero) | 2 |
| Q3 | Trigonometry (sin / cos / tan) | 3 |
| Q4 | Trigonometry (sin / cos / tan) | 3 |
| **Total** | | **10** |

- Answers are calculated by `MathService` methods (`isPrime`, `calculateMod`, `calculateSin`, `calculateCos`, `calculateTan`), never stored manually.
- Angles are in degrees and converted to radians with `Math.toRadians`.
- Decimal answers are accepted with a tolerance of 0.01.
- Undefined tangent values (90°, 270°) are never asked and throw `ArithmeticException` in `calculateTan`.
- Difficulty is `NORMAL` and is stored in the database.
- Leaderboard: `ORDER BY score DESC, played_at ASC LIMIT 10`.

## Testing
Run `src/test/java/service/MathServiceTest.java` (the `main` method) in IntelliJ. It checks prime 7/8/1, `17 % 5`, `sin 30°`, `cos 60°`, `tan 45°` and the zero-divisor error.

## Application Screenshots

### Welcome Page
![Welcome Page](screenshots/home.png)

### Game Page
![Game Page](screenshots/game.png)

### Result Page
![Result Page](screenshots/result.png)

### Leaderboard
![Leaderboard](screenshots/leaderboard.png)

## GitHub Project Directory Link
https://github.com/mohammadjahidulislam/REPOSITORY/tree/main/MathWhiz
