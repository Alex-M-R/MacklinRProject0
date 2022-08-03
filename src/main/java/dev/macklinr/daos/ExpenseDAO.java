package dev.macklinr.daos;

import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;

import java.util.Collection;

public interface ExpenseDAO
{
    Expense createExpense(Expense expense);

    Expense getExpenseById(int id);

    Collection<Expense> getAllExpenses();

    // Get all expenses with specific status
    Collection<Expense> getAllExpensesWithStatus(ExpenseStatus expenseStatus);

    Expense updateExpense(Expense expense);

    boolean deleteExpenseById(int id);

    Collection<Expense> getAllExpensesByEmployeeId(int employeeId);

}
