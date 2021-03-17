/*
package com.example.demo;

public class StaticInnerClassTest {
    public static  void main(String[] args){
        double [] values=new double[20];
        for(int i=0;i<values.length;i++){
            values[i]=100*Math.random();
        }
        ArrayAlg.Pair p=ArrayAlg.minmax(values);
        System.out.println("min= "+p.getFirst());
        System.out.println("max= "+p.getSecond());
        p.returnName();
    }
}
class ArrayAlg{

    public static class Pair{
        private final double first;
        private final double second;
        public Pair(double f,double s){
            first=f;second=s;
        }
        public double getFirst(){
            return first;
        }
        public double getSecond(){
            return second;
        }
        public void returnName(){
            System.out.println(new ArrayAlg.Pair(1,2){}.getClass().getEnclosingClass());
        }


    }

    public static  Pair minmax(double[] values){
        double min=Double.POSITIVE_INFINITY;
        double max=Double.NEGATIVE_INFINITY;
        for(double v:values){
            if(v<min) min=v;
            if(v>max) max=v;
        }
        return new Pair(min,max);
    }
}

 */