package pl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.service.domain.Phrase;
import pl.service.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
class UserRepositoryImp implements UserRepository {

    @Autowired
    private UserJpa userJpa;
    @Autowired
    private PhraseRepositoryImp phraseRepositoryImp;
    @Autowired
    private MapperUserEntity mapperUserEntity;

    @Autowired
    private MapperPhraseEntity mapperPhraseEntity;

    @Override
    public User addNew(User user) {
        UserEntity userEntity = mapperUserEntity.mapToUserEntity(user);
        UserEntity savedUser = userJpa.save(userEntity);
        return mapperUserEntity.mapToUser(savedUser);
    }

    @Override
    public boolean addPhrase(Integer userId, Phrase phrase){
        Optional<UserEntity> userEntity = userJpa.findById(userId);
        if(userEntity.isEmpty()){
            return false;
        }else {
           boolean result = userEntity.get().addNewPhraseEntity(mapperPhraseEntity.mapToPhraseEntity(phrase));
           if(!result){
               return false;
           }else{
               userJpa.save(userEntity.get());
               return true;
           }
        }
    }

    @Override
    public boolean deleteUserPhrase(Integer userId, Phrase phrase) {
        Optional<UserEntity> userEntity = userJpa.findById(userId);
        if(userEntity.isPresent()) {
            boolean result = userEntity.get()
                    .removePhraseEntity(mapperPhraseEntity.mapToPhraseEntity(phrase));
            if(result) {
                userJpa.save(userEntity.get());
                return true;
            }
        }
        return false;
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
}
