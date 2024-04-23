package pl.repository;

import pl.service.domain.Phrase;
import pl.service.domain.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository {

    User addNew(User user);

    boolean addPhrase(Integer userId, Phrase phrase);

    boolean deleteUserPhrase(Integer userId, Phrase phrase);


    List<User> getAll();

    Optional<User> findUserById(Integer id);

    List<String> findAllCategoriesForUser(Integer userId);
}
