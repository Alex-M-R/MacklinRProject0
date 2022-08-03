package dev.macklinr.daotests;

import dev.macklinr.daos.*;
import dev.macklinr.entities.Employee;
import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeDaoTests
{
    private static String employeeTable = "testemployee";
    EmployeeDAO employeeDAO = new EmployeeDaoDB(employeeTable);


    @BeforeAll
    static void setup()
    {
        // Create test table for Employees
        try(Connection conn = ConnectionUtil.createConnection())
        {
            String sql = "create table " + employeeTable + "\n" +
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
    }


    @Test
    @Order(1)
    void create_employee_test()
    {
        Employee employee = new Employee(0,"Alex Macklin-Rivera");
        Employee savedEmployee = employeeDAO.createEmployee(employee);
        Assertions.assertNotEquals(0, savedEmployee.getId());
    }

    @Test
    @Order(2)
    void get_employee_by_id_test()
    {
        Employee employee = employeeDAO.getEmployeeById(1);
        Assertions.assertEquals("Alex Macklin-Rivera", employee.getName());
    }

    @Test
    @Order(3)
    void update_employee_test()
    {
        Employee employee = new Employee(1, "Rodrigo Diaz");
        Employee updatedEmployee = employeeDAO.updateEmployee(employee);
        Employee retrievedEmployee = employeeDAO.getEmployeeById(1);
        Assertions.assertEquals(updatedEmployee, retrievedEmployee);
    }

    @Test
    @Order(4)
    void delete_employee_by_id_test()
    {
        boolean result = employeeDAO.deleteEmployeeById(1);
        Assertions.assertTrue(result);
    }

    @Test
    @Order(5)
    void get_employee_list_test()
    {
        List<Employee> fakeList = new ArrayList<>();
        fakeList.add(new Employee("Alex Macklin-Rivera"));
        fakeList.add(new Employee("Mark Hammil"));
        fakeList.add(new Employee("Carrie Fisher"));
        fakeList.add(new Employee("Harrison Ford"));

        for (Employee newE : fakeList)
        {
            employeeDAO.createEmployee(newE);
        }

        Collection<Employee> list = employeeDAO.getAllEmployees();

        Assertions.assertEquals(4, list.size());
    }

    @AfterAll
    static void teardown()
    {
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
