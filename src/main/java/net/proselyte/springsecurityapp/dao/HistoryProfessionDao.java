package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.HistoryProfession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryProfessionDao extends JpaRepository<HistoryProfession,Long> {
    List<HistoryProfession> findAllByPid(Long pid);

    HistoryProfession findHistoryProfessionByPid(Long pid);

}
