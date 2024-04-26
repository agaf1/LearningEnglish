package pl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


interface UserJpa extends CrudRepository<UserEntity, Integer> {
    @Query("""
            select u from UserEntity u
            """)
    List<UserEntity> findAll();

    @Query("""
            select distinct p.categoryName from UserEntity u join u.phrases p where u.id=:userId
            """)
    List<String> findAllCategoriesForUser(Integer userId);

    @Query("""
            select p from UserEntity u join u.phrases p where u.id=:userId and p.categoryName=:categoryName
            """)
    List<PhraseEntity> findByCategoryName(Integer userId, String categoryName);
}
