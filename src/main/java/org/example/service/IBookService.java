package org.example.service;

import org.example.model.Book;
import java.util.List;

public interface IBookService {
    List<Book> findAll();
    Book findById(Long id);
    Book save(Book book);
    void delete(Long id);
    Book update(Long id, Book updatedBook);
}