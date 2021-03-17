package com.example.demo;

public class MyLinkedListStack<AnyType> {
    private Node<AnyType> beginMarker;
    public MyLinkedListStack(){
        beginMarker=new Node<AnyType>(null,null);
    }
    private static class Node<AnyType>{
        public Node<AnyType> next;
        public AnyType data;
        public Node(AnyType data,Node<AnyType> next){
            this.data=data;
            this.next=next;
        }
    }
    public void push(AnyType data){
        var newNode=new Node<AnyType>(data,beginMarker.next);
        beginMarker.next = newNode;
    }
    public AnyType pop(){
        if(beginMarker.next==null)
            throw new NullPointerException();
        var newNode=beginMarker.next;
        beginMarker.next=newNode.next;
        return newNode.data;
    }
    public AnyType getTop(){
        if(beginMarker.next==null) return null;
        return beginMarker.next.data;
    }
    public boolean isEmpty(){
        return beginMarker.next==null;
    }

}
