package pl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


interface GameJpa extends CrudRepository<GameEntity, Integer> {
 @Query("""
            select g from GameEntity g          
            """)
 List<GameEntity> findAll();

}
