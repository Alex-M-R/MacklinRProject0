package dev.macklinr.utils;

import dev.macklinr.entities.ExpenseStatus;

// collection of methods to try/catch attempts at parsing string input as... something else
public class InputValidation
{
    // checks if string is valid int, and positive. Returns if true, otherwise -1 if invalid
    public static int ValidatePositiveInt(String s)
    {
        int id = -1;

        try
        {
            id = Integer.parseInt(s);

            if (id <= 0)
                id = -1;

        }
        catch (NumberFormatException e)
        {
            // maybe throw exception instead of falling through and returning id as negative on failure
        }

        return id;
    }

    public static ExpenseStatus ValidateStatus(String status)
    {
        status = status.toUpperCase();

        ExpenseStatus[] validValues = ExpenseStatus.values();

        for (ExpenseStatus vs : validValues)
        {
            if (vs.name().equals(status))
            {
                return vs;
            }
        }

        // expense not found!
        throw new RuntimeException("Invalid Expense Status. Received Input: " + status + ". Valid Inputs: PENDING , APPROVED , DENIED");
    }

}
