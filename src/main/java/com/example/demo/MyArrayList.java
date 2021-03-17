package com.example.demo;

import java.util.NoSuchElementException;

public class MyArrayList<AnyType> implements Iterable<AnyType> {
    private static final int DEFAULT_CAPACITY=10;
    private int theSize;
    private AnyType[] theItems;
    private int modCount=0;
    public String toString(){
        String present="[";
        for(int i=0;i<theSize;i++){
            if(i==0) present+=theItems[0];
            else present=present+","+theItems[i];
        }
        present+="]";
        return present;
    }
    public MyArrayList(){
        doClear();
    }
    public void clear(){
        doClear();
    }
    public void doClear(){
        theSize=0;ensureCapacity(DEFAULT_CAPACITY);
        modCount++;
    }
    public int size(){
        return theSize;
    }
    public boolean isEmpty(){
        return theSize==0;
    }
    public void trimToSize(){
        ensureCapacity(size());
    }
    public AnyType get(int idx){
        if(idx<0||idx>=size()) throw new ArrayIndexOutOfBoundsException();
        return theItems[idx];
    }
    public AnyType set(int idx,AnyType newVal){
        if(idx<0||idx>=size()) throw new ArrayIndexOutOfBoundsException();
        AnyType old=theItems[idx];
        theItems[idx]=newVal;
        modCount++;
        return old;

    }
    public boolean add(AnyType x){
        add(size(),x);
        return true;
    }
    public void add(int idx,AnyType x){
        if(theItems.length==size())
            ensureCapacity(size()*2+1);
        if (theSize - idx >= 0) System.arraycopy(theItems, idx, theItems, idx + 1, theSize - idx);
        theItems[idx]=x;
        theSize++;
        modCount++;
    }
    public AnyType remove(int idx){
        AnyType removedItem=theItems[idx];
        for(int i=idx;i<size()-1;i++)
            theItems[i]=theItems[i+1];
        theSize--;
        modCount++;
        return removedItem;
    }
    public java.util.ListIterator<AnyType> iterator(){
        return new ArrayListIterator();
    }

    public void ensureCapacity(int newCapacity){
        if(newCapacity<theSize) return;
        AnyType[] old=theItems;
        //noinspection unchecked
        theItems= (AnyType[]) new Object[newCapacity];
        for(int i=0;i<size();i++)
            theItems[i]=old[i];
        modCount++;

    }
    private class ArrayListIterator implements java.util.ListIterator<AnyType>{
        private int current=0;
        private  boolean backwards=false;
        private int expectedModCount=modCount;
        private  boolean okToRemove=false;

        public boolean hasNext(){
            return current<size();
        }
        public AnyType next(){
            if(modCount!=expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if(!hasNext())
                throw new java.util.NoSuchElementException();
            backwards=false;
            okToRemove=true;
            return theItems[current++];
        }
        public boolean hasPrevious(){
            return current>0;
        }
        public AnyType previous(){
            if(modCount!=expectedModCount)
                throw new java.util.ConcurrentModificationException();
            if(!hasPrevious())
                throw new NoSuchElementException();
            backwards=true;
            okToRemove=true;
            return theItems[--current];
        }
        public void add(AnyType x){
            MyArrayList.this.add(current++,x);
            expectedModCount++;
        }
        public void remove(){
            if(!okToRemove)
                throw new IllegalStateException();
            if(backwards)
                MyArrayList.this.remove(current--);
            else
                MyArrayList.this.remove(--current);
            expectedModCount++;
            okToRemove=false;
        }
        public void set(AnyType newVal){
            MyArrayList.this.set(current,newVal);
            expectedModCount++;
        }
        public  int nextIndex()
        {
            throw new java.lang.UnsupportedOperationException();
        }
        public int previousIndex()
        {
            throw new java.lang.UnsupportedOperationException();
        }

    }

}
