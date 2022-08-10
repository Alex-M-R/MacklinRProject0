package dev.macklinr.handlers.expense;

import dev.macklinr.app.App;
import dev.macklinr.entities.Expense;
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

                Expense temp = App.expenseService.retrieveExpenseById(id);

                if (temp == null)
                {
                    CannedResponse.InvalidExpenseID(ctx, id);
                }
                else
                {
                    App.expenseService.deleteExpense(id);
                    ctx.result("Expense deleted");
                }
            }
            catch (RuntimeException e)
            {
                ctx.status(422);
                ctx.result(e.getMessage());
            }
        }
        else
            CannedResponse.InvalidID(ctx);
    }
}
