package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GameTableRepositoryImpTest {

    @Autowired
    private GameTableRepositoryImp gameTableRepositoryImp;
    @Autowired
    private UserRepositoryImp userRepositoryImp;
    @Autowired
    private GameJpa gameJpa;
    @Autowired
    private MapperGameEntity mapperGameEntity;
    @Autowired
    private PhraseRepositoryImp phraseRepositoryImp;

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
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
        User user = new User(null,"user1");
        Integer userId = userRepositoryImp.addNew(user).id();

        Game game = new Game(null,"game1");
        Integer gameId = gameJpa.save(mapperGameEntity.mapToEntity(game)).getId();

        Phrase phrase = new Phrase();
        phrase.setTypeOfPhrase(Type.WORD);
        phrase.setCategoryName("categoryName");
        phrase.setPolishVersion("polish");
        phrase.setEnglishVersion("english");
        phrase.setAlreadyKnown(false);
        phrase.setNumberOfRepetitions(0);
        userRepositoryImp.addPhrase(userId,phrase);
        Phrase savedPhrase = phraseRepositoryImp.findByEnglishVersion("english").get();

        GameTable gameTable = new GameTable();
        gameTable.setUserId(userId);
        gameTable.setGameId(gameId);
        gameTable.setPhrase(savedPhrase);

        return gameTable;
    }

}