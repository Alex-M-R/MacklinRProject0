package dev.macklinr.daotests;

import dev.macklinr.daos.EmployeeDAO;
import dev.macklinr.daos.EmployeeDaoLocal;
import dev.macklinr.daos.ExpenseDAO;
import dev.macklinr.daos.ExpenseDaoLocal;
import dev.macklinr.entities.Employee;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Originally created to test DaoLocal
public class EmployeeDaoTests
{
    EmployeeDAO employeeDAO = new EmployeeDaoLocal();

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
        employeeDAO.createEmployee(new Employee("Alex Macklin-Rivera"));
        Employee employee = employeeDAO.getEmployeeById(1);
        Assertions.assertEquals("Alex Macklin-Rivera", employee.getName());
    }

    @Test
    @Order(3)
    void update_employee_test()
    {
        employeeDAO.createEmployee(new Employee("Alex Macklin-Rivera"));

        Employee employee = new Employee(1, "Rodrigo Diaz");
        Employee updatedEmployee = employeeDAO.updateEmployee(employee);
        Employee retrievedEmployee = employeeDAO.getEmployeeById(1);
        Assertions.assertEquals(updatedEmployee, retrievedEmployee);
    }

    @Test
    @Order(4)
    void delete_employee_by_id_test()
    {
        employeeDAO.createEmployee(new Employee("Alex Macklin-Rivera"));

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

        Assertions.assertNotNull(list);
    }


    @Test
    @Order(6)
    void attempt_delete_employee_not_found()
    {
        boolean result = employeeDAO.deleteEmployeeById(1);
        Assertions.assertFalse(result);
    }

}
