package asia.fourtitude.interviewq.jumble.controller;

import asia.fourtitude.interviewq.jumble.TestConfig;
import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import asia.fourtitude.interviewq.jumble.entity.GameBoardEntity;
import asia.fourtitude.interviewq.jumble.entity.GameStateEntity;
import asia.fourtitude.interviewq.jumble.model.GameBoard;
import asia.fourtitude.interviewq.jumble.model.GameGuessInput;
import asia.fourtitude.interviewq.jumble.model.GameGuessOutput;
import asia.fourtitude.interviewq.jumble.repository.GameBoardRepository;
import asia.fourtitude.interviewq.jumble.repository.GameStateRepository;
import asia.fourtitude.interviewq.jumble.service.GameBoardService;
import asia.fourtitude.interviewq.jumble.service.GameStateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameApiController.class)
@Import(TestConfig.class)
class GameApiControllerTest {

    @MockBean
    private GameStateService gameStateService;

    @MockBean
    private GameBoardService gameBoardService;

    @Mock
    private GameStateRepository gameStateRepository;

    @Mock
    private GameBoardRepository gameBoardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    JumbleEngine jumbleEngine;

    private String boardId = "d0ea2e92-455a-4009-9605-556f443437c5";
    private String stateId = "f2166472-9fe1-4270-a996-b22f4854e10c";
    private String word = "ample";
    private String original = "example";
    private String scramble = "xpmleae";
    private String createdUser = "a";
    private Date current = new Date();

    private GameState gameState;
    private GameStateEntity gameStateEntity;
    private List<GameStateEntity> gameStateEntityList = new ArrayList<>();
    private List<GameBoardEntity> gameBoardEntityList = new ArrayList<>();
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

        gameState = GameState.builder().id(stateId).original(original).scramble(scramble).subWords(subWords).build();
        gameStateEntity = GameStateEntity.builder().id(stateId).original(original).scramble(scramble).subWords(subWords).build();
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
        gameBoardEntityList.add(gameBoardEntity);
        guessedWords.addAll(subWords.keySet().stream().toList());

    }

    /*
     * NOTE: Refer to "RootControllerTest.java", "GameWebControllerTest.java"
     * as reference. Search internet for resource/tutorial/help in implementing
     * the unit tests.
     *
     * Refer to "http://localhost:8080/swagger-ui/index.html" for REST API
     * documentation and perform testing.
     *
     * Refer to Postman collection ("interviewq-jumble.postman_collection.json")
     * for REST API documentation and perform testing.
     */

    @Test
    void whenCreateNewGame_thenSuccess() throws Exception {
        /*
         * Doing HTTP GET "/api/game/new"
         *
         * Input: None
         *
         * Expect: Assert these
         * a) HTTP status == 200
         * b) `result` equals "Created new game."
         * c) `id` is not null
         * d) `originalWord` is not null
         * e) `scrambleWord` is not null
         * f) `totalWords` > 0
         * g) `remainingWords` > 0 and same as `totalWords`
         * h) `guessedWords` is empty list
         */

        // Mock the service call
        when(jumbleEngine.createGameState(6, 3)).thenReturn(gameState);
        when(gameStateService.saveGameState(gameState)).thenReturn(gameState);

        // Perform GET request and check assertions
        mockMvc.perform(get("/api/game/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(output))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("Created new game."))
                .andExpect(jsonPath("$.id").isNotEmpty());


        // assertTrue(false, "to be implemented");
    }

    @Test
    void givenMissingId_whenPlayGame_thenInvalidId() throws Exception {
        /*
         * Doing HTTP POST "/api/game/guess"
         *
         * Input: JSON request body
         * a) `id` is null or missing
         * b) `word` is null/anything or missing
         *
         * Expect: Assert these
         * a) HTTP status == 404
         * b) `result` equals "Invalid Game ID."
         */
        mockMvc.perform(post("/api/game/guess")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.result").value("Invalid Game ID."));

        // assertTrue(false, "to be implemented");
    }

    @Test
    void givenMissingRecord_whenPlayGame_thenRecordNotFound() throws Exception {
        /*
         * Doing HTTP POST "/api/game/guess"
         *
         * Input: JSON request body
         * a) `id` is some valid ID (but not exists in game system)
         * b) `word` is null/anything or missing
         *
         * Expect: Assert these
         * a) HTTP status == 404
         * b) `result` equals "Game board/state not found."
         */
        mockMvc.perform(post("/api/game/guess")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.result").value("Game board/state not found."));
        // assertTrue(false, "to be implemented");
    }

    @Test
    void givenCreateNewGame_whenSubmitNullWord_thenGuessedIncorrectly() throws Exception {
        /*
         * Doing HTTP POST "/api/game/guess"
         *
         * Given:
         * a) has valid game ID from previously created game
         *
         * Input: JSON request body
         * a) `id` of previously created game
         * b) `word` is null or missing
         *
         * Expect: Assert these
         * a) HTTP status == 200
         * b) `result` equals "Guessed incorrectly."
         * c) `id` equals to `id` of this game
         * d) `originalWord` is equals to `originalWord` of this game
         * e) `scrambleWord` is not null
         * f) `guessWord` is equals to `input.word`
         * g) `totalWords` is equals to `totalWords` of this game
         * h) `remainingWords` is equals to `remainingWords` of previous game state (no change)
         * i) `guessedWords` is empty list (because this is first attempt)
         */

        // Call new game
        when(jumbleEngine.createGameState(6, 3)).thenReturn(gameState);
        when(gameStateService.saveGameState(gameState)).thenReturn(gameState);

        // Call play game
        when(gameStateRepository.findById(stateId)).thenReturn(Optional.of(gameStateEntity));
        when(gameStateService.getGameStateById(input.getId())).thenReturn(gameState);
        when(gameBoardRepository.save(gameBoardEntity)).thenReturn(gameBoardEntity);

        gameBoardService.saveGameBoard(gameBoard);
        gameState.setSubWords(new HashMap<>());
        gameStateService.updateGameState(gameState.getId(), gameState);

        when(gameStateRepository.findById(stateId)).thenReturn(Optional.of(gameStateEntity));
        when(gameStateService.getGuessedWords(gameState.getId())).thenReturn(guessedWords);
        when(gameStateService.getScrambleAsDisplay(gameState.getScramble())).thenReturn(gameState.getScramble());

        input.setWord(null);
        ResultActions response = mockMvc.perform(post("/api/game/guess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        );

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", CoreMatchers.is("Guessed incorrectly.")))
                .andExpect(jsonPath("$.id", CoreMatchers.is(stateId)))
                .andExpect(jsonPath("$.original_word", CoreMatchers.is(original)))
                .andExpect(jsonPath("$.scramble_word").isNotEmpty())
                .andExpect(jsonPath("$.guess_word", CoreMatchers.is(input.getWord())))
                .andExpect(jsonPath("$.total_words", CoreMatchers.is(gameState.getSubWords().size())))
                .andExpect(jsonPath("$.remaining_words", CoreMatchers.is(gameState.getSubWords().size() - guessedWords.size())))
                .andExpect(jsonPath("$.guessed_words").isNotEmpty());

        // assertTrue(false, "to be implemented");
    }

    @Test
    void givenCreateNewGame_whenSubmitWrongWord_thenGuessedIncorrectly() throws Exception {
        /*
         * Doing HTTP POST "/api/game/guess"
         *
         * Given:
         * a) has valid game ID from previously created game
         *
         * Input: JSON request body
         * a) `id` of previously created game
         * b) `word` is some value (that is not correct answer)
         *
         * Expect: Assert these
         * a) HTTP status == 200
         * b) `result` equals "Guessed incorrectly."
         * c) `id` equals to `id` of this game
         * d) `originalWord` is equals to `originalWord` of this game
         * e) `scrambleWord` is not null
         * f) `guessWord` equals to input `guessWord`
         * g) `totalWords` is equals to `totalWords` of this game
         * h) `remainingWords` is equals to `remainingWords` of previous game state (no change)
         * i) `guessedWords` is empty list (because this is first attempt)
         */

        // Call new game
        when(jumbleEngine.createGameState(6, 3)).thenReturn(gameState);
        when(gameStateService.saveGameState(gameState)).thenReturn(gameState);

        // Call play game
        when(gameStateRepository.findById(stateId)).thenReturn(Optional.of(gameStateEntity));
        when(gameStateService.getGameStateById(input.getId())).thenReturn(gameState);
        when(gameBoardRepository.save(gameBoardEntity)).thenReturn(gameBoardEntity);

        gameBoardService.saveGameBoard(gameBoard);
        gameState.setSubWords(new HashMap<>());
        gameStateService.updateGameState(gameState.getId(), gameState);

        when(gameStateRepository.findById(stateId)).thenReturn(Optional.of(gameStateEntity));
        when(gameStateService.getGuessedWords(gameState.getId())).thenReturn(guessedWords);
        when(gameStateService.getScrambleAsDisplay(gameState.getScramble())).thenReturn(gameState.getScramble());

        input.setWord("xxxxx");
        ResultActions response = mockMvc.perform(post("/api/game/guess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        );

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", CoreMatchers.is("Guessed incorrectly.")))
                .andExpect(jsonPath("$.id", CoreMatchers.is(stateId)))
                .andExpect(jsonPath("$.original_word", CoreMatchers.is(original)))
                .andExpect(jsonPath("$.scramble_word").isNotEmpty())
                .andExpect(jsonPath("$.guess_word", CoreMatchers.is(input.getWord())))
                .andExpect(jsonPath("$.total_words", CoreMatchers.is(gameState.getSubWords().size())))
                .andExpect(jsonPath("$.remaining_words", CoreMatchers.is(gameState.getSubWords().size() - guessedWords.size())))
                .andExpect(jsonPath("$.guessed_words").isNotEmpty());

        // fail("to be implemented");
    }

    @Test
    void givenCreateNewGame_whenSubmitFirstCorrectWord_thenGuessedCorrectly() throws Exception {
        /*
         * Doing HTTP POST "/api/game/guess"
         *
         * Given:
         * a) has valid game ID from previously created game
         *
         * Input: JSON request body
         * a) `id` of previously created game
         * b) `word` is of correct answer
         *
         * Expect: Assert these
         * a) HTTP status == 200
         * b) `result` equals "Guessed correctly."
         * c) `id` equals to `id` of this game
         * d) `originalWord` is equals to `originalWord` of this game
         * e) `scrambleWord` is not null
         * f) `guessWord` equals to input `guessWord`
         * g) `totalWords` is equals to `totalWords` of this game
         * h) `remainingWords` is equals to `remainingWords - 1` of previous game state (decrement by 1)
         * i) `guessedWords` is not empty list
         * j) `guessWords` contains input `guessWord`
         */

        // Call new game
        when(jumbleEngine.createGameState(6, 3)).thenReturn(gameState);
        when(gameStateService.saveGameState(gameState)).thenReturn(gameState);

        // Call play game
        when(gameStateRepository.findById(stateId)).thenReturn(Optional.of(gameStateEntity));
        when(gameStateService.getGameStateById(input.getId())).thenReturn(gameState);
        when(gameBoardRepository.save(gameBoardEntity)).thenReturn(gameBoardEntity);

        gameBoardService.saveGameBoard(gameBoard);

        subWords = new HashMap<>();
        subWords.put("ample", false);
        gameState.setSubWords(subWords);
        gameStateService.updateGameState(gameState.getId(), gameState);

        when(gameStateRepository.findById(stateId)).thenReturn(Optional.of(gameStateEntity));
        when(gameStateService.getGuessedWords(gameState.getId())).thenReturn(guessedWords);
        when(gameStateService.getScrambleAsDisplay(gameState.getScramble())).thenReturn(gameState.getScramble());

        input.setWord("ample");
        ResultActions response = mockMvc.perform(post("/api/game/guess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        );

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", CoreMatchers.is("Guessed correctly.")))
                .andExpect(jsonPath("$.id", CoreMatchers.is(stateId)))
                .andExpect(jsonPath("$.original_word", CoreMatchers.is(original)))
                .andExpect(jsonPath("$.scramble_word").isNotEmpty())
                .andExpect(jsonPath("$.guess_word", CoreMatchers.is(input.getWord())))
                .andExpect(jsonPath("$.total_words", CoreMatchers.is(gameState.getSubWords().size())))
                // `remainingWords` is equals to `remainingWords - 1` of previous game state (decrement by 1)
                .andExpect(jsonPath("$.remaining_words", CoreMatchers.is((gameState.getSubWords().size() - guessedWords.size()))))
                .andExpect(jsonPath("$.guessed_words").isNotEmpty());

        // fail("to be implemented");
    }

    @Test
    void givenCreateNewGame_whenSubmitAllCorrectWord_thenAllGuessed() throws Exception {
        /*
         * Doing HTTP POST "/api/game/guess"
         *
         * Given:
         * a) has valid game ID from previously created game
         * b) has submit all correct answers, except the last answer
         *
         * Input: JSON request body
         * a) `id` of previously created game
         * b) `word` is of the last correct answer
         *
         * Expect: Assert these
         * a) HTTP status == 200
         * b) `result` equals "All words guessed."
         * c) `id` equals to `id` of this game
         * d) `originalWord` is equals to `originalWord` of this game
         * e) `scrambleWord` is not null
         * f) `guessWord` equals to input `guessWord`
         * g) `totalWords` is equals to `totalWords` of this game
         * h) `remainingWords` is 0 (no more remaining, game ended)
         * i) `guessedWords` is not empty list
         * j) `guessWords` contains input `guessWord`
         */

        // Call new game
        when(jumbleEngine.createGameState(6, 3)).thenReturn(gameState);
        when(gameStateService.saveGameState(gameState)).thenReturn(gameState);

        // Call play game
        when(gameStateRepository.findById(stateId)).thenReturn(Optional.of(gameStateEntity));
        when(gameStateService.getGameStateById(input.getId())).thenReturn(gameState);
        when(gameBoardRepository.save(gameBoardEntity)).thenReturn(gameBoardEntity);

        gameBoardService.saveGameBoard(gameBoard);
        gameState.setSubWords(subWords);
        gameStateService.updateGameState(gameState.getId(), gameState);

        when(gameStateRepository.findById(stateId)).thenReturn(Optional.of(gameStateEntity));
        when(gameStateService.getGuessedWords(gameState.getId())).thenReturn(guessedWords);
        when(gameStateService.getScrambleAsDisplay(gameState.getScramble())).thenReturn(gameState.getScramble());

        ResultActions response = mockMvc.perform(post("/api/game/guess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
        );

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.result", CoreMatchers.is("All words guessed.")))
                .andExpect(jsonPath("$.id", CoreMatchers.is(stateId)))
                .andExpect(jsonPath("$.original_word", CoreMatchers.is(original)))
                .andExpect(jsonPath("$.scramble_word").isNotEmpty())
                .andExpect(jsonPath("$.guess_word", CoreMatchers.is(input.getWord())))
                .andExpect(jsonPath("$.total_words", CoreMatchers.is(gameState.getSubWords().size())))
                .andExpect(jsonPath("$.remaining_words", CoreMatchers.is(0)))
                .andExpect(jsonPath("$.guessed_words").isNotEmpty());

        // fail("to be implemented");
    }

}
