package com.pythonquest.quiz;

public class QuizResult {

    private int quizId;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private double percentage;

    public QuizResult(int quizId, int totalQuestions,
                      int correctAnswers, int wrongAnswers,
                      double percentage) {

        this.quizId = quizId;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.percentage = percentage;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public double getPercentage() {
        return percentage;
    }
}