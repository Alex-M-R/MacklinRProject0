package dev.macklinr.services;

import dev.macklinr.app.App;
import dev.macklinr.daos.EmployeeDAO;
import dev.macklinr.entities.Employee;

import java.util.Collection;

// implement business rules and check data before inserting i guess
public class EmployeeServiceImplementation implements EmployeeService
{
    private EmployeeDAO employeeDAO;

    public EmployeeServiceImplementation(EmployeeDAO employeeDao)
    {
        this.employeeDAO = employeeDao;
    }

    @Override
    public Employee registerEmployee(Employee employee)
    {
        // could check that employee name is not null, but then I'd have to add if (employee == null) to create handler... to check if we successfully created...
        // FOR NOW - Assume we'll willingly create employees with no name - since they are tracked by their id anyway. So it's not catastrophic failure,
        // but bad data.

     //   if (employee.getName().length() == 0)
     //       throw new RuntimeException("Employee name cannot be empty");     // I guess technically it can but... no

        Employee savedEmployee = this.employeeDAO.createEmployee(employee);

        return savedEmployee;
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
        // add any rules or reasons why an edit would be bad and needs to be accounted for. like empty names etc.
       // if (employee.getName().length() == 0)
       //     throw new RuntimeException("Employee name cannot be empty");


        return this.employeeDAO.updateEmployee(employee);
    }
}
