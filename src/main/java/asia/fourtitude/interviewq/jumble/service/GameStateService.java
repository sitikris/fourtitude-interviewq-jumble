package asia.fourtitude.interviewq.jumble.service;

import asia.fourtitude.interviewq.jumble.core.GameState;

import java.util.List;

public interface GameStateService {
    GameState saveGameState(GameState gameStateModel);

    List<GameState> getAllGameStates();

    GameState getGameStateById(String id);

    GameState updateGameState(String id, GameState updatedGameModel);

    void deleteGameState(String id);

    List<String> getGuessedWords(String stateId);

    String getScrambleAsDisplay(String oldWord);
}
