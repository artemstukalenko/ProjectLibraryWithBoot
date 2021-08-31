package com.artemstukalenko.library.project_library_boot.controller;

import com.artemstukalenko.library.project_library_boot.entity.Subscription;
import com.artemstukalenko.library.project_library_boot.service.CustomSubscriptionRequestService;
import com.artemstukalenko.library.project_library_boot.service.SubscriptionService;
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
public class LibrarianController {

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    FirstView controlledView;

    @Autowired
    CustomSubscriptionRequestService customSubscriptionRequestService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    @ModelAttribute
    public void addTextInformation(Model model) {
        model.addAttribute("locale", controlledView);
    }

    @RequestMapping("/asLibrarian")
    public String getSubscriptionsListPage(Model model) {

        List<Subscription> allSubscriptions = subscriptionService.getAllSubscriptions();
        model.addAttribute("allSubscriptions", allSubscriptions);
        model.addAttribute("allRequests", customSubscriptionRequestService.getAllRequests());
        return "subscriptions-page";
    }

    @RequestMapping("/acceptRequest")
    public String acceptRequest(@RequestParam("requestId") int requestId, Model model) {
        subscriptionService.registerSubscriptionInDB(
                new Subscription(customSubscriptionRequestService.findRequestById(requestId))
        );

        customSubscriptionRequestService.deleteCustomSubscriptionRequestFromDB(requestId);

        model.addAttribute("allSubscriptions", subscriptionService.getAllSubscriptions());
        model.addAttribute("allRequests", customSubscriptionRequestService.getAllRequests());
        LOGGER.info("Request accepted: " + customSubscriptionRequestService.findRequestById(requestId));

        return "subscriptions-page";
    }

    @RequestMapping("/denyRequest")
    public String denyRequest(@RequestParam("requestId") int requestId, Model model) {
        customSubscriptionRequestService.deleteCustomSubscriptionRequestFromDB(requestId);

        model.addAttribute("allSubscriptions", subscriptionService.getAllSubscriptions());
        model.addAttribute("allRequests", customSubscriptionRequestService.getAllRequests());
        LOGGER.info("Request denied: " + customSubscriptionRequestService.findRequestById(requestId));

        return "subscriptions-page";
    }
}
