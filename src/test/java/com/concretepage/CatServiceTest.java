package com.concretepage;

import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.dao.RoleDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.Cat;
import net.proselyte.springsecurityapp.model.Profession;
import net.proselyte.springsecurityapp.service.CatService;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class CatServiceTest {
    @Autowired
    CatService catService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    Cat cat;
    Cat catSpy;

    @Before
    public void setUp() {

        //замена данного выражения более компактным

        //cat = new Cat();
//        catSpy = spy(cat);
//        cat.setName("vasya");
//        cat.setKg(20);
//        cat.setColor("blue");

        catService = spy(catService);
        cat = new Cat("vasya","blue",20);
        catSpy = spy(cat);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void save_SaveCat_ReturnsTrue() {
        catService.save(cat);
        when(catService.findByNameAndColor("vasya","blue")).thenReturn(cat);

        //замена данного выражения более компактным

//        Cat catSave =catService.findByNameAndColor("vasya","blue");
//        assertEquals(catSave.getKg(),cat.getKg(),0);

        assertEquals(catService.findByNameAndColor("vasya","blue").getKg(),cat.getKg(),0);
    }

    @Test
    public void save_saveCat_ReturnFalse() {
        cat.setId(null);
        doThrow(Exception.class).when(catService).save(cat);
        exception.expect(Exception.class);
        catService.save(cat);
        verify(catService).save(cat);
    }

    @Test
    public void delete_deleteCat_ReturnsTrue() {
        when(catSpy.getId()).thenReturn(1L);
        doNothing().when(catService).delete(isA(Long.class));
        when(catService.findById(catSpy.getId())).thenReturn(null);
        assertNull(catService.findById(catSpy.getId()));

//        verify(catService).delete(catSpy.getId());
    }

    @Test
    public void delete_deleteCat_ReturnsFalse() {
        when(catSpy.getId()).thenReturn(-1L);
        exception.expect(EmptyResultDataAccessException.class);
        catService.delete(catSpy.getId());
    }

    @Test
    public void findByName_findCatByName_ReturnsTrue() {
        when(catService.findByName(cat.getName())).thenReturn(cat);
        assertEquals(catService.findByName(cat.getName()).getId(),cat.getId());
    }

    @Test
    public void findByName_findCatByName_ReturnsFalse() {
        when(catService.findByName("")).thenReturn(null);
        assertNull(catService.findByName(""));
        verify(catService).findByName("");
//        assertEquals(catService.findByName(cat.getName()).getId(),cat.getId());
    }

    @Test
    public void findByNameAndColor_findCatByNameAndColor_ReturnsTrue() {
        when(catService.findByNameAndColor(cat.getName(),cat.getColor())).thenReturn(cat);
        assertEquals(catService.findByNameAndColor(cat.getName(),cat.getColor()).getId(),cat.getId());
    }

    @Test
    public void findByNameAndColor_findCatByNameAndColor_ReturnsNull() {
        when(catService.findByNameAndColor("","")).thenReturn(null);
        assertNull(catService.findByNameAndColor("",""));
    }

    @Test
    public void findById_checkFindById_ReturnsTrue() {
        when(catSpy.getId()).thenReturn(1L);
        when(catService.findById(catSpy.getId())).thenReturn(cat);

        //Замена данного выражение боллее комактным

//        Cat catResult = catService.findById(catSpy.getId());
//        System.out.println(catResult);
        assertEquals(catService.findById(catSpy.getId()).getId(),cat.getId());
    }

    @Test
    public void findById_checkFindById_ReturnsNull() {
        when(catService.findById(0L)).thenReturn(null);
        assertNull(catService.findById(0L));
        verify(catService).findById(0L);
    }
}
