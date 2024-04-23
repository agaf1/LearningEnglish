package pl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

interface PhraseJpa extends CrudRepository<PhraseEntity , Integer> {

    @Query("""
            select p from PhraseEntity p where p.polishVersion=:value            
            """)
    Optional<PhraseEntity> findByPolishVersion(String value);

    @Query("""
            select p from PhraseEntity p where p.englishVersion=:value            
            """)
    Optional<PhraseEntity> findByEnglishVersion(String value);
}
