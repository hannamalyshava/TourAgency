package org.example.controller;

import org.example.model.Book;
import org.example.service.BookService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    
    private final BookService service;
    
    public BookController(BookService service) {
        this.service = service;
    }
    
    @GetMapping
    public List<Book> getAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public Book getOne(@PathVariable Long id) {
        return service.findById(id);
    }
    
    @PostMapping
    public Book create(@RequestBody Book book) {
        return service.save(book);
    }
    
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @RequestBody Book book) {
        return service.update(id, book);
    }
    
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}