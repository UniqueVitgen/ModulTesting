package com.concretepage;

import static org.junit.Assert.assertEquals;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class UserDaoTest {
  @Autowired
  private ProfessionDao professionDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private RoleDao roleDao;

  private Profession profession;

  private User user;



  @Before
  public void setUp() {
    user = new User();
    user.setRoles(new HashSet<Role>());
    user.setPassword("123456789");
    user.setLastname("user");
    user.setFirstname("user");
    user.setProfession(professionDao.findOne(1L));
    user.setConfirmPassword(user.getPassword());
    user.setUsername("username1990");
    userDao.save(user);
  }

  @After
  public void tearDown() {
    userDao.delete(user);
  }

  @Test
  public void findOne_CheckFindById_ReturnsTrue(){
    User user1 = userDao.findOne(user.getId());
    assertEquals(user1,user);
  }

  @Test
  public void save_checkSaveUser_ReturnsTrue(){
    int length = userDao.findAll().size();
    User user1 = new User();
    user1.setRoles(new HashSet<Role>());
    user1.setPassword("123456789");
    user1.setLastname("user");
    user1.setFirstname("user");
    user1.setProfession(professionDao.findOne(1L));
    user1.setConfirmPassword(user.getPassword());
    user1.setUsername("username1990");

    userDao.save(user1);
    assertEquals(length+1,userDao.findAll().size());
    userDao.delete(user1.getId());
  }

  @Test
  public void findByUsernameame_CheckFindByUsername_ReturnsTrue(){
    User user1 = userDao.findByUsername(user.getUsername());
    assertEquals(user1.getUsername(),user.getUsername());
    assertEquals(user1.getFirstname(),user.getFirstname());
    assertEquals(user1.getLastname(),user.getLastname());
    assertEquals(user1.getId(),user.getId());
    assertEquals(user1.getProfession(),user.getProfession());
    assertEquals(user1.getConfirmPassword(),user.getConfirmPassword());
    assertEquals(user1,user);
  }
}
