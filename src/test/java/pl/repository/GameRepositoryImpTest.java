package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.Game;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameRepositoryImpTest {

    @Autowired
    private GameRepositoryImp gameRepositoryImp;
    @Autowired
    private GameJpa gameJpa;
    @Autowired
    private MapperGameEntity mapperGameEntity;

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_get_all_games(){
        //given
        Game game1 = new Game(null,"game1");
        Game game2 = new Game(null,"game2");
        Game game3 = new Game(null,"game3");
        gameJpa.save(mapperGameEntity.mapToEntity(game1));
        gameJpa.save(mapperGameEntity.mapToEntity(game2));
        gameJpa.save(mapperGameEntity.mapToEntity(game3));
        //when
        List<Game> games = gameRepositoryImp.findAll();
        //then
        assertThat(games.size()).isEqualTo(3);
    }
}