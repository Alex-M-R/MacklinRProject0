package dev.macklinr.entities;

import dev.macklinr.annotations.Entity;

import java.util.Objects;

// unclear if employee has a list of expenses in their object, but
// more likely/what I'd expect is this is a small example of relational database...
@Entity
public class Employee
{
//    @Id("id")
    int id;

  //  @Column("name")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
