package asia.fourtitude.interviewq.jumble.controller;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import asia.fourtitude.interviewq.jumble.model.GameBoard;
import asia.fourtitude.interviewq.jumble.model.GameGuessInput;
import asia.fourtitude.interviewq.jumble.model.GameGuessOutput;
import asia.fourtitude.interviewq.jumble.service.GameBoardService;
import asia.fourtitude.interviewq.jumble.service.GameStateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@Tag(name = "Game API", description = "Guessing words game REST API endpoint.")
@RequestMapping(path = "/api/game")
@RequiredArgsConstructor
public class GameApiController {

    private final GameStateService gameStateService;

    private final GameBoardService gameBoardService;

    private final JumbleEngine jumbleEngine;

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @Operation(
            summary = "Creates new game board/state",
            description = "Creates a new game board/state and registered into game engine referenced by `id`. All subsequent operation/play is tied to `id`.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = GameGuessOutput.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Success",
                                                    description = "Created a new game/board and registered into system.",
                                                    value = "{\n" +
                                                            "  \"result\": \"Created new game.\",\n" +
                                                            "  \"id\": \"65e0d7a4-59bf-4065-beb1-3c2220d87e1e\",\n" +
                                                            "  \"original_word\": \"titans\",\n" +
                                                            "  \"scramble_word\": \"nisatt\",\n" +
                                                            "  \"total_words\": 29,\n" +
                                                            "  \"remaining_words\": 29,\n" +
                                                            "  \"guessed_words\": []\n" +
                                                            "}")}))})
    @GetMapping(value = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameGuessOutput> newGame() {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        GameGuessOutput output = new GameGuessOutput();

        GameState gameState = this.jumbleEngine.createGameState(6, 3);

        /*
         * TODO:
         * a) Store the game state to the repository, with unique game board ID
         * b) Return the game board/state (GameGuessOutput) to caller
         */

        GameState savedGameState = gameStateService.saveGameState(gameState);

        output.setId(savedGameState.getId());
        output.setResult("Created new game.");
        output.setOriginalWord(gameState.getOriginal());
        output.setScrambleWord(gameStateService.getScrambleAsDisplay(gameState.getScramble()));
        output.setGuessedWords(new ArrayList<>());
        output.setTotalWords(gameState.getSubWords().size());
        int remaining = gameState.getSubWords().size() - gameState.getGuessedWords().size();
        output.setRemainingWords(remaining);

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @Operation(
            summary = "Submits word to play the game",
            description = "Submits a guessed `word`, along with `id` to play the game.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = GameGuessOutput.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Guessed Correctly First Time",
                                                    description = "Guessed correctly the first time.",
                                                    value = "{\n" +
                                                            "  \"result\": \"Guessed correctly.\",\n" +
                                                            "  \"id\": \"88b4278c-5141-42af-86e6-2a1d4cfa5f3b\",\n" +
                                                            "  \"original_word\": \"ranker\",\n" +
                                                            "  \"scramble_word\": \"nekarr\",\n" +
                                                            "  \"guess_word\": \"rank\",\n" +
                                                            "  \"total_words\": 15,\n" +
                                                            "  \"remaining_words\": 14,\n" +
                                                            "  \"guessed_words\": [\n" +
                                                            "    \"rank\"\n" +
                                                            "  ]\n" +
                                                            "}"),
                                            @ExampleObject(
                                                    name = "Guessed Correctly Subsequent",
                                                    description = "Guessed correctly with subsequent word.",
                                                    value = "{\n" +
                                                            "  \"result\": \"Guessed correctly.\",\n" +
                                                            "  \"id\": \"e23a268c-e4af-4123-a610-755e34ac201c\",\n" +
                                                            "  \"original_word\": \"burger\",\n" +
                                                            "  \"scramble_word\": \"rerugb\",\n" +
                                                            "  \"guess_word\": \"rug\",\n" +
                                                            "  \"total_words\": 15,\n" +
                                                            "  \"remaining_words\": 7,\n" +
                                                            "  \"guessed_words\": [\n" +
                                                            "    \"bug\",\n" +
                                                            "    \"bur\",\n" +
                                                            "    \"err\",\n" +
                                                            "    \"rug\",\n" +
                                                            "    \"burr\",\n" +
                                                            "    \"grub\",\n" +
                                                            "    \"rube\",\n" +
                                                            "    \"urge\"\n" +
                                                            "  ]\n" +
                                                            "}"),
                                            @ExampleObject(
                                                    name = "Guessed Incorrectly",
                                                    description = "Guessed with incorrect word.",
                                                    value = "{\n" +
                                                            "  \"result\": \"Guessed incorrectly.\",\n" +
                                                            "  \"id\": \"88b4278c-5141-42af-86e6-2a1d4cfa5f3b\",\n" +
                                                            "  \"original_word\": \"ranker\",\n" +
                                                            "  \"scramble_word\": \"rnraek\",\n" +
                                                            "  \"guess_word\": \"answer\",\n" +
                                                            "  \"total_words\": 15,\n" +
                                                            "  \"remaining_words\": 15,\n" +
                                                            "  \"guessed_words\": []\n" +
                                                            "}"),
                                            @ExampleObject(
                                                    name = "All Guessed",
                                                    description = "All words guessed.",
                                                    value = "{\n" +
                                                            "  \"result\": \"All words guessed.\",\n" +
                                                            "  \"id\": \"353ee769-a472-4704-a5f2-d525f181a03e\",\n" +
                                                            "  \"original_word\": \"gloomy\",\n" +
                                                            "  \"scramble_word\": \"gomlyo\",\n" +
                                                            "  \"guess_word\": \"moo\",\n" +
                                                            "  \"total_words\": 9,\n" +
                                                            "  \"remaining_words\": 0,\n" +
                                                            "  \"guessed_words\": [\n" +
                                                            "    \"goo\",\n" +
                                                            "    \"gym\",\n" +
                                                            "    \"log\",\n" +
                                                            "    \"loo\",\n" +
                                                            "    \"moo\",\n" +
                                                            "    \"glom\",\n" +
                                                            "    \"logo\",\n" +
                                                            "    \"loom\",\n" +
                                                            "    \"gloom\"\n" +
                                                            "  ]\n" +
                                                            "}")})),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = GameGuessOutput.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Invalid ID",
                                                    description = "The input `ID` is invalid.",
                                                    value = "{\n" +
                                                            "  \"result\": \"Invalid Game ID.\"\n" +
                                                            "}"),
                                            @ExampleObject(
                                                    name = "Record not found",
                                                    description = "The `ID` is correct format, but game board/state is not found in system.",
                                                    value = "{\n" +
                                                            "  \"result\": \"Game board/state not found.\"\n" +
                                                            "}")}))})
    @PostMapping(value = "/guess", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameGuessOutput> playGame(
            @Parameter(
                    description = "Submits the `word` to guess.",
                    required = true,
                    schema = @Schema(implementation = GameGuessInput.class),
                    example = "{\n" +
                            "  \"id\": \"4579256c-326f-4169-9b56-6d1d1a2c11f0\",\n" +
                            "  \"word\": \"answer\"\n" +
                            "}")
            @RequestBody GameGuessInput input) {
        /*
         * Refer to the method's Javadoc (above) and implement accordingly.
         * Must pass the corresponding unit tests.
         */
        GameGuessOutput output = new GameGuessOutput();

        /*
         * TODO:
         * a) Validate the input (GameGuessInput)
         * b) Check records exists in repository (search by input `id`)
         * c) From the input guessing `word`, implement the game logic
         * d) Update the game board (and game state) in repository
         * e) Return the updated game board/state (GameGuessOutput) to caller
         */

        try {

            if (Objects.isNull(input.getId()) && Objects.isNull(input.getWord())) {
                output.setResult("Invalid Game ID.");
                return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
            }

            GameState gameState = gameStateService.getGameStateById(input.getId());
            if (gameState == null) {
                output.setResult("Game board/state not found.");
                return new ResponseEntity<>(output, HttpStatus.NOT_FOUND);
            }

            if (gameState.getSubWords().containsKey(input.getWord())) {
                GameBoard gameBoard = new GameBoard();
                gameBoard.setStateId(gameState.getId());
                gameBoard.setWord(input.getWord());
                gameBoardService.saveGameBoard(gameBoard);

                gameState.setWord(input.getWord());
                gameStateService.updateGameState(gameState.getId(), gameState);

                output.setResult("Guessed correctly.");

            } else {
                output.setResult("Guessed incorrectly.");
            }

            List<String> guessedWords = gameStateService.getGuessedWords(gameState.getId());
            int remaining = gameState.getSubWords().size() - guessedWords.size();

            output.setId(gameState.getId());
            output.setOriginalWord(gameState.getOriginal());
            output.setScrambleWord(gameStateService.getScrambleAsDisplay(gameState.getScramble()));
            output.setRemainingWords(remaining);
            output.setGuessedWords(guessedWords);
            output.setGuessWord(input.getWord());
            output.setResult(remaining == 0 ? "All words guessed." : output.getResult());
            output.setTotalWords(gameState.getSubWords().size());

            log.debug("GameGuessOutput : \n{}", objectWriter.writeValueAsString(output));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
