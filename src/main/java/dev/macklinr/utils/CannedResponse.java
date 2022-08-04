package dev.macklinr.utils;

import io.javalin.http.Context;

// contains common response errors
public class CannedResponse
{
    public static void InvalidID(Context ctx)
    {
        ctx.status(400);    // bad request
        ctx.result("Invalid id value of: " + ctx.pathParam("id") + ". ID must be > 0");
    }

    public static void InvalidEmployeeID(Context ctx, int id)
    {
        ctx.status(404);
        ctx.result("No employee with ID: " + id);
    }

    public static void InvalidExpenseID(Context ctx, int id)
    {
        ctx.status(404);
        ctx.result("No expense with ID: " + id);
    }

    public static void ObjectCreated(Context ctx, String json)
    {
        ctx.status(201);
        ctx.result(json);
    }



}
