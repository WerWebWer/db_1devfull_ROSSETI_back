package rosseti.devful.digitalassistant.repository;

import org.springframework.data.repository.CrudRepository;
import rosseti.devful.digitalassistant.model.entities.User;

import java.util.List;

/*
 * Репозиторий для работы с пользователями
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    List<User> findAll();
}