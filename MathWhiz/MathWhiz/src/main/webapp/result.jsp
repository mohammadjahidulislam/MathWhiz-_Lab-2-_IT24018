<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, model.QuestionResult, util.Html" %>
<%
    String ctx = request.getContextPath();
    @SuppressWarnings("unchecked")
    List<QuestionResult> results = (List<QuestionResult>) request.getAttribute("results");
    if (results == null) {
        response.sendRedirect(ctx + "/index.jsp");
        return;
    }
    String playerName = (String) request.getAttribute("playerName");
    Integer totalScore = (Integer) request.getAttribute("totalScore");
    Integer maxScore = (Integer) request.getAttribute("maxScore");
    String dbError = (String) request.getAttribute("dbError");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>JAHID – MATH WHIZ | Result</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <h1>JAHID – MATH WHIZ</h1>
        <div class="subtitle">Final result for <strong><%= Html.esc(playerName) %></strong></div>

        <div class="score"><%= totalScore %> / <%= maxScore %></div>
        <div class="center"><span class="badge">Difficulty: NORMAL</span></div>

        <% if (dbError != null) { %>
            <div class="error" style="margin-top:16px">Your score could not be saved to the database: <%= Html.esc(dbError) %></div>
        <% } %>

        <table>
            <tr><th>Question</th><th>Your answer</th><th>Correct answer</th><th>Marks</th></tr>
            <% for (QuestionResult r : results) { %>
                <tr>
                    <td><%= Html.esc(r.getQuestion()) %></td>
                    <td><%= Html.esc(r.getYourAnswer()) %></td>
                    <td><%= Html.esc(r.getCorrectAnswer()) %></td>
                    <td class="<%= r.isCorrect() ? "ok" : "bad" %>"><%= r.getEarned() %> / <%= r.getMax() %></td>
                </tr>
            <% } %>
        </table>

        <div class="center">
            <a class="btn" href="<%= ctx %>/index.jsp">Play Again</a>
            <a class="btn secondary" href="<%= ctx %>/game?action=leaderboard">Leaderboard</a>
        </div>
    </div>
</div>
</body>
</html>
