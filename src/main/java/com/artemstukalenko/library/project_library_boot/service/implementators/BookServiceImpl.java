package com.artemstukalenko.library.project_library_boot.service.implementators;


import com.artemstukalenko.library.project_library_boot.entity.Book;
import com.artemstukalenko.library.project_library_boot.repositories.BookRepository;
import com.artemstukalenko.library.project_library_boot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public boolean deleteBook(int bookId) {
        bookRepository.deleteById(bookId);
        return true;
    }

    @Override
    public boolean addNewBook(Book bookToAdd) {
        bookRepository.save(bookToAdd);
        return true;
    }

    @Override
    public Book findBookById(int bookId) {
        Optional<Book> foundBook = bookRepository.findById(bookId);
        return Optional.ofNullable(foundBook.get()).orElse(new Book());
    }

    @Override
    @Transactional
    public void setTaken(int id, boolean taken) {
        bookRepository.setTakenValue(id, taken);
    }
}
