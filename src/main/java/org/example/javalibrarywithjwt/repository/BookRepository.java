package org.example.javalibrarywithjwt.repository;

import org.example.javalibrarywithjwt.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
