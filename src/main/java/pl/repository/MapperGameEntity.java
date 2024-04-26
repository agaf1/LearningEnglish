package pl.repository;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.service.domain.Game;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface MapperGameEntity {

    Game mapToGame(GameEntity gameEntity);

    GameEntity mapToEntity(Game game);
}
