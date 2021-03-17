package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;

//判断两序列是否创建一样的二叉树
public class JudgeIsOneTree {
    public static void main(String[] args){
        var array1=new ArrayList<Integer>(Arrays.asList(1,3,2,4));
        var array2=new ArrayList<Integer>(Arrays.asList(3,4,1,2));
        System.out.println(judge(array1,array2));
    }
    /*
    public static boolean judge(ArrayList<Integer> array1,ArrayList<Integer> array2){
        if(array1==null&&array2==null) return true;
        if(array1==null||array2==null) return false;
        if(array1.size()!=array2.size()|| !array1.get(0).equals(array2.get(0))) return false;
        var smallerArray1=new ArrayList<Integer>();
        var largerArray1=new ArrayList<Integer>();
        var smallerArray2=new ArrayList<Integer>();
        var largerArray2=new ArrayList<Integer>();
        for(int i=1;i<array1.size();i++){
            if(array1.get(i)<array1.get(0)) smallerArray1.add(array1.get(i));
            if(array1.get(i)>array1.get(0)) largerArray1.add(array1.get(i));
            if(array2.get(i)<array2.get(0)) smallerArray2.add(array2.get(i));
            if(array2.get(i)>array2.get(0)) largerArray2.add(array2.get(i));
        }
        if(smallerArray1.equals(smallerArray2)&&largerArray1.equals(largerArray2))
            return true;
        else
            return judge(smallerArray1,smallerArray2)&&judge(largerArray1,largerArray2);
    }

     */

    public static boolean judge(ArrayList<Integer> array1,ArrayList<Integer> array2) {
        var tree=new BinarySearchTree<Integer>();
        boolean check;
        for(Integer i:array1){
            tree.insert(i);
        }
        for(Integer i:array2){
            check=tree.check(i);
            if(!check) return false;
        }
        tree.reset();
        return true;

    }
}
