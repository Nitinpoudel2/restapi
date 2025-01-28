package nep.nitin.restapi.repository;

import nep.nitin.restapi.entity.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 *JPA repository for Expense Resource
 *
 */

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    /**
     *It will find the single expense from databases
     * @param expenseId
     * @return Optional
     */
    Optional<ExpenseEntity> findByExpenseId(String expenseId);

}
