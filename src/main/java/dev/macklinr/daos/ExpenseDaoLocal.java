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
    public Collection<Expense> getAllExpensesWithStatus(ExpenseStatus expenseStatus)
    {
        // NOTE: Will need drastic change probably when using database instead of local... but this class is local so LOL don't matter. Create new
        // ExpenseDAODB.java when needed - or something to that affect

        // create temp list
        List<Expense> results = new ArrayList();

        // loop through table values and put anything with desiredStatus into new list
        for (Expense expense : expenseTable.values())
        {
            if (expense.getStatus() == expenseStatus)
            {
                results.add(expense);
            }
        }
        // return new list
        return results;
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

        if (expense == null)
            return false;
        return true;
    }

    @Override
    public Collection<Expense> getAllExpensesByEmployeeId(int employeeId)
    {
        List<Expense> employeeExpenses = new ArrayList();
        Employee employee = App.employeeService.retrieveEmployeeById(employeeId);
        for (Expense expense : expenseTable.values())
        {
            if (employee.getId() == employeeId)
            {
                employeeExpenses.add(expense);
            }
        }
        return employeeExpenses;
    }
}
