package com.artemstukalenko.library.project_library_boot.controller;

import com.artemstukalenko.library.project_library_boot.entity.Book;
import com.artemstukalenko.library.project_library_boot.entity.User;
import com.artemstukalenko.library.project_library_boot.service.BookService;
import com.artemstukalenko.library.project_library_boot.service.UserService;
import com.artemstukalenko.library.project_library_boot.utility.PenaltyCalculator;
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
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    FirstView controlledView;

    @Autowired
    PenaltyCalculator penaltyCalculator;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private List<User> getUpdatedUserList() {
        return userService.getAllUsers();
    }

    private List<User> getUserListWithUpdatedPenalty() {
        return userService.getAllUsers().stream().peek(user -> {user.getUserDetails()
                .setPenalty(penaltyCalculator.calculateUsersPenalty(user));})
                .collect(Collectors.toList());
    }

    @ModelAttribute
    public void addTextInformation(Model model) {
        model.addAttribute("locale", controlledView);
    }

    @RequestMapping("/asAdmin")
    public String getAdminEntryPage(Model model) {
        model.addAttribute("allUsers", getUserListWithUpdatedPenalty());

        return "user-list-page";
    }

    @RequestMapping("/blockUser")
    public String blockUser(@RequestParam("userName") String username, Model model) {
        userService.blockUser(username);
        model.addAttribute("allUsers", getUpdatedUserList());

        return "user-list-page";
    }

    @RequestMapping("/unblockUser")
    public String unblockUser(@RequestParam("userName") String username, Model model) {
        userService.unblockUser(username);
        model.addAttribute("allUsers", getUpdatedUserList());

        return "user-list-page";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(@RequestParam("userName") String username, Model model) {
        userService.deleteUser(username);
        model.addAttribute("allUsers", getUpdatedUserList());
        LOGGER.info("Deleted user: " + userService.findUserByUsername(username));

        return "user-list-page";
    }



    @RequestMapping("/enterInfoForNewBook")
    public String enterInfoForNewBook(Model model) {
        model.addAttribute("newBook", new Book());

        return "enter-info-for-new-book";
    }



    @RequestMapping("/makeUserLibrarian")
    public String makeUserLibrarian(@RequestParam("userName") String username,
                                    Model model) {

        User currentUser = userService.findUserByUsername(username);

        userService.makeUserLibrarian(currentUser.getUsername());
        currentUser.setAuthorityString("ROLE_LIBRARIAN");

        model.addAttribute("allUsers", getUpdatedUserList());
        return "user-list-page";
    }

    @RequestMapping("/depriveLibrarianRole")
    public String depriveLibrarianPrivilegesFromUser(@RequestParam("userName") String username,
                                                     Model model) {

        User currentUser = userService.findUserByUsername(username);

        userService.depriveLibrarianPrivileges(username);
        currentUser.setAuthorityString("ROLE_USER");

        model.addAttribute("allUsers", getUpdatedUserList());
        return "user-list-page";
    }
}
