package dev.macklinr.entities;

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


    public Expense() {
    }

    public Expense(int issuingEmployeeID, ExpenseType expenseType, double expenseAmount, ExpenseStatus status)
    {
        this.issuingEmployeeID = issuingEmployeeID;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.status = status;
    }

    public Expense(int issuingEmployeeID, ExpenseType expenseType, double expenseAmount)
    {
        this.issuingEmployeeID = issuingEmployeeID;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
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

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }
}
