package com.example.demo;

import java.util.LinkedList;
import java.util.List;

public class SeparateChainingHashTable<AnyType> {

    private static final int DEFAULT_TABLE_SIZE=101;
    private List<AnyType> [] theLists;
    private int currentSize;
    public SeparateChainingHashTable(int size){
        theLists=new LinkedList[nextPrime(size)];
        for(int i=0;i<theLists.length;i++)
            theLists[i]=new LinkedList<>();
    }
    public SeparateChainingHashTable(){
        this(DEFAULT_TABLE_SIZE);
    }
    public void makeEmpty(){
        for(int i=0;i<theLists.length;i++)
            theLists[i].clear();
        currentSize=0;
    }
    public boolean contains(AnyType x){
        List<AnyType> whichList=theLists[myhash(x)];
        return whichList.contains(x);
    }
    public void insert(AnyType x){
        List<AnyType> whichList=theLists[myhash(x)];
        if(!whichList.contains(x)){
            whichList.add(x);
            /*
            if(++currentSize>theLists.length)
                rehash();
             */

        }
    }
    public void remove(AnyType x){
        List<AnyType> whichList=theLists[myhash(x)];
        if(whichList.contains(x)){
            whichList.remove(x);
            currentSize--;
        }

    }
    private int myhash(AnyType x){
        int hahVal=x.hashCode();
        hahVal%=theLists.length;
        if(hahVal<0)
            hahVal+=theLists.length;
        return hahVal;
    }
    /*
    private void rehash(){
        HashEntry
    }
     */


    private static int nextPrime(int n){
        if(n%2==0)
            n++;
        for(;;n+=2){
            if(isPrime(n))
                break;
        }
        return n;
    }
    private static boolean isPrime(int n){
        int tempInt=(int) Math.sqrt(n);
        for(int i=2;i<=tempInt;i++){
            if(n%i==0)
                return false;
        }
        return true;
    }


}
