package com.example.loanpayback.repositories;

import com.example.loanpayback.domain.LoanRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRateRepository extends CrudRepository<LoanRate, Integer> {

    @Query(value = "select interest_rate from loan_rate where loan_type = ?1 order by version desc limit 1", nativeQuery = true)
    Double findByLoanType(String type);
}
