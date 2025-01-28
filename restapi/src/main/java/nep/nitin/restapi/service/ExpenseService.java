package nep.nitin.restapi.service;

import nep.nitin.restapi.dto.ExpenseDTO;

import java.util.List;

/**
 *  Service interface for expense moduke
 * @author Nitin Paudel
 */
public interface ExpenseService {
    /**
     * It will fetch the single expenses details from the database
     * @return list
     *
     */
    List<ExpenseDTO> getAllExpenses();
    /**
     *It will fetch the single expenses details from the databases
     * @param expenseId
     * @return ExpenseDTO
     */

    ExpenseDTO getExpenseByExpenseId(String ExpenseId);
}
