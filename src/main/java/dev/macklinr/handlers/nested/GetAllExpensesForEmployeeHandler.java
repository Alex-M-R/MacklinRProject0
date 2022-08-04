package dev.macklinr.handlers.nested;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.utils.CannedResponse;
import dev.macklinr.utils.InputValidation;
import dev.macklinr.entities.Employee;
import dev.macklinr.entities.Expense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;

public class GetAllExpensesForEmployeeHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            // valid ID #
            Employee employee = App.employeeService.retrieveEmployeeById(id);

            if (employee == null)
            {
                CannedResponse.InvalidEmployeeID(ctx, id);
                return;
            }

            // employee is valid. Go ahead and get all expenses for this employee
            Collection<Expense> employeeExpenses = App.expenseService.getAllExpenseByEmployeeId(id);

            Gson gson = new Gson();
            String result = gson.toJson(employee) + "\n\n" + gson.toJson(employeeExpenses);

            ctx.result(result);
        }
        else
            CannedResponse.InvalidID(ctx);
    }
}
