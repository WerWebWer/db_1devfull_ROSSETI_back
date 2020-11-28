package rosseti.devful.digitalassistant.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rosseti.devful.digitalassistant.model.entities.Role;
import rosseti.devful.digitalassistant.model.entities.User;
import rosseti.devful.digitalassistant.repository.RoleRepository;
import rosseti.devful.digitalassistant.repository.UserRepository;
import rosseti.devful.digitalassistant.service.RolesService;

/**
 * Действия при старте приложения
 */
@Component
public class StartHooks implements CommandLineRunner {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String...args) throws Exception {

        List<String> currentRoles = rolesService.findAll()
                .stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());

        List<String> newRolesNames = Arrays
                .stream(new String [] {"ADMIN", "USER", })
                .filter(e -> !currentRoles.contains(e))
                .collect(Collectors.toList());

        // заполняем роли, если они не заполнены
        if (!newRolesNames.isEmpty()) {
            newRolesNames
                    .stream()
                    .map(e -> roleRepository.save(new Role(e)))
                    .collect(Collectors.toList());
        }

        User userAdmin = userRepository.findByUsername("admin");
        if (userAdmin == null) {
            System.out.println("userAdmin==null");
            User user = new User();
            user.setUsername("admin");
            //user.setPassword("$2y$10$MVtFnVKVb4mBMaHbhUAkUel1uKVgpVeUGmnFRsVyi0n/z.VJs37Be");
            user.setPassword("admin");
            userRepository.save(user);
        }

    }
}