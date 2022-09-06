package ru.elias.server.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import ru.elias.server.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String name);

}
