package nep.nitin.restapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nep.nitin.restapi.dto.ExpenseDTO;
import nep.nitin.restapi.io.ExpenseRequest;
import nep.nitin.restapi.io.ExpenseResponse;
import nep.nitin.restapi.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
/**
 * This is a controller class for expense module
 * @author Nitin Paudel
 */
@RestController
@RequiredArgsConstructor
@Slf4j // for accessing logs in the controller
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
     *It will fetch a single expenses from the databases
     * @param expenseId
     * @return ExpenseResponse
     */
        @GetMapping("/expenses/{expenseId}")
        public ExpenseResponse getExpenseById(@PathVariable String expenseId){
            log.info("API GET /expenses/{} called", expenseId);
            ExpenseDTO expenseDTO = expenseService.getExpenseByExpenseId(expenseId);
            log.info("Printing the expense details{}", expenseDTO);
            return mapToExpenseResponse(expenseDTO);
        }
    /**
     *It will delete a single expenses from the databases
     * @param expenseId
     * @return void
     */
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping("/expenses/{expenseId}")
        public void deleteExpenseByExpenseId(@PathVariable String expenseId){
            log.info("API DELETE /expenses/{} called", expenseId);
            expenseService.deleteExpenseByExpenseId(expenseId);
        }
    /**
     *It will save the expense details to the database
     * @param expenseRequest
     * @return expenseResponse
     */
        @ResponseStatus(HttpStatus.CREATED)
        @PostMapping("/expenses")
        public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
            log.info("API POST /expenses called {}", expenseRequest);
            ExpenseDTO expenseDTO =mapToExpenseDTO(expenseRequest);
            expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
            log.info("Printing the expenseDTO {}", expenseDTO);
            return mapToExpenseResponse(expenseDTO);
        }
        @PutMapping("/expenses/{expenseId}")
        public ExpenseResponse updateExpenseDetails(@Valid @RequestBody ExpenseRequest updateRequest, @PathVariable String expenseId) {
            log.info("API PUT/ expenses/{} request body {}", expenseId, updateRequest);
            ExpenseDTO updatedExpenseDTO = mapToExpenseDTO(updateRequest);
            updatedExpenseDTO = expenseService.updateExpenseDetails(updatedExpenseDTO, expenseId);
            log.info("Printing the updated expense dto details {}", updatedExpenseDTO);
             return mapToExpenseResponse(updatedExpenseDTO);

        }
    /**
     *Mapper method to map values from the ExpenseRequest to ExpenseDTO
     * @param expenseRequest
     * @return ExpenseDTO
     */
    private ExpenseDTO mapToExpenseDTO(ExpenseRequest expenseRequest) {
            return modelMapper.map(expenseRequest, ExpenseDTO.class);

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

