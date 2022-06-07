package uz.shahzod.springsecurityloginsystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;
import uz.shahzod.springsecurityloginsystem.entity.User;
import uz.shahzod.springsecurityloginsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.shahzod.springsecurityloginsystem.service.UserService;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @RequestMapping("/dashboard")
    public String dashboard(Principal principal, Model model) {
        User user = this.userRepository.getUserByEmail(principal.getName());

        if (user!= null && !user.isBlocked() ) {
            userService.setLastLoginTime(user);

            List<User> allUsers = userService.getAllUsers();
            assert allUsers != null;

            model.addAttribute("allUsers", allUsers);
            model.addAttribute("user", user);

            return "user/dashboard";
        } else {
            return "redirect:/login";
        }
    }
    @PostMapping("/block")
    public String block(@RequestBody Integer[] ids){
        boolean isCurrentUserBlocked = userService.blockUsers(ids);
        if(isCurrentUserBlocked)
            return "/";
        else
            return "user/dashboard";
    }
    @PostMapping("/delete")
    public ModelAndView delete(@RequestBody Integer[] ids){
        boolean isCurrentUserDeleted = userService.deleteUsers(ids);
        if(isCurrentUserDeleted) {
            ModelAndView mav = new ModelAndView("redirect:/");
            return mav;
        }else {
            ModelAndView mav = new ModelAndView("redirect:/user/dashboard");
            return mav;

        }
    }

    @PostMapping("/unblock")
    public String unblock(@RequestBody Integer[] ids){
        userService.unblockUsers(ids);
        return "user/dashboard";
    }



}
