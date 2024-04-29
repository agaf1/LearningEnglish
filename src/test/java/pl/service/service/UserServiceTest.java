package pl.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.repository.PhraseRepository;
import pl.repository.UserRepository;
import pl.service.domain.Phrase;
import pl.service.domain.User;
import pl.service.service.Exception.PhraseNotExistException;
import pl.service.service.Exception.UserNotExistException;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PhraseRepository phraseRepository;
    @InjectMocks
    private UserService userService;

    @Test
    public void should_call_the_method_addNew_from_userRepository() {
        //given
        User user = new User(1, "name1");
        //when
        userService.create(user);
        //then
        Mockito.verify(userRepository, Mockito.atLeastOnce()).addNew(eq(user));
    }

    @Test
    public void should_call_the_method_addPhrase_from_userRepository() throws UserNotExistException {
        //given
        Integer userId = 1;
        Phrase phrase = new Phrase();
        //when
        userService.addPhrase(userId, phrase);
        //then
        Mockito.verify(userRepository, Mockito.atLeastOnce()).addPhrase(eq(userId), eq(phrase));
    }

    @ParameterizedTest
    @MethodSource("data")
    public void should_call_the_method_deleteUserPhrase_from_userRepository(String typeOfData, String value) throws PhraseNotExistException {
        //given
        Integer userId = 1;

        Phrase phrase = new Phrase();
        switch (typeOfData) {
            case "ID" -> Mockito.when(phraseRepository.findById(eq(Integer.valueOf(value)))).thenReturn(Optional.of(phrase));
            case "polishVersion" -> Mockito.when(phraseRepository.findByPolishVersion(eq(value))).thenReturn(Optional.of(phrase));
            case "englishVersion" -> Mockito.when(phraseRepository.findByEnglishVersion(eq(value))).thenReturn(Optional.of(phrase));
        }
        //when
        userService.deletePhrase(userId, typeOfData, value);
        //then
        Mockito.verify(userRepository, Mockito.atLeastOnce()).deleteUserPhrase(eq(userId), eq(phrase));
    }

    private static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of("ID", "1"),
                Arguments.of("polishVersion", "value2"),
                Arguments.of("englishVersion", "value3")
        );
    }

    @Test
    public void should_throw_PhraseNotExistException_when_call_deletePhrase_method() {
        //given
        Integer userId = 1;
        String typeOfData = "polishVersion";
        String value = "value";
        Mockito.when(phraseRepository.findByPolishVersion(eq(value))).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> userService.deletePhrase(userId, typeOfData, value))
                .isInstanceOf(PhraseNotExistException.class)
                .hasMessage("This phrase was not founded.");

        assertThatThrownBy(() -> userService.deletePhrase(userId, "something", value))
                .isInstanceOf(PhraseNotExistException.class)
                .hasMessage("This phrase was not founded.");
    }

    @Test
    public void should_call_the_method_getAll_from_userRepository() {
        //given
        //when
        userService.getAll();
        //then
        Mockito.verify(userRepository, Mockito.atLeastOnce()).getAll();
    }

    @Test
    public void should_call_the_method_findAllCategoriesForUser_from_userRepository() {
        //given
        Integer userId = 1;
        //when
        userService.findAllCategoriesForUser(userId);
        //then
        Mockito.verify(userRepository, Mockito.atLeastOnce()).findAllCategoriesForUser(eq(userId));
    }


}