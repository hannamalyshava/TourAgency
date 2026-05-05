package org.example.service;

import org.example.model.Book;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService implements IBookService {
    
    private final BookRepository repository;
    
    public BookService(BookRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }
    
    @Override
    public Book findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }
    
    @Override
    public Book save(Book book) {
        return repository.save(book);
    }
    
    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    @Override
    public Book update(Long id, Book updatedBook) {
        Book existingBook = findById(id);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setIsbn(updatedBook.getIsbn());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());
        existingBook.setGenre(updatedBook.getGenre());
        existingBook.setPrice(updatedBook.getPrice());
        existingBook.setPagesCount(updatedBook.getPagesCount());
        return repository.save(existingBook);
    }
}