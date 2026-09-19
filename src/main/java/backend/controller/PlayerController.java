package backend.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.entity.Player;
import backend.service.PlayerService;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerPlayer(@RequestBody Player player) {

        try {
            Player savedPlayer =
                    playerService.registerPlayer(player);

            return ResponseEntity.ok(savedPlayer);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginPlayer(
            @RequestBody Player loginRequest) {

        Optional<Player> player =
                playerService.login(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                );

        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        }

        return ResponseEntity
                .badRequest()
                .body("Invalid username or password");
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getByUsername(
            @PathVariable String username) {

        Optional<Player> player =
                playerService.findByUsername(username);

        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id) {

        Optional<Player> player =
                playerService.findById(id);

        if (player.isPresent()) {
            return ResponseEntity.ok(player.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/progress")
    public ResponseEntity<?> updateProgress(
            @PathVariable Long id,
            @RequestParam int xp,
            @RequestParam int level) {

        Optional<Player> updatedPlayer =
                playerService.updateProgress(id, xp, level);

        if (updatedPlayer.isPresent()) {
            return ResponseEntity.ok(updatedPlayer.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/add-xp")
    public ResponseEntity<?> addXp(
            @PathVariable Long id,
            @RequestParam int amount) {

        if (amount <= 0) {
            return ResponseEntity
                    .badRequest()
                    .body("XP amount must be greater than 0");
        }

        Optional<Player> updatedPlayer =
                playerService.addXp(id, amount);

        if (updatedPlayer.isPresent()) {
            return ResponseEntity.ok(updatedPlayer.get());
        }

        return ResponseEntity.notFound().build();
    }
}