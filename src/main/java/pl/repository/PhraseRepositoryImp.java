package pl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.service.domain.Phrase;

import java.util.Optional;

@Repository
class PhraseRepositoryImp implements PhraseRepository{

    @Autowired
    private PhraseJpa phraseJpa;
    @Autowired
    private MapperPhraseEntity mapperPhraseEntity;

    @Override
    public Phrase add(Phrase phrase) {
       PhraseEntity savedPhrase = phraseJpa.save(mapperPhraseEntity.mapToPhraseEntity(phrase));
       return mapperPhraseEntity.mapToPhrase(savedPhrase);
    }

    @Override
    public Optional<Phrase> findById(Integer phraseId) {
        return phraseJpa.findById(phraseId).map(mapperPhraseEntity::mapToPhrase);
    }

    @Override
    public Optional<Phrase> findByPolishVersion(String value) {
        return phraseJpa.findByPolishVersion(value).map(mapperPhraseEntity::mapToPhrase);
    }

    @Override
    public Optional<Phrase> findByEnglishVersion(String value) {
        return phraseJpa.findByEnglishVersion(value).map(mapperPhraseEntity::mapToPhrase);
    }


}
