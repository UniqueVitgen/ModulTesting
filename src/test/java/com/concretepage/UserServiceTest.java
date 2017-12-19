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
    public void findByUsernameOrId_checkFindByUsername_ReturnsTrue(){
        User user1 = userService.findByUsername("username1990");
        System.out.println(userService.findByUsernameOrId("username1990",0));
        assertEquals(user1.getId(),userService.findByUsernameOrId("username1990",0).getId());
//        assertNotNull(userService.findByUsernameOrId("username1990",0));
    }

    @Test
    public void findByUsernameOrId_checkFindById_ReturnsTrue(){
        User user1 = userService.findByUsernameOrId("user",2);
        System.out.println(userService.findByUsernameOrId("username1990",0));
        assertEquals(user1.getId(),userService.findByUsername("vitalors1997").getId());
//        assertNotNull(userService.findByUsernameOrId("username1990",0));
    }

    @Test
    public void findByFirstNameOrLastName_checkFindByFirstName_ReturnTrue(){
        User user1 = userService.findByFirstNameOrLastName("user","21");
        System.out.println(user1.getFirstname());
        System.out.println(user.getFirstname());
        Assert.assertEquals(user1.getFirstname(),user.getFirstname());
    }

    @Test
    public void findByFirstNameOrLastName_checkFindByLastName_ReturnTrue(){
        User user1 = userService.findByFirstNameOrLastName("user5","Orsik");
        System.out.println(user1.getFirstname());
        System.out.println(user.getFirstname());
        Assert.assertNotEquals(user1.getLastname(),user.getFirstname());
        Assert.assertEquals(user1.getLastname(),userService.findByUsernameOrId("234",4).getLastname());
    }


//    @Test
//    public void findByUsername_checkFindByUsername_ReturnsTrue(){
//        User user1 = userService.findByUsername(user.getUsername());
//        assertEquals(user1.getUsername(),user.getUsername());
//        assertEquals(user1.getFirstname(),user.getFirstname());
//        assertEquals(user1.getLastname(),user.getLastname());
//        assertEquals(user1.getId(),user.getId());
//        assertEquals(user1.getProfession(),user.getProfession());
//    }
//
//    @Test
//    public void save_checkSavingUser_ReturnsTrue(){
//        User user1 = userService.findByUsername(user.getUsername());
//        assertEquals(user1.getUsername(),user.getUsername());
//        assertEquals(user1.getFirstname(),user.getFirstname());
//        assertEquals(user1.getLastname(),user.getLastname());
//        assertEquals(user1.getId(),user.getId());
//        assertEquals(user1.getProfession(),user.getProfession());
//    }
}
