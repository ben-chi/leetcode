package com.example.demo;

public class MemoryCell {
    private Object storedValue;
    public Object read(){
        return storedValue;
    }
    public void write(Object x){
        storedValue=x;
    }
    public static void test(Person[] e){
        if(e.length>0) e[0]=new Student("jacker","math");
    }
}
