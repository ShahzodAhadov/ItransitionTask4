package uz.shahzod.springsecurityloginsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.shahzod.springsecurityloginsystem.entity.User;
import uz.shahzod.springsecurityloginsystem.repository.UserRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


//    returns true if current user is blocking himself, else false
    public boolean blockUsers(Integer[] ids) {
        List<Integer> userIds = Arrays.asList(ids);

        List<User> allById = userRepository.findAllById(userIds);

        boolean isCurrentUserBlocked = false;
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(User u : allById){
            if(user.getUsername().equals(u.getEmail())) isCurrentUserBlocked = true;
            u.setBlocked(true);
            userRepository.save(u);
        }
        return isCurrentUserBlocked;
    }


    //    returns true if current user is delete himself, else false
    public boolean deleteUsers(Integer[] ids) {
        List<Integer> userIds = Arrays.asList(ids);

        List<User> allById = userRepository.findAllById(userIds);

        boolean isCurrentUserDeleted = false;
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        for(User u : allById){
            if(user.getUsername().equals(u.getEmail())) isCurrentUserDeleted = true;
            userRepository.deleteAllById(userIds);
        }
        return isCurrentUserDeleted;
    }

    public List<User> getAllUsers(){
        List<User> all = userRepository.findAll();
        return all;
    }

    public void setLastLoginTime(User user) {
        user.setLastLoginTime(new Date());
        userRepository.save(user);
    }

    public void unblockUsers(Integer[] ids) {
        List<Integer> userIds = Arrays.asList(ids);

        List<User> allById = userRepository.findAllById(userIds);

        for(User u : allById){
            if(u.isBlocked()) {
                u.setBlocked(false);
                userRepository.save(u);
            }
        }
    }

}
