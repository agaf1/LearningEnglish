package pl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.service.domain.GameTable;
@Repository
@RequiredArgsConstructor
class GameTableRepositoryImp implements GameTableRepository{

    private final GameTableJpa gameTableJpa;

    @Override
    @Transactional
    public GameTable save(GameTable gameTable) {
        GameTableEntity saved = gameTableJpa.save(GameTableEntity.create(gameTable));
        return GameTableEntity.getGameTable(saved);
    }
}
