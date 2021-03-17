package com.example.demo;

import java.nio.BufferUnderflowException;

public class BinomialQueue<AnyType extends Comparable<? super AnyType>> {
    private static final int DEFAULT_TREES=1;
    private int currentSize;
    private Node<AnyType>[] theTrees;
    public BinomialQueue(){
        expandTheTree(DEFAULT_TREES);
        currentSize=0;
    }
    public BinomialQueue(AnyType item){
        expandTheTree(DEFAULT_TREES);
        currentSize=0;
        if(item!=null){
            theTrees[0]=new Node<>(item);
            currentSize=1;
        }

    }
    public void merge(BinomialQueue<AnyType> rhs){
        if(this==rhs)
            return;
        currentSize+=rhs.currentSize;
        if(currentSize>capacity()){
            int maxLength=Math.max(theTrees.length,rhs.theTrees.length);
            expandTheTree(maxLength+1);
        }
        Node<AnyType> carry=null;
        for(int i=0,j=1;j<=currentSize;i++,j*=2){
            Node<AnyType> t1=theTrees[i];
            Node<AnyType> t2=i<rhs.theTrees.length?rhs.theTrees[i]:null;
            int whichCase=t1==null?0:1;
            whichCase+=t2==null?0:2;
            whichCase+=carry==null?0:4;
            switch (whichCase){
                case 0:
                case 1:
                    break;
                case 2:
                    theTrees[i]=t2;
                    rhs.theTrees[i]=null;
                case 4:
                    theTrees[i]=carry;
                    carry=null;
                    break;
                case 3:
                    carry=combineTrees(t1,t2);
                    theTrees[i]=rhs.theTrees[i]=null;
                    break;
                case 5:
                    carry=combineTrees(t1,carry);
                    theTrees[i]=null;
                    break;
                case 6:
                    carry=combineTrees(t2,carry);
                    rhs.theTrees[i]=null;
                    break;
                case 7:
                    theTrees[i]=carry;
                    carry=combineTrees(t1,t2);
                    rhs.theTrees[i]=null;
                    break;
            }
        }
        for(int k=0;k<rhs.theTrees.length;k++)
            rhs.theTrees[k]=null;
        rhs.currentSize=0;
    }
    public void insert(AnyType x){
        merge(new BinomialQueue<>(x));
    }
    public AnyType findMin(){
        int minIndex=findMinIndex();
        return theTrees[minIndex].element;
    }
    public AnyType deleteMin(){
        if(isEmpty())
            throw new BufferUnderflowException();
        int minIndex=findMinIndex();
        AnyType minItem=theTrees[minIndex].element;
        Node<AnyType> deletedTree=theTrees[minIndex].leftChild;
        BinomialQueue<AnyType> deletedQueue=new BinomialQueue<>();
        deletedQueue.expandTheTree(minIndex+1);
        deletedQueue.currentSize=(1<<minIndex)-1;
        for(int j=minIndex-1;j>=0;j--){
            deletedQueue.theTrees[j]=deletedTree;
            deletedTree=deletedTree.nextSibling;
            deletedQueue.theTrees[j].nextSibling=null;
        }
        theTrees[minIndex]=null;
        currentSize-=deletedQueue.currentSize+1;
        merge(deletedQueue);
        return minItem;
    }
    public boolean isEmpty(){
        return currentSize==0;
    }
    public void makeEmpty(){
        for(int i=0;i<theTrees.length;i++)
            theTrees[i]=null;
        currentSize=0;
    }
    private static class Node<AnyType>{
        AnyType element;
        Node<AnyType> leftChild;
        Node<AnyType> nextSibling;
        Node(AnyType theElement){
            this(theElement,null,null);
        }
        Node(AnyType theElement,Node<AnyType> lt,Node<AnyType> nt){
            element=theElement;
            leftChild=lt;
            nextSibling=nt;
        }
    }
    @SuppressWarnings("unchecked")
    private void expandTheTree(int newNumTrees){
        if(theTrees==null){
            theTrees=new Node[newNumTrees];
            return;
        }
        if(newNumTrees<theTrees.length)
            return;
        Node<AnyType>[] oldTrees=theTrees;
        theTrees=new Node[newNumTrees];
        for(int i=0;i<oldTrees.length;i++)
                theTrees[i]=oldTrees[i];
    }
    private Node<AnyType> combineTrees(Node<AnyType> t1,Node<AnyType> t2){
        if(t1.element.compareTo(t2.element)>0)
            return combineTrees(t2,t1);
        t2.nextSibling=t1.leftChild;
        t1.leftChild=t2;
        return t1;
    }
    private int capacity(){
        return (1<<theTrees.length)-1;
    }
    private int findMinIndex(){
        if(theTrees==null)
            throw new BufferUnderflowException();
        AnyType temp=theTrees[0].element;
        int minIndex=0;
        for(int i=1;i<theTrees.length;i++){
            if(theTrees[i].element.compareTo(temp)<0){
                minIndex=i;
                temp=theTrees[i].element;
            }
        }
        return minIndex;
    }

}
