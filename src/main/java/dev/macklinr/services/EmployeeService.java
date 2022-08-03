package dev.macklinr.services;

import dev.macklinr.entities.Employee;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface EmployeeService
{
    Employee registerEmployee(Employee employee);

    Employee retrieveEmployeeById(int id);

    boolean deleteEmployee(int id) throws RuntimeException;

    Collection<Employee> getAllEmployees();

    Employee modifyEmployee(Employee employee);
}
