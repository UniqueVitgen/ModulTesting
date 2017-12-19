package net.proselyte.springsecurityapp.service;

import net.proselyte.springsecurityapp.model.Cat;

public interface CatService {

//    void save(Cat cat);

    //Возвращаем присланный назад объект кота
    // уже с дополнительными заполнеными полями (как id)

    Cat save(Cat cat);

    Cat findById(long id);

    Cat findByName(String name);

    Cat findByNameAndColor(String name, String Color);

    void delete(long id);
}
