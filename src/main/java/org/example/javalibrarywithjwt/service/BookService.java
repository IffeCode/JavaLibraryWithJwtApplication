package org.example.javalibrarywithjwt.service;

import jakarta.transaction.Transactional;
import org.example.javalibrarywithjwt.model.Author;
import org.example.javalibrarywithjwt.model.Book;
import org.example.javalibrarywithjwt.repository.AuthorRepository;
import org.example.javalibrarywithjwt.repository.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    //Listar alla böcker
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    //Hämtar böcker baserat på id
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Book with id " + id + " not found."
                        ));
    }

    //Skapar en ny bok
    public Book create(Book book) {
        book.setId(null);

        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(()->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Author not found!"));

        book.setAuthor(author);

        return bookRepository.save(book);
    }

    /*
    Hämtar bok baserat på id för att uppdatera,
    om boken inte finns får användaren upp NOT FOUND
    if-satser för att kunna uppdatera enskilda rader utan att behöva uppdatera samtliga samtidigt
    och att få null på värden som inte ändras i uppdateringen
     */
    public Book update(Long id, Book book) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Book with id " + id + " not found."));

        if (book.getTitle() != null) {
            existingBook.setTitle(book.getTitle());
        }
        if (book.getDescription() != null) {
            existingBook.setDescription(book.getDescription());
        }
        if (book.getAuthor() != null) {
            Author author = authorRepository.findById(book.getAuthor().getId())
                    .orElseThrow(()-> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Author not found!"
            ));
            existingBook.setAuthor(author);
        }

        return bookRepository.save(existingBook);
    }

    //Hämtar book baserat på id för att radera
    public void deleteById(Long id) {
        bookRepository.findById(id)
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Book with id " + id + " not found."
                        ));
        bookRepository.deleteById(id);
    }



}
