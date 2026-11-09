<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List, model.Quiz, util.Html" %>
<%
    String ctx = request.getContextPath();
    Quiz quiz = (Quiz) session.getAttribute("quiz");
    if (quiz == null) {
        response.sendRedirect(ctx + "/index.jsp");
        return;
    }
    String playerName = (String) session.getAttribute("playerName");
    @SuppressWarnings("unchecked")
    List<String> errors = (List<String>) request.getAttribute("errors");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>JAHID – MATH WHIZ | Game</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
<div class="container">
    <div class="card">
        <h1>JAHID – MATH WHIZ</h1>
        <div class="subtitle">Player: <strong><%= Html.esc(playerName) %></strong> &nbsp;|&nbsp; Difficulty: <span class="badge">NORMAL</span></div>

        <% if (errors != null && !errors.isEmpty()) { %>
            <div class="error">Please fix the following:
                <ul>
                    <% for (String e : errors) { %><li><%= Html.esc(e) %></li><% } %>
                </ul>
            </div>
        <% } %>

        <form method="post" action="<%= ctx %>/game">
            <input type="hidden" name="action" value="submit">

            <div class="question">
                <span class="marks">2 marks</span>
                <p><strong>Q1.</strong> Is <strong><%= quiz.getPrimeNumber() %></strong> a prime number?</p>
                <input type="number" name="answerPrime" min="0" max="1" step="1"
                       placeholder="1 = Prime, 0 = Not Prime" value="<%= Html.param(request, "answerPrime") %>" required>
                <div class="hint">Enter 1 if it is prime, otherwise 0.</div>
            </div>

            <div class="question">
                <span class="marks">2 marks</span>
                <p><strong>Q2.</strong> Calculate the remainder: <strong><%= quiz.getModA() %> % <%= quiz.getModB() %></strong></p>
                <input type="number" name="answerMod" step="1"
                       placeholder="Integer answer" value="<%= Html.param(request, "answerMod") %>" required>
            </div>

            <div class="question">
                <span class="marks">3 marks</span>
                <p><strong>Q3.</strong> Calculate <strong><%= quiz.getTrig1Func() %>(<%= quiz.getTrig1Angle() %>&deg;)</strong></p>
                <input type="text" name="answerTrig1" inputmode="decimal"
                       placeholder="e.g. 0.5" value="<%= Html.param(request, "answerTrig1") %>" required>
                <div class="hint">Angle is in degrees. Give at least 2 decimal places (tolerance 0.01).</div>
            </div>

            <div class="question">
                <span class="marks">3 marks</span>
                <p><strong>Q4.</strong> Calculate <strong><%= quiz.getTrig2Func() %>(<%= quiz.getTrig2Angle() %>&deg;)</strong></p>
                <input type="text" name="answerTrig2" inputmode="decimal"
                       placeholder="e.g. 0.87" value="<%= Html.param(request, "answerTrig2") %>" required>
                <div class="hint">Angle is in degrees. Give at least 2 decimal places (tolerance 0.01).</div>
            </div>

            <button type="submit" class="btn">Submit Answers</button>
        </form>
    </div>
</div>
</body>
</html>
