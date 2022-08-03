package dev.macklinr.daotests;

import dev.macklinr.daos.EmployeeDAO;
import dev.macklinr.daos.EmployeeDaoDB;
import dev.macklinr.daos.ExpenseDAO;
import dev.macklinr.daos.ExpenseDaoDB;
import dev.macklinr.entities.Employee;
import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;
import dev.macklinr.entities.ExpenseType;
import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpenseDaoTests
{
    static ExpenseDAO expenseDao = new ExpenseDaoDB();

    @BeforeAll
    static void setup()
    {
        // Create test table for Employees
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "create table testemployee\n" +
                    "(\n" +
                    "\tid serial primary key, \t\t\t\n" +
                    "\tname varchar(40) not null \t\n" +
                    ");";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Create test table for Expenses
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "create table testexpense\n" +
                    "(\n" +
                    "\tid serial primary key,\n" +
                    "\temployeeID int references testemployee(id),\n" +
                    "\texpenseType varchar(40) check (expenseType in ('LODGING', 'TRAVEL', 'FOOD', 'MISC')),\t\n" +
                    "\tamount decimal,\n" +
                    "\tdescription varchar(40),\n" +
                    "\texpenseStatus varchar(40) check (expenseStatus in ('PENDING', 'ACCEPTED', 'DENIED'))\n" +
                    ");";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_expense_test()
    {
        // create an employee for this and future tests
        EmployeeDAO employeeDAO = new EmployeeDaoDB();
        Employee testEmployee = new Employee();
        testEmployee.setId(0);
        testEmployee.setName("Alexander Macklin-Rivera");
        employeeDAO.createEmployee(testEmployee);

        Expense newExpense = new Expense(0, 1, ExpenseType.FOOD, 200.00, ExpenseStatus.PENDING,"a test expense");
        Expense savedExpense = expenseDao.createExpense(newExpense);

        Assertions.assertNotEquals(0,savedExpense.getId());
    }

    @Test
    @Order(2)
    void get_expense_by_id_test()
    {
        Expense expense = expenseDao.getExpenseById(1);
        Assertions.assertEquals("a test expense", expense.getDescription());
    }

    @Test
    @Order(3)
    void update_expense_test()
    {
        Expense newExpense = new Expense(1, 1, ExpenseType.FOOD, 10000.00, ExpenseStatus.PENDING,"a more expensive test expense");
        Expense updatedExpense = expenseDao.updateExpense(newExpense);
        Expense retrievedExpense = expenseDao.getExpenseById(1);
        Assertions.assertEquals(updatedExpense, retrievedExpense);
    }

    @Test
    @Order(4)
    void delete_expense_by_id_test()
    {
        boolean result = expenseDao.deleteExpenseById(1);
        Assertions.assertTrue(result);
    }

    @Test
    @Order(5)
    void get_all_expenses_test()
    {
        List<Expense> fakeList = new ArrayList<>();
        fakeList.add(new Expense(0, 1, ExpenseType.FOOD, 10000.00, ExpenseStatus.PENDING,"a more expensive test expense"));
        fakeList.add(new Expense(0, 1, ExpenseType.FOOD, 10000.00, ExpenseStatus.PENDING,"a more expensive test expense"));
        fakeList.add(new Expense(0, 1, ExpenseType.FOOD, 10000.00, ExpenseStatus.PENDING,"a more expensive test expense"));
        fakeList.add(new Expense(0, 1, ExpenseType.FOOD, 10000.00, ExpenseStatus.PENDING,"a more expensive test expense"));

        for (Expense newE : fakeList)
        {
            expenseDao.createExpense(newE);
        }

        Collection<Expense> list = expenseDao.getAllExpenses();

        Assertions.assertEquals(4, list.size());
    }

    @AfterAll
    static void teardown()
    {
        // Drop test table Expense
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "drop table testexpense";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Drop test table Employee
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "drop table testemployee";

            Statement statement = conn.createStatement();
            statement.execute(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
