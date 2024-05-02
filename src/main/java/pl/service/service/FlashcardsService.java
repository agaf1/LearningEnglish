package pl.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.repository.GameTableRepository;
import pl.service.domain.AnswerResult;
import pl.service.domain.GameTable;
import pl.service.domain.Phrase;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlashcardsService {

    public static final int MIN_CORRECT_ANSWER = 2;
    private final GameTableRepository gameTableRepository;


    public boolean getListOfWord() {
        List<GameTable> gameTables = gameTableRepository.getAll();
        if (gameTables.isEmpty()) {
            return false;
        }
        return true;
    }

    public Phrase nextWord() {

        List<GameTable> gameTables = gameTableRepository.getAll();

        List<Phrase> phrases = gameTables.stream().map(g -> g.getPhrase()).toList();

        List<Phrase> actualPhrases = prepareListOfPhrase(phrases);

        return actualPhrases.stream().findFirst().orElseThrow();
    }

    public AnswerResult checkAnswer(Integer phraseId, String englishVersion) {

        GameTable actualGameTable = findActualPhraseByPhraseId(phraseId);

        AnswerResult result = checkAnswer(actualGameTable, englishVersion);

        return result;
    }

    private List<Phrase> prepareListOfPhrase(List<Phrase> phrases) {

        List<Phrase> actualPhrases = getByNumberOfRepetitions(phrases, 0);

        if (actualPhrases.isEmpty()) {
            actualPhrases = getByNumberOfRepetitions(phrases, 1);
        }
        if (actualPhrases.isEmpty()) {
            actualPhrases = getByNumberOfRepetitions(phrases, 2);
        }
        if (actualPhrases.isEmpty()) {
            actualPhrases = getByNumberOfRepetitions(phrases, 3);
        }
        if (actualPhrases.isEmpty()) {
            actualPhrases = getByNumberOfRepetitions(phrases, 4);
        }
        return actualPhrases;
    }

    private List<Phrase> getByNumberOfRepetitions(List<Phrase> phrases, int number) {
        return phrases.stream().filter(p -> p.getNumberOfRepetitions() == number).toList();
    }

    private GameTable findActualPhraseByPhraseId(Integer phraseId) {
        GameTable gameTable = gameTableRepository.getAll().stream()
                .filter(g -> g.getPhrase().getId().equals(phraseId))
                .findFirst()
                .orElseThrow();
        return gameTable;
    }

    private AnswerResult checkAnswer(GameTable gameTable, String englishVersion) {
        Phrase actualPhrase = gameTable.getPhrase();

        AnswerResult result = new AnswerResult(true, actualPhrase.getEnglishVersion());

        if (actualPhrase.isEnglishEqual(englishVersion)) {
            actualPhrase.increaseRepetition();
            if (actualPhrase.isAlreadyKnown() && actualPhrase.getNumberOfRepetitions() >= MIN_CORRECT_ANSWER) {
                gameTableRepository.deletePhrase(actualPhrase.getId());
                return result;
            }
            actualPhrase.setAlreadyKnown(true);

        } else {
            actualPhrase.increaseRepetition();
            actualPhrase.setAlreadyKnown(false);
            result = new AnswerResult(false, actualPhrase.getEnglishVersion());
        }

        gameTableRepository.save(gameTable);

        return result;
    }


}
