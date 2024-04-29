package pl.repository;

import pl.service.domain.Game;

import java.util.List;


public interface GameRepository {
    List<Game> findAll();

    Game findByIdOrThrow(Integer id);

    Game save(Game game);

}
