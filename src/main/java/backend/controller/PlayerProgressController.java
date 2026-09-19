package backend.controller;

import backend.entity.PlayerProgress;
import backend.service.PlayerProgressService;
import backend.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
@CrossOrigin(origins = "*")
public class PlayerProgressController {

private final PlayerProgressService progressService;
private final PlayerService playerService;

public PlayerProgressController(
        PlayerProgressService progressService,
        PlayerService playerService) {

    this.progressService = progressService;
    this.playerService = playerService;
}

@PostMapping("/{playerId}/level/{level}")
public ResponseEntity<?> saveProgress(
        @PathVariable Long playerId,
        @PathVariable int level,
        @RequestParam boolean completed) {

    boolean alreadyCompleted =
            progressService.isAlreadyCompleted(playerId, level);

    PlayerProgress progress =
            progressService.saveProgress(
                    playerId,
                    level,
                    completed
            );

    if (completed && !alreadyCompleted) {
        playerService.addXp(playerId, 50);
    }

    return ResponseEntity.ok(progress);
}

@GetMapping("/{playerId}")
public ResponseEntity<List<PlayerProgress>> getProgress(
        @PathVariable Long playerId) {

    return ResponseEntity.ok(
            progressService.getPlayerProgress(playerId)
    );
}

}
