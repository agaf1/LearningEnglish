package pl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.service.domain.Game;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class GameRepositoryImp implements GameRepository {

    private final GameJpa gameJpa;
    private final MapperGameEntity mapperGameEntity;

    @Override
    @Transactional
    public Game save(Game game){
        GameEntity gameEntity = gameJpa.save(mapperGameEntity.mapToEntity(game));
        return mapperGameEntity.mapToGame(gameEntity);
    }

    @Override
    @Transactional(readOnly = false)
    public List<Game> findAll() {
        List<GameEntity> gamesEntity = gameJpa.findAll();
        return gamesEntity.stream().map(mapperGameEntity::mapToGame).toList();
    }
    @Override
    @Transactional(readOnly = false)
    public Game findByIdOrThrow(Integer id) {
        GameEntity gamesEntity = gameJpa.findById(id).orElseThrow();
        return mapperGameEntity.mapToGame(gamesEntity);
    }
}
