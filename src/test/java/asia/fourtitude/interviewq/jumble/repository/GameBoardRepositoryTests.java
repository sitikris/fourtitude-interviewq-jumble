package asia.fourtitude.interviewq.jumble.repository;

import asia.fourtitude.interviewq.jumble.entity.GameBoardEntity;
import asia.fourtitude.interviewq.jumble.entity.GameStateEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GameBoardRepositoryTests {

    @Autowired
    private GameStateRepository gameStateRepository;

    @Autowired
    private GameBoardRepository gameBoardRepository;

    private final String boardId = "d0ea2e92-455a-4009-9605-556f443437c5";
    private final String stateId = "f2166472-9fe1-4270-a996-b22f4854e10c";
    private final String stateId2 = "8aca1549-a841-42e3-ac02-5007bff50a7b";
    private final String word = "ample";
    private final String original = "example";
    private final String scramble = "xpmleae";

    @BeforeEach
    public void setup() {
    }

    @Test
    public void GameBoardRepository_SaveAll_ReturnSavedGameBoard() {

        // Arrange
        GameBoardEntity gameBoard = GameBoardEntity.builder()
                .stateId(stateId)
                .word(word).build();

        // Act
        GameBoardEntity savedGameBoard = gameBoardRepository.save(gameBoard);

        // Assert
        Assertions.assertThat(savedGameBoard).isNotNull();
        Assertions.assertThat(savedGameBoard.getId()).isNotNull();
    }

    @Test
    public void GameBoardRepository_GetAll_ReturnMoreThenOneGameBoard() {

        GameStateEntity stateEntity = GameStateEntity.builder()
                .original(original)
                .scramble(scramble).build();
        gameStateRepository.save(stateEntity);

        GameBoardEntity gameBoard = GameBoardEntity.builder()
                .stateId(stateEntity.getId())
                .word(word).build();
        GameBoardEntity gameBoard2 = GameBoardEntity.builder()
                .stateId(stateEntity.getId())
                .word(word).build();

        gameBoardRepository.save(gameBoard);
        gameBoardRepository.save(gameBoard2);

        List<GameBoardEntity> gameBoardList = gameBoardRepository.findAll();

        Assertions.assertThat(gameBoardList).isNotNull();
        Assertions.assertThat(gameBoardList.size()).isEqualTo(2);
    }

    @Test
    public void GameBoardRepository_FindById_ReturnGameBoard() {
        GameBoardEntity gameBoard = GameBoardEntity.builder()
                .stateId(stateId)
                .word(word).build();

        gameBoardRepository.save(gameBoard);

        GameBoardEntity gameBoardList = gameBoardRepository.findById(gameBoard.getId()).get();

        Assertions.assertThat(gameBoardList).isNotNull();
    }

    @Test
    public void GameBoardRepository_FindByStateId_ReturnGameBoardNotNull() {

        GameStateEntity stateEntity = GameStateEntity.builder()
                .original(original)
                .scramble(scramble).build();
        gameStateRepository.save(stateEntity);

        GameBoardEntity gameBoard = GameBoardEntity.builder()
                .stateId(stateEntity.getId())
                .word(word).build();

        gameBoardRepository.save(gameBoard);

        List<GameBoardEntity> gameBoardEntities = gameBoardRepository
                .findByStateId(gameBoard.getStateId()).get();

        Assertions.assertThat(gameBoardEntities).isNotNull();
    }

    @Test
    public void GameBoardRepository_FindByWord_ReturnGameBoardNotNull() {

        GameStateEntity stateEntity = GameStateEntity.builder()
                .original(original)
                .scramble(scramble).build();
        gameStateRepository.save(stateEntity);

        GameBoardEntity gameBoard = GameBoardEntity.builder()
                .stateId(stateEntity.getId())
                .word(word).build();

        gameBoardRepository.save(gameBoard);

        List<GameBoardEntity> gameBoardEntities = gameBoardRepository
                .findByStateIdAndWord(gameBoard.getStateId(), gameBoard.getWord()).get();

        Assertions.assertThat(gameBoardEntities).isNotNull();
    }

    @Test
    public void GameBoardRepository_UpdateGameBoard_ReturnGameBoardNotNull() {

        GameStateEntity stateEntity = GameStateEntity.builder()
                .original(original)
                .scramble(scramble).build();
        gameStateRepository.save(stateEntity);

        GameBoardEntity gameBoard = GameBoardEntity.builder()
                .stateId(stateEntity.getId())
                .word(word).build();

        gameBoardRepository.save(gameBoard);

        GameBoardEntity gameBoardSave = gameBoardRepository.findById(gameBoard.getId()).get();
        gameBoardSave.setStateId(stateId2);
        gameBoardSave.setWord("rose");

        GameBoardEntity updatedGameBoard = gameBoardRepository.save(gameBoardSave);

        Assertions.assertThat(updatedGameBoard.getStateId()).isNotNull();
        Assertions.assertThat(updatedGameBoard.getWord()).isNotNull();
    }

    @Test
    public void GameBoardRepository_GameBoardDelete_ReturnGameBoardIsEmpty() {

        GameStateEntity stateEntity = GameStateEntity.builder()
                .original(original)
                .scramble(scramble).build();
        gameStateRepository.save(stateEntity);

        GameBoardEntity gameBoard = GameBoardEntity.builder()
                .stateId(stateEntity.getId())
                .word(word).build();

        gameBoardRepository.save(gameBoard);

        gameBoardRepository.deleteById(gameBoard.getId());
        Optional<GameBoardEntity> gameBoardReturn = gameBoardRepository.findById(gameBoard.getId());

        Assertions.assertThat(gameBoardReturn).isEmpty();
    }


}
