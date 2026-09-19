package backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import backend.entity.Player;
import backend.repository.PlayerRepository;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player registerPlayer(Player player) {

        if (playerRepository.existsByUsername(player.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (playerRepository.existsByEmail(player.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        player.setXp(0);
        player.setLevel(1);

        return playerRepository.save(player);
    }

    public Optional<Player> findByUsername(String username) {
        return playerRepository.findByUsername(username);
    }

    public Optional<Player> findByEmail(String email) {
        return playerRepository.findByEmail(email);
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> login(String username, String password) {

        Optional<Player> player =
                playerRepository.findByUsername(username);

        if (player.isPresent()
                && player.get().getPassword().equals(password)) {
            return player;
        }

        return Optional.empty();
    }

    public Optional<Player> updateProgress(Long id, int xp, int level) {

        Optional<Player> playerOptional =
                playerRepository.findById(id);

        if (playerOptional.isPresent()) {

            Player player = playerOptional.get();

            player.setXp(xp);
            player.setLevel(level);

            return Optional.of(playerRepository.save(player));
        }

        return Optional.empty();
    }

    public Optional<Player> addXp(Long id, int amount) {

        Optional<Player> playerOptional =
                playerRepository.findById(id);

        if (playerOptional.isPresent()) {

            Player player = playerOptional.get();

            int newXp = player.getXp() + amount;

            player.setXp(newXp);

            int newLevel = (newXp / 100) + 1;

            player.setLevel(newLevel);

            return Optional.of(playerRepository.save(player));
        }

        return Optional.empty();
    }
}