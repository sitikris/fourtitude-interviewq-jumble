package asia.fourtitude.interviewq.jumble.mapper;

import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

public interface BaseMapper<E, M> {

  @Mappings({
  })
  E toEntity(M model);

  @Mappings({
  })
  M toModel(E entity);

  @Mappings({
  })
  List<M> toModelList(List<E> entities);

  @Mappings({
  })
  List<E> toEntityList(List<M> models);

  @Mappings({
  })
  E update(@MappingTarget E entity, E updates);

}

