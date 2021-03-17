package com.example.demo;

import java.nio.BufferUnderflowException;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class MyTreeSet <AnyType extends Comparable<? super AnyType>>{
    private static class BinaryNode<AnyType>{
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
        BinaryNode<AnyType> parent;
        BinaryNode(AnyType element,BinaryNode<AnyType> left,BinaryNode<AnyType> right,
        BinaryNode<AnyType> parent){
            this.element=element;
            this.left=left;
            this.right=right;
            this.parent=parent;
        }
        BinaryNode(AnyType element){
            this(element,null,null,null);
    }
    }
    private BinaryNode<AnyType> root;
    private int modCount=0;
    public MyTreeSet(){
        root=null;
    }
    public MyTreeSet(BinaryNode<AnyType> t){root=t;}
    public void makeEmpty(){
        root=null;
        modCount++;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public boolean contains(AnyType x){
        return contains(x,root);
    }
    private boolean contains(AnyType x,BinaryNode<AnyType> t){
        if(t==null) return  false;
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            return  contains(x,t.left);
        else if(compareResult>0)
            return  contains(x,t.right);
        return true;
    }
    public AnyType findMin(){
        if(isEmpty())
            throw new BufferUnderflowException();
        else
            return  findMin(root).element;

    }
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t){
        if(t==null)
            return null;
        while(t.left!=null)
            t=t.left;
        return t;
    }
    public AnyType findMax(){
        if(isEmpty())
            throw new BufferUnderflowException();
        else
            return findMax(root).element;
    }
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t){
        if(t==null)
            return null;
        while(t.right!=null)
            t=t.right;
        return t;
    }
    public void insert(AnyType element){
        root=insert(element,root,null);
    }
    private BinaryNode<AnyType> insert(AnyType element,BinaryNode<AnyType> child,
                                       BinaryNode<AnyType> parent){
        if(child==null){
            modCount++;
            return new BinaryNode<>(element,null,null,parent);
        }

        int compareResult=element.compareTo(child.element);
        if(compareResult<0)
            child.left=insert(element,child.left,child);
        else if(compareResult>0)
            child.right=insert(element,child.right,child);
        return child;

    }
    public void remove(AnyType element){
        root=remove(element,root);
    }
    private BinaryNode<AnyType> remove(AnyType element,BinaryNode<AnyType> t){
        if(t==null)
            return null;
        int compareResult=element.compareTo(t.element);
        if(compareResult<0)
            t=remove(element,t.left);
        else if(compareResult>0)
            t=remove(element,t.right);
        else if(t.left!=null&&t.right!=null){
            t.element=findMin(t.right).element;
            t.right=remove(t.element,t.right);
        }
        else{
            modCount++;
            BinaryNode<AnyType> parent=t.parent;
            t=t.left==null?t.right:t.left;
            t.parent=parent;
        }
        return t;
    }
    public void printTree(){
        if(isEmpty())
            System.out.println("Empty TreeSet");
        else
            printTree(root);

    }
    private void printTree(BinaryNode<AnyType> t){
        if(t==null)
            return;
        printTree(t.left);
        System.out.println(t.element);
        printTree(t.right);
    }
    public java.util.Iterator<AnyType> iterator(){
        return new MyTreeSetIterator();
    }
    private class MyTreeSetIterator implements java.util.Iterator<AnyType>{
        private BinaryNode<AnyType> current=findMin(root);
        private int ExpectedModCount=modCount;
        private boolean okToRemove=false;
        private boolean atEnd=false;
        private BinaryNode<AnyType> previous;
        public boolean hasNext(){
            return  !atEnd;
        }
        public AnyType next(){
            if(ExpectedModCount!=modCount)
                throw new ConcurrentModificationException();
            if(!hasNext())
                throw new NoSuchElementException();
            previous=current;
            AnyType item=current.element;
            if(current.right!=null)
                current=findMin(current.right);
            BinaryNode<AnyType> child=current;
            current=current.parent;
            while(current!=null&&child!=current.left){
                //child!=current.left&&current!=null//
                child=current;
                current=current.parent;
            }
            if(current==null)
                atEnd=true;
            okToRemove=true;
            return item;
        }
        public void remove(){
            if(ExpectedModCount!=modCount)
                throw new ConcurrentModificationException();
            if(!okToRemove)
                throw new IllegalStateException();
            MyTreeSet.this.remove(previous.element);
            ExpectedModCount++;
            okToRemove=false;

        }
    }
    public int countLeaves(){
        return countLeaves(root);
    }
    private int countLeaves(BinaryNode<AnyType> t){
        if(t==null)
            return 0;
        else if(t.left==null&&t.right==null)
            return 1;
        else
            return countLeaves(t.right)+countLeaves(t.left);
    }
    public int countNodes(){
        return countNodes(root);
    }
    private int countNodes(BinaryNode<AnyType> t){
        if(t==null)
            return 0;
        else
            return countNodes(t.left)+countNodes(t.right)+1;
    }
    public int countFull(){
        return countFull(root);
    }
    private int countFull(BinaryNode<AnyType> t){
        if(t==null)
            return 0;
        int current=(t.left!=null&&t.right!=null)?1:0;
        return current+countFull(t.left)+countFull(t.right);

    }
    public void levelOrderPrint(){
        var list=new LinkedList<BinaryNode<AnyType>>();
        if(root==null)
            throw new NullPointerException();
        list.addFirst(root);
        while(!list.isEmpty()){
            var tempNode=list.removeLast();
            System.out.println(tempNode.element);
            if(tempNode.left!=null)
                list.addFirst(tempNode.left);
            if(tempNode.right!=null)
                list.addFirst(tempNode.right);
        }
    }
    public boolean isomorphic(MyTreeSet<AnyType> aTree){
        return this.isomorphic(root,aTree.root);
    }
    private boolean isomorphic(BinaryNode<AnyType> t1,BinaryNode<AnyType> t2){
        if(t1==null&&t2==null)
            return true;
        else if(t1==null||t2==null)
            return false;
        if(t1.element!=t2.element)
            return false;
        else{
            return (isomorphic(t1.left,t2.left)&&isomorphic(t1.right,t2.right))||
                    (isomorphic(t1.left,t2.right)&&isomorphic(t1.right,t2.left));
        }

    }
    public void testLeft(AnyType x){
        var t=new BinaryNode<AnyType>(x);
        var test=t.left;
        System.out.println(test==t.right);
    }
}
