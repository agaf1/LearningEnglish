package pl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.service.domain.GameTable;
@Repository
@RequiredArgsConstructor
class GameTableRepositoryImp implements GameTableRepository{

    private final GameTableJpa gameTableJpa;

    @Override
    public GameTable save(GameTable gameTable) {
        GameTableEntity saved = gameTableJpa.save(GameTableEntity.create(gameTable));
        return GameTableEntity.getGameTable(saved);
    }
}
