package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String showUsers(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.listUsers());
        return "index";
    }

    @DeleteMapping(value = "delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/new")
    public String newUser(User user) {
        return "/new";
    }

    @PutMapping(value = "/new")
    public String saveUser(@Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return "/new";
        }
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/edit/{id}")
    public String findUser(@PathVariable("id") int id, ModelMap model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "/edit";
    }

    @PatchMapping(value = "/edit/{id}")
    public String updateUser(@PathVariable("id") int id,
                             @Validated User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "/edit";
        }

        userService.updateUser(user);
        return "redirect:/";
    }
}