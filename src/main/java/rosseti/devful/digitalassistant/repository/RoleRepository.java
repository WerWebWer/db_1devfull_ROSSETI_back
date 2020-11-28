package rosseti.devful.digitalassistant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import rosseti.devful.digitalassistant.model.entities.Role;


/*
 * Репозиторий для работы с ролями
 */
public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<List<Role>> findByName(String name);

}