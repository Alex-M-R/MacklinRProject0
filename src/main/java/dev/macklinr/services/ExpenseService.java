package dev.macklinr.services;

import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;

import java.util.Collection;

public interface ExpenseService
{
    Expense registerExpense(Expense expense) throws RuntimeException;

    Expense retrieveExpenseById(int id);

    boolean deleteExpense(int id) throws RuntimeException;

    Expense modifyExpense(Expense expense) throws RuntimeException;

    Expense updateExpenseStatus(int expenseID, ExpenseStatus newExpenseStatus) throws RuntimeException;

    Collection<Expense> getAllExpenseByEmployeeId(int employeeID);

    Collection<Expense> getAllExpenses();

    Collection<Expense> getAllExpensesByStatus(ExpenseStatus status);
}
