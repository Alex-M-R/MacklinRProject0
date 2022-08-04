package dev.macklinr.handlers.expense;

import dev.macklinr.app.App;
import dev.macklinr.utils.CannedResponse;
import dev.macklinr.utils.InputValidation;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class DeleteExpenseHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            try
            {
                boolean result = App.expenseService.deleteExpense(id);

                if (result)
                {
                  //  ctx.status(204); // 204 - no content stops the ctx.result from being printed. I'd rather just send "Expense deleted" than leave blank.
                    ctx.result("Expense deleted");
                }
                else
                    CannedResponse.InvalidExpenseID(ctx, id);

                return;
            }
            catch (RuntimeException e)
            {
                ctx.status(400);
                ctx.result(e.getMessage());
            }
        }
        else
            CannedResponse.InvalidID(ctx);
    }
}
