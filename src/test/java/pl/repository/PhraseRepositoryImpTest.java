package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.Phrase;
import pl.service.domain.Type;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql("/clean-db.sql")
class PhraseRepositoryImpTest {

    @Autowired
    private PhraseRepository phraseRepository;

    @Test

    public void should_find_phrase_by_id() {
        //given
        Phrase savedPhrase1 = phraseRepository.add(createNewPhrase());
        Phrase savedPhrase2 = phraseRepository.add(createNewPhrase());

        //when
        Phrase actualPhrase1 = phraseRepository.findById(savedPhrase1.getId()).get();
        Phrase actualPhrase2 = phraseRepository.findById(savedPhrase2.getId()).get();
        //then
        assertThat(actualPhrase1).isEqualTo(savedPhrase1);
        assertThat(actualPhrase2).isEqualTo(savedPhrase2);
    }

    @Test

    public void should_not_found_phrase() {
        //given

        Phrase savedPhrase1 = phraseRepository.add(createNewPhrase());

        Integer phraseId = 15;
        //when
        Optional<Phrase> result = phraseRepository.findById(phraseId);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void should_find_phrase_by_polishVersion() {
        //given
        Phrase savedPhrase1 = phraseRepository.add(createPolishPhrase("polish1"));
        Phrase savedPhrase2 = phraseRepository.add(createPolishPhrase("polish2"));

        //when
        Optional<Phrase> result = phraseRepository.findByPolishVersion(savedPhrase1.getPolishVersion());
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get()).isEqualTo(savedPhrase1);
    }

    @Test
    public void should_not_find_phrase_by_polishVersion() {
        //given
        Phrase savedPhrase1 = phraseRepository.add(createPolishPhrase("polish1"));
        Phrase savedPhrase2 = phraseRepository.add(createPolishPhrase("polish2"));
        String word = "nothing";
        //when
        Optional<Phrase> result = phraseRepository.findByPolishVersion(word);
        //then
        assertThat(result).isEmpty();
    }

    @Test

    public void should_find_phrase_by_englishVersion() {
        //given

        Phrase savedPhrase1 = phraseRepository.add(createEnglishPhrase("english1"));
        Phrase savedPhrase2 = phraseRepository.add(createEnglishPhrase("english2"));

        //when
        Optional<Phrase> actualEnglishVersion = phraseRepository.findByEnglishVersion(savedPhrase1.getEnglishVersion());

        //then
        assertThat(actualEnglishVersion).isNotEmpty();
        assertThat(actualEnglishVersion.get()).isEqualTo(savedPhrase1);
    }

    @Test

    public void should_not_find_phrase_by_englishVersion() {
        //given
        Phrase savedPhrase1 = phraseRepository.add(createEnglishPhrase("english1"));
        Phrase savedPhrase2 = phraseRepository.add(createEnglishPhrase("english2"));

        String word = "nothing";
        //when
        Optional<Phrase> result = phraseRepository.findByEnglishVersion(word);
        //then
        assertThat(result).isEmpty();
    }


    private Phrase createPolishPhrase(String polishVersion) {
        Phrase phrase = createNewPhrase();
        phrase.setPolishVersion(polishVersion);
        return phrase;
    }
    private Phrase createEnglishPhrase(String version) {
        Phrase phrase = createNewPhrase();
        phrase.setEnglishVersion(version);
        return phrase;
    }
    private Phrase createNewPhrase() {
        Phrase phrase = new Phrase();
        phrase.setTypeOfPhrase(Type.WORD);
        phrase.setCategoryName("categoryName");
        phrase.setPolishVersion("polish");
        phrase.setEnglishVersion("english");
        phrase.setAlreadyKnown(false);
        phrase.setNumberOfRepetitions(0);
        return phrase;
    }

}