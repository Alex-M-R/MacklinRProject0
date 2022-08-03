package dev.macklinr.handlers.expense;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.utils.InputValidation;
import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;

public class GetAllExpensesWithStatusHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {

        String test = ctx.queryParam("status");
        System.out.println(test);

    //    System.out.println("Reached Get All Expenses With Status Handler");
        try
        {
            ExpenseStatus status = InputValidation.ValidateStatus(ctx.pathParam("status"));

            Collection<Expense> expenses = App.expenseService.getAllExpensesByStatus(status);

            Gson gson = new Gson();
            String json;
            json = gson.toJson(expenses);

            ctx.result(json);
        }
        catch (RuntimeException e)
        {
            ctx.status(400);
            ctx.result(e.getMessage());
            return;
        }
    }
}
