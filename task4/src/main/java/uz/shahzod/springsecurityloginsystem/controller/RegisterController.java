package uz.shahzod.springsecurityloginsystem.controller;

import lombok.RequiredArgsConstructor;
import uz.shahzod.springsecurityloginsystem.entity.User;
import uz.shahzod.springsecurityloginsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uz.shahzod.springsecurityloginsystem.service.AuthService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final AuthService authService;

    @PostMapping("/register")
    public String registerUserForm(@Valid @ModelAttribute User user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "signup";
        }
        authService.userRegister(user,model);

        session.setAttribute("successRegister", "User registered successful");
        return "redirect:login";
    }
}
