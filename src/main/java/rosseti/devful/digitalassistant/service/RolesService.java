package rosseti.devful.digitalassistant.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import rosseti.devful.digitalassistant.model.entities.Role;
import rosseti.devful.digitalassistant.repository.RoleRepository;
;

/**
 * Сервис для работы с ролями пользователей
 */
@Service
public class RolesService {

    private final RoleRepository roleRepository;

    public RolesService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}