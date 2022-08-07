package dev.macklinr.services;

import dev.macklinr.app.App;
import dev.macklinr.daos.EmployeeDAO;
import dev.macklinr.entities.Employee;

import java.util.Collection;

// implement business rules and check data before inserting i guess
public class EmployeeServiceImplementation implements EmployeeService
{
    private final EmployeeDAO employeeDAO;

    public EmployeeServiceImplementation(EmployeeDAO employeeDao)
    {
        this.employeeDAO = employeeDao;
    }

    @Override
    public Employee registerEmployee(Employee employee)
    {
        return this.employeeDAO.createEmployee(employee);
    }

    @Override
    public Employee retrieveEmployeeById(int id)
    {
        return this.employeeDAO.getEmployeeById(id);
    }

    @Override
    public boolean deleteEmployee(int id) throws RuntimeException
    {
        int expenses = App.expenseService.getAllExpenseByEmployeeId(id).size();
        //Check that expense do NOT exist for the current employee before deleting.
        if (expenses > 0)
        {
            throw new RuntimeException("Delete employee failed. Cannot delete employee while employee has " + expenses + " expense records.");
        }

        return this.employeeDAO.deleteEmployeeById(id);
    }

    @Override
    public Collection<Employee> getAllEmployees()
    {
        return this.employeeDAO.getAllEmployees();
    }

    @Override
    public Employee modifyEmployee(Employee employee)
    {
        return this.employeeDAO.updateEmployee(employee);
    }
}
