package model;

public class QuestionResult {
    private final String question;
    private final String yourAnswer;
    private final String correctAnswer;
    private final boolean correct;
    private final int earned;
    private final int max;

    public QuestionResult(String question, String yourAnswer, String correctAnswer,
                          boolean correct, int earned, int max) {
        this.question = question;
        this.yourAnswer = yourAnswer;
        this.correctAnswer = correctAnswer;
        this.correct = correct;
        this.earned = earned;
        this.max = max;
    }

    public String getQuestion() { return question; }
    public String getYourAnswer() { return yourAnswer; }
    public String getCorrectAnswer() { return correctAnswer; }
    public boolean isCorrect() { return correct; }
    public int getEarned() { return earned; }
    public int getMax() { return max; }
}
