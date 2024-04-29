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
import pl.service.domain.User;
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
@WebMvcTest(UserController.class)
@Import({UserMapperImpl.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;


    @Test
    public void should_show_list_of_users() throws Exception {
        //given
        User user1 = new User(1, "name1", List.of());
        User user2 = new User(2, "name2", List.of());
        List<User> users = List.of(user1, user2);

        when(userService.getAll()).thenReturn(users);

        //when
        MvcResult result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andReturn();

        //then
        var content = result.getResponse().getContentAsString();
        assertThat(content).contains(user1.name(), user2.name());
    }

    @Test
    public void should_show_add_new_user_form() throws Exception {
        MvcResult result = mockMvc.perform(get("/users/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addUser"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("Your name");
    }

    @Test
    public void should_save_new_user_and_show_it() throws Exception {
        //given
        String name = "Name1";

        User user = new User(null,"name1");
        User createdUser = new User(1,"name1");
        when(userService.create(eq(user))).thenReturn(createdUser);

        //when and then
        MvcResult result = mockMvc.perform(post("/users/create").param("name",name))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users"))
                .andReturn();

    }

}