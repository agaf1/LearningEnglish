package pl.repository;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.type.NumericBooleanConverter;
import pl.service.domain.Type;

import java.util.Objects;

@Entity
@Table(name = "phrases")
@Data
class PhraseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String categoryName;
    @Enumerated(EnumType.STRING)
    private Type typeOfPhrase;
    private String polishVersion;
    private String englishVersion;
    @Convert(converter = NumericBooleanConverter.class)
    private boolean alreadyKnown;
    private Integer numberOfRepetitions;


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
