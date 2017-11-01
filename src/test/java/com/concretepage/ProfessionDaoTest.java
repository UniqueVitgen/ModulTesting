package com.concretepage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.dao.RoleDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Profession;
import net.proselyte.springsecurityapp.model.Role;
import net.proselyte.springsecurityapp.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ProfessionDaoTest {
    @Autowired
    private ProfessionDao professionDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private Profession profession;



    @Before
    public void setUp() {
        profession = new Profession();
        profession.setName("Tester");
        profession.setFee(500);
        professionDao.save(profession);
    }

    @After
    public void tearDown() {
        professionDao.delete(profession.getId());
    }

    @Test
    public void save_SaveProfession_ReturnsTrue(){
        int length = professionDao.findAll().size();
        Profession profession1 = new Profession();
        profession1.setFee(200);
        profession1.setName("New");
        professionDao.save(profession1);
        assertEquals(length+1,professionDao.findAll().size());
        professionDao.delete(profession1.getId());
    }


    @Test
    public void findOne_CheckFindById_ReturnsTrue(){
        Profession profession1 = professionDao.findOne(profession.getId());
        assertEquals(profession1.getName(),profession.getName());
        assertEquals(profession1.getFee(),profession.getFee());
        assertEquals(profession1.getId(),profession.getId());
        assertEquals(profession1.getUsers(),profession.getUsers());
    }

    @Test
    public void findOne_CheckFindByName_ReturnsTrue(){
        Profession profession1 = professionDao.findByName(profession.getName());
        assertEquals(profession1.getName(),profession.getName());
        assertEquals(profession1.getFee(),profession.getFee());
        assertEquals(profession1.getId(),profession.getId());
        assertEquals(profession1.getUsers(),profession.getUsers());
    }
}
