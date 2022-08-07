package dev.macklinr.handlers.expense;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class GetAllExpensesHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        Gson gson = new Gson();

        String param = ctx.queryParam("status");

        Collection<Expense> expenses;

        if (param != null)
        {
            param = param.toUpperCase();
            // optional parameter being used.

            try
            {
                ExpenseStatus status = ExpenseStatus.valueOf(param);
                expenses = App.expenseService.getAllExpensesByStatus(status);
            }
            catch (IllegalArgumentException e)
            {
                ctx.status(400);
                ctx.result("Invalid param: " + param + ". Valid parameters: PENDING, APPROVED, DENIED");
                return;
            }
        }
        else
        {
            expenses = App.expenseService.getAllExpenses();
        }

        String result = gson.toJson(expenses);
        ctx.result(result);
    }
}