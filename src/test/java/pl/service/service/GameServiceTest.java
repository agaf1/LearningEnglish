package pl.service.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.repository.GameRepository;

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
}