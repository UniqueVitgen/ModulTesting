package com.concretepage;

import net.proselyte.springsecurityapp.controller.ProfessionController;
import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.model.Profession;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
public class ProfessionControllerTest {

//    @Autowired
//    private WebApplicationContext wac;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Autowired
    ProfessionDao professionDao;

    @InjectMocks
    private ProfessionController professionController;

    @Test
    @WithMockUser
    public void changeProfession_WithUser_ReturnPage() throws Exception {

        Profession profession = professionDao.findAll().get(0);
        System.out.println(profession.getId());
        String urlTemplate = "/change-"+profession.getId();
        mvc.perform(get(urlTemplate))
                .andExpect(status().is4xxClientError());
//        .andExpect(model().attributeExists("profession"));
    }

    @Test
    public void changeProfession_WithAdmin_ReturnPage() throws Exception {
        Profession profession = professionDao.findAll().get(0);
        System.out.println(profession.getId());
        String urlTemplate = "/change-"+profession.getId();
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(model().attributeExists("profession"));
    }

    @Test
    public void changeProfession_WithoutUser_ReturnRedirect() throws Exception {
        Profession profession = professionDao.findAll().get(0);
        System.out.println(profession.getId());
        String urlTemplate = "/change-"+profession.getId();
        mvc.perform(get(urlTemplate))
                .andExpect(redirectedUrl("http://localhost/login"));
    }

//    public void changeProfession_WithAdmin_ReturnPage()
}
