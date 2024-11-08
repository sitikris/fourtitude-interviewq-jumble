package asia.fourtitude.interviewq.jumble.service;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.entity.GameBoardEntity;
import asia.fourtitude.interviewq.jumble.entity.GameStateEntity;
import asia.fourtitude.interviewq.jumble.mapper.GameBoardMapper;
import asia.fourtitude.interviewq.jumble.model.GameBoard;
import asia.fourtitude.interviewq.jumble.model.GameGuessInput;
import asia.fourtitude.interviewq.jumble.model.GameGuessOutput;
import asia.fourtitude.interviewq.jumble.repository.GameBoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class GameBoardServiceTest {

    @Mock
    private GameBoardRepository gameBoardRepository;

    @InjectMocks
    private GameBoardServiceImpl gameBoardService;

    @Spy
    private GameBoardMapper gameBoardMapper = Mappers.getMapper(GameBoardMapper.class);

    private final String boardId = "d0ea2e92-455a-4009-9605-556f443437c5";
    private final String stateId = "f2166472-9fe1-4270-a996-b22f4854e10c";
    private final String word = "ample";
    private final String original = "example";
    private final String scramble = "xpmleae";

    private GameState gameState;
    private GameStateEntity gameStateEntity;
    private final List<GameBoardEntity> gameBoardEntityList = new ArrayList<>();
    private final List<GameBoard> gameBoardList = new ArrayList<>();

    private GameBoardEntity gameBoardEntity;
    private GameGuessOutput output;
    private GameBoard gameBoard;
    private GameGuessInput input;

    HashMap<String, Boolean> subWords = new HashMap<>();
    List<String> guessedWords = new ArrayList<>();

    @BeforeEach
    public void setup() {

        subWords = new HashMap<>();
        subWords.put("ample", false);
        subWords.put("amp", false);

        gameState = GameState.builder()
                .id(stateId)
                .original(original)
                .scramble(scramble)
                .subWords(subWords).build();
        gameStateEntity = GameStateEntity.builder()
                .id(stateId)
                .original(original)
                .scramble(scramble)
                .subWords(subWords).build();
        gameBoardEntity = GameBoardEntity.builder().id(boardId).stateId(stateId).word(word).build();
        gameBoard = GameBoard.builder().id(boardId).stateId(stateId).word(word).build();


        input = GameGuessInput.builder().id(stateId).word(word).build();
        output = GameGuessOutput.builder()
                .id(stateId)
                // .result(null)
                .originalWord(original)
                .scrambleWord(scramble)
                // .guessWord(word)
                .totalWords(0)
                .remainingWords(0)
                .build();

        gameBoardList.add(gameBoard);
        gameBoardEntityList.add(gameBoardEntity);
        guessedWords.addAll(subWords.keySet().stream().toList());

    }

    @Test
    public void GameBoardService_CreateGameBoard_ReturnsGameBoardModel() {
        GameBoardEntity entity = gameBoardMapper.toEntity(gameBoard);

        when(gameBoardRepository.save(Mockito.any(GameBoardEntity.class))).thenReturn(entity);

        GameBoard savedGameBoard = gameBoardService.saveGameBoard(gameBoardMapper.toModel(entity));

        Assertions.assertThat(savedGameBoard).isNotNull();
    }

    @Test
    public void GameBoardService_GetAllGameBoard_ReturnsResponseModel() {
        when(gameBoardRepository.findAll()).thenReturn(gameBoardEntityList);

        List<GameBoard> gameBoards = gameBoardService.getAllGameBoards();

        Assertions.assertThat(gameBoards).isNotNull();
    }

    @Test
    public void GameBoardService_FindById_ReturnGameBoardModel() {
        when(gameBoardRepository.findById(boardId)).thenReturn(Optional.ofNullable(gameBoardEntity));

        GameBoard gameBoardReturn = gameBoardService.getGameBoardById(boardId);

        Assertions.assertThat(gameBoardReturn).isNotNull();
    }

    @Test
    public void GameBoardService_UpdateGameBoard_ReturnGameBoardModel() {
        when(gameBoardRepository.findById(boardId)).thenReturn(Optional.ofNullable(gameBoardEntity));
        when(gameBoardRepository.save(gameBoardEntity)).thenReturn(gameBoardEntity);

        GameBoard updateReturn = gameBoardService.updateGameBoard(boardId, gameBoard);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void GameBoardService_DeleteGameBoardById_ReturnVoid() {
        when(gameBoardRepository.findById(boardId)).thenReturn(Optional.ofNullable(gameBoardEntity));
        doNothing().when(gameBoardRepository).delete(gameBoardEntity);

        assertAll(() -> gameBoardService.getGameBoardById(boardId));
    }
}
