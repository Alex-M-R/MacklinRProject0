package dev.macklinr.daos;

import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;

import java.util.Collection;

// NYI
public class ExpenseDaoDB implements ExpenseDAO
{

    @Override
    public Expense createExpense(Expense expense)
    {
        return null;
    }

    @Override
    public Expense getExpenseById(int id)
    {
        return null;
    }

    @Override
    public Collection<Expense> getAllExpenses()
    {
        return null;
    }

    @Override
    public Collection<Expense> getAllExpensesWithStatus(ExpenseStatus expenseStatus)
    {
        return null;
    }

    @Override
    public Expense updateExpense(Expense expense)
    {
        return null;
    }

    @Override
    public boolean deleteExpenseById(int id)
    {
        return false;
    }

    @Override
    public Collection<Expense> getAllExpensesByEmployeeId(int employeeId)
    {
        return null;
    }
}
