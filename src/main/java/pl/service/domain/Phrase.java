package pl.service.domain;

import lombok.Data;


@Data
public class Phrase {

    private Integer id;
    private String categoryName;
    private Type typeOfPhrase;
    private String polishVersion;
    private String englishVersion;
    private boolean alreadyKnown;
    private int numberOfRepetitions;

    public boolean isEnglishEqual(String englishVersion){
        return this.englishVersion.equals(englishVersion);
    }

    public void increaseRepetition(){
        numberOfRepetitions ++;
    }
}
