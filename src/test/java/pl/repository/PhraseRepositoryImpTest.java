package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.Phrase;
import pl.service.domain.Type;

import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PhraseRepositoryImpTest {

    @Autowired
    private PhraseRepositoryImp phraseRepositoryImp;

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_save_phrase(){
        //given
        Phrase phrase = createNewPhrase();
        //when
        Phrase savedPhrase = phraseRepositoryImp.add(phrase);
        //then
        assertThat(savedPhrase.getId()).isNotNull();

        assertThat(savedPhrase.getTypeOfPhrase()).isEqualTo(phrase.getTypeOfPhrase());
        assertThat(savedPhrase.getCategoryName()).isEqualTo(phrase.getCategoryName());
        assertThat(savedPhrase.getPolishVersion()).isEqualTo(phrase.getPolishVersion());
        assertThat(savedPhrase.getEnglishVersion()).isEqualTo(phrase.getEnglishVersion());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_phrase_by_id(){
        //given
        Phrase phrase1 = createNewPhrase();
        Phrase phrase2 = createNewPhrase();
        Phrase savedPhrase1 = phraseRepositoryImp.add(phrase1);
        Phrase savedPhrase2 = phraseRepositoryImp.add(phrase2);
        Integer phraseId = savedPhrase2.getId();
        //when
        Optional<Phrase> result = phraseRepositoryImp.findById(phraseId);
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(phraseId);
        assertThat(result.get().getEnglishVersion()).isEqualTo(phrase2.getEnglishVersion());
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_not_found_phrase(){
        //given
        Phrase phrase1 = createNewPhrase();
        Phrase phrase2 = createNewPhrase();
        Phrase savedPhrase1 = phraseRepositoryImp.add(phrase1);
        Phrase savedPhrase2 = phraseRepositoryImp.add(phrase2);
        Integer phraseId = 15;
        //when
        Optional<Phrase> result = phraseRepositoryImp.findById(phraseId);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_phrase_by_polishVersion(){
        //given
        Phrase phrase1 = createNewPhrase();
        phrase1.setPolishVersion("polish1");
        Phrase phrase2 = createNewPhrase();
        phrase2.setPolishVersion("polish2");
        Phrase savedPhrase1 = phraseRepositoryImp.add(phrase1);
        Phrase savedPhrase2 = phraseRepositoryImp.add(phrase2);

        //when
        Optional<Phrase> result = phraseRepositoryImp.findByPolishVersion(savedPhrase1.getPolishVersion());
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(savedPhrase1.getId());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_not_find_phrase_by_polishVersion(){
        //given
        Phrase phrase1 = createNewPhrase();
        phrase1.setPolishVersion("polish1");
        Phrase phrase2 = createNewPhrase();
        phrase2.setPolishVersion("polish2");
        Phrase savedPhrase1 = phraseRepositoryImp.add(phrase1);
        Phrase savedPhrase2 = phraseRepositoryImp.add(phrase2);
        String word = "nothing";
        //when
        Optional<Phrase> result = phraseRepositoryImp.findByPolishVersion(word);
        //then
        assertThat(result).isEmpty();
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_phrase_by_englishVersion(){
        //given
        Phrase phrase1 = createNewPhrase();
        phrase1.setEnglishVersion("english1");
        Phrase phrase2 = createNewPhrase();
        phrase2.setEnglishVersion("english2");
        Phrase savedPhrase1 = phraseRepositoryImp.add(phrase1);
        Phrase savedPhrase2 = phraseRepositoryImp.add(phrase2);

        //when
        Optional<Phrase> result = phraseRepositoryImp.findByEnglishVersion(savedPhrase1.getEnglishVersion());
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(savedPhrase1.getId());
    }

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_not_find_phrase_by_englishVersion(){
        //given
        Phrase phrase1 = createNewPhrase();
        phrase1.setEnglishVersion("english1");
        Phrase phrase2 = createNewPhrase();
        phrase2.setEnglishVersion("english1");
        Phrase savedPhrase1 = phraseRepositoryImp.add(phrase1);
        Phrase savedPhrase2 = phraseRepositoryImp.add(phrase2);
        String word = "nothing";
        //when
        Optional<Phrase> result = phraseRepositoryImp.findByEnglishVersion(word);
        //then
        assertThat(result).isEmpty();
    }


    private Phrase createNewPhrase(){
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