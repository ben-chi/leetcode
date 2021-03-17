package com.example.demo;

public interface HashFamily<AnyType> {
    int hash(AnyType x,int which);
    int getNumberOfFunctions();
    void generateNewFunctions();
}
