package uz.shahzod.springsecurityloginsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uz.shahzod.springsecurityloginsystem.entity.User;
import uz.shahzod.springsecurityloginsystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    // method to check if user is logged in or not
    public boolean isUserLoggedIn() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.getUserByEmail(userDetails.getUsername());
            if(user != null && !user.isBlocked()) {
                user.setLastLoginTime(new Date());
                userRepository.save(user);
                return true;
            }else
                return false;

        }
        return false;

    }

    public void userRegister(User user, Model model){
        try {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRole("ROLE_USER");

            user.setRegistrationTime(new Date());
            user.setLastLoginTime(new Date());
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);

            model.addAttribute("user", user);
            this.userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
        }
    }

}
