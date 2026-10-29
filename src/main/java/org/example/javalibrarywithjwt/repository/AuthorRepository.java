package org.example.javalibrarywithjwt.repository;

import org.example.javalibrarywithjwt.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Long> {
}
