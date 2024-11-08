package asia.fourtitude.interviewq.jumble.mapper;

import asia.fourtitude.interviewq.jumble.entity.GameBoardEntity;
import asia.fourtitude.interviewq.jumble.model.GameBoard;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GameBoardMapper extends BaseMapper<GameBoardEntity, GameBoard> {
    GameBoardMapper INSTANCE = Mappers.getMapper(GameBoardMapper.class);

}
