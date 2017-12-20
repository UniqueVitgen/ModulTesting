package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.ProfessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
    private ProfessionDao professionDao;

    @Override
    public void delete(Long id) {
        professionDao.delete(id);
    }
}
