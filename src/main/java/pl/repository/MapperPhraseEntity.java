package pl.repository;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.service.domain.Phrase;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface MapperPhraseEntity {

    PhraseEntity mapToPhraseEntity(Phrase phrase);

    Phrase mapToPhrase(PhraseEntity phraseEntity);

}
