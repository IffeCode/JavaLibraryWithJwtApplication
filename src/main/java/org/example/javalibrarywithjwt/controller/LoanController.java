package org.example.javalibrarywithjwt.controller;

import jakarta.validation.Valid;
import org.example.javalibrarywithjwt.model.BorrowRequest;
import org.example.javalibrarywithjwt.model.Loan;
import org.example.javalibrarywithjwt.repository.LoanRepository;
import org.example.javalibrarywithjwt.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    //Hämtar samtliga lån
    @GetMapping("/")
    public List<Loan> getLoans() {
        return loanService.findAll();
    }

    //Hämtar specifik lån baserat på id
    @GetMapping("/{id}")
    public Loan findLoanById(@PathVariable Long id) {
        return loanService.findById(id);
    }

    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan borrowLoan(@Valid @RequestBody BorrowRequest borrowRequest) {
        //Kopplar in autentiserade användare med JWT
        throw new UnsupportedOperationException(
                "JWT authentication must be implpemented before borrowing!"
        );
    }

}
