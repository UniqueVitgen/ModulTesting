package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Cat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatDao extends JpaRepository<Cat, Long> {
    void deleteById(long id);

    Cat findByName(String name);

    Cat findByColor(String color);

    Cat findByNameAndColor(String name, String color);

}
