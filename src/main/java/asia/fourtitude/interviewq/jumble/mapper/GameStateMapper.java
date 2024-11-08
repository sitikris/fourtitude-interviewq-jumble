package asia.fourtitude.interviewq.jumble.mapper;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.entity.GameStateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GameStateMapper extends BaseMapper<GameStateEntity, GameState> {
    GameStateMapper INSTANCE = Mappers.getMapper(GameStateMapper.class);
}
