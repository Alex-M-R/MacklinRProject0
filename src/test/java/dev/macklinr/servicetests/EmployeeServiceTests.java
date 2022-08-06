package dev.macklinr.servicetests;

import dev.macklinr.daos.EmployeeDAO;
import dev.macklinr.daos.EmployeeDaoDB;
import dev.macklinr.entities.Employee;
import dev.macklinr.services.EmployeeService;
import dev.macklinr.services.EmployeeServiceImplementation;
import dev.macklinr.utils.ConnectionUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTests
{
    private EmployeeDAO  eDao= Mockito.mock(EmployeeDAO.class);
    private static String employeeTable = "testemployee";
    EmployeeService employeeService = new EmployeeServiceImplementation(eDao);

    @Test
    @Order(1)
    void create_employee_test()
    {
        Employee fakeEmployee = new Employee("Alex Macklin-Rivera");
        Mockito.when(eDao.createEmployee(fakeEmployee)).thenReturn(new Employee(1, fakeEmployee.getName()));

        Employee createdEmployee = employeeService.registerEmployee(fakeEmployee);
        Assertions.assertEquals(1, createdEmployee.getId());

    }

    @Test
    @Order(2)
    void get_employee_test()
    {
        Mockito.when(eDao.getEmployeeById(1)).thenReturn(new Employee(1, "Mark Hammil"));

        Employee retrievedEmployee = employeeService.retrieveEmployeeById(1);

        Assertions.assertEquals("Mark Hammil", retrievedEmployee.getName());
    }

    @Test
    @Order(5)
    void delete_employee_by_id_test()
    {
        Mockito.when(eDao.deleteEmployeeById(1)).thenReturn(true);

        Assertions.assertTrue(employeeService.deleteEmployee(1));
    }

    @Test
    @Order(6)
    void update_employee_test()
    {
        Employee editedEmployee = new Employee(1, "Alex");

        Mockito.when(eDao.updateEmployee(editedEmployee)).thenReturn(editedEmployee);

        Assertions.assertEquals("Alex", employeeService.modifyEmployee(editedEmployee).getName());
    }

}
