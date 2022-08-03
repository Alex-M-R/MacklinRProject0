package dev.macklinr.entities;

// unclear if employee has a list of expenses in their object, but
// more likely/what I'd expect is this is a small example of relational database...
public class Employee
{
    int id;

    private String name;


    public Employee()
    {

    }

    public Employee(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public Employee(String name)
    {
        this.name = name;

    }

    public int getId()
    {
        return id;
    }
    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
