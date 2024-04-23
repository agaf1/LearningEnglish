package pl.service.service;

import pl.repository.PhraseRepository;
import pl.repository.UserRepository;
import pl.service.domain.Phrase;
import pl.service.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.service.service.Exception.PhraseNotExistException;
import pl.service.service.Exception.UserNotExistException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PhraseRepository phraseRepository;

    @Transactional
    public User create(User user) {
        return userRepository.addNew(user);
    }

    @Transactional
    public boolean addPhrase(Integer userId, Phrase phrase) {
        return userRepository.addPhrase(userId, phrase);
    }

    @Transactional
    public boolean deletePhrase(Integer userId, String typeOfData, String value) throws PhraseNotExistException {
        Optional<Phrase> phrase;
        switch (typeOfData) {
            case "ID"-> {
                Integer phraseId = Integer.valueOf(value);
                phrase = phraseRepository.findById(phraseId);
            }
            case "polishVersion"-> phrase = phraseRepository.findByPolishVersion(value);
            case "englishVersion" -> phrase = phraseRepository.findByEnglishVersion(value);
            default->throw new PhraseNotExistException("This phrase was not founded.");
        }
        if(phrase.isEmpty()){
            throw new PhraseNotExistException("This phrase was not founded.");
        }
        return userRepository.deleteUserPhrase(userId,phrase.get());
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User findByIdOrThrow(Integer id) throws UserNotExistException {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotExistException(String.format("User with  id {} is not exist", id)));
    }

    public List<String> findAllCategoriesForUser(Integer userId){
        return userRepository.findAllCategoriesForUser(userId);
    }

}
