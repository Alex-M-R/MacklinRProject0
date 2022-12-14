package dev.macklinr.daos;

import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;
import dev.macklinr.entities.ExpenseType;
import dev.macklinr.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// NYI
public class ExpenseDaoDB implements ExpenseDAO
{

    String tableName;  // setup SQL using this instead of literal for table name. This way I can easily change the table I'm using

    public ExpenseDaoDB()
    {
        super();
        this.tableName = "expense";
    }

    public ExpenseDaoDB(String tableName)
    {
        this.tableName = tableName;
    }

    @Override
    public Expense createExpense(Expense expense)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "insert into " + this.tableName + " values (default, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, expense.getIssuingEmployeeID()); // employeeID
            preparedStatement.setString(2, expense.getExpenseType().name()); // expenseType
            preparedStatement.setDouble(3,expense.getExpenseAmount()); //amount
            preparedStatement.setString(4, expense.getDescription()); // description
            preparedStatement.setString(5, expense.getStatus().name()); //expenseStatus


            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys(); // returns the id that was created
            rs.next();   // have to move cursor to the first valid record

            int generatedKey = rs.getInt("id");

            expense.setId(generatedKey);
            return expense;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Expense getExpenseById(int id)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + this.tableName + " where id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();   // have to move cursor to the first valid record

            Expense expense = new Expense();

            expense.setId(rs.getInt("id"));
            expense.setIssuingEmployeeID(rs.getInt("employeeid"));
            expense.setExpenseType(ExpenseType.valueOf(rs.getString("expensetype")));
            expense.setExpenseAmount(rs.getDouble("amount"));
            expense.setDescription(rs.getString("description"));
            expense.setStatus(ExpenseStatus.valueOf(rs.getString("expensestatus")));

            return expense;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Expense> getAllExpenses()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + this.tableName;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            List<Expense> expenseList = new ArrayList<>();
            while(rs.next())
            {
                Expense expense = new Expense();

                expense.setId(rs.getInt("id"));
                expense.setIssuingEmployeeID(rs.getInt("employeeid"));
                expense.setExpenseType(ExpenseType.valueOf(rs.getString("expensetype")));
                expense.setExpenseAmount(rs.getDouble("amount"));
                expense.setDescription(rs.getString("description"));
                expense.setStatus(ExpenseStatus.valueOf(rs.getString("expensestatus")));

                expenseList.add(expense);
            }

            return expenseList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Expense updateExpense(Expense expense)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "update " + this.tableName + " set employeeid = ?, expensetype = ?, amount = ?, description = ?, expensestatus = ? where id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, expense.getIssuingEmployeeID());
            preparedStatement.setString(2, expense.getExpenseType().name());
            preparedStatement.setDouble(3, expense.getExpenseAmount());
            preparedStatement.setString(4, expense.getDescription());
            preparedStatement.setString(5, expense.getStatus().name());
            preparedStatement.setInt(6, expense.getId());

            preparedStatement.executeUpdate();
            return expense;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteExpenseById(int id)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "delete from " + this.tableName + " where id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
