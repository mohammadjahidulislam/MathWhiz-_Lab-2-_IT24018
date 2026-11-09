package controller;

import dao.ScoreDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.GameScore;
import model.QuestionResult;
import model.Quiz;
import service.MathService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameServlet extends HttpServlet {

    private static final String DIFFICULTY = "NORMAL";
    private final ScoreDAO dao = new ScoreDAO();
    private final Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("leaderboard".equals(action)) {
            showLeaderboard(req, resp);
        } else if ("play".equals(action) && currentQuiz(req) != null) {
            forward(req, resp, "/game.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("start".equals(action)) {
            startGame(req, resp);
        } else if ("submit".equals(action)) {
            submitAnswers(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        }
    }

    // ---------- Screen 1 -> Screen 2 ----------
    private void startGame(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("playerName");
        name = name == null ? "" : name.trim();
        if (name.isEmpty()) {
            req.setAttribute("error", "Please enter your name to start the game.");
            forward(req, resp, "/index.jsp");
            return;
        }
        if (name.length() > 100) name = name.substring(0, 100);

        HttpSession session = req.getSession(true);
        session.setAttribute("playerName", name);
        session.setAttribute("quiz", MathService.generateQuiz(random));
        resp.sendRedirect(req.getContextPath() + "/game?action=play");
    }

    // ---------- Screen 2 -> Screen 3 ----------
    private void submitAnswers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Quiz quiz = currentQuiz(req);
        HttpSession session = req.getSession(false);
        String name = session == null ? null : (String) session.getAttribute("playerName");
        if (quiz == null || name == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // validation
        List<String> errors = new ArrayList<>();
        Integer ansPrime = parseInt(req.getParameter("answerPrime"), "Q1", errors);
        if (ansPrime != null && ansPrime != 0 && ansPrime != 1) {
            errors.add("Q1: enter 1 (prime) or 0 (not prime).");
        }
        Integer ansMod = parseInt(req.getParameter("answerMod"), "Q2", errors);
        Double ansTrig1 = parseDouble(req.getParameter("answerTrig1"), "Q3", errors);
        Double ansTrig2 = parseDouble(req.getParameter("answerTrig2"), "Q4", errors);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            forward(req, resp, "/game.jsp");
            return;
        }

        // evaluate using the Java methods (no stored answers)
        List<QuestionResult> results = new ArrayList<>();
        int totalScore = 0;

        int expPrime = MathService.isPrime(quiz.getPrimeNumber());
        boolean primeCorrect = ansPrime == expPrime;
        if (primeCorrect) totalScore += 2;
        results.add(new QuestionResult("Is " + quiz.getPrimeNumber() + " prime? (1 = Prime, 0 = Not Prime)",
                String.valueOf(ansPrime), String.valueOf(expPrime), primeCorrect, primeCorrect ? 2 : 0, 2));

        int expMod = MathService.calculateMod(quiz.getModA(), quiz.getModB());
        boolean modCorrect = ansMod == expMod;
        if (modCorrect) totalScore += 2;
        results.add(new QuestionResult(quiz.getModA() + " % " + quiz.getModB(),
                String.valueOf(ansMod), String.valueOf(expMod), modCorrect, modCorrect ? 2 : 0, 2));

        double expTrig1 = MathService.calculateTrig(quiz.getTrig1Func(), quiz.getTrig1Angle());
        boolean trig1Correct = MathService.isClose(expTrig1, ansTrig1);
        if (trig1Correct) totalScore += 3;
        results.add(new QuestionResult(quiz.getTrig1Func() + "(" + quiz.getTrig1Angle() + "\u00B0)",
                format(ansTrig1), format(expTrig1), trig1Correct, trig1Correct ? 3 : 0, 3));

        double expTrig2 = MathService.calculateTrig(quiz.getTrig2Func(), quiz.getTrig2Angle());
        boolean trig2Correct = MathService.isClose(expTrig2, ansTrig2);
        if (trig2Correct) totalScore += 3;
        results.add(new QuestionResult(quiz.getTrig2Func() + "(" + quiz.getTrig2Angle() + "\u00B0)",
                format(ansTrig2), format(expTrig2), trig2Correct, trig2Correct ? 3 : 0, 3));

        // save to database
        try {
            dao.save(new GameScore(name, totalScore, DIFFICULTY, LocalDateTime.now()));
        } catch (Exception e) {
            log("Could not save score", e);
            req.setAttribute("dbError", e.getMessage());
        }

        session.removeAttribute("quiz");   // a new game needs a new quiz
        req.setAttribute("playerName", name);
        req.setAttribute("totalScore", totalScore);
        req.setAttribute("maxScore", 10);
        req.setAttribute("results", results);
        forward(req, resp, "/result.jsp");
    }

    // ---------- Screen 4 ----------
    private void showLeaderboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<GameScore> scores = new ArrayList<>();
        try {
            scores = dao.findTop10();
        } catch (Exception e) {
            log("Could not load leaderboard", e);
            req.setAttribute("dbError", e.getMessage());
        }
        req.setAttribute("scores", scores);
        forward(req, resp, "/leaderboard.jsp");
    }

    // ---------- helpers ----------
    private Quiz currentQuiz(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session == null ? null : (Quiz) session.getAttribute("quiz");
    }

    private Integer parseInt(String text, String label, List<String> errors) {
        if (text == null || text.trim().isEmpty()) {
            errors.add(label + ": please enter an answer.");
            return null;
        }
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            errors.add(label + ": enter a whole number (integers only).");
            return null;
        }
    }

    private Double parseDouble(String text, String label, List<String> errors) {
        if (text == null || text.trim().isEmpty()) {
            errors.add(label + ": please enter an answer.");
            return null;
        }
        try {
            double d = Double.parseDouble(text.trim().replace(',', '.'));
            if (Double.isNaN(d) || Double.isInfinite(d)) throw new NumberFormatException();
            return d;
        } catch (NumberFormatException e) {
            errors.add(label + ": enter a decimal number, for example 0.5");
            return null;
        }
    }

    private String format(double d) {
        return String.format(java.util.Locale.US, "%.4f", d);
    }

    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }
}
