package org.example.javalibrarywithjwt.model;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class BorrowRequest {

    @NotNull(message = "Book ID cannot be empty!")
    private Long bookId;

    @NotNull(message = "Due date is required")
    //Hindrar användaren från att sätta ett datum innan nutiden
    @FutureOrPresent(message = "Due date must be now or in the future")
    private LocalDate dueDate;

    public BorrowRequest() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
