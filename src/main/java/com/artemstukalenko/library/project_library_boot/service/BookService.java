package com.artemstukalenko.library.project_library_boot.service;

import com.artemstukalenko.library.project_library_boot.entity.Book;

import java.util.List;

public interface BookService {

    public List<Book> getAllBooks();

    public boolean deleteBook(int bookId);

    public boolean addNewBook(Book bookToAdd);

    public Book findBookById(int bookId);

    public void setTaken(int id, boolean taken);
}
