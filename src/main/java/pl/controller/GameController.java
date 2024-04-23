package pl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.service.domain.Game;
import pl.service.domain.User;
import pl.service.service.Exception.UserNotExistException;
import pl.service.service.GameService;
import pl.service.service.UserService;

import java.util.List;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    @GetMapping(path = "/user/{userId}/games")
    public String showAllGames(@PathVariable  int userId, Model model) {
        try {
            User user = userService.findByIdOrThrow(userId);

            List<Game> games = gameService.showAll();
            String userName = user.name();
            model.addAttribute("userName", userName);
            model.addAttribute("userId", userId);
            model.addAttribute("games", games);
            return "games";
        } catch (UserNotExistException e) {
            String message = e.getMessage();
            model.addAttribute("message", message);
            return "exception";
        }
    }

    @GetMapping(path = "/user/{userId}/games/{gameId}")
    public String showAllCategoryOfPhrasesForThisUser(
            @PathVariable int userId, @PathVariable int gameId, Model model) throws UserNotExistException {
            List<String> categories = userService.findAllCategoriesForUser(userId);
            String userName = userService.findByIdOrThrow(userId).name();
            model.addAttribute("userName", userName);
            model.addAttribute("userId", userId);
            model.addAttribute("gameId",gameId);
            model.addAttribute("categories", categories);
        return "games-categoryPhrase";
    }


}
