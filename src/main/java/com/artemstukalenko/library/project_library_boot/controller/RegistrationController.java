package com.artemstukalenko.library.project_library_boot.controller;

import com.artemstukalenko.library.project_library_boot.entity.User;
import com.artemstukalenko.library.project_library_boot.entity.UserDetails;
import com.artemstukalenko.library.project_library_boot.service.UserService;
import com.artemstukalenko.library.project_library_boot.view.FirstView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    UserService userService;

    @Autowired
    FirstView controlledView;

    @Autowired
    User potentialUser;

    private boolean detailsAreNotValid;

    @ModelAttribute
    public void addTextInformation(Model model) {
        model.addAttribute("locale", controlledView);
    }

    @RequestMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("potentialUser", potentialUser);

        return "register-page";
    }

    @RequestMapping("/registerDetails")
    public String registerDetails(@Valid @ModelAttribute("newUserDetails") UserDetails newUserDetails,
                                  BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            detailsAreNotValid = true;
            return "register-details-page";
        }
        detailsAreNotValid = false;
        newUserDetails.setUsername(potentialUser.getUsername());
        potentialUser.setUserDetails(newUserDetails);
        userService.updateUser(potentialUser);

        if(detailsAreNotValid) {
            userService.deleteUser(potentialUser.getUsername());
        }

        LOGGER.info("Registered user: " + potentialUser + "; " + newUserDetails);

        return "redirect:/login";
    }

    @RequestMapping("/deletePotentialUser")
    public String deletePotentialUser() {
        userService.deleteUser(potentialUser.getUsername());

        return "redirect:/login";
    }

    @RequestMapping("/registerNewUser")
    public String registerNewUser(@ModelAttribute("potentialUser") User potentialUser1,
                                  Model model) {

        if(!userService.registerUser(potentialUser1)) {
            model.addAttribute("loginNotValid", true);
            return "register-page";
        }

        potentialUser1.setUserDetails(new UserDetails(potentialUser1));

        potentialUser = potentialUser1;

        model.addAttribute("newUserDetails", potentialUser.getUserDetails());

        return "register-details-page";
    }

}
