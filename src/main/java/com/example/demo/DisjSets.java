package com.example.demo;

import java.util.Arrays;

public class DisjSets {
    private int[] s;
    public DisjSets(int numElements){
        s=new int[numElements];
        Arrays.fill(s, -1);
    }
    public int find(int x){
        if(s[x]<0)
            return x;
        else
            return s[x]=find(s[x]);
    }
    public void union(int root1,int root2){
        if(s[root2]<s[root1])
            s[root1]=root2;
        else{
            if(s[root1]==s[root2])
                s[root1]--;
            s[root2]=s[root1];
        }
    }
}
