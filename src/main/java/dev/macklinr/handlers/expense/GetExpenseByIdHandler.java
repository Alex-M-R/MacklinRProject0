package dev.macklinr.handlers.expense;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.utils.CannedResponse;
import dev.macklinr.utils.InputValidation;
import dev.macklinr.entities.Expense;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GetExpenseByIdHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            // valid ID #
            Expense expense = App.expenseService.retrieveExpenseById(id);

            if (expense == null)
            {
                CannedResponse.InvalidExpenseID(ctx, id);
                return;
            }

            Gson gson = new Gson();
            String json;
            json = gson.toJson(expense);

            ctx.result(json);
        }
        else
        {
            CannedResponse.InvalidID(ctx);
        }
    }
}
