package pl.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.service.domain.Type;

import java.util.Objects;

@Entity
@Table(name = "phrases")
@NoArgsConstructor
@Getter
@Setter
class PhraseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String categoryName;
    @Enumerated(EnumType.STRING)
    private Type typeOfPhrase;
    private String polishVersion;
    private String englishVersion;
    private boolean alreadyKnown;
    private int numberOfRepetitions;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhraseEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 17;
    }
}
