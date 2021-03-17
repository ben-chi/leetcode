package com.example.demo;

import java.nio.BufferUnderflowException;
import java.util.LinkedHashSet;

public class LeftistHeap <AnyType extends Comparable<? super AnyType>>{
    private Node<AnyType> root;
    public LeftistHeap(){
        root=null;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public void makeEmpty(){
        root=null;
    }
    public void merge(LeftistHeap<AnyType> rhs){
        if(this==rhs)
            return;
        root=merge(root,rhs.root);
        rhs.root=null;
    }
    public AnyType findMin(){
        return root.element;
    }
    public void insert(AnyType x){
        root=merge(new Node<>(x),root);
    }
    public AnyType deleteMin(){
        if(isEmpty())
            throw new BufferUnderflowException();
        AnyType minItem=root.element;
        root=merge(root.left,root.right);
        return minItem;
    }
    private Node<AnyType> merge(Node<AnyType> h1,Node<AnyType> h2){
        if(h1==null)
            return h2;
        if(h2==null)
            return h1;
        Node<AnyType> temp;
        if(h1.element.compareTo(h2.element)>0){
            temp=h1;
            h1=h2;
            h2=temp;
        }
        Node<AnyType> H1=h1;
        Node<AnyType> currentNode=h1;
        h1=h1.right;
        while(h1!=null&&h2!=null){
            if(h1.element.compareTo(h2.element)<0){
                currentNode.right=h1;
                h1=h1.right;
            }
            else{
                currentNode.right=h2;
                h2=h2.right;
            }
            currentNode=currentNode.right;
        }
        if(h1!=null)
            currentNode.right=h1;
        if(h2!=null)
            currentNode.right=h2;
        var stack=new MyLinkedListStack<Node<AnyType>>();
        temp=H1;
        while(temp!=null){
            stack.push(temp);
            temp=temp.right;
        }
        while(!stack.isEmpty()){
            temp=stack.pop();
            if(temp.left==null){
                temp.npl=0;
                swapChildren(temp);
            }
            else if(temp.right==null)
                temp.npl=0;
            else if(temp.left.npl<temp.right.npl){
                swapChildren(temp);
                temp.npl=temp.right.npl+1;
            }
            else
                temp.npl=temp.right.npl+1;
        }
        return H1;

    }
    /*
    private Node<AnyType> merge(Node<AnyType> h1,Node<AnyType> h2){
        if(h1==null)
            return h2;
        if(h2==null)
            return h1;
        if(h1.element.compareTo(h2.element)<0)
            return merge1(h1,h2);
        else
            return merge1(h2,h1);
    }
    private Node<AnyType> merge1(Node<AnyType> h1,Node<AnyType> h2){
        if(h1.left==null)
            h1.left=h2;
        else {
            h1.right=merge(h1.right,h2);
            if(h1.left.npl<h1.right.npl)
                swapChildren(h1);
            h1.npl=h1.right.npl+1;
        }
        return h1;
    }

*/
    private void swapChildren(Node<AnyType> t){
        Node<AnyType> temp=t.left;
        t.left=t.right;
        t.right=temp;
    }
    private static class Node<AnyType>{
        AnyType element;
        Node<AnyType> left;
        Node<AnyType> right;
        int npl;
        Node(AnyType theElement){
            this(theElement,null,null);
        }
        Node(AnyType theElement,Node<AnyType> lt,Node<AnyType> rt){
            element=theElement;
            left=lt;
            right=rt;
            npl=0;
        }
    }
}
