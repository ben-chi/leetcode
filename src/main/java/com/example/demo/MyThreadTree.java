package com.example.demo;

public class MyThreadTree<AnyType extends Comparable<? super AnyType>> {
    private static class BinaryNode<AnyType>{
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
        boolean leftIsThread=false;
        boolean rightIsThread=false;
        BinaryNode(AnyType element, BinaryNode<AnyType> left, BinaryNode<AnyType> right){
            this.element=element;
            this.left=left;
            this.right=right;
        }
        BinaryNode(AnyType element){
            this(element,null,null);
        }
    }
    private BinaryNode<AnyType> root;
    private BinaryNode<AnyType> pre;
    public MyThreadTree(){root=null;}
    public AnyType findMin(){
        return findMin(root).element;
    }
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> t){
        if(t==null)
            return null;
        while (!t.left.leftIsThread)
            t=t.left;
        return t;
    }
    public AnyType findMax(){
        return findMax(root).element;
    }
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> t){
        if(t==null)
            return null;
        while (!t.right.rightIsThread)
            t=t.right;
        return t;
    }
    public void toThread(){
        pre=null;
        toThread(root);
        var temp=root;
        while(temp.right!=null)
            temp=temp.right;
        temp.rightIsThread=true;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public void printTree(){
        if(isEmpty())
            System.out.println("Empty TreeSet");
        else
            printTree(root);

    }
    private void printTree(BinaryNode<AnyType> t){
        if(!t.leftIsThread)
            printTree(t.left);
        System.out.println(t.element);
        if(!t.rightIsThread)
            printTree(t.right);

    }
    private void toThread(BinaryNode<AnyType> t){
        if(t!=null){
            toThread(t.left);
            if(t.left==null){
                t.leftIsThread=true;
                t.left=pre;
            }
            if(pre!=null&&pre.right==null){
                pre.rightIsThread=true;
                pre.right=t;
            }
            pre=t;
            toThread(t.right);
        }
    }
    public void threadInorderTraverse(){
        threadInorderTraverse(root);
    }
    private void threadInorderTraverse(BinaryNode<AnyType> t){
        if(t==null){
            System.out.println("Empty tree");
            return;
        }
        while(t.left!=null)
            t=t.left;
        while(t!=null){
            System.out.print(t.element+" ");
            if(t.rightIsThread)
                t=t.right;
            else{
                t=t.right;
                while(!t.leftIsThread)
                    t=t.left;
            }

        }
    }
    public void insert(AnyType x){
        root=insert(x,root,null,0);
    }
    private BinaryNode<AnyType> insert(AnyType x,BinaryNode<AnyType> t
            ,BinaryNode<AnyType> parent,int leftOrRight){
        if(t==null&&parent==null){
            t= new BinaryNode<AnyType>(x,null,null);
            t.rightIsThread=true;
            t.leftIsThread=true;
            return t;
        }
        else if(t==null){
            t=new BinaryNode<AnyType>(x,null,null);
            t.leftIsThread=true;
            t.rightIsThread=true;
            if(leftOrRight==0){
                parent.leftIsThread=false;
                t.left=parent.left;
                t.right=parent;
            }
            else{
                parent.rightIsThread=false;
                t.left=parent;
                t.right=parent.right;
            }
            return t;
        }
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            t.left=insert(x,t.left,t,0);
        else if(compareResult>0)
            t.right=insert(x,t.right,t,1);
        return t;
    }
    public void remove(AnyType x){
        root=remove(x,root);
    }
    private BinaryNode<AnyType> remove(AnyType x,BinaryNode<AnyType> t){
        if(t==null)
            return null;
        int compareResult=x.compareTo(t.element);
        if(compareResult<0)
            t=remove(x,t.left);
        else if(compareResult>0)
            t=remove(x,t.right);
        else if(!t.leftIsThread&&!t.rightIsThread){
            t=findMin(t.right);
            t.right=remove(t.element,t.right);
        }
        else if(t.leftIsThread){
            findMin(t.right).left=t.left;
            t=t.right;
        }
        else{
            findMax(t.left).right=t.right;
            t=t.left;
        }
        return t;
    }
}
