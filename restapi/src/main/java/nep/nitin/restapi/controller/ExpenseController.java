package nep.nitin.restapi.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nep.nitin.restapi.dto.ExpenseDTO;
import nep.nitin.restapi.io.ExpenseResponse;
import nep.nitin.restapi.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
/**
 * This is a controller class for expense module
 * @author Nitin Paudel
 */
@RestController
@RequiredArgsConstructor
@Slf4j // for accessing logs in the controller
@CrossOrigin("*")


public class ExpenseController {
    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    /**
     *It will fetch the expenses from the databases
     * @return list
     */
    @GetMapping("/expenses")
    public List<ExpenseResponse> getExpenses(){
        log.info("API GET /expenses called");
        //call the service method and convert the expense DTO to Expense Response and return the list/response
        List<ExpenseDTO> list = expenseService.getAllExpenses();
        log.info("printing the data from the service{}", list);
        List<ExpenseResponse> response = list.stream().map(expenseDTO-> mapToExpenseResponse(expenseDTO)).collect(Collectors.toList());
        return response;
        }
    /**
     * Mapper method for converting expense dto object to expense response
     * @param
     * @return ExpenseResponse
     **/
    private ExpenseResponse mapToExpenseResponse(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseResponse.class);
        }
    }

