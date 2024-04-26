package pl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.service.domain.Game;

import java.util.List;

@Repository
@RequiredArgsConstructor
class GameRepositoryImp implements GameRepository {

    private final GameJpa gameJpa;
    private final MapperGameEntity mapperGameEntity;

    @Override
    public List<Game> findAll() {
        List<GameEntity> gamesEntity = gameJpa.findAll();
        return gamesEntity.stream().map(mapperGameEntity::mapToGame).toList();
    }
}
