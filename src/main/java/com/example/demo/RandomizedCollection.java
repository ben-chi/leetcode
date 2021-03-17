package com.example.demo;

import java.util.*;

public class RandomizedCollection {
    Map<Integer, Set<Integer>> idx;
    List<Integer> num;
    public RandomizedCollection() {
        idx=new HashMap<Integer, Set<Integer>>();
        num=new ArrayList<Integer>();
    }

    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
        num.add(val);
        Set<Integer> set=idx.getOrDefault(val,new HashSet<Integer>());
        set.add(num.size());
        idx.put(val,set);
        return set.size()==1;
    }

    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
        if(!idx.containsKey(val))
            return false;
        Iterator<Integer> it=idx.get(val).iterator();
        int valAddress=it.next();
        int lastNum=num.get(num.size()-1);
        num.set(valAddress,lastNum);
        idx.get(val).remove(valAddress);
        idx.get(lastNum).remove(num.size()-1);
        if(valAddress!=num.size()-1)
            idx.get(lastNum).add(valAddress);
        if(idx.get(val).size()==0)
            idx.remove(val);
        num.remove(num.size()-1);
        return true;
    }

    /** Get a random element from the collection. */
    public int getRandom() {
        return num.get((int)(Math.random()*num.size()));
    }
}
