package dev.macklinr.handlers.employee;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.utils.InputValidation;
import dev.macklinr.entities.Employee;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetEmployeeByIdHandler implements Handler
{

    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {

        // not sure where I want my validate input code to live yet. Currently in Employee Service Implementation,
        // but maybe move to App? or possibly create a static class whose entire purpose is to valid this crap.. probably end up doing that

        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            // valid ID #
            Employee employee = App.employeeService.retrieveEmployeeById(id);

            if (employee == null)
            {
                ctx.status(404);
                ctx.result("No employee with ID: " + id);
                return;
            }

            Gson gson = new Gson();
            String json;
            json = gson.toJson(employee);

            ctx.result(json);
        }
        else
        {
            ctx.status(422);
            ctx.result("Invalid id value of : " + ctx.pathParam("id"));
        }
    }
}
