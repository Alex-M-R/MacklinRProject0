package dev.macklinr.daos;

import dev.macklinr.entities.Employee;

import java.util.Collection;

public interface EmployeeDAO
{
    Employee createEmployee(Employee employee);

    Employee getEmployeeById(int id);

    Collection<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    boolean deleteEmployeeById(int id);
}
