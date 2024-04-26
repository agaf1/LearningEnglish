package pl.repository;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.type.NumericBooleanConverter;
import pl.service.domain.GameTable;
import pl.service.domain.Phrase;
import pl.service.domain.Type;

import java.util.Objects;

@Entity
@Table(name = "game_table")
@Data
class GameTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private Integer gameId;
    private Integer phraseId;
    private String categoryName;
    private String polishVersion;
    private String englishVersion;
    @Enumerated(EnumType.STRING)
    private Type typeOfPhrase;
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean alreadyKnown;
    private Integer numberOfRepetitions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameTableEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 3;
    }

    public static GameTableEntity create(GameTable gameTable){
        GameTableEntity gameTableEntity = new GameTableEntity();
        gameTableEntity.setUserId(gameTable.getUserId());
        gameTableEntity.setGameId(gameTable.getGameId());
        gameTableEntity.setPhraseId(gameTable.getPhrase().getId());
        gameTableEntity.setCategoryName(gameTable.getPhrase().getCategoryName());
        gameTableEntity.setTypeOfPhrase(gameTable.getPhrase().getTypeOfPhrase());
        gameTableEntity.setPolishVersion(gameTable.getPhrase().getPolishVersion());
        gameTableEntity.setEnglishVersion(gameTable.getPhrase().getEnglishVersion());
        gameTableEntity.setAlreadyKnown(gameTable.getPhrase().isAlreadyKnown());
        gameTableEntity.setNumberOfRepetitions(gameTable.getPhrase().getNumberOfRepetitions());
        return gameTableEntity;
    }

    public static GameTable getGameTable(GameTableEntity gameTableEntity){
        GameTable gameTable = new GameTable();
        gameTable.setId(gameTableEntity.getId());
        gameTable.setUserId(gameTableEntity.getUserId());
        gameTable.setGameId(gameTableEntity.getGameId());

        Phrase phrase = new Phrase();
        phrase.setId(gameTableEntity.getPhraseId());
        phrase.setCategoryName(gameTableEntity.getCategoryName());
        phrase.setTypeOfPhrase(gameTableEntity.getTypeOfPhrase());
        phrase.setPolishVersion(gameTableEntity.getPolishVersion());
        phrase.setEnglishVersion(gameTableEntity.getEnglishVersion());
        phrase.setAlreadyKnown(gameTableEntity.getAlreadyKnown());
        phrase.setNumberOfRepetitions(gameTableEntity.getNumberOfRepetitions());

        gameTable.setPhrase(phrase);

        return gameTable;
    }
}
