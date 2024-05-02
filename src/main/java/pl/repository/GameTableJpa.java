package pl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface GameTableJpa extends CrudRepository<GameTableEntity,Integer> {


    @Query("""
            Select g from GameTableEntity g where g.phraseId=:phraseId
            """)
    GameTableEntity findAllByPhraseId(Integer phraseId);

}
