package asia.fourtitude.interviewq.jumble.service;

import asia.fourtitude.interviewq.jumble.model.GameBoard;

import java.util.List;

public interface GameBoardService {
    GameBoard saveGameBoard(GameBoard gameBoard);

    List<GameBoard> findByStateIdAndWord(GameBoard gameBoard);

    List<GameBoard> getAllGameBoards();

    List<GameBoard> getAllGameBoardsByStateId(String stateId);

    GameBoard getGameBoardById(String id);

    GameBoard updateGameBoard(String id, GameBoard updatedGameModel);

    void deleteGameBoard(String id);
}
