package org.example.javalibrarywithjwt.controller;

import jakarta.validation.Valid;
import org.example.javalibrarywithjwt.model.Book;
import org.example.javalibrarywithjwt.repository.BookRepository;
import org.example.javalibrarywithjwt.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //Hämtar alla böcker
    @GetMapping("/")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    //Hämtar en specifik bok baserat på id
    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    //Skapar en bok
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody Book book) {
        return bookService.create(book);
    }

    //Uppdaterar en bok
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id,
                       @Valid @RequestBody Book book) {
        return bookService.update(id, book);
    }
    //Raderar en bok
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.deleteById(id);
    }

}
