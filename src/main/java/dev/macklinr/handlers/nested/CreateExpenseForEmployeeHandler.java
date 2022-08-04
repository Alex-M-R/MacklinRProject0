package dev.macklinr.handlers.nested;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.utils.CannedResponse;
import dev.macklinr.utils.InputValidation;
import dev.macklinr.entities.Expense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CreateExpenseForEmployeeHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            // valid ID #
            if (App.employeeService.retrieveEmployeeById(id) == null)
            {
                CannedResponse.InvalidEmployeeID(ctx, id);
                return;
            }

            // employee is valid. Go ahead and try to make the new expense
            Gson gson = new Gson();
            String response;

            Expense newExpense = gson.fromJson(ctx.body(), Expense.class);
           newExpense.setIssuingEmployeeID(id);

            try
            {
                Expense createdExpense = App.expenseService.registerExpense(newExpense);
                response = gson.toJson(createdExpense);
            }
            catch(RuntimeException e)
            {
                ctx.status(400);    // Bad Request (?)
                response = e.getMessage();
            }

            ctx.result(response);
        }
        else
        {
            CannedResponse.InvalidID(ctx);
        }
    }
}
