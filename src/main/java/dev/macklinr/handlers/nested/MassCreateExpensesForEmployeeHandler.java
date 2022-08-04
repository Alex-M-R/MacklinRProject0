package dev.macklinr.handlers.nested;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.macklinr.app.App;
import dev.macklinr.entities.Expense;
import dev.macklinr.utils.CannedResponse;
import dev.macklinr.utils.InputValidation;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MassCreateExpensesForEmployeeHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            // valid ID #
            if (App.employeeService.retrieveEmployeeById(id) == null)
            {
                ctx.status(404);
                ctx.result("No employee with ID: " + id);
                return;
            }

            // employee is valid. Go ahead and try to make the new expenses
            Gson gson = new Gson();

            Type expenseListType = new TypeToken<ArrayList<Expense>>(){}.getType();

            List<Expense> expensesToAdd = gson.fromJson(ctx.body(), expenseListType);

            List<Expense> createdExpenses = new ArrayList();
            List<Expense> failedExpenses = new ArrayList();

            for (Expense expense : expensesToAdd)
            {
                expense.setIssuingEmployeeID(id);   // force expenses to be for the specified employeeID
                try
                {
                    Expense createdExpense = App.expenseService.registerExpense(expense);
                    createdExpenses.add(createdExpense);

                }
                catch(RuntimeException e)
                {
                    failedExpenses.add(expense);
                }
            }
            // created all the expenses we could, while the rest failed. output results
            String result = "Successfully created " + createdExpenses.size() + " expense records.\r\nFailed to created " + failedExpenses.size() + " records.\r\n Successful:\r\n"
                    + gson.toJson(createdExpenses) + "\r\n Failed:\r\n" + gson.toJson(failedExpenses);
            ctx.result(result);
        }
        else
            CannedResponse.InvalidID(ctx);
    }
}
