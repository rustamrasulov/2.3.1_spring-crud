package crud.controller;

import crud.model.User;
import crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
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

    @GetMapping(value = "delete/{id}")
    public String deleteUser(@PathVariable("id") int id, ModelMap model) {
        userService.deleteUserById(id);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newUser(User user) {
        return "/new";
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String saveUser(@Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            return "/new";
        }
        userService.addUser(user);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String findUser(@PathVariable("id") int id, ModelMap model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        System.out.println("User found");
        return "/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public String updateUser(@PathVariable("id") int id, @Validated User user, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "/edit";
        }
        userService.updateUser(user);
        model.addAttribute("users", userService.listUsers());
        return "redirect:/";
    }


}
