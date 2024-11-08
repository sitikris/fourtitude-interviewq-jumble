package asia.fourtitude.interviewq.jumble.controller;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import asia.fourtitude.interviewq.jumble.model.GameBoard;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(path = "/game")
@SessionAttributes("board")
public class GameWebController {

    private static final Logger LOG = LoggerFactory.getLogger(GameWebController.class);

    private final JumbleEngine jumbleEngine;

    @Autowired(required = true)
    public GameWebController(JumbleEngine jumbleEngine) {
        this.jumbleEngine = jumbleEngine;
    }

    @ModelAttribute("board")
    public GameBoard gameBoard() {
        /*
         * This method with "@ModelAttribute" annotation, is so that
         * Spring can create/initialize an attribute into session scope.
         */
        return new GameBoard();
    }

    private void scrambleWord(GameBoard board) {
        if (board.getState() != null) {
            String oldScramble = board.getState().getScramble();
            int num = 0;
            do {
                String scramble = this.jumbleEngine.scramble(board.getState().getOriginal());
                board.getState().setScramble(scramble);
                num += 1;
            } while (oldScramble.equals(board.getState().getScramble()) && num <= 10);
        }
    }

    @GetMapping(path = "/goodbye")
    public String goodbye(SessionStatus status) {
        status.setComplete();
        return "game/board";
    }

    @GetMapping("/help")
    public String doGetHelp() {
        return "game/help";
    }

    @GetMapping("/new")
    public String doGetNew(
            @Valid @ModelAttribute(name = "board") GameBoard board,
            BindingResult bindingResult, Model model) {
        GameState state = this.jumbleEngine.createGameState(6, 3);

        /*
         * TODO:
         * a) Assign the created game `state` (with randomly picked word) into
         *        game `board` (session attribute)
         * b) Presentation page to show the information of game board/state
         * c) Must pass the corresponding unit tests
         */

        board.setState(state);
        if (board.getWord() == null) {
            board.setWord("");
        }
        model.addAttribute("board", board);

        return "game/board";
    }

    @GetMapping("/play")
    public String doGetPlay(@ModelAttribute(name = "board") GameBoard board) {
        scrambleWord(board);

        return "game/board";
    }

    @PostMapping("/play")
    public String doPostPlay(
            @Valid @ModelAttribute(name = "board") GameBoard board,
            BindingResult bindingResult, Model model) {
        if (board == null || board.getState() == null) {
            // session expired
            return "game/board";
        }

        scrambleWord(board);

        /*
         * TODO:
         * a) Validate the input `word`
         * b) From the input guessing `word`, implement the game logic
         * c) Update the game `board` (session attribute)
         * d) Show the error: "Guessed incorrectly", when word is guessed incorrectly.
         * e) Presentation page to show the information of game board/state
         * f) Must pass the corresponding unit tests
         */
        List<String> guessedWords = board.getState().getGuessedWords();
        log.debug("Sub words : {}", board.getState().getSubWords());

        int remaining = board.getState().getSubWords().size() - guessedWords.size();
        log.debug("Total possible : {}", board.getState().getSubWords().size());
        if (guessedWords.isEmpty()) {
            log.debug("No word guessed yet.");
        } else {
            log.debug("Words guessed  : {}", guessedWords.size());
            int pos = 0;
            for (String word : guessedWords) {
                pos += 1;
                log.debug("{}. {}", pos, word);
            }
        }
        if (remaining > 0) {
            String scramble = jumbleEngine.scramble(board.getState().getOriginal());
            board.getState().setScramble(scramble);

            log.debug("Remaining words: {}", remaining);
            log.debug("Original       : {}", board.getState().getOriginal());
            log.debug("Letters        : {}", board.getState().getScrambleAsDisplay());

            String word = board.getWord().trim();
            if (board.getState().updateGuessWord(word)) {
                log.debug("[{}] guessed correctly", word);
            } else {
                log.debug("[{}] guessed incorrectly", word);
            }
        }

        model.addAttribute("board", board);

        return "game/board";
    }

}
