package asia.fourtitude.interviewq.jumble.service;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.entity.GameBoardEntity;
import asia.fourtitude.interviewq.jumble.entity.GameStateEntity;
import asia.fourtitude.interviewq.jumble.mapper.GameStateMapper;
import asia.fourtitude.interviewq.jumble.model.GameBoard;
import asia.fourtitude.interviewq.jumble.model.GameGuessInput;
import asia.fourtitude.interviewq.jumble.model.GameGuessOutput;
import asia.fourtitude.interviewq.jumble.repository.GameStateRepository;
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
public class GameStateServiceTest {

    @Mock
    private GameStateRepository gameStateRepository;

    @InjectMocks
    private GameStateServiceImpl gameStateService;

    @Spy
    private GameStateMapper gameStateMapper = Mappers.getMapper(GameStateMapper.class);

    private String boardId = "d0ea2e92-455a-4009-9605-556f443437c5";
    private String stateId = "f2166472-9fe1-4270-a996-b22f4854e10c";
    private String word = "ample";
    private String original = "example";
    private String scramble = "xpmleae";

    private GameState gameState;
    private GameStateEntity gameStateEntity;
    private List<GameStateEntity> gameStateEntityList = new ArrayList<>();
    private List<GameBoard> gameBoardList = new ArrayList<>();

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
        gameStateEntityList.add(gameStateEntity);
        guessedWords.addAll(subWords.keySet().stream().toList());

    }

    @Test
    public void GameStateService_CreateGameState_ReturnsGameStateModel() {
        GameStateEntity entity = gameStateMapper.toEntity(gameState);

        when(gameStateRepository.save(Mockito.any(GameStateEntity.class))).thenReturn(entity);

        GameState savedGameState = gameStateService.saveGameState(gameStateMapper.toModel(entity));

        Assertions.assertThat(savedGameState).isNotNull();
    }

    @Test
    public void GameStateService_GetAllGameState_ReturnsResponseModel() {
        when(gameStateRepository.findAll()).thenReturn(gameStateEntityList);

        List<GameState> gameStates = gameStateService.getAllGameStates();

        Assertions.assertThat(gameStates).isNotNull();
    }

    @Test
    public void GameStateService_FindById_ReturnGameStateModel() {
        when(gameStateRepository.findById(stateId)).thenReturn(Optional.ofNullable(gameStateEntity));

        GameState gameStateReturn = gameStateService.getGameStateById(stateId);

        Assertions.assertThat(gameStateReturn).isNotNull();
    }

    @Test
    public void GameStateService_UpdateGameState_ReturnGameStateModel() {
        when(gameStateRepository.findById(stateId)).thenReturn(Optional.ofNullable(gameStateEntity));
        when(gameStateRepository.save(gameStateEntity)).thenReturn(gameStateEntity);

        GameState updateReturn = gameStateService.updateGameState(stateId, gameState);

        Assertions.assertThat(updateReturn).isNotNull();
    }

    @Test
    public void GameStateService_DeleteGameStateById_ReturnVoid() {
        when(gameStateRepository.findById(stateId)).thenReturn(Optional.ofNullable(gameStateEntity));
        doNothing().when(gameStateRepository).delete(gameStateEntity);

        assertAll(() -> gameStateService.getGameStateById(stateId));
    }
}

