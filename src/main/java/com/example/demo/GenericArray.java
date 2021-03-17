package com.example.demo;

public class GenericArray<T> {
    private T[] array;

    @SuppressWarnings("unchecked")
    public GenericArray(int sz) {
        array = (T[]) new Object[sz];
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] rep() {
        return array;
    } //应该在运行时出口做文章

    public static void main(String[] args) {
        GenericArray<Integer> gai = new GenericArray<Integer>(10);
        // Integer[] ia = gai.rep(); //ClassCastException
        Integer[] oa = gai.rep(); //只能返回对象数组类型为Object[]
    }



}