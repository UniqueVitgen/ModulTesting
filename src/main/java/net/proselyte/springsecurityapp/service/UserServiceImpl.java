package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.dao.RoleDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Role;
import net.proselyte.springsecurityapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private ProfessionDao professionDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setProfession(professionDao.findByName(user.getProfessionname()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleDao.getOne(1L));
        user.setRoles(roles);
        userDao.save(user);
    }

    public User findByUsernameOrId(String username, long id) {
        User user = userDao.findByUsername(username);
        if(user == null) {
            user = userDao.findOne(id);
        }
        return user;
    }

    public User findByFirstNameOrLastName(String firstname, String lastname) {
        User user = userDao.findByFirstname(firstname);
        if(user == null) {
            user = userDao.findByLastname(lastname);
        }
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
