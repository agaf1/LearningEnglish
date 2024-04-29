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
@Sql("/clean-db.sql")
class UserRepositoryImpTest {

    @Autowired
    private UserRepositoryImp userRepositoryImp;

    @Test
    public void should_add_new_user() {
        //given
        User user = createNewUser("name1");
        //when
        User savedUser = userRepositoryImp.addNew(user);
        //then
        assertThat(savedUser.name()).isEqualTo("name1");
        assertThat(savedUser.phrases().size()).isEqualTo(0);
    }

    @Test
    public void should_add_phrase_to_user() {
        //given
        User user = createNewUser("name1");
        User savedUser = userRepositoryImp.addNew(user);
        //when
        userRepositoryImp.addPhrase(savedUser.id(), createNewPhrase());
        //then
        User actualUser = userRepositoryImp.findUserById(savedUser.id()).get();
        assertThat(actualUser.phrases().size()).isEqualTo(1);
    }

    @Test
    public void should_throw_exception_when_add_phrase_to_not_exist_user() {
        //given
        User user = createNewUser("name1");
        userRepositoryImp.addNew(user);

        //when
        //then
        assertThatThrownBy(() -> userRepositoryImp.addPhrase(2, createNewPhrase()))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void should_delete_phrase_from_user() {
        //given
        User user1 = createNewUser("user1");
        User savedUser1 = userRepositoryImp.addNew(user1);

        userRepositoryImp.addPhrase(savedUser1.id(), createNewPhrase());
        userRepositoryImp.addPhrase(savedUser1.id(), createNewPhrase());

        User actualUser1 = userRepositoryImp.findUserById(savedUser1.id()).get();
        Phrase phraseToDelete = actualUser1.phrases().get(0);

        //when
        userRepositoryImp.deleteUserPhrase(savedUser1.id(), phraseToDelete);

        //then
        actualUser1 = userRepositoryImp.findUserById(savedUser1.id()).get();
        assertThat(actualUser1.phrases().size()).isEqualTo(1);
        assertThat(actualUser1.phrases()).doesNotContain(phraseToDelete);
    }



    @Test
    public void should_throw_exception_when_call_delete_phrase_method_with_wrong_userId() {
        //given
        User user1 = createNewUser("user1");
        User savedUser1 = userRepositoryImp.addNew(user1);
        userRepositoryImp.addPhrase(savedUser1.id(), createNewPhrase());
        User actualUser1 = userRepositoryImp.findUserById(savedUser1.id()).get();
        Phrase phraseToDelete = actualUser1.phrases().get(0);
        //when
        //then
        assertThatThrownBy(() -> userRepositoryImp.deleteUserPhrase(2, phraseToDelete))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void should_get_all_users() {
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
        assertThat(users).contains(savedUser1, savedUser2, savedUser3);
    }

    @Test
    public void should_find_user_by_id() {
        //given
        User user1 = createNewUser("user1");
        User user2 = createNewUser("user2");
        User savedUser1 = userRepositoryImp.addNew(user1);
        User savedUser2 = userRepositoryImp.addNew(user2);

        Integer userId = savedUser2.id();

        //when
        Optional<User> actualUser2 = userRepositoryImp.findUserById(userId);
        //then
        assertThat(actualUser2).isNotEmpty();
        assertThat(actualUser2.get()).isEqualTo(savedUser2);
    }

    @Test
    public void should_return_empty_optional_when_not_found_user() {
        //given
        User user = createNewUser("user1");
        userRepositoryImp.addNew(user);
        Integer userId = 10;
        //when
        Optional<User> savedUser = userRepositoryImp.findUserById(userId);
        //then
        assertThat(savedUser).isEmpty();
    }

    @Test
    public void should_show_all_categories_for_given_user() {
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

        userRepositoryImp.addPhrase(savedUser1.id(), phrase1);
        userRepositoryImp.addPhrase(savedUser1.id(), phrase2);
        userRepositoryImp.addPhrase(savedUser1.id(), phrase3);
        userRepositoryImp.addPhrase(savedUser2.id(), phrase3);

        //when
        List<String> categories = userRepositoryImp.findAllCategoriesForUser(savedUser1.id());

        //then
        assertThat(categories.size()).isEqualTo(2);
        assertThat(categories).contains("Animals", "Cars");
    }

    @Test
    public void should_find_all_phrases_with_the_same_category_for_given_user() {
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

        userRepositoryImp.addPhrase(savedUser1.id(), phrase1);
        userRepositoryImp.addPhrase(savedUser1.id(), phrase2);
        userRepositoryImp.addPhrase(savedUser1.id(), phrase3);
        userRepositoryImp.addPhrase(savedUser2.id(), phrase3);

        //when
        List<Phrase> phrases = userRepositoryImp.findByCategoryName(savedUser1.id(), "Animals");

        //then
        assertThat(phrases.size()).isEqualTo(2);
        assertThat(phrases.get(0).getCategoryName()).isEqualTo("Animals");
    }


    private User createNewUser(String name) {
        User user = new User(null, name);
        return user;
    }

    private Phrase createNewPhrase() {
        Phrase phrase = new Phrase();
        phrase.setTypeOfPhrase(Type.WORD);
        phrase.setCategoryName("categoryName");
        phrase.setPolishVersion("polish");
        phrase.setEnglishVersion("english");
        phrase.setAlreadyKnown(false);
        phrase.setNumberOfRepetitions(0);
        return phrase;
    }

    private Phrase createNewPhrase(Integer id) {
        Phrase phrase = createNewPhrase();
        phrase.setId(id);
        return phrase;
    }

}