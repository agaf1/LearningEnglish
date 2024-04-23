package pl.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.service.domain.Phrase;
import pl.service.domain.Type;
import pl.service.service.PhraseService;
import pl.service.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(PhraseController.class)
@Import({PhraseMapperImpl.class})
//@AutoConfigureMockMvc
class PhraseControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PhraseService phraseService;
    @MockBean
    private UserService userService;

    @Test
    public void should_show_view_phrases_with_userId() throws Exception {
        //given
        Integer userId = 1;
        //when
        MvcResult result = mockMvc.perform(get("/user/{userId}/phrases", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("phrases"))
                .andReturn();
        //then
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("Add phrase", String.valueOf(userId));
        assertThat(content).doesNotContain("userId");
    }

    @Test
    public void should_show_add_form() throws Exception {
        //given
        Integer userId = 1;
        //when
        MvcResult result = mockMvc.perform(get("/user/{userId}/phrases/addForm", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("addPhrase"))
                .andReturn();
        //then
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains(String.valueOf(userId), "WORD");
        assertThat(content).doesNotContain("userId");
    }

    @Test
    public void should_show_delete_form() throws Exception {
        //given
        Integer userId = 1;
        //when
        MvcResult result = mockMvc.perform(get("/user/{userId}/phrases/deleteForm", userId))
                .andExpect(status().isOk())
                .andExpect(view().name("deletePhrase"))
                .andReturn();
        //then
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains(String.valueOf(userId), "typeOfData");
        assertThat(content).doesNotContain("userId");
    }

    @Test
    public void should_create_new_phrase_and_redirect_to_other_view() throws Exception {
        //given
        Integer userId = 1;
        List<String> listArgument = List.of("categoryName","WORD","polishVersion","englishVersion");

        Phrase phrase = new Phrase();
        phrase.setCategoryName("categoryName");
        phrase.setTypeOfPhrase(Type.WORD);
        phrase.setPolishVersion("polishVersion");
        phrase.setEnglishVersion("englishVersion");

        when(userService.addPhrase(eq(userId),eq(phrase))).thenReturn(true);

        //when and then
        MvcResult result = mockMvc.perform(post("/user/{userId}/phrases/create",userId)
                        .param("categoryName",listArgument.get(0)
                                ,"WORD",listArgument.get(1)
                                ,"polishVersion",listArgument.get(2)
                                ,"englishVersion",listArgument.get(3)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/"+ String.valueOf(userId)+"/phrases"))
                .andReturn();
    }

    @Test
    public void should_delete_phrase_and_redirect_to_other_view() throws Exception {
        //given
        Integer userId = 1;
        String typeOfData = "ID";
        String value = "1";

        when(userService.deletePhrase(eq(userId),eq(typeOfData),eq(value))).thenReturn(true);

        //when and then
        MvcResult result = mockMvc.perform(post("/user/{userId}/phrases/delete",userId)
                .param("typeOfData",typeOfData,"value",value))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/"+String.valueOf(userId)+"/phrases"))
                .andReturn();

    }
}