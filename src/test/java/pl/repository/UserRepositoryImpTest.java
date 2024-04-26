package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.Phrase;
import pl.service.domain.Type;
import pl.service.domain.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserRepositoryImpTest {

    @Autowired
    private UserRepositoryImp userRepositoryImp;

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_add_new_user(){
        //given
        User user = createNewUser("name1");
        //when
        User savedUser = userRepositoryImp.addNew(user);
        //then
        assertThat(savedUser.name()).isEqualTo("name1");
        assertThat(savedUser.phrases().size()).isEqualTo(0);
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_add_phrase_to_user(){
        //given
        User user = createNewUser("name1");
        User savedUser = userRepositoryImp.addNew(user);
        Integer userId = savedUser.id();
        Phrase phrase = createNewPhrase();
        //when
        boolean result = userRepositoryImp.addPhrase(userId,phrase);
        //then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_throw_exception_when_add_phrase_to_not_exist_user(){
        //given
        User user = createNewUser("name1");
        User savedUser = userRepositoryImp.addNew(user);
        Integer userId = savedUser.id();
        Phrase phrase = createNewPhrase();
        //when
        //then
        if(userId != 2) {
            assertThatThrownBy(()->userRepositoryImp.addPhrase(2,phrase))
                    .isInstanceOf(NoSuchElementException.class);
        }else{
            assertThatThrownBy(()->userRepositoryImp.addPhrase(3,phrase))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_delete_phrase_from_user_list_of_phrases(){
        //given
        User user1 = createNewUser("user1");
        User savedUser1 = userRepositoryImp.addNew(user1);
        User user2 = createNewUser("user2");
        User savedUser2 = userRepositoryImp.addNew(user2);
        Phrase phrase1 = createNewPhrase();
        Phrase phrase2 = createNewPhrase();
        userRepositoryImp.addPhrase(savedUser1.id(),phrase1);
        userRepositoryImp.addPhrase(savedUser1.id(),phrase2);
        userRepositoryImp.addPhrase(savedUser2.id(),phrase1);
        userRepositoryImp.addPhrase(savedUser2.id(),phrase2);

        User user1FromDB = userRepositoryImp.findUserById(savedUser1.id()).get();
        Phrase phraseToDelete = user1FromDB.phrases().get(0);

        //when
        boolean result = userRepositoryImp.deleteUserPhrase(savedUser1.id(),phraseToDelete);

        //then
        assertThat(result).isEqualTo(true);

        user1FromDB = userRepositoryImp.findUserById(savedUser1.id()).get();
        assertThat(user1FromDB.phrases().size()).isEqualTo(1);

        User user2FromDB = userRepositoryImp.findUserById(savedUser2.id()).get();
        assertThat(user2FromDB.phrases().size()).isEqualTo(2);
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_return_false_when_not_delete_phrase(){
        //given
        User user1 = createNewUser("user1");
        User savedUser1 = userRepositoryImp.addNew(user1);
        User user2 = createNewUser("user2");
        User savedUser2 = userRepositoryImp.addNew(user2);
        Phrase phrase1 = createNewPhrase();
        Phrase phrase2 = createNewPhrase();
        userRepositoryImp.addPhrase(savedUser1.id(),phrase1);
        userRepositoryImp.addPhrase(savedUser1.id(),phrase2);
        userRepositoryImp.addPhrase(savedUser2.id(),phrase1);
        userRepositoryImp.addPhrase(savedUser2.id(),phrase2);

        Phrase phraseToDelete = createNewPhrase();
        phraseToDelete.setId(10);

        //when
        boolean result = userRepositoryImp.deleteUserPhrase(savedUser1.id(),phraseToDelete);

        //then
        assertThat(result).isEqualTo(false);

        User user1FromDB = userRepositoryImp.findUserById(savedUser1.id()).get();
        assertThat(user1FromDB.phrases().size()).isEqualTo(2);

        User user2FromDB = userRepositoryImp.findUserById(savedUser2.id()).get();
        assertThat(user2FromDB.phrases().size()).isEqualTo(2);
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_throw_exception_when_call_delete_phrase_method_with_wrong_userId(){
        //given
        User user1 = createNewUser("user1");
        User savedUser1 = userRepositoryImp.addNew(user1);
        Phrase phrase = createNewPhrase();
        userRepositoryImp.addPhrase(savedUser1.id(),phrase);
        User user1FromDB = userRepositoryImp.findUserById(savedUser1.id()).get();
        Phrase phraseToDelete = user1FromDB.phrases().get(0);
        //when
        //then
        if(savedUser1.id() != 2) {
            assertThatThrownBy(()->userRepositoryImp.deleteUserPhrase(2,phraseToDelete))
                    .isInstanceOf(NoSuchElementException.class);
        }else{
            assertThatThrownBy(()->userRepositoryImp.deleteUserPhrase(3,phraseToDelete))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_get_all_users(){
        //given
        User user1 = createNewUser("user1");
        User user2 = createNewUser("user2");
        User user3 = createNewUser("user3");
        User savedUser1 = userRepositoryImp.addNew(user1);
        User savedUser2 = userRepositoryImp.addNew(user2);
        User savedUser3 = userRepositoryImp.addNew(user3);
        //when
        List<User> users = userRepositoryImp.getAll();
        //then
        assertThat(users.size()).isEqualTo(3);
        assertThat(users).contains(savedUser1,savedUser2,savedUser3);
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_user_by_id(){
        //given
        User user1 = createNewUser("user1");
        User user2 = createNewUser("user2");
        User savedUser1 = userRepositoryImp.addNew(user1);
        User savedUser2 = userRepositoryImp.addNew(user2);

        Integer userId = savedUser2.id();

        //when
        Optional<User> userFound = userRepositoryImp.findUserById(userId);
        //then
        assertThat(userFound).isNotEmpty();
        assertThat(userFound.get().name()).isEqualTo("user2");
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_return_empty_optional_when_not_found_user(){
        //given
        User user = createNewUser("user1");
        userRepositoryImp.addNew(user);
        Integer userId = 10;
        //when
        Optional<User> userFound = userRepositoryImp.findUserById(userId);
        //then
        assertThat(userFound).isEmpty();
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_show_all_categories_for_given_user(){
        //given
        User user1 = createNewUser("user1");
        User savedUser1 = userRepositoryImp.addNew(user1);
        User user2 = createNewUser("user2");
        User savedUser2 = userRepositoryImp.addNew(user2);
        Phrase phrase1 = createNewPhrase();
        phrase1.setCategoryName("Animals");
        Phrase phrase2 = createNewPhrase();
        phrase2.setCategoryName("Cars");
        Phrase phrase3 = createNewPhrase();
        phrase3.setCategoryName("Animals");

        userRepositoryImp.addPhrase(savedUser1.id(),phrase1);
        userRepositoryImp.addPhrase(savedUser1.id(),phrase2);
        userRepositoryImp.addPhrase(savedUser1.id(),phrase3);
        userRepositoryImp.addPhrase(savedUser2.id(),phrase3);

        //when
        List<String> categories = userRepositoryImp.findAllCategoriesForUser(savedUser1.id());

        //then
        assertThat(categories.size()).isEqualTo(2);
        assertThat(categories).contains("Animals", "Cars");
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_all_phrases_with_the_same_category_for_given_user(){
        //given
        User user1 = createNewUser("user1");
        User savedUser1 = userRepositoryImp.addNew(user1);
        User user2 = createNewUser("user2");
        User savedUser2 = userRepositoryImp.addNew(user2);
        Phrase phrase1 = createNewPhrase();
        phrase1.setCategoryName("Animals");
        Phrase phrase2 = createNewPhrase();
        phrase2.setCategoryName("Cars");
        Phrase phrase3 = createNewPhrase();
        phrase3.setCategoryName("Animals");

        userRepositoryImp.addPhrase(savedUser1.id(),phrase1);
        userRepositoryImp.addPhrase(savedUser1.id(),phrase2);
        userRepositoryImp.addPhrase(savedUser1.id(),phrase3);
        userRepositoryImp.addPhrase(savedUser2.id(),phrase3);

        //when
        List<Phrase> phrases = userRepositoryImp.findByCategoryName(savedUser1.id(),"Animals");

        //then
        assertThat(phrases.size()).isEqualTo(2);
        assertThat(phrases.get(0).getCategoryName()).isEqualTo("Animals");
    }


    private User createNewUser(String name){
        User user = new User(null,name);
        return user;
    }

    private Phrase createNewPhrase(){
        Phrase phrase = new Phrase();
        phrase.setTypeOfPhrase(Type.WORD);
        phrase.setCategoryName("categoryName");
        phrase.setPolishVersion("polish");
        phrase.setEnglishVersion("english");
        phrase.setAlreadyKnown(false);
        phrase.setNumberOfRepetitions(0);
        return phrase;
    }

}