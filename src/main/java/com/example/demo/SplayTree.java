package com.example.demo;

import org.springframework.lang.NonNull;

import java.nio.BufferUnderflowException;

public class SplayTree <AnyType extends  Comparable<? super AnyType>>  {
    private static class BinaryNode<AnyType>{
        BinaryNode(AnyType theElement){
            this(theElement,null,null,null);
        }
        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt,
                   BinaryNode<AnyType> pa){
            element=theElement;
            left=lt;right=rt;
            parent=pa;
        }
        public boolean isLeft(){
            if(parent==null)
                return false;
            return this == parent.left;
        }
        public boolean isRight(){
            if(parent==null)
                return false;
            return this==parent.right;
        }
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
        BinaryNode<AnyType> parent;
    }
    private BinaryNode<AnyType> root;
    public SplayTree(){
        root=null;
    }
    public void makeEmpty(){
        root=null;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public void printTree(){
        if(isEmpty())
            System.out.println("Empty tree");
        else
            printTree(root);
    }
    private void printTree(BinaryNode<AnyType> t){
        if(t!=null){printTree(t.right);
            printTree(t.left);

            System.out.println(t.element);
        }
    }
    public boolean contains(AnyType x){
        return contains(x,root);
    }
    private boolean contains(AnyType x, BinaryNode<AnyType> t){
        if(t==null)
            return  false;
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            return  contains(x,t.left);
        else if(compareResult>0)
            return  contains(x,t.right);
        else{
            balance(t);
            return true;
        }

    }
//此方法过于复杂，未修改完
    private void balance(BinaryNode<AnyType> t) {
        while (t.parent != null) {
            if (t.parent.parent == null) {
                if (t.isLeft()) {
                    t.parent.left = t.right;
                    t.parent.parent=t;
                    t.right = t.parent;
                    t.parent=null;
                }
                if (t.isRight()) {
                    t.parent.right = t.left;
                    t.left = t.parent;
                    t.parent=null;
                }

            }
            else {
                var tempGrandparent=t.parent.parent.parent;
                if (t.isRight() && t.parent.isLeft()) {
                    BinaryNode<AnyType> temp1 = t.left;
                    BinaryNode<AnyType> temp2 = t.right;
                    t.left = t.parent;
                    t.right = t.parent.parent;
                    t.parent.parent.left = temp2;
                    t.parent.right = temp1;
                } else if (t.isLeft() && t.parent.isLeft()) {
                    BinaryNode<AnyType> temp1 = t.right;
                    BinaryNode<AnyType> temp2 = t.parent.right;
                    t.right = t.parent;
                    t.parent.right = t.parent.parent;
                    t.parent.left = temp1;
                    t.parent.parent.left = temp2;
                } else if (t.parent.isRight() && t.isLeft()) {
                    BinaryNode<AnyType> temp1 = t.left;
                    BinaryNode<AnyType> temp2 = t.right;
                    t.left = t.parent;
                    t.right = t.parent.parent;
                    t.parent.left = temp1;
                    t.parent.parent.right = temp2;
                } else if (t.parent.isRight() && t.isRight()) {
                    BinaryNode<AnyType> temp1 = t.left;
                    BinaryNode<AnyType> temp2 = t.parent.left;
                    t.right = t.parent;
                    t.parent.right = t.parent.parent;
                    t.parent.right = temp1;
                    t.parent.parent.right = temp2;
                }
                t.parent=tempGrandparent;
            }

        }
        root=t;
    }



    public void insert(AnyType x){
        root=insert(x,root,null);
    }
    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> t,
                                       BinaryNode<AnyType> parent){
        if(t==null)
            return new BinaryNode<>(x,null,null,parent);
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            t.left=insert(x,t.left,t);
        else if(compareResult>0)
            t.right=insert(x,t.right,t);
        return t;
    }
    public void remove(AnyType x){
        root=remove(x,root);
    }
    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> t){
        if(t==null)
            return t;
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            t.left=remove(x,t.left);
        else if(compareResult>0)
            t.right=remove(x,t.right);
        else if(t.left!=null&&t.right!=null) {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        }
        else{
            BinaryNode<AnyType> parent=t.parent;
            t=(t.left!=null)? t.left:t.right;
            t.parent=parent;
        }

        return t;
    }
    public AnyType findMin()  {
        if(isEmpty()) throw new BufferUnderflowException();
        if(findMin(root)==null)
            throw new BufferUnderflowException();
        return findMin(root).element;
    }
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t){
        if(t==null)
            return null;
        else if(t.left==null)
            return t;
        else
            return findMin(t.left);
    }
}
