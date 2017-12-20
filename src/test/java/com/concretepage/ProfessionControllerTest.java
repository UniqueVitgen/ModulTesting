package com.concretepage;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import net.proselyte.springsecurityapp.controller.ProfessionController;
import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.model.Profession;
import net.proselyte.springsecurityapp.service.ProfessionService;
import net.proselyte.springsecurityapp.service.ProfessionServiceImpl;
import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.web.filter.DelegatingFilterProxy;

@RunWith(SpringJUnit4ClassRunner.class)
@DbUnitConfiguration(databaseConnection={"dataSource"})
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@WebAppConfiguration
//@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
//        DirtiesContextTestExecutionListener.class,
//        TransactionalTestExecutionListener.class,
//        DbUnitTestExecutionListener.class })
public class ProfessionControllerTest {

//    @Autowired
//    private WebApplicationContext wac;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Autowired
    private ProfessionDao professionDao;

    @Autowired
    private ProfessionService professionService;

    @InjectMocks
    private ProfessionController professionController;


    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
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

    @Test
    public void history_checkHistory_ReturnPage() throws Exception{
        String urlTemplate = "/history-" + professionDao.findAll().get(0).getId();
        mvc.perform(get(urlTemplate).with(user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("historyProfession"));
    }

    @Test
    public void add_checkAdd_ReturnPage() throws Exception {
        String urlTemplate = "/add";
        mvc.perform(get(urlTemplate).with(user("user")))
                .andExpect(status().isOk())
                .andExpect(view().name("addProfession"));
    }

    @Test
    public void save_checkCancel_ReturnTrue() throws Exception {
        String urlTemplate = "/save?name=&fee=&action=cancel";
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/change"));
    }

    @Test
    @ExpectedDatabase("professions.xml")
    public void save_checkSaveWithValidData_ReturnTrue() throws Exception {
        String urlTemplate = "/save?name=ab&fee=200&action=save";
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeDoesNotExist("emptyName","emptyFee","badSizeName","badSymbolsName"))
                .andExpect(view().name("redirect:/change"));
        professionDao.delete(professionDao.findAll().get(professionDao.findAll().size()-1));
    }

    @Test
    public void save_checkSaveWithInvalidData_ReturnTrue() throws Exception {
        String urlTemplate = "/save?name=11&fee=200&action=save";
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(model().attributeExists("badSymbolsName"))
                .andExpect(view().name("addProfession"));
    }

    @Test
    public void delete_checkDelete_ReturnTrue() throws Exception {

        Profession profession = new Profession();
        profession.setName("name");
        profession.setFee(200);
        professionDao.save(profession);
        profession = professionDao.findAll().get(professionDao.findAll().size()-1);
        String urlTemplate = "/delete-" +(profession.getId());
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/change"));

    }

    @Test
    public void edit_checkSave_ReturnFalse()throws Exception{
        Profession profession = professionDao.findAll().get(professionDao.findAll().size()-1);
        String urlTemplate = "/edit-" +(profession.getId())+"?name=120&fee=200&action=save";
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("editProfession"));
    }

    @Test
    public void edit_checkSave_ReturnTrue()throws Exception{Profession profession = new Profession();
        profession.setName("name");
        profession.setFee(200);
        professionDao.save(profession);
        profession = professionDao.findAll().get(professionDao.findAll().size()-1);
        String urlTemplate = "/edit-" +(profession.getId())+"?name=abb&fee=200&action=save";
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/welcome"));
        professionDao.delete(profession.getId());
    }

    @Test
    public void edit_checkCancel_ReturnTrue()throws Exception{
        Profession profession = professionDao.findAll().get(professionDao.findAll().size()-1);
        String urlTemplate = "/edit-" +(profession.getId())+"?name=120&fee=200&action=cancel";
        mvc.perform(get(urlTemplate).with(user("user").roles("ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/welcome"));
    }

//    public void changeProfession_WithAdmin_ReturnPage()
}
