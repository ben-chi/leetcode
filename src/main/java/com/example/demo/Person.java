package com.example.demo;

public abstract class Person {
    private String name;
    public Person(String name){
        this.name=name;
    }
    public Person(){
        this.name="Joker";
    }
    public abstract String getDescription();
    public String getName(){
        return name;
    }
}
