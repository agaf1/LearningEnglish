package pl.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.service.domain.Game;
import pl.service.domain.Phrase;
import pl.service.domain.User;
import pl.service.service.Exception.UserNotExistException;
import pl.service.service.GameService;
import pl.service.service.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(GameController.class)
@Import({UserMapperImpl.class})
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private GameService gameService;


    @Test
    public void should_show_list_of_games() throws Exception {
        //given
        Game game1 = new Game(1, "Flashcards");
        Game game2 = new Game(2, "scattered");
        List<Game> games = List.of(game1,game2);

        User user = new User(123, "name");

        //when
        when(userService.findByIdOrThrow(user.id())).thenReturn(user);
        when(gameService.showAll()).thenReturn(games);

        MvcResult result = mockMvc.perform(get("/user/{userId}/games",user.id()))
                .andExpect(status().isOk())
                .andExpect(view().name("games"))
                .andReturn();

        // then

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("name", "Flashcards", "scattered");
    }

    @Test
    public void should_show_all_categories_of_phrases_for_given_user() throws Exception {
        //given
        List<String> categories = List.of("category1","category2");
        User user = new User(1,"user1");
        Integer userId = user.id();
        Integer gameId = 1;

        when(userService.findAllCategoriesForUser(userId)).thenReturn(categories);
        when(userService.findByIdOrThrow(userId)).thenReturn(user);

        //when
        MvcResult result = mockMvc
                .perform(get("/user/{userId}/games/{gameId}",userId,gameId))
                .andExpect(status().isOk())
                .andExpect(view().name("games-categoryPhrase"))
                .andReturn();

        //then

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("category1","category2");
    }

    @Test
    public void should_prepare_and_start_game() throws Exception {
        //given
        Integer userId = 1;
        Integer gameId = 1;
        String categoryName = "flashcards";

        when(gameService.startGame(eq(gameId))).thenReturn("flashcards");

        //when
        MvcResult result = mockMvc
                .perform(post("/user/{userId}/games/{gameId}/{categoryName}",userId,gameId,categoryName))
                .andExpect(status().isOk())
                .andExpect(view().name("flashcards"))
                .andReturn();

        //then

        verify(gameService, Mockito.atLeastOnce()).prepareTheGame(eq(userId),eq(gameId),eq(categoryName));

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("Hello! Play in Flashcards!");
    }

}