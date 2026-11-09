<%@ page contentType="text/html;charset=UTF-8" language="java"
         import="java.util.List, java.util.Locale, java.time.format.DateTimeFormatter, model.GameScore, util.Html" %>
<%
    String ctx = request.getContextPath();
    @SuppressWarnings("unchecked")
    List<GameScore> scores = (List<GameScore>) request.getAttribute("scores");
    if (scores == null) {
        response.sendRedirect(ctx + "/game?action=leaderboard");
        return;
    }
    String dbError = (String) request.getAttribute("dbError");
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a", Locale.ENGLISH);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>JAHID – MATH WHIZ | Leaderboard</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <h1>JAHID – MATH WHIZ</h1>
        <div class="subtitle">Leaderboard – Top 10 scores</div>

        <% if (dbError != null) { %>
            <div class="error">Could not load scores from the database: <%= Html.esc(dbError) %></div>
        <% } %>

        <table>
            <tr><th>Rank</th><th>Player</th><th>Score</th><th>Difficulty</th><th>Date / Time</th></tr>
            <% if (scores.isEmpty()) { %>
                <tr><td colspan="5" class="center">No scores yet. Be the first to play!</td></tr>
            <% } %>
            <% int rank = 1; for (GameScore g : scores) { %>
                <tr class="<%= rank == 1 ? "rank1" : "" %>">
                    <td><%= rank++ %></td>
                    <td><%= Html.esc(g.getPlayerName()) %></td>
                    <td><%= g.getScore() %> / 10</td>
                    <td><%= Html.esc(g.getDifficulty()) %></td>
                    <td><%= g.getPlayedAt() == null ? "-" : g.getPlayedAt().format(fmt) %></td>
                </tr>
            <% } %>
        </table>

        <div class="center">
            <a class="btn" href="<%= ctx %>/index.jsp">Play Game</a>
        </div>
    </div>
</div>
</body>
</html>
