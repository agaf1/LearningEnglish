package pl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.service.domain.Game;

import java.util.List;

@Repository
 class GameRepositoryImp implements GameRepository{

    @Autowired
    private GameJpa gameJpa;

    @Autowired
    private MapperGameEntity mapperGameEntity;

    @Override
    public List<Game> findAll() {
        List<GameEntity> gamesEntity = gameJpa.findAll();
       return gamesEntity.stream().map(mapperGameEntity::mapToGame).toList();
    }
}
