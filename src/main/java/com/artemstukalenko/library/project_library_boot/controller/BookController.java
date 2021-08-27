package com.artemstukalenko.library.project_library_boot.controller;

import com.artemstukalenko.library.project_library_boot.entity.Book;
import com.artemstukalenko.library.project_library_boot.service.BookService;
import com.artemstukalenko.library.project_library_boot.service.UserService;
import com.artemstukalenko.library.project_library_boot.utility.Searcher;
import com.artemstukalenko.library.project_library_boot.utility.Sorter;
import com.artemstukalenko.library.project_library_boot.view.FirstView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BookController {

    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @Autowired
    FirstView controlledView;

    @Autowired
    Sorter sorter;

    @Autowired
    Searcher searcher;

    List<Book> currentBookList;

    @ModelAttribute
    public void addEssentialAttributes(Model model) {
        model.addAttribute("locale", controlledView);
        model.addAttribute("listSorter", sorter);
        model.addAttribute("allBooks", currentBookList);
        model.addAttribute("searcher", searcher);
    }

    @RequestMapping("/booksList")
    public String getBookListPage(HttpServletRequest request, Model model) {

        int desiredPageNumber = request.getParameter("pageNumber") == null ? 1 :
                Integer.parseInt(request.getParameter("pageNumber"));

        if (sorter.getSortMethod() == null && searcher.getSearchCriteria() == null) {
            currentBookList = bookService.getAllBooks();
        }

        Map<Integer, List<Book>> bookListPages = getBookPages(currentBookList);


        model.addAttribute("allBooks", bookListPages.get(desiredPageNumber));
        model.addAttribute("pagesCount", bookListPages.keySet());

        return "book-list-page";
    }

    private Map<Integer, List<Book>> getBookPages(List<Book> allBooks) {
        Map<Integer, List<Book>> pages = new HashMap<>();
        int pageSizeLimit = 5;
        int pageCounter = 1;
        int currentBookIndex = 0;

        while (pageCounter <= allBooks.size()/pageSizeLimit + 1) {
            int countOfBooksOnThePage = 0;
            List<Book> page = new ArrayList<>();
            while (countOfBooksOnThePage < pageSizeLimit && currentBookIndex < allBooks.size()) {
                page.add(allBooks.get(currentBookIndex));
                currentBookIndex++;
                countOfBooksOnThePage++;
            }

            pages.put(pageCounter, page);
            pageCounter++;
        }

        return pages;
    }

    @RequestMapping("/sort")
    public String getSortedPage(@ModelAttribute("listSorter") Sorter sorter,
                                Model model) {

        currentBookList = sorter.sortList(currentBookList);

        return "forward:/booksList";
    }

    @RequestMapping("/searchBook")
    public String searchForBooks(@ModelAttribute("searcher") Searcher searcher,
                                 Model model) {
        currentBookList = searcher
                .getResultOfTheBookSearch(bookService.getAllBooks());

        return "forward:/booksList";
    }

    @RequestMapping("/getUnfilteredBookList")
    public String getUnfilteredBookList() {
        searcher.setSearchCriteria(null);
        sorter.setSortMethod(null);

        return "forward:/booksList";
    }

    @RequestMapping("/changeTakenValue")
    public String changeTakenValueOfBook(@RequestParam("bookId") int bookId,
                                         Model model) {

        Book currentBook = bookService.findBookById(bookId);

        currentBook.setTaken(!currentBook.getTaken());

        model.addAttribute("allBooks", bookService.getAllBooks());

        return "book-list-page";
    }

    @RequestMapping("/addNewBook")
    public String addNewBook(@ModelAttribute("newBook") Book bookToAdd, Model model) {
        bookService.addNewBook(bookToAdd);

        model.addAttribute("allBooks", bookService.getAllBooks());

        return "book-list-page";
    }

    @RequestMapping("/deleteBook")
    public String deleteBook(@RequestParam("bookId") int bookId, Model model) {
        bookService.deleteBook(bookId);

        model.addAttribute("allBooks", bookService.getAllBooks());

        return "book-list-page";
    }
}
