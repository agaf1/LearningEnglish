package pl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.service.domain.User;
import pl.service.service.UserService;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(path = "/users")
    public String showAllUsers(Model model){
        List<User> users = userService.getAll();
        model.addAttribute("users",users);
        return "users";
    }

    @GetMapping(path = "/users/add")
    public String addUser(){
        return "addUser";
    }

    @PostMapping(path = "/users/create")
    public RedirectView createUser(@ModelAttribute UserDTO userDTO){
        User newUser = userMapper.mapToUser(userDTO);
        User sevedUser = userService.create(newUser);
        return new RedirectView("/users");
    }
}
