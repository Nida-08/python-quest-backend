package com.pythonquest.quiz;

public class LeaderboardEntry {

    private String playerName;
    private int score;
    private double percentage;

    public LeaderboardEntry(String playerName, int score, double percentage) {
        this.playerName = playerName;
        this.score = score;
        this.percentage = percentage;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public double getPercentage() {
        return percentage;
    }
}