package dev.macklinr.handlers.expense;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.entities.Expense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class CreateExpenseHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        String json = ctx.body();
        Gson gson = new Gson();

        String response = "";

        Expense expense = gson.fromJson(json, Expense.class);

        try
        {
            Expense registeredExpense = App.expenseService.registerExpense(expense);

            response = gson.toJson(registeredExpense);

            ctx.status(201);
        }
        catch(RuntimeException e)
        {
            ctx.status(400);    // Bad Request (?)
            response = e.getMessage();
        }
        finally
        {
            ctx.result(response);
        }
    }
}
