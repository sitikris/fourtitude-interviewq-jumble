package asia.fourtitude.interviewq.jumble.service;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import asia.fourtitude.interviewq.jumble.entity.GameStateEntity;
import asia.fourtitude.interviewq.jumble.exception.ResourceNotFoundException;
import asia.fourtitude.interviewq.jumble.mapper.GameStateMapper;
import asia.fourtitude.interviewq.jumble.repository.GameStateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameStateServiceImpl implements GameStateService {
    private final GameStateRepository gameStateRepository;

    private final GameStateMapper gameStateMapper;

    private final JumbleEngine jumbleEngine;

    @Override
    @Transactional
    public GameState saveGameState(GameState gameState) {
        GameStateEntity entity = gameStateMapper.toEntity(gameState);
        entity.getSubWords().forEach((s, aBoolean) -> entity.getSubWords().put(s, false));
        GameStateEntity savedGameState = gameStateRepository.save(entity);
        return gameStateMapper.toModel(savedGameState);
    }

    @Override
    public List<GameState> getAllGameStates() {
        return gameStateRepository.findAll().stream()
                .map(gameStateMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public GameState getGameStateById(String id) {
        return gameStateRepository.findById(id)
                .map(gameStateMapper::toModel)
                .orElseThrow(() -> new RuntimeException("GameState not found"));
    }

    @Override
    @Transactional
    public GameState updateGameState(String id, GameState gameState) {
        return gameStateRepository.findById(id)
                .map(existingGameState -> {
                    existingGameState.getSubWords().put(gameState.getWord(), true);
                    return gameStateMapper.toModel(gameStateRepository.save(existingGameState));
                })
                .orElseThrow(() -> new RuntimeException("GameState not found"));
    }

    @Override
    public void deleteGameState(String id) {
        GameStateEntity pokemon = gameStateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("GameStateEntity could not be delete"));
        gameStateRepository.delete(pokemon);
    }

    @Override
    public List<String> getGuessedWords(String stateId) {
        GameStateEntity entity = gameStateRepository.findById(stateId)
                .orElseThrow(() -> new RuntimeException("GameStateEntity not found"));
        return entity.getSubWords().entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey).toList();
    }

    @Override
    public String getScrambleAsDisplay(String scramble) {
        List<String> list = new ArrayList<>();
        for (char ch : jumbleEngine.scramble(scramble).toCharArray()) {
            list.add(Character.toString(ch));
        }
        return String.join(" ", list);
    }

}
