package rosseti.devful.digitalassistant.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rosseti.devful.digitalassistant.model.entities.User;
import rosseti.devful.digitalassistant.repository.RoleRepository;
import rosseti.devful.digitalassistant.repository.UserRepository;


/**
 * Сервис для работы с сущностями пользователей
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found.");
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                new ArrayList<>(user.getRoles()));
        return userDetails;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public User findUserByName(String name) {
        User user = userRepository.findByUsername(name);
        if(user == null){
            throw new UsernameNotFoundException("User not found.");
        }
        return user;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public Long saveUser(User user) {
        return userRepository.save(user).getId();
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
}
