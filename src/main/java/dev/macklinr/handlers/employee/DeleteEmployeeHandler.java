package dev.macklinr.handlers.employee;

import dev.macklinr.app.App;
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
                boolean result = App.employeeService.deleteEmployee(id);    // returns the 'deleted' employee. Returns null if there was nothing to delete

                if (result)
                {
                  //  ctx.status(204); // 204 - no content stops the ctx.result from being printed. I'd rather just send "Employee deleted" than leave blank.
                    ctx.result("Employee deleted");
                }
                else
                {
                    ctx.status(404);
                    ctx.result("Employee not found");
                }
            }
            catch (RuntimeException e)
            {
                    ctx.status(401);
                    ctx.result(e.getMessage());
            }
        }
        else
        {
            // invalid int parameter
            ctx.status(422);
            ctx.result("Invalid id value of : " + ctx.pathParam("id"));
        }
    }
}
