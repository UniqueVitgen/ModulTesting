package com.concretepage;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Profession;
import net.proselyte.springsecurityapp.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.request.DefaultRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import javax.servlet.Filter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
public class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    @Autowired
    private  Filter springSecurityFilterChain;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProfessionDao professionDao;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters(springSecurityFilterChain)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void statistics_withUser_ReturnPage() throws Exception {
        String urlTemplate = "/statistics";
        mvc.perform(get(urlTemplate).with(user("user").roles("USER")))
                .andExpect(model().attributeExists("professions"));
//                .andExpect(model().attributeExists("profession"));
    }

    @Test
    public void statistics_withAdmin_ReturnPage() throws Exception {
        String urlTemplate = "/statistics";
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(model().attributeExists("professions"));
//                .andExpect(model().attributeExists("profession"));
    }

    @Test
    public void statistics_withoutUser_ReturnPage() throws Exception {
        String urlTemplate = "/statistics";
        mvc.perform(get(urlTemplate))
                .andExpect(redirectedUrl("http://localhost/login"));
//                .andExpect(model().attributeExists("profession"));
    }

    @Test
    public void getRegistration_checkRegistration_returnPage() throws Exception {
        String urlTemplate = "/registration";
        mvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andDo(print())
        .andExpect(view().name("registration"));
//                .andExpect(model().attributeExists("profession"));
    }

    @Test
    public void postRegistration_checkRegistrationWithInvalidData() throws Exception {
        String urlTemplate = "/registration";
        User user = new User();
        user.setUsername("user1");
        user.setLastname("last");
        user.setFirstname("last");
        user.setPassword("password");
        user.setConfirmPassword("password");
        String json = mapper.writeValueAsString(user);
        mvc.perform(post(urlTemplate).with(csrf())
                .param("username","user1")
                        .param("lastname","first")
                        .param("firstname","first")
                        .param("professionname","Билдел")
                        .param("password","password")
                        .param("confirmPassword","password")
                .param("_csrf","59db1fbf-5f0a-4d8e-8e24-55721a153fdf")

                .flashAttr("userForm",user)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void postRegistration_checkRegistrationWithValidData() throws Exception {
        String urlTemplate = "/registration";
        Profession profession = professionDao.findAll().get(professionDao.findAll().size()-1);
        User user = userDao.findAll().get(userDao.findAll().size()-1);
        mvc.perform(post(urlTemplate).with(csrf())
                .param("username","username" + user.getId())
                .param("lastname","first")
                .param("firstname","first")
                .param("professionname",profession.getName())
                .param("password","password")
                .param("confirmPassword","password")
        )
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/welcome"));
        user = userDao.findAll().get(userDao.findAll().size()-1);
        userDao.delete(user.getId());
    }

    @Test
    public void login_simpleLogin_ReturnPage() throws Exception {
        String urlTemplate = "/login";
        mvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void login_logoutLogin_ReturnPage() throws Exception {
        String urlTemplate = "/login?logout";
        mvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void login_errorLogin_ReturnPage() throws Exception {
        String urlTemplate = "/login?error";
        mvc.perform(get(urlTemplate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void welcome_testWelcome_ReturnPage() throws Exception {
        String urlTemplate = "/welcome";
        mvc.perform(get(urlTemplate).with(user("suer")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("welcome"));
    }

    @Test
    public void change_testChange_ReturnPage() throws Exception {
        String urlTemplate = "/change";
        mvc.perform(get(urlTemplate).with(user("suer")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("changeProfession"));
    }
}
