package net.proselyte.springsecurityapp.validator;

import net.proselyte.springsecurityapp.model.Profession;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for {@link net.proselyte.springsecurityapp.model.User} class,
 * implements {@link Validator} interface.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");
        if (user.getUsername().length() < 8 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username");
        }
        else {
            if (userService.findByUsername(user.getUsername()) != null) {
                errors.rejectValue("username", "Duplicate.userForm.username");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"firstname","Required");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"lastname","Required");

        if (user.getFirstname().length() < 2 || user.getFirstname().length() > 32) {
            errors.rejectValue("firstname", "Size.userForm.firstname");
        }

        if (user.getLastname().length() < 2 || user.getLastname().length() > 32) {
            errors.rejectValue("lastname", "Size.userForm.lastname");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        else {
            if (!user.getConfirmPassword().equals(user.getPassword())) {
                errors.rejectValue("confirmPassword", "Different.userForm.password");
            }
        }
    }

    public void validateProfession(Model model, String name, int fee, Profession profession) {
        if(name.equals("empty") || name.equals("")) {
            model.addAttribute("emptyName","Укажите Название профессии");
        }
        else {
            profession.setName(name);
            if (name.length() < 2 || name.length() > 32) {
                model.addAttribute("badSizeName","Название должно содержать от 2 до 32 символов");
            }
            if(!name.matches("^[a-zA-Zа-яА-Я]{0,}$")){
                model.addAttribute("badSymbolsName","Название должно содержать русские и латинские символы");
            }
        }
        if(fee == -1) {
            profession.setFee(fee);
            model.addAttribute("emptyFee","Укажите Заработную плату");
        }
        else {
            profession.setFee(fee);
        }
    }
}
