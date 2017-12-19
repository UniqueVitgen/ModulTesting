package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.dao.CatDao;
import net.proselyte.springsecurityapp.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatServiceImpl implements CatService {

    @Autowired
    CatDao catDao;

    CatServiceImpl() throws Exception {
//        throw new Exception();
    }
    //    void save(Cat cat) { catDao.save(cat);}
    //Возвращаем присланный назад объект кота
    // уже с дополнительными заполнеными полями (как id)
    @Override
    public Cat save(Cat cat) {
        catDao.save(cat);
        return catDao.findAll().get(catDao.findAll().size()-1);
    }

    @Override
    public Cat findById(long id) {
        return catDao.findOne(id);
    }

    @Override
    public Cat findByName(String name) {
        return catDao.findByName(name);
    }

    @Override
    public Cat findByNameAndColor(String name, String color) {
        return catDao.findByNameAndColor(name, color);
    }

    @Override
    public void delete(long id) {
        catDao.delete(id);
    }
}
