package pl.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    public void should_call_the_method_addNew_from_userRepository(){
        //given
        User user = new User(1,"name1");
        //when
        userService.create(user);
        //then
        Mockito.verify(userRepository,Mockito.atLeastOnce()).addNew(eq(user));
    }

    @Test
    public void should_call_the_method_addPhrase_from_userRepository() throws UserNotExistException {
        //given
        Integer userId = 1;
        Phrase phrase = new Phrase();
        //when
        userService.addPhrase(userId,phrase);
        //then
        Mockito.verify(userRepository,Mockito.atLeastOnce()).addPhrase(eq(userId),eq(phrase));
    }

    //TODO to sie nadaje na test sparametryzowany
    //https://www.baeldung.com/parameterized-tests-junit-5
    @Test
    public void should_call_the_method_deleteUserPhrase_from_userRepository() throws PhraseNotExistException {
        //given
        Integer userId =1;
        String typeOfData1 = "ID";
        String typeOfDate2 = "polishVersion";
        String typeOfData3 = "englishVersion";
        String value1 = "1";
        String value2 = "value2";
        String value3 = "value3";
        Phrase phrase = new Phrase();
        Mockito.when(phraseRepository.findById(eq(1))).thenReturn(Optional.of(phrase));
        Mockito.when(phraseRepository.findByPolishVersion(eq(value2))).thenReturn(Optional.of(phrase));
        Mockito.when(phraseRepository.findByEnglishVersion(eq(value3))).thenReturn(Optional.of(phrase));
        //when

        userService.deletePhrase(userId,typeOfData1,value1);
        userService.deletePhrase(userId,typeOfDate2,value2);
        userService.deletePhrase(userId,typeOfData3,value3);
        //then
        Mockito.verify(userRepository,Mockito.times(3)).deleteUserPhrase(eq(userId),eq(phrase));
    }

    @Test
    public void should_throw_PhraseNotExistException_when_call_deletePhrase_method(){
        //given
        Integer userId =1;
        String typeOfData = "polishVersion";
        String value = "value";
        Mockito.when(phraseRepository.findByPolishVersion(eq(value))).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> userService.deletePhrase(userId,typeOfData,value))
                .isInstanceOf(PhraseNotExistException.class)
                .hasMessage("This phrase was not founded.");

        assertThatThrownBy(() -> userService.deletePhrase(userId,"something",value))
                .isInstanceOf(PhraseNotExistException.class)
                .hasMessage("This phrase was not founded.");
    }

    @Test
    public void should_call_the_method_getAll_from_userRepository(){
        //given
        //when
        userService.getAll();
        //then
        Mockito.verify(userRepository,Mockito.atLeastOnce()).getAll();
    }

    @Test
    public void should_call_the_method_findAllCategoriesForUser_from_userRepository(){
        //given
        Integer userId = 1;
        //when
        userService.findAllCategoriesForUser(userId);
        //then
        Mockito.verify(userRepository,Mockito.atLeastOnce()).findAllCategoriesForUser(eq(userId));
    }


}