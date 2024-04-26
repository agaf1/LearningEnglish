package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.Type;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserJpaTest {

    @Autowired
    private UserJpa userJpa;

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
        public void should_find_all_users_from_DB(){
        //given
        UserEntity user1 = createUserEntity("name1");
        UserEntity user2 = createUserEntity("name2");
        userJpa.save(user1);
        userJpa.save(user2);
        //when
        List<UserEntity> users = userJpa.findAll();
        //then
        assertThat(users.size()).isEqualTo(2);
        assertThat(users.get(0).getName()).isEqualTo(user1.getName());
        assertThat(users.get(1).getName()).isEqualTo(user2.getName());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_all_categories_for_given_user(){
        //given
        UserEntity user1 = createUserEntity("user1");
        UserEntity savedUser1 = userJpa.save(user1);
        UserEntity user2 = createUserEntity("user2");
        UserEntity savedUser2 = userJpa.save(user2);

        PhraseEntity phrase1 = createPhraseEntity("Animals");
        PhraseEntity phrase2 = createPhraseEntity("Cars");
        PhraseEntity phrase3 = createPhraseEntity("Home");
        user1.addNewPhraseEntity(phrase1);
        user1.addNewPhraseEntity(phrase2);
        user2.addNewPhraseEntity(phrase3);
        savedUser1 = userJpa.save(user1);
        savedUser2 = userJpa.save(user2);
        //when
        List<String> categories = userJpa.findAllCategoriesForUser(savedUser1.getId());
        //then
        assertThat(categories.size()).isEqualTo(2);
        assertThat(categories).contains("Animals","Cars");
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_phrases_for_given_userId_and_categoryName(){
        //given
        UserEntity user1 = createUserEntity("user1");
        UserEntity user2 = createUserEntity("user2");
        PhraseEntity phrase1 = createPhraseEntity("Animals");
        PhraseEntity phrase2 = createPhraseEntity("Animals");
        PhraseEntity phrase3 = createPhraseEntity("Cars");
        PhraseEntity phrase4 = createPhraseEntity("Animals");
        user1.addNewPhraseEntity(phrase1);
        user1.addNewPhraseEntity(phrase2);
        user2.addNewPhraseEntity(phrase3);
        user2.addNewPhraseEntity(phrase4);
        UserEntity savedUser1 = userJpa.save(user1);
        UserEntity savedUser2 = userJpa.save(user2);

        Integer userId = savedUser1.getId();
        String categoryName = phrase1.getCategoryName();
        //when
        List<PhraseEntity> phrases = userJpa.findByCategoryName(userId,categoryName);
        //then
        assertThat(phrases.size()).isEqualTo(2);
        assertThat(phrases.get(0).getCategoryName()).isEqualTo(phrase1.getCategoryName());
        assertThat(phrases.get(1).getCategoryName()).isEqualTo(phrase2.getCategoryName());
    }

    private UserEntity createUserEntity(String userName){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userName);
        return userEntity;
    }
    private PhraseEntity createPhraseEntity(String categoryName){
        PhraseEntity phraseEntity = new PhraseEntity();
        phraseEntity.setCategoryName(categoryName);
        phraseEntity.setTypeOfPhrase(Type.valueOf("WORD"));
        phraseEntity.setPolishVersion("kot");
        phraseEntity.setEnglishVersion("cat");
        phraseEntity.setAlreadyKnown(false);
        phraseEntity.setNumberOfRepetitions(0);
        return phraseEntity;
    }


}