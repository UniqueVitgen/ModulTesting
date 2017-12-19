package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.User;

/**
 * Service class for {@link net.proselyte.springsecurityapp.model.User}
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    public User findByUsernameOrId(String username, long id);

    public User findByFirstNameOrLastName(String firstname, String lastname);
}
