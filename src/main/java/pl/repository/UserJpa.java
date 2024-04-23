package pl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


interface UserJpa extends CrudRepository<UserEntity, Integer> {
    @Query("""
            select u from UserEntity u            
            """)
    List<UserEntity> findAll();

    @Query(value = """
            select distinct phrases.category_name from phrases
             where phrases.id
             in (select phrases_id from users_phrases where user_entity_id=:userId)         
            """,nativeQuery = true)
    List<String> findAllCategoriesForUser(Integer userId);
}
