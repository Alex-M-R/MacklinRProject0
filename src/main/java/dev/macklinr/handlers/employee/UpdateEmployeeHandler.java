package dev.macklinr.handlers.employee;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.utils.CannedResponse;
import dev.macklinr.utils.InputValidation;
import dev.macklinr.entities.Employee;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

// handling PUT /employees{id}   but honestly not sure if this is correct. I'm assuming I'm overwriting employee at specified index with provided employee object in
// ctx.body(), but the library rest api doesn't check if the sent book has the same id provided in the request... so could be a book with id 3, in the map with a
// key of 2... i think.
public class UpdateEmployeeHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            if (App.employeeService.retrieveEmployeeById(id) != null)   // if an employee already exists at that ID, we overwrite.
            {
                Gson gson = new Gson();
                Employee employee = gson.fromJson(ctx.body(),Employee.class);

                employee.setId(id); // force employee ID to pathParam id just in case

                App.employeeService.modifyEmployee(employee);
            }
            else    // no employee at that ID. send 404 employee not found
            {
                CannedResponse.InvalidEmployeeID(ctx, id);
            }
        }
        else
        {
            CannedResponse.InvalidID(ctx);
        }
    }
}