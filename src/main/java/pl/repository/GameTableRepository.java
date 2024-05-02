package pl.repository;

import pl.service.domain.GameTable;

import java.util.List;

public interface GameTableRepository {

    GameTable save(GameTable gameTable);

    void clearAllGameTable();

    List<GameTable> getAll();

    void deletePhrase(Integer phraseId);

}
