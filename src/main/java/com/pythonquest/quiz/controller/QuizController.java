
package com.pythonquest.quiz.controller;

import com.pythonquest.quiz.Question;
import com.pythonquest.quiz.QuizResult;
import com.pythonquest.quiz.QuizSubmission;
import com.pythonquest.quiz.LeaderboardEntry;
import com.pythonquest.quiz.XpUpdate;
import com.pythonquest.quiz.dto.QuestionResponse;

import backend.service.PlayerService;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8443")
@RequestMapping("/api")
public class QuizController {

    private final PlayerService playerService;

    public QuizController(PlayerService playerService) {
        this.playerService = playerService;
    }

    private QuizResult latestResult;
    private XpUpdate latestXpUpdate;
    private final List<LeaderboardEntry> leaderboard = new ArrayList<>();

    @GetMapping("/quiz")
    public List<QuestionResponse> getQuiz() {

        Question question1 = new Question(
                1,
                "Which function is used to print something in Python?",
                "input()", "print()", "scan()", "display()", "print()"
        );

        Question question2 = new Question(
                2,
                "Which symbol is used to assign a value to a variable?",
                "=", "==", ":", "->", "="
        );

        return List.of(
                new QuestionResponse(
                        question1.getId(),
                        question1.getQuestion(),
                        question1.getOptionA(),
                        question1.getOptionB(),
                        question1.getOptionC(),
                        question1.getOptionD()
                ),
                new QuestionResponse(
                        question2.getId(),
                        question2.getQuestion(),
                        question2.getOptionA(),
                        question2.getOptionB(),
                        question2.getOptionC(),
                        question2.getOptionD()
                )
        );
    }

    @PostMapping("/quiz/submit")
    public QuizResult submitQuiz(@RequestBody QuizSubmission submission) {

        Map<Integer, String> correctAnswers = new HashMap<>();

        correctAnswers.put(1, "print()");
        correctAnswers.put(2, "=");

        int correct = 0;

        for (Map.Entry<Integer, String> answer : submission.getAnswers().entrySet()) {

            String correctAnswer = correctAnswers.get(answer.getKey());

            if (correctAnswer != null &&
                    correctAnswer.equals(answer.getValue())) {
                correct++;
            }
        }

        int total = correctAnswers.size();
        int wrong = total - correct;

        double percentage = ((double) correct / total) * 100;

        latestResult = new QuizResult(
                submission.getQuizId(),
                total,
                correct,
                wrong,
                percentage
        );

        int earnedXp = correct * 10;

        latestXpUpdate = new XpUpdate(
                submission.getQuizId(),
                correct,
                earnedXp
        );

        playerService.addXp(
                submission.getPlayerId(),
                earnedXp
        );

        return latestResult;
    }

    @GetMapping("/quiz/result")
    public QuizResult getQuizResult() {
        return latestResult;
    }

    @GetMapping("/quiz/xp")
    public XpUpdate getEarnedXp() {
        return latestXpUpdate;
    }

    @PostMapping("/leaderboard")
    public LeaderboardEntry addToLeaderboard(
            @RequestParam String playerName,
            @RequestParam int score,
            @RequestParam double percentage) {

        LeaderboardEntry entry = new LeaderboardEntry(
                playerName,
                score,
                percentage
        );

        leaderboard.add(entry);

        return entry;
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardEntry> getLeaderboard() {

        leaderboard.sort(
                Comparator.comparingInt(
                        LeaderboardEntry::getScore
                ).reversed()
        );

        return leaderboard;
    }
}
