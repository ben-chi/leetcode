package com.example.demo;

import org.springframework.context.annotation.ScopeMetadata;

import java.util.NoSuchElementException;
import java.lang.Math.*;

public class MinMaxHeap<AnyType extends Comparable<? super AnyType>> {
    private AnyType[] array;
    private int currentSize;
    private static final int DEFAULT_CAPACITY=10;
    public MinMaxHeap(){
        this(DEFAULT_CAPACITY);
    }
    @SuppressWarnings("unchecked")
    public MinMaxHeap(int capacity){
        currentSize=0;
        array=(AnyType[])new Comparable[capacity];
    }
    @SuppressWarnings("unchecked")
    public MinMaxHeap(AnyType [] items){
        currentSize=items.length;
        array=(AnyType[])new Comparable[(currentSize+2)*11/10];
        int i=1;
        for(AnyType item:items)
            array[i++]=item;
        buildHeap();
    }
    public void insert(AnyType x){
        if(currentSize==array.length-1)
            enlargeAArray(array.length*2+1);
        int hole=++currentSize;
        int layerNum=(int)(Math.log10(currentSize)/Math.log10(2));
        array[0]=x;
        if(layerNum%2==0){
            if(x.compareTo(array[hole/4])<0){
                for(;x.compareTo(array[hole/4])<0;hole=hole/4)
                    array[hole]=array[hole/4];
            }
            else if(x.compareTo(array[hole/2])>0){
                array[hole]=array[hole/2];
                hole=hole/2;
                for(;x.compareTo(array[hole/4])>0;hole=hole/4)
                    array[hole]=array[hole/4];
            }
        }
        else{
            if(x.compareTo(array[hole/4])>0){
                for(;x.compareTo(array[hole/4])>0;hole=hole/4)
                    array[hole]=array[hole/4];
            }
            else if(x.compareTo(array[hole/2])<0){
                array[hole]=array[hole/2];
                hole=hole/2;
                for(;x.compareTo(array[hole/4])<0;hole=hole/4)
                    array[hole]=array[hole/4];
            }
        }
        array[hole]=x;
    }
    public AnyType deleteMin(){
        AnyType minItem=array[1];
        AnyType x=array[currentSize--];
        array[0]=array[1]=x;
        int currentPos=1;
        int hole=currentPos;
        boolean indicator=false;

        while(currentPos*4<=currentSize){
            for(int i=0;i<4&&(currentPos*4+i)<=currentSize;i++){
                if(array[currentPos*4+i].compareTo(array[currentPos])<0){
                    hole=currentPos*4+i;
                    array[currentPos]=array[currentPos*4+i];
                }
            }
            if(currentPos==hole){
                indicator=true;
                break;
            }
            else
                currentPos=hole;
        }
        if(indicator)
            return minItem;
        if(hole*2>currentSize){
            if(x.compareTo(array[hole/2])>0){
                array[hole]=array[hole/2];
                hole=hole/2;
                for(;x.compareTo(array[hole/4])>0;hole=hole/4)
                    array[hole]=array[hole/4];
            }
            array[hole]=x;
        }
        else if(hole*2==currentSize){
            if(array[hole].compareTo(array[hole*2])<0)
                return minItem;
            else{
                array[hole]=array[hole*2];
                for(;x.compareTo(array[hole/4])>0;hole=hole/4)
                    array[hole]=array[hole/4];
                array[hole]=x;
            }
        }
        else {
            if(array[hole].compareTo(array[hole*2])<0&&array[hole].compareTo(array[hole*2+1])<0)
                return minItem;
            else {
                int temp=(array[hole*2].compareTo(array[hole*2+1])<0)?hole*2:(hole*2+1);
                array[hole]=array[temp];
                hole=temp;
                for(;x.compareTo(array[hole/4])>0;hole=hole/4)
                    array[hole]=array[hole/4];
                array[hole]=x;
            }
        }
        return minItem;

    }
    //deleteMax几乎相同

    public boolean isEmpty(){
        return currentSize==0;
    }
    public void makeEmpty(){
        currentSize=0;
        for(int i=0;i<array.length;i++)
            array[i]=null;
    }
    @SuppressWarnings("unchecked")
    private void enlargeAArray(int newSize){
        if(newSize<array.length)
            return;
        AnyType[] oldArray=array;
        array=(AnyType[]) new Comparable[newSize];
        currentSize=0;
        for(int i=1;i<oldArray.length;i++)
            insert(oldArray[i]);

    }
    public void printHeap(){
        for(int i=1;i<=currentSize;i++)
            System.out.println(array[i]);
    }
    private void buildHeap(){

    }

}
