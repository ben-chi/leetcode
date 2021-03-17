package com.example.demo;



import java.time.LocalDate;
import java.util.Objects;

public class Employee extends Person implements Comparable<Employee>,Cloneable{
    private double salary;
    protected LocalDate hireDay;

    public Employee(String name,double salary,int year,int month,int day){
        super(name);
        this.salary=salary;
        hireDay=LocalDate.of(year,month,day);
    }

    public Employee(String name){
        super(name);
        this.salary=0;
        this.hireDay=LocalDate.of(1970,1,1);
    }
    public double getSalary(){
        return salary;
    }
    public  LocalDate getHireDay(){
        return hireDay;
    }
    public void raiseSalary(double byPercent){
        double raise=salary*byPercent/100;
        salary+=raise;
    }
    public String getDescription(){
        return String.format("an employee with a salary of $%.2f",getSalary());
    }


    public boolean equals(Object otherObject){
        if(this==otherObject) return true;
        if(otherObject==null)  return false;
        if(this.getClass()!=otherObject.getClass()) return false;
        Employee other=(Employee) otherObject;
        return this.getName().equals(other.getName())&&
                this.getSalary()==other.getSalary()&&
                this.hireDay.equals(other.hireDay);
    }
    public int hashCode(){
        return Objects.hash(getName(),salary,hireDay);
    }
    public String toString(){
        return getClass().getName()+"[name="+getName()+",salary="+
                salary+",hireDay="+getHireDay()+"]";
    }
    public int compareTo(Employee otherObject){
        return Double.compare(salary,otherObject.salary);
    }
    public Employee clone() throws CloneNotSupportedException{
        return (Employee) super.clone();
    }

}
