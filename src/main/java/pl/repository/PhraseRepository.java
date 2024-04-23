package pl.repository;

import pl.service.domain.Phrase;

import java.util.Optional;

public interface PhraseRepository {

    Phrase add(Phrase phrase);

    Optional<Phrase> findById(Integer phraseId);

    Optional<Phrase> findByPolishVersion(String value);

    Optional<Phrase> findByEnglishVersion(String value);
}
