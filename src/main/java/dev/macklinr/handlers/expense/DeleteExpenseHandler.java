package dev.macklinr.handlers.expense;

import dev.macklinr.app.App;
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
                    ctx.status(204);
                    ctx.result("Expense deleted");
                }
                else
                {
                    ctx.status(404);
                    ctx.result("Expense not found");
                }
            }
            catch (RuntimeException e)
            {
                ctx.status(422);
                ctx.result(e.getMessage());
            }
        }
        else
        {
            // invalid int parameter
            ctx.status(400);
            ctx.result("Invalid id value of : " + ctx.pathParam("id"));
        }
    }
}
