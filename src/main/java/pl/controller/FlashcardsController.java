package pl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.service.domain.AnswerResult;
import pl.service.domain.Phrase;
import pl.service.service.FlashcardsService;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FlashcardsController {

    private final FlashcardsService flashcardsService;


    @GetMapping(path = "/{userId}/flashcards")
    public String nextWord(@PathVariable Integer userId, Model model) {
        if (flashcardsService.getListOfWord() == false) {
            model.addAttribute("userId", userId);
            return "flashcards-end";
        }
        Phrase phrase = flashcardsService.nextWord();

        model.addAttribute("phrase", phrase);
        model.addAttribute("userId", userId);
        return "flashcards-next-word";
    }

    @PostMapping(path = "/{userId}/flashcards/translate/{phraseId}")
    public String checkAnswer(@PathVariable Integer userId,
                              @PathVariable Integer phraseId,
                              @ModelAttribute AnswerDTO answerDTO,
                              Model model) {
        AnswerResult result = flashcardsService.checkAnswer(phraseId, answerDTO.englishVersion());
        String massage;
        if (result.isCorrect() == true) {
            massage = "Good answer ! ";
        } else {
            massage = "Wrong answer! Correct answer is: " + result.correctWord() ;
        }
        model.addAttribute("message",massage);
        model.addAttribute("userId",userId);

        return "flashcards-answer";
    }


}
