package pl.service.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Phrase {

    private Integer id;
    private String categoryName;
    private Type typeOfPhrase;
    private String polishVersion;
    private String englishVersion;
    private boolean alreadyKnown;
    private int numberOfRepetitions;


}
