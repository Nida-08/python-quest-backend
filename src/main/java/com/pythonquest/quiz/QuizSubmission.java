package com.pythonquest.quiz;

import java.util.Map;

public class QuizSubmission {

    private Long playerId;
    private int quizId;
    private Map<Integer, String> answers;

    public QuizSubmission(Long playerId, int quizId,
                          Map<Integer, String> answers) {
        this.playerId = playerId;
        this.quizId = quizId;
        this.answers = answers;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public int getQuizId() {
        return quizId;
    }

    public Map<Integer, String> getAnswers() {
        return answers;
    }
}