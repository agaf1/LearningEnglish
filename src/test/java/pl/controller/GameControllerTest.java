package pl.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.service.domain.Game;
import pl.service.domain.User;
import pl.service.service.GameService;
import pl.service.service.UserService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private GameService gameService;
    @MockBean
    private UserService userService;

    @Test
    public void should_show_list_of_games() throws Exception {
        //given
        Game game1 = new Game(1, "Flashcards");
        Game game2 = new Game(2, "scattered");
        List<Game> games = List.of(game1,game2);

        User user = new User(123, "name");

        //when
        when(gameService.showAll()).thenReturn(games);
        when(userService.findByIdOrThrow(user.id())).thenReturn(user);

        MvcResult result = mockMvc.perform(get("/user/{userId}/games",user.id()))
                .andExpect(status().isOk())
                .andExpect(view().name("games"))
                .andReturn();

        // then

        String content = result.getResponse().getContentAsString();
        Assertions.assertThat(content).contains("name", "Flashcards", "scattered");
    }

}