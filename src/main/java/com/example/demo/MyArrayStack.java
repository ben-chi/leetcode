package com.example.demo;

public class MyArrayStack<AnyType> {
    private AnyType[] theArray;
    private int topOfStack;
    private static final int DEFAULT_CAPACITY=50;
    @SuppressWarnings("unchecked")
    public MyArrayStack(){
        theArray=(AnyType[]) new Object[DEFAULT_CAPACITY];
        topOfStack=-1;
    }
    public int getTopOfStack(){
        return  topOfStack;
    }
    public void push(AnyType x){
        if(topOfStack==(DEFAULT_CAPACITY-1))
            throw new IndexOutOfBoundsException();
        theArray[++topOfStack]=x;
    }
    public AnyType pop(){
        if(topOfStack==-1)
            throw new IndexOutOfBoundsException();
        return theArray[topOfStack--];
    }
    public AnyType getTop(){
        if(topOfStack==-1) return null;
        return theArray[topOfStack];
    }

}
