package org.example.javalibrarywithjwt.repository;

import org.example.javalibrarywithjwt.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
