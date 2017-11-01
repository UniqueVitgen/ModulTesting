package com.concretepage;

import javax.transaction.Transactional;

import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.dao.RoleDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Profession;
import net.proselyte.springsecurityapp.model.Role;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.validator.UserValidator;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ValidationTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private ProfessionDao professionDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserValidator userValidator;

    private Profession profession;

    private User user;

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
    }

    @After
    public void TearDown(){

    }

    @Test
    public void validate_checkValidateDataOfUser_ReturnTrue(){

        Errors errors = new BeanPropertyBindingResult(user, "userForm");
        userValidator.validate(user,errors);

        assertFalse(errors.hasErrors());

    }


    @Test
    public void validate_checkValidateUsername_ReturnFalse(){
        user.setUsername("user");
        Errors errors = new BeanPropertyBindingResult(user, "userForm");
        userValidator.validate(user,errors);
        String goal = errors.getAllErrors().get(0).toString();
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("username"));

    }

    @Test
    public void validate_checkValidatePassword_ReturnFalse(){
        user.setPassword("123");
        Errors errors = new BeanPropertyBindingResult(user, "userForm");
        userValidator.validate(user,errors);
        String goal = errors.getAllErrors().get(0).toString();
        assertTrue(errors.hasErrors());
        assertNotNull(errors.getFieldError("password"));

    }

    public ValidationTest() {
    }
}
