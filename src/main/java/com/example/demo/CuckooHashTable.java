package com.example.demo;

import java.util.Random;

public class CuckooHashTable <AnyType>{
    private static final double MAX_LOAD=0.4;
    private static final int ALLOWED_REHASHES=1;
    private static final int DEFAULT_TABLE_SIZE=101;

    private final HashFamily<? super AnyType> hashFunctions;
    private final int numHashFunctions;
    private AnyType [] array;
    private int currentSize;

    public CuckooHashTable(HashFamily<? super AnyType> hf){
        this(hf,DEFAULT_TABLE_SIZE);
    }
    public CuckooHashTable(HashFamily<? super AnyType> hf,int size){
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
    public boolean insert(AnyType x){
        if(contains(x))
            return false;
        if(currentSize>=array.length*MAX_LOAD)
            expand();
        return insertHelper1(x);
    }
    private int rehashes=0;
    private Random r=new Random();
    private boolean insertHelper1(AnyType x){
        final int COUNT_LIMIT=100;
        while(true){
            int lastPos=-1;
            int pos;
            for(int count=0;count<COUNT_LIMIT;count++){
                for(int i=0;i<numHashFunctions;i++){
                    pos=myhash(x,i);
                    if(array[pos]==null){
                        array[pos]=x;
                        currentSize++;
                        return true;
                    }
                }
                int i=0;
                do{
                    pos=myhash(x,r.nextInt(numHashFunctions));
                }while(pos==lastPos&&i++<5);
                AnyType tmp=array[lastPos=pos];
                array[pos]=x;
                x=tmp;
            }
            if(++rehashes>ALLOWED_REHASHES){
                expand();
                rehashes=0;
            }
            else
                rehash();
        }
    }
    private void expand(){
        rehash((int)(array.length/MAX_LOAD));
    }
    private void rehash(){
        hashFunctions.generateNewFunctions();
        rehash(array.length);
    }
    private void rehash(int newLength){
        AnyType[] oldArray=array;
        allocateArray(nextPrime(newLength));
        currentSize=0;
        for(AnyType str:oldArray)
            if(str!=null)
                insert(str);
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
