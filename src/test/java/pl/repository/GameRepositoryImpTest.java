package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.Game;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("/clean-db.sql")
class GameRepositoryImpTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void should_save_game_into_DB() {
        //given
        Game game = new Game(null, "game1");
        //when
        Game savedGame = gameRepository.save(game);
        //then
        assertThat(savedGame.id()).isNotNull();
        assertThat(savedGame).isEqualTo(gameRepository.findByIdOrThrow(savedGame.id()));
    }

    @Test
    public void should_get_all_games() {
        //given
        Game game1 = new Game(null, "game1");
        Game game2 = new Game(null, "game2");
        Game game3 = new Game(null, "game3");
        var expectedGames = Set.of(game1, game2, game3);

        expectedGames.forEach(g -> gameRepository.save(g));
        //when
        List<Game> actualGames = gameRepository.findAll();
        //then
        assertThat(actualGames.size()).isEqualTo(expectedGames.size());

        assertThat(actualGames).extracting("name")
                .containsAnyElementsOf(expectedGames.stream().map(Game::name).toList());
    }
}