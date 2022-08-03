package dev.macklinr.daos;

import dev.macklinr.entities.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDaoLocal implements EmployeeDAO
{
    private Map<Integer, Employee> employeeTable = new HashMap();

    private int idMaker = 1;

    @Override
    public Employee createEmployee(Employee employee)
    {
        employee.setId(idMaker++);

        employeeTable.put(employee.getId(),employee);

        return employee;
    }

    @Override
    public Employee getEmployeeById(int id)
    {
        return employeeTable.get(id);
    }

    @Override
    public Collection<Employee> getAllEmployees()
    {
        return this.employeeTable.values();
    }  // test for this... not sure if correct as is

    @Override
    public Employee updateEmployee(Employee employee)
    {
        this.employeeTable.put(employee.getId(),employee);
        return employee;
    }

    @Override
    public boolean deleteEmployeeById(int id)
    {
        Employee employee = employeeTable.remove(id);

        if (employee == null)
            return false;
        return true;
    }
}
