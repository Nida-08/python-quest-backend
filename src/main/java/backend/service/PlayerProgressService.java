package backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import backend.entity.PlayerProgress;
import backend.repository.PlayerProgressRepository;

@Service
public class PlayerProgressService {

    private final PlayerProgressRepository progressRepository;

    public PlayerProgressService(
            PlayerProgressRepository progressRepository) {

        this.progressRepository = progressRepository;
    }

    public PlayerProgress saveProgress(
            Long playerId,
            int level,
            boolean completed) {

        Optional<PlayerProgress> existingProgress =
                progressRepository.findByPlayerIdAndCompletedLevel(
                        playerId,
                        level
                );

        if (existingProgress.isPresent()) {

            PlayerProgress progress = existingProgress.get();

            progress.setCompleted(completed);

            return progressRepository.save(progress);
        }

        PlayerProgress progress = new PlayerProgress();

        progress.setPlayerId(playerId);
        progress.setCompletedLevel(level);
        progress.setCompleted(completed);

        return progressRepository.save(progress);
    }

    public List<PlayerProgress> getPlayerProgress(Long playerId) {

        return progressRepository.findByPlayerId(playerId);
    }

    public boolean isAlreadyCompleted(Long playerId, int level) {

        Optional<PlayerProgress> existingProgress =
                progressRepository.findByPlayerIdAndCompletedLevel(
                        playerId,
                        level
                );

        return existingProgress.isPresent()
                && existingProgress.get().isCompleted();
    }
}