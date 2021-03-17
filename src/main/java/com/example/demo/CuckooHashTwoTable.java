package com.example.demo;

import java.util.Random;
//未完成
public class CuckooHashTwoTable<AnyType> {
    private static final double MAX_LOAD=0.4;
    private static final int ALLOWED_REHASHES=1;
    private static final int DEFAULT_TABLE_SIZE=101;

    private final HashFamily<? super AnyType> hashFunctions;
    private final int numHashFunctions;
    private AnyType [] array;
    private int currentSize;

    public CuckooHashTwoTable(HashFamily<? super AnyType> hf){
        this(hf,DEFAULT_TABLE_SIZE);
    }
    public CuckooHashTwoTable(HashFamily<? super AnyType> hf,int size){
        allocateArray(nextPrime(size));
        doClear();
        hashFunctions=hf;
        numHashFunctions=hf.getNumberOfFunctions();
    }
    private void doClear(){
        currentSize=0;
        for(int i=0;i<array.length;i++)
            array[i]=null;
    }
    public void makeEmpty(){
        doClear();
    }
    @SuppressWarnings("unchecked")
    private void allocateArray(int arraySize){
        array=(AnyType[]) new Object[arraySize];
    }
    public boolean contains(AnyType x){
        return findPos(x)!=-1;
    }
    private int myhash(AnyType x,int which){
        int hashVal=hashFunctions.hash(x,which);
        hashVal%=array.length;
        if(hashVal<0)
            hashVal+=array.length;
        return hashVal;
    }
    private int findPos(AnyType x){
        for(int i=0;i<numHashFunctions;i++){
            int pos=myhash(x,i);
            if(array[pos]!=null&&array[pos].equals(x))
                return pos;
        }
        return -1;
    }
    public boolean remove(AnyType x){
        int pos=findPos(x);
        if(pos!=-1){
            array[pos]=null;
            currentSize--;
        }
        return pos!=-1;
    }
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
