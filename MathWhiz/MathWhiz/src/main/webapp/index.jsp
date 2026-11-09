<%@ page contentType="text/html;charset=UTF-8" language="java" import="util.Html" %>
<%
    String ctx = request.getContextPath();
    String error = (String) request.getAttribute("error");
    String saved = (String) session.getAttribute("playerName");
    if (saved == null) saved = "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>JAHID – MATH WHIZ</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <h1>JAHID – MATH WHIZ</h1>
        <div class="subtitle">Four mathematical challenges. Ten marks. Can you top the leaderboard?</div>

        <% if (error != null) { %>
            <div class="error"><%= Html.esc(error) %></div>
        <% } %>

        <form method="post" action="<%= ctx %>/game">
            <input type="hidden" name="action" value="start">
            <label for="playerName">Player Name</label>
            <input type="text" id="playerName" name="playerName" maxlength="100"
                   placeholder="Enter your name" value="<%= Html.esc(saved) %>" required autofocus>
            <button type="submit" class="btn">Start Game</button>
        </form>

        <h2>How to play</h2>
        <ul>
            <li>Q1 – Prime number (2 marks)</li>
            <li>Q2 – Modulus operation (2 marks)</li>
            <li>Q3 – Trigonometry (3 marks)</li>
            <li>Q4 – Trigonometry (3 marks)</li>
        </ul>
        <div>Difficulty: <span class="badge">NORMAL</span></div>

        <div class="links"><a href="<%= ctx %>/game?action=leaderboard">View Leaderboard</a></div>
    </div>
</div>
</body>
</html>
