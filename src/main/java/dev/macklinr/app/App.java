package dev.macklinr.app;

import dev.macklinr.daos.EmployeeDaoDB;
import dev.macklinr.daos.ExpenseDaoDB;
import dev.macklinr.handlers.employee.*;
import dev.macklinr.handlers.expense.*;
import dev.macklinr.handlers.nested.CreateExpenseForEmployeeHandler;
import dev.macklinr.handlers.nested.GetAllExpensesForEmployeeHandler;
import dev.macklinr.handlers.nested.MassCreateExpensesForEmployeeHandler;
import dev.macklinr.services.EmployeeService;
import dev.macklinr.services.EmployeeServiceImplementation;
import dev.macklinr.services.ExpenseService;
import dev.macklinr.services.ExpenseServiceImplementation;
import io.javalin.Javalin;

public class App
{
    private static final String employeeTable = "employee";
    private static final String expenseTable = "expense";

    public static final EmployeeService employeeService = new EmployeeServiceImplementation(new EmployeeDaoDB(employeeTable));

    public static final ExpenseService expenseService = new ExpenseServiceImplementation(new ExpenseDaoDB(expenseTable));

    public static void main(String[] args)
    {
        Javalin app = Javalin.create(config ->{config.enableDevLogging();});

        // Handler Objects

        // Employee Handlers
        CreateEmployeeHandler createEmployeeHandler = new CreateEmployeeHandler();
        GetEmployeeByIdHandler getEmployeeByIdHandler = new GetEmployeeByIdHandler();
        UpdateEmployeeHandler updateEmployeeHandler = new UpdateEmployeeHandler();
        DeleteEmployeeHandler deleteEmployeeHandler = new DeleteEmployeeHandler();
        GetAllEmployeesHandler getAllEmployeesHandler = new GetAllEmployeesHandler();

        // Expense Handlers
        CreateExpenseHandler createExpenseHandler = new CreateExpenseHandler();
        DeleteExpenseHandler deleteExpenseHandler = new DeleteExpenseHandler();
        GetAllExpensesHandler getAllExpensesHandler = new GetAllExpensesHandler();
        GetExpenseByIdHandler getExpenseByIdHandler = new GetExpenseByIdHandler();
        UpdateExpenseHandler updateExpenseHandler = new UpdateExpenseHandler();
        UpdateExpenseStatusHandler updateExpenseStatusHandler = new UpdateExpenseStatusHandler();

        // Nested Handlers
        CreateExpenseForEmployeeHandler createExpenseForEmployeeHandler = new CreateExpenseForEmployeeHandler();
        GetAllExpensesForEmployeeHandler getAllExpensesForEmployeeHandler = new GetAllExpensesForEmployeeHandler();

        // Outside of project scope/requirements
        MassCreateEmployeesHandler massCreateEmployeesHandler = new MassCreateEmployeesHandler();
        MassCreateExpensesForEmployeeHandler massCreateExpensesForEmployeeHandler = new MassCreateExpensesForEmployeeHandler();

// Rest api routes

    // Required Employee Routes

        // POST /employees              -> Creates employee and returns a 201 status code
        app.post("/employees",createEmployeeHandler);

        // GET /employees               -> returns all employees
        app.get("/employees", getAllEmployeesHandler);

        // GET /employees/{id}          -> returns specific employee or a 404 if employee not found
        app.get("/employees/{id}", getEmployeeByIdHandler);

        // PUT /employees/{id}          -> Overwrite specific employee or return a 404 if employee not found
        app.put("/employees/{id}", updateEmployeeHandler);

        // DELETE /employees/{id}       -> deletes employee or returns a 404 if employee not found
        app.delete("/employees/{id}", deleteEmployeeHandler);

    // outside of project scope/requirements
        app.post("/employees-bulk", massCreateEmployeesHandler);


    // Required Expenses Routes

        // POST /expenses                       -> creates expense and returns a 201 status code
        app.post("/expenses",createExpenseHandler);

        // GET /expenses                        -> returns all expenses or all expenses with status (optional parameter ?status='status').
        app.get("/expenses", getAllExpensesHandler);

        // GET /expenses/{id}                   -> get specific expense or return a 404 if not found
        app.get("/expenses/{id}", getExpenseByIdHandler);

        // PUT /expenses/{id}                   -> update (overwrite) expense record or return 404 if not found
        app.put("/expenses/{id}", updateExpenseHandler);

        // PATCH /expenses/{id}/{approve/deny}  -> update status of a record or return a 404 if record not found
        app.patch("/expenses/{id}/{newStatus}", updateExpenseStatusHandler);

        // DELETE /expenses/{id}                -> delete expense record OR return 404 if not found OR return 422 if no longer Pending (Approved/Denied)
        app.delete("/expenses/{id}", deleteExpenseHandler);


    // Nested Routes - confirmed "nested" just refers to the structure of the route declaration below
        // GET /employees/{id}/expenses         -> returns all expenses for specific employee
        app.get("/employees/{id}/expenses", getAllExpensesForEmployeeHandler);

        // POST /employees/{id}/expenses        -> add an expense to specific employee
        app.post("/employees/{id}/expenses", createExpenseForEmployeeHandler);

    // outside of project scope/requirements
        app.post("/employees/{id}/expenses-bulk", massCreateExpensesForEmployeeHandler);

        app.start();
    }
}

