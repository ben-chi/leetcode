package com.example.demo;

public class LinearProbingHashTable<AnyType> {
    private static final int DEFAULT_TABLE_SIZE=11;
    private HashEntry<AnyType>[] array;
    private int currentSize;
    private int collisionTime=0;
    public int getCollisionTime(){
        return collisionTime;
    }
    private void allocateArray(int arraySize){
        array=new HashEntry[nextPrime(arraySize)];
    }
    public void makeEmpty(){
        currentSize=0;
        for(int i=0;i<array.length;i++)
            array[i]=null;
    }
    public LinearProbingHashTable(int size){
        allocateArray(size);
        makeEmpty();
    }
    public LinearProbingHashTable(){
        this(DEFAULT_TABLE_SIZE);
    }

    public boolean contains(AnyType x){
        int currentPos=findPos(x);
        return isActive(currentPos);
    }
    public void insert(AnyType x){
        int position=myhash(x);
        if(array[position]!=null&&!array[position].equals(x))
            collisionTime+=1;
        int currentPos=findPos(x);
        if(isActive(currentPos))
            return;
        array[currentPos]=new HashEntry<>(x,true);
        currentSize++;
        if(currentSize>array.length/2)
            rehash();
    }
    public void remove(AnyType x){
        int currentPos=findPos(x);
        if(isActive(currentPos))
            array[currentPos].isActive=false;
    }
    private static class HashEntry<AnyType>{
        public AnyType element;
        public boolean isActive;
        public HashEntry(AnyType e)
        {
            this(e,true);
        }
        public HashEntry(AnyType e,boolean i){
            element=e;
            isActive=i;
        }
    }
    private void rehash(){
        HashEntry<AnyType>[] oldArray=array;
        allocateArray(nextPrime(2*oldArray.length));
        currentSize=0;
        for(int i=0;i<oldArray.length;i++)
            if(oldArray[i]!=null&&oldArray[i].isActive)
                insert(oldArray[i].element);
    }
    private int myhash(AnyType x){
        int hahVal=x.hashCode();
        hahVal%=array.length;
        if(hahVal<0)
            hahVal+=array.length;
        return hahVal;
    }
    private boolean isActive(int currentPos){
        return array[currentPos]!=null&&array[currentPos].isActive;
    }
    private int findPos(AnyType x){
        int currentPos=myhash(x);
        while(array[currentPos]!=null&&
                !array[currentPos].element.equals(x)){
            collisionTime+=1;
            currentPos+=1;
            if(currentPos>=array.length)
                currentPos-=array.length;
        }
        return currentPos;
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
