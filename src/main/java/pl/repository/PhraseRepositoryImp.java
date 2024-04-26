package pl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.service.domain.Phrase;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class PhraseRepositoryImp implements PhraseRepository{

    private final PhraseJpa phraseJpa;
    private final MapperPhraseEntity mapperPhraseEntity;

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
