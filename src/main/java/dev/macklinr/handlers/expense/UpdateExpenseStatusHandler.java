package dev.macklinr.handlers.expense;

import com.google.gson.Gson;
import dev.macklinr.app.App;
import dev.macklinr.utils.InputValidation;
import dev.macklinr.entities.Expense;
import dev.macklinr.entities.ExpenseStatus;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class UpdateExpenseStatusHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        // patch - given approve or deny as url input, and must change specified expense's status accordingly. Can't use the enum as they don't match

        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            // valid ID #
            Expense expense = App.expenseService.retrieveExpenseById(id);

            // can't update an expense that doesn't exist
            if (expense == null)
            {
                ctx.status(404);
                ctx.result("No expense with ID: " + id);
                return;
            }

            // id is valid and expense found. update status

            String newStatus = ctx.pathParam("newStatus");

            try
            {
                if (newStatus.equals("approve"))
                {
                    App.expenseService.updateExpenseStatus(id,ExpenseStatus.APPROVED);
                }
                else if (newStatus.equals("deny"))
                {
                    App.expenseService.updateExpenseStatus(id,ExpenseStatus.DENIED);
                }
                else
                {
                    // new status is invalid
                    ctx.status(400);
                    ctx.result(newStatus + " is not a valid status. Valid status inputs: approve , deny");
                    return;
                }

                Gson gson = new Gson();
                String json;
                json = gson.toJson(expense);

                ctx.result(json);
            }
            catch (RuntimeException e)
            {
                ctx.status(422);
                ctx.result(e.getMessage());
            }
        }
        else
        {
            ctx.status(400);
            ctx.result("Invalid id value of : " + ctx.pathParam("id"));
        }
    }
}
