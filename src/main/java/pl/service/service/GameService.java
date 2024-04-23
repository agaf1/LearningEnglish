package pl.service.service;

import org.springframework.transaction.annotation.Transactional;
import pl.repository.GameRepository;
import pl.service.domain.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> showAll(){
        return gameRepository.findAll();
    }
}
