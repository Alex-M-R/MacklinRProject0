package dev.macklinr.daos;

import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;

import java.util.Collection;

public interface ExpenseDAO
{
    Expense createExpense(Expense expense);

    Expense getExpenseById(int id);

    Collection<Expense> getAllExpenses();

    Expense updateExpense(Expense expense);

    boolean deleteExpenseById(int id);
}
