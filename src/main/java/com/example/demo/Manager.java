package com.example.demo;

import java.util.Objects;

public class Manager extends Employee{
    private double bonus;
    public Manager(String name,double salary,int year,int month,int day){
        super(name, salary, year, month, day);
        bonus=0;
    }
    public void setBonus(double bonus){
        this.bonus=bonus;
    }
    public double getSalary(double extra){
        double baseSalary=super.getSalary();
        return baseSalary+bonus+extra;
    }
    public double getBonus(){
        return bonus;
    }




    public boolean equals(Object otherObject){
        if(!super.equals(otherObject)) return false;
        Manager other=(Manager) otherObject;
        return bonus==other.bonus;
    }

    public String toString(){
        return super.toString()+"[bonus="+bonus+"]";
    }
    public int compareTo(Employee otherEmployee){
        Manager other=(Manager) otherEmployee;
        return Double.compare(bonus,other.bonus);
    }

    public Manager clone() throws CloneNotSupportedException{
        return (Manager) super.clone();
    }





}
