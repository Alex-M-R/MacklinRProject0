package dev.macklinr.handlers.expense;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.entities.Employee;
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

        Collection<Expense> expenses = null;

        if (param != null)
        {
            param = param.toUpperCase();
            // optional parameter being used.


            switch (param)
            {
                case "PENDING":
                    expenses = App.expenseService.getAllExpensesByStatus(ExpenseStatus.PENDING);
                    break;
                case "APPROVED":
                    expenses = App.expenseService.getAllExpensesByStatus(ExpenseStatus.APPROVED);
                    break;
                case "DENIED":
                    expenses = App.expenseService.getAllExpensesByStatus(ExpenseStatus.DENIED);
                    break;
                default:
                    ctx.result("Invalid param: " + param +". Valid parameters: PENDING, APPROVED, DENIED.");
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
/*
// Get all Expense Handler Pseudo code

Gson gson = new Gson();

String optionalParameter = ctx.queryParam(


 */