package dev.macklinr.handlers.employee;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.entities.Employee;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class GetAllEmployeesHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        Gson gson = new Gson();

        Collection<Employee> employees = App.employeeService.getAllEmployees();

        String result = gson.toJson(employees);

        ctx.result(result);
    }
}
