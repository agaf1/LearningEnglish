package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("/clean-db.sql")
class GameJpaTest {

    @Autowired
    private GameJpa gameJpa;


    @Test
    public void should_find_all_games(){
        //given
        GameEntity game1 = createNew("game1");
        GameEntity game2 = createNew("game2");
        GameEntity game3 = createNew("game3");
        GameEntity game4 = createNew("game4");
        GameEntity savedGame1 = gameJpa.save(game1);
        GameEntity savedGame2 = gameJpa.save(game2);
        GameEntity savedGame3 = gameJpa.save(game3);
        GameEntity savedGame4 = gameJpa.save(game4);
        //when
        List<GameEntity> games = gameJpa.findAll();
        //then
        assertThat(games.size()).isEqualTo(4);
        assertThat(games).contains(savedGame1,savedGame2,savedGame3,savedGame4);
    }

    private GameEntity createNew(String name){
        GameEntity gameEntity = new GameEntity();
        gameEntity.setName(name);
        return gameEntity;
    }

}