package pl.service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.repository.GameTableRepository;
import pl.service.domain.AnswerResult;
import pl.service.domain.GameTable;
import pl.service.domain.Phrase;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlashcardsService {

    public static final int MIN_CORRECT_ANSWER = 2;
    private final GameTableRepository gameTableRepository;


    public boolean getListOfWord() {
        List<GameTable> gameTables = gameTableRepository.getAll();

        return !gameTables.isEmpty();
    }

    public Phrase nextWord() {

        List<GameTable> gameTables = gameTableRepository.getAll();

        List<Phrase> phrases = gameTables.stream().map(GameTable::getPhrase).toList();

        List<Phrase> actualPhrases = prepareListOfPhrase(phrases);

        return actualPhrases.stream().findFirst().orElseThrow();
    }

    public AnswerResult checkAnswer(Integer phraseId, String englishVersion) {

        GameTable actualGameTable = findActualPhraseByPhraseId(phraseId);

        AnswerResult result = checkAnswer(actualGameTable, englishVersion);

        return result;
    }

    private List<Phrase> prepareListOfPhrase(List<Phrase> phrases) {
        return phrases.stream()
                .sorted(Comparator.comparingInt(Phrase::getNumberOfRepetitions))
                .collect(Collectors.toList());

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
