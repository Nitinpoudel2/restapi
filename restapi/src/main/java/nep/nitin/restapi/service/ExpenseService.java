package nep.nitin.restapi.service;

import nep.nitin.restapi.dto.ExpenseDTO;

import java.util.List;

/**
 *  Service interface for expense moduke
 * @author Nitin Paudel
 */
public interface ExpenseService {
    /**
     * It will fetch the expenses from the databases
     * @return the list
     */
    List<ExpenseDTO> getAllExpenses();

}
