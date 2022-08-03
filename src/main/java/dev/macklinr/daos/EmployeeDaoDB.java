package dev.macklinr.daos;

import dev.macklinr.entities.Employee;
import dev.macklinr.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeDaoDB implements EmployeeDAO
{

    static String tableName = "testemployee";  // setup SQL using this instead of literal for table name. This way I can easily change whether or not I'm using employee table, or a test table
    @Override
    public Employee createEmployee(Employee employee)
    {
        // try with resources syntax. It automatically closes the connection at the end of the try
        // or at the end of the catch. No connection.close() method needed if going this approach.
        try(Connection conn = ConnectionUtil.createConnection())
        {
            // insert into book values (default, 'The Stranger', 'Albert Camus', 0);
            String sql = "insert into " + tableName + " values (default, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, employee.getName());

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys(); // returns the id that was created
            rs.next();   // have to move cursor to the first valid record

            int generatedKey = rs.getInt("id");

            employee.setId(generatedKey);
            return employee;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee getEmployeeById(int id)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + tableName + " where id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();   // have to move cursor to the first valid record

            Employee employee = new Employee();

            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));

            return employee;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Employee> getAllEmployees()
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "select * from " + tableName;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            List<Employee> employeeList = new ArrayList();
            while(rs.next())
            {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employeeList.add(employee);
            }

            return employeeList;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee updateEmployee(Employee employee)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "update " + tableName + " set name = ? where id = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1,employee.getName());
            preparedStatement.setInt(2,employee.getId());

            preparedStatement.executeUpdate();
            return employee;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteEmployeeById(int id)
    {
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "delete from " + tableName + " where id = ?";

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
