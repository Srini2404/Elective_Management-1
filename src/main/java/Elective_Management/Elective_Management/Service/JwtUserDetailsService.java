package Elective_Management.Elective_Management.Service;

import Elective_Management.Elective_Management.Entity.Role;
import Elective_Management.Elective_Management.Entity.User;
import Elective_Management.Elective_Management.dao.UserDAO;
import Elective_Management.Elective_Management.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private JwtRolesService roleService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDAO userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                getAuthority(user));
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return user;
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public User saveStudent(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder().encode(user.getPassword()));

        Role role = roleService.findByName("STUDENT");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        newUser.setRoles(roleSet);

        return userDao.save(newUser);
    }

    public User saveInstructor(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder().encode(user.getPassword()));

        Role role = roleService.findByName("INSTRUCTOR");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        newUser.setRoles(roleSet);

        return userDao.save(newUser);
    }

    public User saveAdmin(UserDTO user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder().encode(user.getPassword()));
        Role role = roleService.findByName("ADMIN");
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        role = roleService.findByName("STUDENT");
        roles.add(role);
        role = roleService.findByName("INSTRUCTOR");
        roles.add(role);

        newUser.setRoles(roles);
        return userDao.save(newUser);
    }
}
