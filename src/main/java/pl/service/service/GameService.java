package pl.service.service;

import org.springframework.transaction.annotation.Transactional;
import pl.repository.GameRepository;
import pl.repository.GameTableRepository;
import pl.repository.UserRepository;
import pl.service.domain.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.service.domain.GameTable;
import pl.service.domain.GameType;
import pl.service.domain.Phrase;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameTableRepository gameTableRepository;

    public List<Game> showAll() {
        return gameRepository.findAll();
    }

    @Transactional(readOnly = false)
    public void prepareTheGame(Integer userId, Integer gameId, String categoryName) {
        createGameTable(userId, gameId, categoryName);
    }

    @Transactional(readOnly = false)
    private void createGameTable(Integer userId, Integer gameId, String categoryName) {
        gameTableRepository.clearAllGameTable();
        List<Phrase> phrases = userRepository.findByCategoryName(userId, categoryName);
        for (Phrase phrase : phrases) {
            GameTable gameTable = new GameTable();
            gameTable.setUserId(userId);
            gameTable.setGameId(gameId);
            gameTable.setPhrase(phrase);
            gameTableRepository.save(gameTable);
        }
    }

    public String startGame(Integer gameId) {
        String namePage = "home";

        try {
            GameType gameType = Enum.valueOf(GameType.class,
                    gameRepository.findByIdOrThrow(gameId).name().toUpperCase());

            switch (gameType) {
                case FLASHCARDS -> namePage = "flashcards";
                case SCATTERING_WORDS -> namePage = "scattering-words";
            }
        } catch (Exception e){
            namePage = "home";
        }
        return namePage;
    }
}
