package nep.nitin.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nep.nitin.restapi.dto.ExpenseDTO;
import nep.nitin.restapi.entity.ExpenseEntity;
import nep.nitin.restapi.entity.ProfileEntity;
import nep.nitin.restapi.exceptions.ResourceNotFoundException;
import nep.nitin.restapi.repository.ExpenseRepository;
import nep.nitin.restapi.service.AuthService;
import nep.nitin.restapi.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
/**
 *Service implementation for Expense Module
 * @author Nitin-Paudel
 */

@Service
@RequiredArgsConstructor
@Slf4j

public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    /**
     * It will fetch the expenses from the databases
     * @return the list
     */
    @Override
    public List<ExpenseDTO> getAllExpenses() {
        //call the repository method and convert the Entity object to DTO object
        // return the list of objects
        Long loggedInProfileId = authService.getLoggedInProfile().getId();
        List<ExpenseEntity> list= expenseRepository.findByOwnerId(loggedInProfileId);
        log.info("printing the data from repository {}",list);
        List<ExpenseDTO> listOfExpenses = list.stream().map(expenseEntity -> mapToExpenseDTO(expenseEntity)).collect(Collectors.toList());
        //Return the list
        return listOfExpenses;
    }
    /**
     *It will fetch the single expense details from the databases
     * @param expenseId
     * @return ExpenseDTO
     */
    @Override
    public ExpenseDTO getExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        log.info("Printing the expense entity details {}",expenseEntity);
        return mapToExpenseDTO(expenseEntity);
    }


    /**
     *It will delete the expense from database
     *  @param expenseId
     * @return void
     */
    @Override
    public void deleteExpenseByExpenseId(String expenseId) {
        ExpenseEntity expenseEntity = getExpenseEntity(expenseId);
        expenseRepository.delete(expenseEntity);
    }
    /**
     *It will save the expense details to the database
     * @param expenseDTO
     * @return ExpenseDTO
     */
    @Override
    public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
        ProfileEntity profileEntity = authService.getLoggedInProfile();
        ExpenseEntity newExpenseEntity = mapToExpenseEntity(expenseDTO);
        newExpenseEntity.setExpenseId(UUID.randomUUID().toString());
        newExpenseEntity.setOwner(profileEntity);
        newExpenseEntity = expenseRepository.save(newExpenseEntity);
        log.info("Printing the expense entity details {} ", newExpenseEntity);
        return mapToExpenseDTO(newExpenseEntity);
    }

    @Override
    public ExpenseDTO updateExpenseDetails(ExpenseDTO expenseDTO, String expenseId) {
        ExpenseEntity existingExpense = getExpenseEntity(expenseId);
        ExpenseEntity updatedExpenseEntity = mapToExpenseEntity(expenseDTO);
        updatedExpenseEntity.setId(existingExpense.getId());
        updatedExpenseEntity.setExpenseId(existingExpense.getExpenseId());
        updatedExpenseEntity.setCreatedAt(existingExpense.getCreatedAt());
        updatedExpenseEntity.setUpdatedAt(existingExpense.getUpdatedAt());
        updatedExpenseEntity.setOwner(authService.getLoggedInProfile());
        updatedExpenseEntity =  expenseRepository.save(updatedExpenseEntity);
        log.info("Printing the updated expense entity details {}", updatedExpenseEntity);
        return mapToExpenseDTO(updatedExpenseEntity);
    }

    /**
     *Mapper method to map values from Expense dto to Expense entity
     * @param expenseDTO
     * @return ExpenseEntity
     */
    private ExpenseEntity mapToExpenseEntity(ExpenseDTO expenseDTO) {
        return modelMapper.map(expenseDTO, ExpenseEntity.class);
    }

    /**
     *Mapper method to convert expense entity to expense DTO
     * @param expenseEntity
     * @return the ExpenseDTO
     */
    private ExpenseDTO mapToExpenseDTO(ExpenseEntity expenseEntity) {
        return modelMapper.map(expenseEntity, ExpenseDTO.class);
    }
    /**
     *Fetch the expense by expense id from database
     * @param expenseId
     * @return the ExpenseEntity
     */
    private ExpenseEntity getExpenseEntity(String expenseId) {
        Long id = authService.getLoggedInProfile().getId();
        return expenseRepository.findByOwnerIdAndExpenseId(id, expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found for the expense id" + expenseId));
    }
}
