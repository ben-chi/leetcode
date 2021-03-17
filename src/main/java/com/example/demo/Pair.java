package com.example.demo;

import java.lang.reflect.Array;
import java.util.function.Supplier;

public class Pair<T,U> {
    private T first;
    private  U second;
    public Pair(){first=null;second=null;}//
    public Pair(T first, U second){this.first=first;this.second=second;}
    public T getFirst(){return  first;}
    public U getSecond(){return  second; }
    public void setFirst(T newValue){first=newValue;}
    public void setSecond(U newValue){second=newValue;}
/*
    public static <T> Pair<T> makePair(Supplier<T> constr){
        return new Pair<>(constr.get(),constr.get());

    }

 */
    /*
    public static <T> Pair<T> makePair(Class<T> cl)
    {
        try{
            return new Pair<>(cl.getConstructor().newInstance(),
                    cl.getConstructor().newInstance());
        }
        catch (Exception e){return null;}
    }

     */
    /*
    @SafeVarargs
    public static <T extends Comparable> T[] minmax(T... a){
        var result= Array.newInstance(a.getClass().getComponentType(),2);
    }

     */
}