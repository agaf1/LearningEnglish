package pl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.service.domain.GameTable;

import java.util.List;

@Repository
@RequiredArgsConstructor
class GameTableRepositoryImp implements GameTableRepository{

    private final GameTableJpa gameTableJpa;
    private final MapperPhraseEntity mapperPhraseEntity;

    @Override
    @Transactional(readOnly = false)
    public GameTable save(GameTable gameTable) {
        GameTableEntity saved = gameTableJpa.save(GameTableEntity.create(gameTable));
        return GameTableEntity.getGameTable(saved);
    }

    @Override
    public void clearAllGameTable(){
        gameTableJpa.deleteAll();
    }

    @Override
    public List<GameTable> getAll(){
        List<GameTableEntity> gameTableEntities = (List<GameTableEntity>) gameTableJpa.findAll();
        return gameTableEntities.stream().map(g -> GameTableEntity.getGameTable(g)).toList();
    }

    @Override
    public void deletePhrase(Integer phraseId){
        GameTableEntity gameTableEntity = gameTableJpa.findAllByPhraseId(phraseId);
        gameTableJpa.delete(gameTableEntity);
    }

}
