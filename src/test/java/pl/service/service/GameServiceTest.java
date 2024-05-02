package pl.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.repository.GameRepository;
import pl.service.domain.Game;
import pl.service.domain.GameType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private GameService gameService;

    @Test
    public void should_call_the_method_findAll_from_gameRepository(){
        //given
        //when
        gameService.showAll();
        //then
        Mockito.verify(gameRepository,Mockito.atLeastOnce()).findAll();
    }

    @Test
    public void should_return_game_name_flashcards(){
        //given
        Game game = new Game(1, "flashcards");
        when(gameRepository.findByIdOrThrow(eq(game.id()))).thenReturn(game);
        //when
        String result = gameService.startGame(game.id());
        //then
        assertThat(result).isEqualTo("flashcards");
    }
    @Test
    public void should_return_game_name_scattering_words(){
        //given
        Game game = new Game(1, "scattering_words");
        when(gameRepository.findByIdOrThrow(eq(game.id()))).thenReturn(game);
        //when
        String result = gameService.startGame(game.id());
        //then
        assertThat(result).isEqualTo("scattering-words");
    }
    @Test
    public void should_return_name_home(){
        //given
        Game game = new Game(1, "nothing");
        //when
        String result = gameService.startGame(game.id());
        //then
        assertThat(result).isEqualTo("home");
    }
}