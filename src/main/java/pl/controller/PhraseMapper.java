package pl.controller;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.service.domain.Phrase;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PhraseMapper {

    Phrase mapToPhrase(PhraseDto phraseDto);

    PhraseDto mapToPhraseDto(Phrase phrase);
}
