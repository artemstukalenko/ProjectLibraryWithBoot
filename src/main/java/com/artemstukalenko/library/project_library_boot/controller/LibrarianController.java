package com.artemstukalenko.library.project_library_boot.controller;

import com.artemstukalenko.library.project_library_boot.entity.Subscription;
import com.artemstukalenko.library.project_library_boot.service.CustomSubscriptionRequestService;
import com.artemstukalenko.library.project_library_boot.service.SubscriptionService;
import com.artemstukalenko.library.project_library_boot.view.FirstView;
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

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("locale", controlledView);
    }

    @RequestMapping("/asLibrarian")
    public String getLibrarianEntryPage(Model model) {

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

        return "subscriptions-page";
    }

    @RequestMapping("/denyRequest")
    public String denyRequest(@RequestParam("requestId") int requestId, Model model) {
        customSubscriptionRequestService.deleteCustomSubscriptionRequestFromDB(requestId);

        model.addAttribute("allSubscriptions", subscriptionService.getAllSubscriptions());
        model.addAttribute("allRequests", customSubscriptionRequestService.getAllRequests());

        return "subscriptions-page";
    }
}
