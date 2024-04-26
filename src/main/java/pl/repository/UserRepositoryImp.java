package pl.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.service.domain.Phrase;
import pl.service.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserRepositoryImp implements UserRepository {

    private final UserJpa userJpa;
    private final MapperUserEntity mapperUserEntity;
    private final MapperPhraseEntity mapperPhraseEntity;

    @Override
    public User addNew(User user) {
        UserEntity userEntity = mapperUserEntity.mapToUserEntity(user);
        UserEntity savedUser = userJpa.save(userEntity);
        return mapperUserEntity.mapToUser(savedUser);
    }

    @Override
    public boolean addPhrase(Integer userId, Phrase phrase) {

        UserEntity userEntity = userJpa.findById(userId).orElseThrow();

        boolean result = userEntity.addNewPhraseEntity(mapperPhraseEntity.mapToPhraseEntity(phrase));

        if (result == true) {
            userJpa.save(userEntity);
        }
        return result;
    }

    @Override
    public boolean deleteUserPhrase(Integer userId, Phrase phrase) {

        UserEntity userEntity = userJpa.findById(userId).orElseThrow();

        boolean result = userEntity.removePhraseEntity(mapperPhraseEntity.mapToPhraseEntity(phrase));

        if (result) {
            userJpa.save(userEntity);
        }
        return result;
    }

    @Override
    public List<User> getAll() {
        List<UserEntity> usersEntity = userJpa.findAll();
        return usersEntity.stream().map(mapperUserEntity::mapToUser).toList();
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return userJpa.findById(id).map(mapperUserEntity::mapToUser);
    }

    @Override
    public List<String> findAllCategoriesForUser(Integer userId) {
        return userJpa.findAllCategoriesForUser(userId);
    }

    @Override
    public List<Phrase> findByCategoryName(Integer userId, String categoryName) {
        return userJpa.findByCategoryName(userId, categoryName)
                .stream().map(p -> mapperPhraseEntity.mapToPhrase(p))
                .toList();
    }
}
