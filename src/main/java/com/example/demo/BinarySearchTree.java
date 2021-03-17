package com.example.demo;

import java.lang.reflect.AnnotatedArrayType;
import java.nio.BufferUnderflowException;
import java.util.prefs.NodeChangeEvent;

public class BinarySearchTree<AnyType extends  Comparable<? super AnyType>> {
    private static class BinaryNode<AnyType>{
        BinaryNode(AnyType theElement){
            this(theElement,null,null);
        }
        BinaryNode(AnyType theElement,BinaryNode<AnyType> lt,BinaryNode<AnyType> rt){
            element=theElement;
            left=lt;right=rt;
            flag=1;
        }
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
        int flag;
    }
    private BinaryNode<AnyType> root;
    public BinarySearchTree(){
        root=null;
    }
    public void makeEmpty(){
        root=null;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public boolean contains(AnyType x){
        return contains(x,root);
    }
    public AnyType findMin()  {
        if(isEmpty()) throw new BufferUnderflowException();
        if(findMin(root)==null)
            throw new BufferUnderflowException();
        return findMin(root).element;
    }
    public AnyType findMax()  {
        if(isEmpty()) throw new BufferUnderflowException();
        if(findMax(root)==null)
            throw new BufferUnderflowException();
        return findMax(root).element;
    }
    public void insert(AnyType x){
        root=insert(x,root);
    }
    public void remove(AnyType x){
        root=remove(x,root);
    }
    public void printTree(){
        if(isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }
    private void printTree(BinaryNode<AnyType> t){
        if(t!=null){
            printTree(t.left);
            if(t.flag!=0)
                System.out.println(t.element);
            printTree(t.right);
        }
    }
    private boolean contains(AnyType x, BinaryNode<AnyType> t){
        if(t==null) return false;
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            return contains(x,t.left);
        else if(compareResult>0)
            return  contains(x,t.right);
        else
            return t.flag!=0;
    }
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t){
        if(t==null)
            return null;
        BinaryNode<AnyType> current=t.flag!=0?t:null;
        BinaryNode<AnyType> leftMin=findMin(t.left);
        BinaryNode<AnyType> rightMin=findMin(t.right);
        if(leftMin!=null)
            return leftMin;
        else if(current!=null)
            return current;
        else return rightMin;

    }
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t){
        if(t==null)
            return null;
        BinaryNode<AnyType> current=t.flag!=0?t:null;
        BinaryNode<AnyType> leftMax=findMax(t.left);
        BinaryNode<AnyType> rightMax=findMax(t.right);
        if(rightMax!=null)
            return rightMax;
        else if(current!=null)
            return current;
        else return leftMax;
    }
    private BinaryNode<AnyType> insert(AnyType x,BinaryNode<AnyType> t){
        if(t==null)
            return new BinaryNode<>(x,null,null);
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            t.left=insert(x,t.left);
        else if(compareResult>0)
            t.right=insert(x,t.right);
        else if(t.flag==0)
            t.flag=1;
        return t;
    }
    private BinaryNode<AnyType> remove(AnyType x,BinaryNode<AnyType> t){
        if(t==null)
            return t;
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            t.left=remove(x,t.left);
        else if(compareResult>0)
            t.right=remove(x,t.right);
        else if(t.flag==1)
            t.flag=0;
        return t;

    }

    public void reset(){
        reset(root);
    }
    private void reset(BinaryNode<AnyType> t){
        if(t!=null){
            t.flag=0;
            reset(t.right);
            reset(t.left);
        }
    }
    public boolean check(AnyType x){
        return check(x,root);
    }
    private boolean check(AnyType x,BinaryNode<AnyType> t){
        if(t==null) return false;
        int compareResult=x.compareTo(t.element);
        if(compareResult<0){
            if(t.flag==0)
                return false;
            else
                return check(x,t.left);
        }
        else if(compareResult>0){
            if(t.flag==0)
                return false;
            else
                return check(x,t.right);
        }
        else{
            t.flag=1;
            return true;
        }

    }


}
