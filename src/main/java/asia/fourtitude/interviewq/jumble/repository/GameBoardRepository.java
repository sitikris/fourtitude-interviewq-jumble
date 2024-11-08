package asia.fourtitude.interviewq.jumble.repository;

import asia.fourtitude.interviewq.jumble.entity.GameBoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameBoardRepository extends JpaRepository<GameBoardEntity, String> {
    Optional<List<GameBoardEntity>> findByStateId(String stateId);

    Optional<List<GameBoardEntity>> findByStateIdAndWord(String stateId, String word);
}
