package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("/clean-db.sql")
class GameTableRepositoryImpTest {

    @Autowired
    private GameTableRepository gameTableRepositoryImp;
    @Autowired
    private UserRepository userRepositoryImp;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private MapperGameEntity mapperGameEntity;
    @Autowired
    private PhraseRepository phraseRepositoryImp;

    @Test
    public void should_save_to_DB(){
        //given
        GameTable gameTable = createDataInDB();
        //when
        GameTable result = gameTableRepositoryImp.save(gameTable);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getGameId()).isEqualTo(gameTable.getGameId());
        assertThat(result.getUserId()).isEqualTo(gameTable.getUserId());
        assertThat(result.getPhrase().getId()).isEqualTo(gameTable.getPhrase().getId());
    }

    private GameTable createDataInDB(){
        Phrase phrase = new Phrase();
        phrase.setTypeOfPhrase(Type.WORD);
        phrase.setCategoryName("categoryName");
        phrase.setPolishVersion("polish");
        phrase.setEnglishVersion("english");
        phrase.setAlreadyKnown(false);
        phrase.setNumberOfRepetitions(0);

        User newUser = new User(null,"user1", List.of(phrase));
        User user = userRepositoryImp.addNew(newUser);

        Game newGame = new Game(null,"game1");
        Game game = gameRepository.save(newGame);

        GameTable gameTable = new GameTable();
        gameTable.setUserId(user.id());
        gameTable.setGameId(game.id());
        gameTable.setPhrase(user.phrases().get(0));

        return gameTable;

    }

}