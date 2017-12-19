package com.concretepage;

import net.proselyte.springsecurityapp.model.Cat;
import net.proselyte.springsecurityapp.service.CatService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/appconfig-root.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class CatServiceIntegrationTest {
    //Рефакторим все вызвовы с функцией save
    @Autowired
    CatService catService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    Cat cat;

    @Before
    public void setUp() {

        if(catService.findById(2L) != null) catService.delete(2L);

        //замена данного выражения более компактным

        //рефакторинг вследствии изменения функции save
        cat = new Cat();
        cat.setName("vasya");
        cat.setKg(20);
        cat.setColor("blue");
        cat = catService.save(cat);
//        catService.save(cat);
//        cat = catService.findByName("vasya");
    }

    @After
    public void tearDown() {
        catService.delete(cat.getId());
    }

    @Test
    public void save_SaveCat_ReturnsTrue() {
        //рефакторинг вследствии изменения функции save
        Cat cat1 = catService.save(cat);

        assertEquals(cat1.getKg(),cat.getKg(),0);

        //замена данного выражения более компактным

//        Cat catSave =catService.findByNameAndColor("vasya","blue");
//        assertEquals(catSave.getKg(),cat.getKg(),0);

//        assertEquals(catService.findByNameAndColor("vasya","blue").getKg(),cat.getKg(),0);
    }

    @Test
    public void save_saveCat_ReturnFalse() {
        cat.setId(null);
        exception.expect(Exception.class);
        catService.save(cat);
    }

    @Test
    public void delete_deleteCat_ReturnsTrue() {
        Cat cat1 = new Cat();
        cat1.setName("bold");
        cat1.setKg(10);
        cat1.setColor("white");
        cat1 = catService.save(cat1);
        //рефакторинг вследствии изменения функции save
//        cat1 = catService.findByName("bold");
        catService.delete(cat1.getId());
        System.out.println(cat);
        assertNull(catService.findById(cat1.getId()));

//        verify(catService).delete(catSpy.getId());
    }

    @Test
    public void delete_deleteCat_ReturnsFalse() {
//        Cat cat1 = new Cat();
//        cat1.setName("bold");
//        cat1.setKg(10);
//        cat1.setColor("white");
//        catService.save(cat1);
//        cat1 = catService.findByName("bold");
        exception.expect(EmptyResultDataAccessException.class);
        catService.delete(-1L);
        System.out.println(cat);
//        exception.expect(EmptyResultDataAccessException.class);
//        catService.delete(cat.getId());
    }

    @Test
    public void findByName_findCatByName_ReturnsTrue() {
        assertEquals(catService.findByName(cat.getName()).getId(),cat.getId());
    }

    @Test
    public void findByName_findCatByName_ReturnsFalse() {
        assertNull(catService.findByName(""));
//        assertEquals(catService.findByName(cat.getName()).getId(),cat.getId());
    }

    @Test
    public void findByNameAndColor_findCatByNameAndColor_ReturnsTrue() {
        assertEquals(catService.findByNameAndColor(cat.getName(),cat.getColor()).getId(),cat.getId());
    }

    @Test
    public void findByNameAndColor_findCatByNameAndColor_ReturnsNull() {
        assertNull(catService.findByNameAndColor("",""));
    }

    @Test
    public void findById_checkFindById_ReturnsTrue() {

        //Замена данного выражение боллее комактным

//        Cat catResult = catService.findById(catSpy.getId());
//        System.out.println(catResult);
        assertEquals(catService.findById(cat.getId()).getId(),cat.getId());
    }

    @Test
    public void findById_checkFindById_ReturnsNull() {
        assertNull(catService.findById(0L));
    }
}
