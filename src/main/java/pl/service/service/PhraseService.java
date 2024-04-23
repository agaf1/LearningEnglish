package pl.service.service;

import pl.repository.PhraseRepository;
import pl.service.domain.Phrase;
import pl.service.domain.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhraseService {

    private final PhraseRepository phraseRepository;

    @Transactional
    public Phrase add(Phrase phrase) {
        return phraseRepository.add(phrase);
    }

}
