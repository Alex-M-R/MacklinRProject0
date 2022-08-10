package dev.macklinr.services;

import dev.macklinr.app.App;
import dev.macklinr.daos.ExpenseDAO;
import dev.macklinr.entities.Employee;
import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// Expense/Business Rules - enforced in the business service layer (services) not in the dao(object creation/get/update/delete)
// All expenses have a single employee as the issuer
// Expenses start as pending and must then be approved or denied
// Once approved or denied they CANNOT be deleted or edited
// Cannot have a negative expense
public class ExpenseServiceImplementation implements ExpenseService
{

    private ExpenseDAO expenseDAO;

    public ExpenseServiceImplementation(ExpenseDAO expenseDao)
    {
        this.expenseDAO = expenseDao;
    }

    @Override
    public Expense registerExpense(Expense expense) throws RuntimeException
    {
        // check expense has valid ID # as employee
        int eID = expense.getIssuingEmployeeID();
        if (eID > 0)
        {
            Employee existingEmployee = App.employeeService.retrieveEmployeeById(eID);

            // check if employee exists
           if (existingEmployee != null) // check that an employee exists at that ID
           {
               // check if expense is negative
               if (expense.getExpenseAmount() > 0)
               {
                   // force status to Pending and then create and save our new expense
                   expense.setStatus(ExpenseStatus.PENDING);
                   return this.expenseDAO.createExpense(expense);
               }

               throw new RuntimeException("Invalid expense value: " + expense.getExpenseAmount());

           }
        }
       throw new RuntimeException("Issuing Employee could not be verified. Employee ID: " + eID);
    }

    @Override
    public Expense retrieveExpenseById(int id)
    {
        return this.expenseDAO.getExpenseById(id);
    }

    @Override
    public boolean deleteExpense(int id) throws RuntimeException
    {
        Expense existing = this.expenseDAO.getExpenseById(id);

        if (existing == null)
            return false;

        // check expense is still pending. otherwise can't delete
        if (existing.getStatus() == ExpenseStatus.PENDING)
        {
            return this.expenseDAO.deleteExpenseById(id);
        }
            throw new RuntimeException("Delete expense failed. Existing expense is no longer pending. Current Status: " + existing.getStatus());
    }

    @Override
    public Expense modifyExpense(Expense expense) throws RuntimeException
    {
        Expense existing = this.expenseDAO.getExpenseById(expense.getId());

       // check that existing expense isn't accepted or pending before overwriting
        if (existing.getStatus() == ExpenseStatus.PENDING)
        {
            // check that new Expense has valid employee reference
            Employee existingEmployee = App.employeeService.retrieveEmployeeById(expense.getIssuingEmployeeID());
            if (existingEmployee != null)
            {
                if (expense.getExpenseAmount() <= 0)
                {
                    throw new RuntimeException("Invalid expense value: " + expense.getExpenseAmount());
                }
                expense.setStatus(ExpenseStatus.PENDING);   // in case user failed to add a PENDING status in the JSON. Otherwise it breaks business rule implementation
                return this.expenseDAO.updateExpense(expense);
            }
            else
                throw new RuntimeException("Employee with ID: " + expense.getIssuingEmployeeID() + " could not be found. Expense must have a valid issuing employee.");
        }
        else
            throw new RuntimeException("Update expense failed. Existing expense is no longer pending. Current Status: " + existing.getStatus());
    }

    @Override
    public Expense updateExpenseStatus(int expenseID, ExpenseStatus newExpenseStatus) throws RuntimeException
    {
        Expense existing = this.expenseDAO.getExpenseById(expenseID);

        // check that existing expense isn't accepted or denied before overwriting
        if (existing.getStatus() == ExpenseStatus.PENDING)
        {
            existing.setStatus(newExpenseStatus);
            return this.expenseDAO.updateExpense(existing);
        }
        else
            throw new RuntimeException("Update expense failed. Existing expense is not pending. Current Status: " + existing.getStatus());
    }

    @Override
    public Collection<Expense> getAllExpenseByEmployeeId(int employeeID)
    {
        Collection<Expense> allExpenses = this.expenseDAO.getAllExpenses();

        Collection<Expense> employeeExpenses = allExpenses.stream().filter(expense -> expense.getIssuingEmployeeID() == employeeID).collect(Collectors.toList());

        return employeeExpenses;
    }

    @Override
    public Collection<Expense> getAllExpenses()
    {
        return this.expenseDAO.getAllExpenses();
    }

    @Override
    public Collection<Expense> getAllExpensesByStatus(ExpenseStatus status)
    {
        Collection<Expense> allExpenses = this.expenseDAO.getAllExpenses();

        Collection<Expense> expensesWithStatus = allExpenses.stream().filter(expense -> expense.getStatus() == status).collect(Collectors.toList());

        return expensesWithStatus;
    }
}
