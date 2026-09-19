package backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.entity.PlayerProgress;

public interface PlayerProgressRepository extends JpaRepository<PlayerProgress, Long> {

    List<PlayerProgress> findByPlayerId(Long playerId);

    Optional<PlayerProgress> findByPlayerIdAndCompletedLevel(
            Long playerId,
            int completedLevel
    );
}
