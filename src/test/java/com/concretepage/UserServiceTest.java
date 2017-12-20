package com.concretepage;

import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.dao.RoleDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Role;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    private User user;

    @Autowired
    private ProfessionDao professionDao;

    @Before
    public void setUp(){
        user = new User();
        user.setRoles(new HashSet<Role>());
        user.setPassword("123456789");
        user.setLastname("user");
        user.setFirstname("user");
        user.setProfession(professionDao.findOne(1L));
        user.setConfirmPassword(user.getPassword());
        user.setUsername("username1990");
        userService.save(user);
    }

    @After
    public void TearDown(){
        userDao.delete(user);
    }


    @Test
    public void save_testSave_ReturnTrue() {

    }

    @Test
    public void findByUsername_testFindByUsername_ReturnUser() {
        User findedUser = userService.findByUsername("username1990");
        assertEquals(findedUser.getUsername(), user.getUsername());
    }
}
