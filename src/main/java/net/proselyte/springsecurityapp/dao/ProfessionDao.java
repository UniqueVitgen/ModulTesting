package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ProfessionDao extends JpaRepository<Profession, Long> {
    Profession findByName(String name);

    @Override
    List<Profession> findAll();
}
