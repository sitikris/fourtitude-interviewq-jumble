package asia.fourtitude.interviewq.jumble.service;

import asia.fourtitude.interviewq.jumble.entity.GameBoardEntity;
import asia.fourtitude.interviewq.jumble.exception.ResourceNotFoundException;
import asia.fourtitude.interviewq.jumble.mapper.GameBoardMapper;
import asia.fourtitude.interviewq.jumble.model.GameBoard;
import asia.fourtitude.interviewq.jumble.repository.GameBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameBoardServiceImpl implements GameBoardService {
    private final GameBoardRepository gameBoardRepository;

    private final GameBoardMapper gameBoardMapper;

    @Override
    @Transactional
    public GameBoard saveGameBoard(GameBoard gameBoard) {
        GameBoardEntity entity = gameBoardMapper.toEntity(gameBoard);
        GameBoardEntity savedEntity = gameBoardRepository.save(entity);
        return gameBoardMapper.toModel(savedEntity);
    }

    @Override
    public List<GameBoard> getAllGameBoards() {
        return gameBoardMapper.toModelList(gameBoardRepository.findAll());
    }

    @Override
    public List<GameBoard> getAllGameBoardsByStateId(String stateId) {
        Optional<List<GameBoardEntity>> subWordOpt = gameBoardRepository.findByStateId(stateId);
        return subWordOpt.map(gameBoardMapper::toModelList).orElse(null);
    }

    @Override
    public List<GameBoard> findByStateIdAndWord(GameBoard gameBoard) {
        Optional<List<GameBoardEntity>> subWordOpt = gameBoardRepository.findByStateIdAndWord(gameBoard.getStateId(), gameBoard.getWord());
        return subWordOpt.map(gameBoardMapper::toModelList).orElse(null);
    }

    @Override
    public GameBoard getGameBoardById(String id) {
        return gameBoardRepository.findById(id)
                .map(gameBoardMapper::toModel)
                .orElseThrow(() -> new RuntimeException("GameBoard not found"));
    }

    @Override
    @Transactional
    public GameBoard updateGameBoard(String id, GameBoard gameBoard) {
        return gameBoardRepository.findById(id)
                .map(existingGameState -> gameBoardMapper.toModel(gameBoardRepository.save(existingGameState)))
                .orElseThrow(() -> new RuntimeException("GameBoard not found"));
    }

    @Override
    public void deleteGameBoard(String id) {
        GameBoardEntity entity = gameBoardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GameBoardEntity could not be delete"));
        gameBoardRepository.delete(entity);
    }

}
