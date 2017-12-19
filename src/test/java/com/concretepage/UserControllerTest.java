package com.concretepage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
public class UserControllerTest {

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
}
