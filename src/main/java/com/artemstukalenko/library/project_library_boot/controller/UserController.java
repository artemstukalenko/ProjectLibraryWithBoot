package com.artemstukalenko.library.project_library_boot.controller;

import com.artemstukalenko.library.project_library_boot.entity.Subscription;
import com.artemstukalenko.library.project_library_boot.entity.User;
import com.artemstukalenko.library.project_library_boot.service.SubscriptionService;
import com.artemstukalenko.library.project_library_boot.service.UserService;
import com.artemstukalenko.library.project_library_boot.view.FirstView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    FirstView controlledView;

    @Autowired
    UserService userService;

    @Autowired
    MainController mainController;

    User currentUser;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @ModelAttribute
    public void addEssentialAttributes(Model model) {
        model.addAttribute("locale", controlledView);
        currentUser = mainController.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
    }

    @RequestMapping("/viewSubscriptions")
    public String getUserSubscriptionsPage(Model model) {
        currentUser = mainController.getCurrentUser();
        model.addAttribute("userSubscriptionList", userService.
                findUserByUsername(currentUser.getUsername()).getSubscriptionList());

        return "my-subscriptions";
    }

    @RequestMapping("/payPenalty")
    public String payPenalty(Model model) {
        currentUser = mainController.getCurrentUser();
        model.addAttribute("userPenaltySum", currentUser.getUserDetails().getUserPenalty());

        return "pay-penalty-form";
    }

    @RequestMapping("/confirmPayment")
    public String confirmPayment(@RequestParam("userSum") int userSum, Model model) {
        userService.updatePenaltyInfo(currentUser.getUsername(), currentUser.getUserDetails().getUserPenalty() - userSum);
        currentUser = userService.findUserByUsername(currentUser.getUsername());
        model.addAttribute("currentUser", currentUser);
        LOGGER.info("User " + currentUser + " has paid his penalty");

        return "homepage";
    }

}
