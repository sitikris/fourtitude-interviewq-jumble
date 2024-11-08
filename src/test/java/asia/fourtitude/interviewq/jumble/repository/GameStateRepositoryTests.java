package asia.fourtitude.interviewq.jumble.repository;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.entity.GameStateEntity;
import asia.fourtitude.interviewq.jumble.mapper.GameStateMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameStateRepositoryTests {

    @Autowired
    private GameStateRepository gameStateRepository;

    @Spy
    private GameStateMapper gameStateMapper = Mappers.getMapper(GameStateMapper.class);

    private final String word = "ample";

    private final String original = "example";
    private final String scramble = "xpmleae";

    private final String original2 = "elephant";
    private final String scramble2 = "aeehlnpt";

    HashMap<String, Boolean> subWords = new HashMap<>();
    HashMap<String, Boolean> subWords2 = new HashMap<>();

    @BeforeEach
    public void setup() {

        subWords = new HashMap<>();
        subWords.put("ample", false);
        subWords.put("amp", false);

        subWords2 = new HashMap<>();
        subWords2.put("pant", false);
        subWords2.put("plant", false);
    }

    @Test
    public void GameStateRepository_SaveAll_ReturnSavedGameState() {

        // Arrange
        GameStateEntity gameState = GameStateEntity.builder()
                .original(original)
                .scramble(scramble)
                .subWords(subWords).build();

        // Act
        GameStateEntity savedGameState = gameStateRepository.save(gameState);

        // Assert
        Assertions.assertThat(savedGameState).isNotNull();
        Assertions.assertThat(savedGameState.getId()).isNotNull();
    }

    @Test
    public void GameStateRepository_GetAll_ReturnMoreThenOneGameState() {

        GameStateEntity gameState = GameStateEntity.builder()
                .original(original)
                .scramble(scramble)
                .subWords(subWords).build();
        GameStateEntity gameState2 = GameStateEntity.builder()
                .original(original2)
                .scramble(scramble2)
                .subWords(subWords2).build();

        gameStateRepository.save(gameState);
        gameStateRepository.save(gameState2);

        List<GameStateEntity> gameStateList = gameStateRepository.findAll();

        Assertions.assertThat(gameStateList).isNotNull();
        Assertions.assertThat(gameStateList.size()).isEqualTo(2);
    }

    @Test
    public void GameStateRepository_FindById_ReturnGameState() {
        GameStateEntity gameState = GameStateEntity.builder()
                .original(original)
                .scramble(scramble)
                .subWords(subWords).build();

        gameStateRepository.save(gameState);

        GameStateEntity gameStateList = gameStateRepository.findById(gameState.getId()).get();

        Assertions.assertThat(gameStateList).isNotNull();
    }

    @Test
    public void GameStateRepository_UpdateGameState_ReturnGameStateNotNull() {

        GameStateEntity gameState = GameStateEntity.builder()
                .original(original)
                .scramble(scramble)
                .subWords(subWords).build();

        gameStateRepository.save(gameState);

        GameState updatedGameState = gameStateRepository.findById(gameState.getId())
                .map(existingGameState -> {
                    existingGameState.getSubWords().put(word, true);
                    return gameStateMapper.toModel(gameStateRepository.save(existingGameState));
                }).get();


        Assertions.assertThat(updatedGameState.getOriginal()).isNotNull();
        Assertions.assertThat(updatedGameState.getScramble()).isNotNull();
        Assertions.assertThat(updatedGameState.getSubWords()).isNotEmpty();
    }

    @Test
    public void GameStateRepository_GameStateDelete_ReturnGameStateIsEmpty() {

        GameStateEntity gameState = GameStateEntity.builder()
                .original(original)
                .scramble(scramble)
                .subWords(subWords).build();

        gameStateRepository.save(gameState);

        gameStateRepository.deleteById(gameState.getId());
        Optional<GameStateEntity> gameStateReturn = gameStateRepository.findById(gameState.getId());

        Assertions.assertThat(gameStateReturn).isEmpty();
    }


}
