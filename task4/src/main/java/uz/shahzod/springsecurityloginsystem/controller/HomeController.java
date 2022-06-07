package uz.shahzod.springsecurityloginsystem.controller;

import lombok.RequiredArgsConstructor;
import uz.shahzod.springsecurityloginsystem.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.shahzod.springsecurityloginsystem.service.AuthService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AuthService authService;

    @RequestMapping("/")
    public String main() {
        return "redirect:login";
    }

    @RequestMapping("/login")
    public String login() {
        // checking if user is login or not
        // if user is logged in then redirect to the user dashboard and didn't allow to sign up until user logouts
        if (authService.isUserLoggedIn()) {
            return "redirect:/user/dashboard";
        }
        return "login";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        // checking if user is login or not
        // if user is logged in then not allowing to sign up
        if (authService.isUserLoggedIn()) {
            return "redirect:/user/dashboard";
        }
        model.addAttribute("user", new User());
        return "signup";
    }
}