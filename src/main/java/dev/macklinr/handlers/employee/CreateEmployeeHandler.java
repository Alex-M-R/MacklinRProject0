package dev.macklinr.handlers.employee;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.entities.Employee;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CreateEmployeeHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        String json = ctx.body();
        Gson gson = new Gson();

        Employee employee = gson.fromJson(json, Employee.class);

        Employee registererEmployee = App.employeeService.registerEmployee(employee);

        String employeeJson = gson.toJson(registererEmployee);

        ctx.status(201);
        ctx.result(employeeJson);
    }
}