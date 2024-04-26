package pl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.service.domain.Type;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PhraseJpaTest {

    @Autowired
    private PhraseJpa phraseJpa;

    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_find_Phrase_by_polish_version(){
        //given
        PhraseEntity phraseEntity = createPhraseEntity();
        PhraseEntity savedPhrase = phraseJpa.save(phraseEntity);
        String polishVersion = phraseEntity.getPolishVersion();
        //when
        Optional<PhraseEntity> result = phraseJpa.findByPolishVersion(polishVersion);
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getPolishVersion()).isEqualTo(polishVersion);
        assertThat(result.get().getId()).isEqualTo(savedPhrase.getId());
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_not_find_Phrase_by_polish_version(){
        //given
        PhraseEntity phraseEntity = createPhraseEntity();
        PhraseEntity savedPhrase = phraseJpa.save(phraseEntity);
        String polishVersion = "lala";
        //when
        Optional<PhraseEntity> result = phraseJpa.findByPolishVersion(polishVersion);
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
    public void should_find_Phrase_by_english_version(){
        //given
        PhraseEntity phraseEntity = createPhraseEntity();
        PhraseEntity savedPhrase = phraseJpa.save(phraseEntity);
        String englishVersion = phraseEntity.getEnglishVersion();
        //when
        Optional<PhraseEntity> result = phraseJpa.findByEnglishVersion(englishVersion);
        //then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getEnglishVersion()).isEqualTo(englishVersion);
        assertThat(result.get().getId()).isEqualTo(savedPhrase.getId());
    }
    @Test
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 0")
    @Sql(statements = "truncate game_table")
    @Sql(statements = "truncate games")
    @Sql(statements = "truncate phrases")
    @Sql(statements = "truncate users")
    @Sql(statements = "truncate users_phrases")
    @Sql(statements = "SET FOREIGN_KEY_CHECKS = 1")
    public void should_not_find_Phrase_by_english_version(){
        //given
        PhraseEntity phraseEntity = createPhraseEntity();
        PhraseEntity savedPhrase = phraseJpa.save(phraseEntity);
        String englishVersion = "lala";
        //when
        Optional<PhraseEntity> result = phraseJpa.findByEnglishVersion(englishVersion);
        //then
        assertThat(result).isEmpty();
    }


    private PhraseEntity createPhraseEntity(){
        PhraseEntity phraseEntity = new PhraseEntity();
        phraseEntity.setCategoryName("category");
        phraseEntity.setTypeOfPhrase(Type.WORD);
        phraseEntity.setPolishVersion("polski");
        phraseEntity.setEnglishVersion("english");
        phraseEntity.setAlreadyKnown(false);
        phraseEntity.setNumberOfRepetitions(0);
        return phraseEntity;
    }
}