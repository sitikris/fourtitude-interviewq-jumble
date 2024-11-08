package asia.fourtitude.interviewq.jumble.repository;

import asia.fourtitude.interviewq.jumble.entity.GameStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameStateEntity, String> {
}
