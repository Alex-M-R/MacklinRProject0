package dev.macklinr.entities;

import java.util.Objects;

// Expense/Business Rules - enforced in the business service layer (services) not in the dao(object creation/get/update/delete)
// All expenses have a single employee as the issuer
// Expenses start as pending and must then be approved or denied
// Once approved or denied they CANNOT be deleted or edited
// Cannot have a negative expense
public class Expense
{
    private int id;
   private int issuingEmployeeID;
    private ExpenseType expenseType;
    private double expenseAmount;
    private ExpenseStatus status;
    private String description;

    public Expense()
    {
    }

    public Expense(int issuingEmployeeID, ExpenseType expenseType, double expenseAmount, ExpenseStatus status, String description)
    {
        this.issuingEmployeeID = issuingEmployeeID;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.status = status;
        this.description = description;
    }

    public Expense(int id, int issuingEmployeeID, ExpenseType expenseType, double expenseAmount, ExpenseStatus status, String description)
    {
        this.id = id;
        this.issuingEmployeeID = issuingEmployeeID;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public int getIssuingEmployeeID() {
        return issuingEmployeeID;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIssuingEmployeeID(int issuingEmployeeID) {
        this.issuingEmployeeID = issuingEmployeeID;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public void setExpenseAmount(double expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public void setStatus(ExpenseStatus status)
    {
        this.status = status;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return id == expense.id && issuingEmployeeID == expense.issuingEmployeeID && Double.compare(expense.expenseAmount, expenseAmount) == 0 && expenseType == expense.expenseType && status == expense.status && Objects.equals(description, expense.description);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, issuingEmployeeID, expenseType, expenseAmount, status, description);
    }
}
