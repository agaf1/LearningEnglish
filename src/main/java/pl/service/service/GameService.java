package pl.service.service;

import org.springframework.transaction.annotation.Transactional;
import pl.repository.GameRepository;
import pl.repository.GameTableRepository;
import pl.repository.UserRepository;
import pl.service.domain.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.service.domain.GameTable;
import pl.service.domain.Phrase;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameTableRepository gameTableRepository;

    public List<Game> showAll(){
        return gameRepository.findAll();
    }

    public void prepareTheGame(Integer userId, Integer gameId, String categoryName){
        createGameTable(userId,gameId,categoryName);
    }


    private void createGameTable(Integer userId, Integer gameId, String categoryName) {
        List<Phrase> phrases = userRepository.findByCategoryName(userId, categoryName);
        for (Phrase phrase : phrases) {
            GameTable gameTable = new GameTable();
            gameTable.setUserId(userId);
            gameTable.setGameId(gameId);
            gameTable.setPhrase(phrase);
            gameTableRepository.save(gameTable);
        }
    }
}
