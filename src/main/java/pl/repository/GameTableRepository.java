package pl.repository;

import pl.service.domain.GameTable;

public interface GameTableRepository {

    GameTable save(GameTable gameTable);
}
