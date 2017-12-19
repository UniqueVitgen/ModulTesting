package net.proselyte.springsecurityapp.service;

import org.springframework.security.core.Authentication;

/**
 * Service for Security.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

public interface SecurityService {

    String findLoggedInUsername();

    String findLoggedInUsername(Authentication auth);

    String findLoggedInUsername(String username, String password);

    Authentication autoLogin(String username, Object password);

    void autoLogin(String username, String password);
}
