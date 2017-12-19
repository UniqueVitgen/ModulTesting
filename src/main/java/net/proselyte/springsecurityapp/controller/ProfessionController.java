package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.HistoryProfessionDao;
import net.proselyte.springsecurityapp.dao.ProfessionDao;
import net.proselyte.springsecurityapp.model.HistoryProfession;
import net.proselyte.springsecurityapp.model.Profession;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.UserService;
import net.proselyte.springsecurityapp.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class ProfessionController {

    @Autowired
    private ProfessionDao professionDao;

    @Autowired
    private UserService userService;

    @Autowired
    private HistoryProfessionDao historyProfessionDao;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = {"/delete-{id}"}, method = RequestMethod.GET)
    public String delete(@PathVariable("id") long id, Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
        professionDao.delete(id);
        List<Profession> professions = professionDao.findAll();
        model.addAttribute("professions",professions);
        return "changeProfession";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = {"/change-{id}"}, method = RequestMethod.GET)
    public String changeProfession(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String url =  request.getRequestURL().toString() + "?" + request.getQueryString();
        int index = Integer.parseInt(url.substring(url.indexOf("-")+ 1, url.indexOf("?")));
        model.addAttribute("index",index);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
        Profession profession = professionDao.findOne(id);
        model.addAttribute("profession",profession);
        List<Profession> professions = professionDao.findAll();
        model.addAttribute("professions",professions);
        return "editProfession";
    }

    @RequestMapping(value = {"/history-{id}"}, method = RequestMethod.GET)
    public String historyProfession(@PathVariable("id") Long id, Model model, HttpServletRequest request) {
        String url =  request.getRequestURL().toString() + "?" + request.getQueryString();
        int index = Integer.parseInt(url.substring(url.indexOf("-")+ 1, url.indexOf("?")));
        model.addAttribute("index",index);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
//        HistoryProfession profession = historyProfessionDao.findHistoryProfessionByPid(1L);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        model.addAttribute("format",dateFormat);
        List<HistoryProfession> professions = historyProfessionDao.findAllByPid(id);
        model.addAttribute("professions",professions);
        return "historyProfession";
    }

    @RequestMapping(value = {"/edit-{id}"})
    public String edit(Model model,@PathVariable long id,
                       @RequestParam("fee") int fee,
                       @RequestParam("name") String nameProf,
                       @RequestParam("action") String action,
                       HttpServletRequest request) {
        String url =  request.getRequestURL().toString() + "?" + request.getQueryString();
        url.substring(url.indexOf("-"));
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
        if(action.equals("save")) {
            Profession profession = professionDao.findOne(id);
            userValidator.validateProfession(model,nameProf,fee,profession);
            if(fee == -1) {
                profession.setFee(fee);
                model.addAttribute("emptyFee","Укажите Заработную плату");
            }
            else {
                profession.setFee(fee);
            }
            model.addAttribute("profession",profession);
            if(model.containsAttribute("emptyName") ||
                    model.containsAttribute("emptyFee") ||
                    model.containsAttribute("badSizeName") ||
                    model.containsAttribute("badSymbolsName")
                    )
            {
                return "addProfession";
            }
            profession.setFee(fee);
            profession.setName(nameProf);
            professionDao.save(profession);
        }
        List<Profession> professions = professionDao.findAll();
        model.addAttribute("professions",professions);
        return "redirect:/welcome";
    }


    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String add(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
        return "addProfession";
    }

    @RequestMapping(value = {"/save"}, method = RequestMethod.GET)
    public String save(Model model, ModelMap modelMap, @RequestParam(value = "name", required = false, defaultValue = "empty") String pName,
                       @RequestParam(value = "fee", required = false, defaultValue = "-1") int fee,
                       @RequestParam(value = "action") String action) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(name);
        model.addAttribute("user",user);
        if(action.equals("save")){
            Profession profession = new Profession();

            userValidator.validateProfession(model,pName,fee,profession);
            model.addAttribute("profession",profession);
            if(model.containsAttribute("emptyName") ||
                    model.containsAttribute("emptyFee") ||
                    model.containsAttribute("badSizeName") ||
                    model.containsAttribute("badSymbolsName")
                    )
            {
                return "addProfession";
            }
            profession.setName(pName);
            profession.setFee(fee);
            professionDao.save(profession);
        }
        List<Profession> professions = professionDao.findAll();
        model.addAttribute("professions",professions);
        return "redirect:/change";
    }
}
