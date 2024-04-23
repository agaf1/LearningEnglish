package pl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.service.domain.Phrase;
import pl.service.service.Exception.PhraseNotExistException;
import pl.service.service.UserService;

@Controller
public class PhraseController {

    @Autowired
    private UserService userService;
    @Autowired
    private PhraseMapper phraseMapper;

    @GetMapping(path = "/user/{userId}/phrases")
    public String managePhrases(@PathVariable Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "phrases";
    }

    @GetMapping(path = "/user/{userId}/phrases/addForm")
    public String addPhrase(@PathVariable Integer userId, Model model) {
        model.addAttribute("userId", userId);
        return "addPhrase";
    }

    @PostMapping(path = "/user/{userId}/phrases/create")
    public RedirectView createNewPhrase(@PathVariable Integer userId, @ModelAttribute PhraseDto phraseDto) {
        Phrase phrase = phraseMapper.mapToPhrase(phraseDto);
        userService.addPhrase(userId, phrase);
        return new RedirectView("/user/{userId}/phrases");
    }

    @GetMapping(path = "/user/{userId}/phrases/deleteForm")
    public String deletePhraseForm(@PathVariable Integer userId, Model model){
        model.addAttribute("userId", userId);
        return "deletePhrase";
    }

    @PostMapping(path = "/user/{userId}/phrases/delete")
    public RedirectView deletePhraseFromDB(@PathVariable Integer userId, String typeOfData, String value) throws PhraseNotExistException {
        userService.deletePhrase(userId,typeOfData,value);
        return new RedirectView("/user/{userId}/phrases");
    }

}
