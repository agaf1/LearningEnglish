package pl.repository;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
 class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<PhraseEntity> phrases = new HashSet<>();

    void addNewPhraseEntity(PhraseEntity phraseEntity){
        this.phrases.add(phraseEntity);
    }

    void removePhraseEntity(PhraseEntity phraseEntity){
       this.phrases.remove(phraseEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 13;
    }
}
