package dev.macklinr.handlers.employee;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.macklinr.app.App;
import dev.macklinr.entities.Employee;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MassCreateEmployeesHandler implements Handler
{

    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        Gson gson = new Gson();

        Type employeeListType = new TypeToken<ArrayList<Employee>>(){}.getType();

        List<Employee> employeesToAdd = gson.fromJson(ctx.body(), employeeListType);

        List<Employee> addedEmployees = new ArrayList();

        for(Employee e : employeesToAdd)
        {
            addedEmployees.add(App.employeeService.registerEmployee(e));
        }

        String result = "Created " + addedEmployees.size() + " employees shown below: " + gson.toJson(addedEmployees);
        //result = gson.toJson(addedEmployees);
        ctx.status(201);
        ctx.result(result);
    }
}
