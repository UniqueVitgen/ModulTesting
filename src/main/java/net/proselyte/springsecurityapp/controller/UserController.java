package net.proselyte.springsecurityapp.controller;

import jdk.nashorn.internal.ir.RuntimeNode;
import net.proselyte.springsecurityapp.dao.HistoryProfessionDao;
import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.model.HistoryProfession;
import net.proselyte.springsecurityapp.model.Profession;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.SecurityService;
import net.proselyte.springsecurityapp.service.UserService;
import net.proselyte.springsecurityapp.validator.UserValidator;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Controller for {@link net.proselyte.springsecurityapp.model.User}'s pages.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ProfessionDao professionDao;

    @Autowired
    private HistoryProfessionDao historyProfessionDao;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        List<Profession> professionList = professionDao.findAll();
        model.addAttribute("professions",professionList);
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if(userForm.getProfessionname() == null){
            userForm.setProfessionname(professionDao.findAll().get(0).getName());
        }
        if (bindingResult.hasErrors()) {
            List<Profession> professionList = professionDao.findAll();
            model.addAttribute("professions",professionList);
            return "registration";
        }
        Profession profession = professionDao.findByName(userForm.getProfessionname());
        profession.getUsers().add(userForm);
        userService.save(userForm);
        professionDao.save(profession);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Логин или пароль введены не верно.");
        }

        if (logout != null) {
            model.addAttribute("message", "Успешный выход.");
        }

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
        return "welcome";
    }

    @RequestMapping(value = {"/statistics"}, method = RequestMethod.GET)
    public String statistics(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
        List<Profession> professions = professionDao.findAll();
        model.addAttribute("professions",professions);
        return "statistics";
    }



    @RequestMapping(value = {"/saveWithoutName"}, method = RequestMethod.GET)
    public String saveWithoutName(Model model, ModelMap modelMap,
                                  @RequestParam(value = "fee") int fee,
                                  @RequestParam(value = "action") String action) {
        {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(name);
            model.addAttribute("user",user);
            if(action.equals("save")){
                Profession profession = new Profession();
                profession.setFee(fee);
                model.addAttribute("profession",profession);
                return "addProfession";
            }
            else {
                List<Profession> professions = professionDao.findAll();
                model.addAttribute("professions",professions);
                return "redirect:/change";
            }
        }
    }

    @RequestMapping(value = {"/saveWithoutFee"}, method = RequestMethod.GET)
    public String saveWithoutName(Model model, ModelMap modelMap,
                                  @RequestParam(value = "name", required = false, defaultValue = "10") String pName,
                                  @RequestParam(value = "action", required = false, defaultValue = "10") String action) {
        {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.findByUsername(name);
            model.addAttribute("user",user);
            if(action.equals("save")){
                Profession profession = new Profession();
                profession.setName(pName);
                model.addAttribute("profession",profession);
                return "addProfession";
            }
            else {
                List<Profession> professions = professionDao.findAll();
                model.addAttribute("professions",professions);
                return "redirect:/change";
            }
        }
    }

    @RequestMapping(value = {"/change"}, method = RequestMethod.GET)
    public String change(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
        List<Profession> professions = professionDao.findAll();
        model.addAttribute("professions",professions);
        return "changeProfession";
    }



    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
