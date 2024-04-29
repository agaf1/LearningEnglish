package pl.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.service.domain.Game;
import pl.service.domain.User;
import pl.service.service.Exception.UserNotExistException;
import pl.service.service.GameService;
import pl.service.service.UserService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    @GetMapping(path = "/user/{userId}/games")
    public String showAllGames(@PathVariable int userId, Model model) {
        try {
            User user = userService.findByIdOrThrow(userId);

            List<Game> games = gameService.showAll();
            String userName = user.name();
            model.addAttribute("userName", userName);
            model.addAttribute("userId", userId);
            model.addAttribute("games", games);
            return "games";
        } catch (UserNotExistException e) {
            log.error("User not exists userId=" + userId, e);
            String message = "User not found";
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
        model.addAttribute("gameId", gameId);
        model.addAttribute("categories", categories);
        return "games-categoryPhrase";
    }

    @PostMapping(path = "/user/{userId}/games/{gameId}/{categoryName}")
    public void startTheGame(@PathVariable Integer userId,
                             @PathVariable Integer gameId,
                             @PathVariable String categoryName) {
        gameService.prepareTheGame(userId, gameId, categoryName);

    }


}
