package org.example.javalibrarywithjwt.service;

import jakarta.transaction.Transactional;
import org.example.javalibrarywithjwt.model.Book;
import org.example.javalibrarywithjwt.model.BorrowRequest;
import org.example.javalibrarywithjwt.model.Loan;
import org.example.javalibrarywithjwt.model.User;
import org.example.javalibrarywithjwt.repository.BookRepository;
import org.example.javalibrarywithjwt.repository.LoanRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class LoanService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Loan findById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Loan not found!")
                );
    }

    //Användare skall kunna låna en bok
    public Loan borrowBook(User user, BorrowRequest borrowrequest){

        Book book = bookRepository.findById(borrowrequest.getBookId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Book not found!"));
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(borrowrequest.getDueDate());
        loan.setReturnDate(null);

        return loanRepository.save(loan);

    }



}
