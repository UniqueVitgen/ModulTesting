package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByFirstname(String firstname);

    User findByLastname(String lastname);
}
