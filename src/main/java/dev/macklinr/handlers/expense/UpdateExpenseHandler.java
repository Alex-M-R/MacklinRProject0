package dev.macklinr.handlers.expense;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.entities.ExpenseStatus;
import dev.macklinr.utils.CannedResponse;
import dev.macklinr.utils.InputValidation;
import dev.macklinr.entities.Expense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class UpdateExpenseHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            // valid ID #. check Expense exists
            if (App.expenseService.retrieveExpenseById(id) != null)
            {
                Gson gson = new Gson();
                Expense updatedExpense = gson.fromJson(ctx.body(),Expense.class);

                updatedExpense.setId(id);

                try
                {
                    Expense savedExpense = App.expenseService.modifyExpense(updatedExpense);

                    ctx.result(gson.toJson(savedExpense));
                }
                catch (RuntimeException e)
                {
                    ctx.result(e.getMessage());
                }
            }
            else
            {
                CannedResponse.InvalidExpenseID(ctx, id);
                return;
            }
        }
        else
            CannedResponse.InvalidID(ctx);
    }
}
