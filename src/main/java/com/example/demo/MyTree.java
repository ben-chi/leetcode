package com.example.demo;

public class MyTree<AnyType> {
    private BinaryNode<AnyType> root;
    public MyTree(AnyType element,MyTree<AnyType> leftTree,MyTree<AnyType> rightTree){
        root= new BinaryNode<>(element,leftTree.root,rightTree.root);
    }
    public MyTree(BinaryNode<AnyType> x){
        root=x;
    }
    public MyTree(AnyType element){
        root=new BinaryNode<>(element);
    }
    public  void postOrder(){
        if(root.left!=null) {
            var leftTree = new MyTree<AnyType>(root.left);
            leftTree.postOrder();
        }
        if(root.right!=null){
            var rightTree=new MyTree<AnyType>(root.right);
            rightTree.postOrder();
        }
        if(root!=null)
            System.out.print(root.element);

    }

    private class BinaryNode<AnyType>{
        AnyType element;
        BinaryNode<AnyType> left;
        BinaryNode<AnyType> right;
        public BinaryNode(AnyType element,BinaryNode<AnyType> left,BinaryNode<AnyType> right){
            this.element=element;
            this.left=left;
            this.right=right;
        }
        public BinaryNode(AnyType element){
            this(element,null,null);
        }

    }
}
