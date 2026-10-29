package org.example.javalibrarywithjwt.controller;

import jakarta.validation.Valid;
import org.example.javalibrarywithjwt.model.Author;
import org.example.javalibrarywithjwt.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //Listar alla författare
    @GetMapping("/")
    public List<Author> findAll() {
        return authorService.findAll();
    }

    @GetMapping("/{id}")
    public Author findById(@PathVariable Long id) {
        return authorService.findById(id);
    }

    /*
    ResponseEntity istället för public Author create -
    får 201 created istället för 200 ok,
    tydligare med 201!
     */
    @PostMapping
    public ResponseEntity<Author> create(
            @Valid @RequestBody Author author) {
        Author createdAuthor = authorService.create(author);

        return ResponseEntity.status(
                HttpStatus.CREATED)
                .body(createdAuthor);
    }

    //uppdaterar författare
    @PutMapping("/{id}")
    public Author update(@PathVariable Long id,
                         @Valid @RequestBody Author updatedAuthor) {
        return authorService.updateAuthor(id, updatedAuthor);
    }

    /*
    Ger 204 no content istället för 200 ok,
    tydligare context för användaren om vad som skett!
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        authorService.deleteAuthor(id);

        return ResponseEntity.noContent().build();
    }

}
