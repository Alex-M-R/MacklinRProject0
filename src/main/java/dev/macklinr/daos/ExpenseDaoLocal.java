package dev.macklinr.daos;

import dev.macklinr.app.App;
import dev.macklinr.entities.Employee;
import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;

import java.util.*;

public class ExpenseDaoLocal implements ExpenseDAO
{
    private Map<Integer, Expense> expenseTable = new HashMap();

    private int idMaker = 1;

    @Override
    public Expense createExpense(Expense expense)
    {
        expense.setId(idMaker++);

        expenseTable.put(expense.getId(), expense);
        return expense;
    }

    @Override
    public Expense getExpenseById(int id)
    {
        return expenseTable.get(id);
    }

    @Override
    public Collection<Expense> getAllExpenses()
    {
        return expenseTable.values();
    }

    @Override
    public Expense updateExpense(Expense expense)
    {
        this.expenseTable.put(expense.getId(), expense);
        return expense;
    }

    @Override
    public boolean deleteExpenseById(int id)
    {
        Expense expense = expenseTable.remove(id);

        return expense != null;
    }
}
