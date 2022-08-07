package dev.macklinr.handlers.employee;

import dev.macklinr.app.App;
import dev.macklinr.entities.Employee;
import dev.macklinr.utils.CannedResponse;
import dev.macklinr.utils.InputValidation;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class DeleteEmployeeHandler implements Handler
{
    @Override
    public void handle(@NotNull Context ctx) throws Exception
    {
        int id = InputValidation.ValidatePositiveInt(ctx.pathParam("id"));

        if (id > 0)
        {
            try
            {
                Employee temp = App.employeeService.retrieveEmployeeById(id);

                if (temp == null)
                {
                    // no employee send error
                    CannedResponse.InvalidEmployeeID(ctx, id);
                }
                else
                {
                    App.employeeService.deleteEmployee(id);    // always returning true after switching to DB. checking if employee at index exists before deleting
                    ctx.status(200);
                    ctx.result("Employee deleted");
                }
            }
            catch (RuntimeException e)
            {
                    ctx.status(400);
                    ctx.result(e.getMessage());
            }
        }
        else
        {
            // invalid int parameter
            CannedResponse.InvalidID(ctx);
        }
    }
}
