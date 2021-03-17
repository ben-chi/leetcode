package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class HopscotchHashTable<AnyType> {
    private final static int MAX_DIST=4;
    private static final int DEFAULT_TABLE_SIZE=101;
    private static final double MAX_LOAD=0.5;
    private HashEntry<AnyType> [] array;
    private int currentSize;
    public HopscotchHashTable(int size){
        currentSize=0;
        array=new HashEntry[nextPrime(size)];
        for(int i=0;i<size;i++)
            array[i]=null;
    }
    public HopscotchHashTable(){
        this(DEFAULT_TABLE_SIZE);
    }
    private static class HashEntry<AnyType>{
        public AnyType element;
        public boolean[] Hop;
        public HashEntry(AnyType element){
            this.element=element;
            Hop=new boolean[MAX_DIST];
        }
    }
    public boolean contains(AnyType x){
        return findPos(x)!=-1;
    }
    public boolean insert(AnyType x){
        if(contains(x))
            return false;
        if(currentSize>=array.length*MAX_LOAD)
            expand();
        return insertHelper1(x);
    }
    @SuppressWarnings("unchecked")
    private void allocateArray(int size){
        array=new HashEntry[nextPrime(size)];
    }
    private void rehash(int newLength){
        HashEntry<AnyType>[] oldArray=array;
        allocateArray(nextPrime(newLength));
        currentSize=0;
        AnyType x;
        for (HashEntry<AnyType> anyTypeHashEntry : oldArray) {
            if (anyTypeHashEntry != null) {
                x = anyTypeHashEntry.element;
                insert(x);
            }
        }

    }
    private void expand(){
        rehash((int)(array.length/MAX_LOAD));
    }
    public boolean insertHelper1(AnyType x){
        int originalPos=myhash(x);
        int currentPos=originalPos;
        int stepForward=0;
        int tempPos;
        for(;stepForward<array.length;stepForward++){
            currentPos=(originalPos+stepForward)%array.length;
            if(array[currentPos]==null)
                break;
        }
        while(stepForward>=MAX_DIST){
            int i;
            read_data:
            for(i=MAX_DIST-1;i>=0;i--){
                tempPos=(currentPos-i)%array.length;
                for(int j=0;j<i;j++){
                    if(array[tempPos].Hop[j]){
                        tempPos=(currentPos-i+j)%array.length;
                        array[tempPos].Hop[j]=false;
                        array[tempPos].Hop[i]=true;
                        if(array[currentPos]==null)
                            array[currentPos]=new HashEntry<>(array[tempPos].element);
                        else
                            array[currentPos].element=array[tempPos].element;
                        array[tempPos].element=null;
                        currentPos=tempPos;
                        stepForward=stepForward-i+j;
                        break read_data;
                    }
                }
            }
            if(i==-1)
                break;

        }
        if(stepForward>=MAX_DIST){
            System.out.println("failed");
            return false;
        }
        else {
            if(array[currentPos]==null)
                array[currentPos]=new HashEntry<>(x);
            else
                array[currentPos].element=x;
            return true;
        }


    }
    private int findPos(AnyType x){
        int currentPos=myhash(x);
        HashEntry<AnyType> currentEntry=array[currentPos];
        for(int i=0;i<MAX_DIST;i++){
            if(currentEntry.Hop[i]&&array[currentPos+i]!=null&&array[currentPos+i].equals(x))
                return currentPos+i;
        }
        return -1;

    }
    private int myhash(AnyType x){
        int hahVal=x.hashCode();
        hahVal%=array.length;
        if(hahVal<0)
            hahVal+=array.length;
        return hahVal;
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
