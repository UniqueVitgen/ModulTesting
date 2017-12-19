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

    private BeanPropertyBindingResult bindingResultTest;
    private BeanPropertyBindingResult bindingResultValidator;

    @Before
    public void setUp(){
        user = new User();
        bindingResultTest = new BeanPropertyBindingResult(user, "recordForm");
        bindingResultValidator = new BeanPropertyBindingResult(user, "recordForm");
    }

    @After
    public void TearDown(){

    }

    @Test
    public void validate_withSizeUsernameAndSizePassword_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeFirstNameAndSizePassword_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeLastNameAndSizePassword_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsername_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndDontMatchConfirmPassword_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("supermane");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeFirstNameAndSizeLastNameAndSizePassword_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superma");
        user.setConfirmPassword("supermane");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("password", "Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeFirstName_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeFirstNameAndDontMatchConfirmPassword_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeFirstNameAndSizeLastName_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeFirstNameAndSizeLastNameAndDontMatchConfrirmPassword_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeLastName_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeUsernameAndSizeLastNameAndDontMatchConfirmPassword_ReturnError(){
        user.setUsername("user1");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("supermana");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Size.userForm.username");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    //13-24

    @Test
    public void validate_withSizePassword_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeFirstNameAndSizePassword_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeLastNameAndSizePassword_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_checkValidation_ReturnSuccess(){
        user.setUsername("username1");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDontMatchConfirmPassword_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("supermane");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeFirstNameAndSizeLastNameAndSizePassword_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superma");
        user.setConfirmPassword("supermane");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("password", "Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeFirstName_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeFirstNameAndDontMatchConfirmPassword_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeFirstNameAndSizeLastName_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeFirstNameAndSizeLastNameAndDontMatchConfrirmPassword_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeLastName_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withSizeLastNameAndDontMatchConfirmPassword_ReturnError(){
        user.setUsername("username1");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("supermana");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    //25-36

    @Test
    public void validate_withDuplicateUsernameAndSizePassword_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeFirstNameAndSizePassword_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeLastNameAndSizePassword_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superma");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("password","Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsername_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndDontMatchConfirmPassword_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("big");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("supermane");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeFirstNameAndSizeLastNameAndSizePassword_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superma");
        user.setConfirmPassword("supermane");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("password", "Size.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeFirstName_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeFirstNameAndDontMatchConfirmPassword_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("b");
        user.setLastname("big");
        user.setPassword("superman");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeFirstNameAndSizeLastName_ReturnError(){
        user.setUsername("vitalors1997");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeFirstNameAndSizeLastNameAndDontMatchConfrirmPassword_ReturnError(){
        user.setUsername("username1997");
        user.setFirstname("b");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superma");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("firstname", "Size.userForm.firstname");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeLastName_ReturnError(){
        user.setUsername("username1997");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("superman");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

    @Test
    public void validate_withDuplicateUsernameAndSizeLastNameAndDontMatchConfirmPassword_ReturnError(){
        user.setUsername("username1997");
        user.setFirstname("big");
        user.setLastname("b");
        user.setPassword("superman");
        user.setConfirmPassword("supermana");
        userValidator.validate(user,bindingResultValidator);
        bindingResultTest.rejectValue("username", "Duplicate.userForm.username");
        bindingResultTest.rejectValue("lastname", "Size.userForm.lastname");
        bindingResultTest.rejectValue("confirmPassword", "Different.userForm.password");
        assertEquals(bindingResultTest,bindingResultValidator);
    }

}
