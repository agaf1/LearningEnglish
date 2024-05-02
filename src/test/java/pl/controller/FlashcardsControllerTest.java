package pl.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.service.domain.AnswerResult;
import pl.service.domain.Phrase;
import pl.service.domain.Type;
import pl.service.service.FlashcardsService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(FlashcardsController.class)
class FlashcardsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FlashcardsService flashcardsService;

    @Test
    public void should_give_next_word() throws Exception {
        //given
        Integer userId = 1;
        Phrase phrase = createNewPhrase();
        Mockito.when(flashcardsService.getListOfWord()).thenReturn(true);
        Mockito.when(flashcardsService.nextWord()).thenReturn(phrase);
        //when
        MvcResult result = mockMvc.perform(get("/{userId}/flashcards", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("flashcards-next-word"))
                .andReturn();
        //then
        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("polish");
    }

    @Test
    public void should_return_end_game() throws Exception {
        //given
        Integer userId = 1;
        Mockito.when(flashcardsService.getListOfWord()).thenReturn(false);
        //when
        MvcResult result = mockMvc.perform(get("/{userId}/flashcards", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("flashcards-end"))
                .andReturn();
        //then
        String content = result.getResponse().getContentAsString();

        assertThat(content).contains("Congratulations! You know all the words in this category.");
    }

    @Test
    public void should_check_answer_and_return_yes_when_answer_is_correct() throws Exception {
        //given
        Integer phraseId = 1;
        Integer userId = 1;
        String englishVersion = "english";
        Mockito.when(flashcardsService.checkAnswer(eq(phraseId), eq(englishVersion)))
                .thenReturn(new AnswerResult(true, englishVersion));
        //when
        MvcResult result = mockMvc.perform(post("/{userId}/flashcards/translate/{phraseId}", userId, phraseId).param("englishVersion", englishVersion))
                .andExpect(status().isOk())
                .andExpect(view().name("flashcards-answer"))
                .andReturn();
    }

    private Phrase createNewPhrase() {
        Phrase phrase = new Phrase();
        phrase.setId(1);
        phrase.setCategoryName("category");
        phrase.setTypeOfPhrase(Type.WORD);
        phrase.setPolishVersion("polish");
        phrase.setEnglishVersion("english");
        phrase.setAlreadyKnown(false);
        phrase.setNumberOfRepetitions(0);
        return phrase;
    }
}