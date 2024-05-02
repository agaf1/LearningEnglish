package pl.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.repository.GameTableRepository;
import pl.service.domain.AnswerResult;
import pl.service.domain.GameTable;
import pl.service.domain.Phrase;
import pl.service.domain.Type;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class FlashcardsServiceTest {

    @Mock
    private GameTableRepository gameTableRepository;
    @InjectMocks
    private FlashcardsService flashcardsService;

    @Test
    public void should_return_false_when_list_of_gameTable_is_empty(){
        //given
        List<GameTable> emptyList = new ArrayList<>();
        Mockito.when(gameTableRepository.getAll()).thenReturn(emptyList);
        //when
        boolean result = flashcardsService.getListOfWord();
        //then
        assertThat(result).isFalse();
    }
    @Test
    public void should_return_true_when_list_of_gameTable_is_not_empty(){
        //given
        Mockito.when(gameTableRepository.getAll()).thenReturn(prepareListOfGameTable(0));
        //when
        boolean result = flashcardsService.getListOfWord();
        //then
        assertThat(result).isTrue();
    }
    @Test
    public void should_return_first_phrase_from_list_of_gameTable(){
        //given
        Mockito.when(gameTableRepository.getAll()).thenReturn(prepareListOfGameTable(3));
        //when
        Phrase phrase = flashcardsService.nextWord();
        //then
        assertThat(phrase.getId()).isEqualTo(1);
        assertThat(phrase.getNumberOfRepetitions()).isEqualTo(3);
    }

    @Test
    public void should_return_positive_answer() throws InstantiationException, IllegalAccessException {
        //given
        List<GameTable> gameTables = prepareListOfGameTable(0);
        Mockito.when(gameTableRepository.getAll()).thenReturn(gameTables);
        Integer phraseId = 1;
        String englishVersion = "english1";
        Mockito.when(gameTableRepository.save(eq(gameTables.get(0))))
                .thenReturn(new GameTable());
        //when
        AnswerResult positiveResult = flashcardsService.checkAnswer(phraseId,englishVersion);
        //then
        assertThat(positiveResult.isCorrect()).isTrue();
        assertThat(positiveResult.correctWord()).isEqualTo(englishVersion);
    }

//    @Test
//    public void should_remove_good_already_known_phrase_from_Game_Table(){
//        //given
//        List<GameTable> gameTables = prepareListOfGameTable(2);
//        //when
//        //then
//    }

    @Test
    public void should_return_negative_answer() throws InstantiationException, IllegalAccessException {
        //given
        List<GameTable> gameTables = prepareListOfGameTable(0);
        Mockito.when(gameTableRepository.getAll()).thenReturn(gameTables);
        Integer phraseId = 1;
        String englishVersion = "something";
        Mockito.when(gameTableRepository.save(eq(gameTables.get(0))))
                .thenReturn(new GameTable());
        //when
        AnswerResult positiveResult = flashcardsService.checkAnswer(phraseId,englishVersion);
        //then
        assertThat(positiveResult.isCorrect()).isFalse();
        assertThat(positiveResult.correctWord()).isEqualTo(gameTables.get(0).getPhrase().getEnglishVersion());
    }



    private List<GameTable> prepareListOfGameTable(int numberOfRepetition){
        Phrase phrase1 = createNewPhrase(1,"polish1","english1",numberOfRepetition);
        Phrase phrase2 = createNewPhrase(2,"polish2", "english2",numberOfRepetition);
        GameTable gameTable1 = createNewGameTable(1,phrase1);
        GameTable gameTable2 = createNewGameTable(2,phrase2);
        return List.of(gameTable1,gameTable2);
    }

    private Phrase createNewPhrase(Integer id, String polishVersion, String englishVersion, int numberOfRepetition){
        Phrase phrase = new Phrase();
        phrase.setId(id);
        phrase.setTypeOfPhrase(Type.WORD);
        phrase.setCategoryName("category");
        phrase.setPolishVersion(polishVersion);
        phrase.setEnglishVersion(englishVersion);
        phrase.setAlreadyKnown(false);
        phrase.setNumberOfRepetitions(numberOfRepetition);
        return phrase;
    }

    private GameTable createNewGameTable(Integer id, Phrase phrase){
        GameTable gameTable = new GameTable();
        gameTable.setGameId(1);
        gameTable.setUserId(1);
        gameTable.setId(id);
        gameTable.setPhrase(phrase);
        return gameTable;
    }

}