package com.pythonquest.quiz;

public class XpUpdate {

    private int quizId;
    private int correctAnswers;
    private int earnedXp;

    public XpUpdate(int quizId, int correctAnswers, int earnedXp) {
        this.quizId = quizId;
        this.correctAnswers = correctAnswers;
        this.earnedXp = earnedXp;
    }

    public int getQuizId() {
        return quizId;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getEarnedXp() {
        return earnedXp;
    }
}