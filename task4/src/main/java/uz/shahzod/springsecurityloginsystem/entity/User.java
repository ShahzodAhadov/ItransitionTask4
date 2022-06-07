package uz.shahzod.springsecurityloginsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "Name should not be blank")
    @Size(max = 20, message = "Name should not exceed 20 characters")
    private String name;

    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, message = "Password length should be minimum 8 characters")
    private String password;

    @NotBlank(message = "Email should not be blank")
    @Column(unique = true)
    private String email;

    private Date lastLoginTime;
    private Date registrationTime;
    private boolean isBlocked;
    private String role;

    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.registrationTime = new Date();
    }


    public User(String name, String password, String email, boolean isAccountNonExpired, boolean isAccountNonLocked, boolean isCredentialsNonExpired, boolean isEnabled) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.lastLoginTime = new Date();
        this.registrationTime = new Date();
        this.isBlocked = false;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", isAccountNonExpired=" + isAccountNonExpired +
                ", isAccountNonLocked=" + isAccountNonLocked +
                ", isCredentialsNonExpired=" + isCredentialsNonExpired +
                ", isEnabled=" + isEnabled +
                '}';
    }
}