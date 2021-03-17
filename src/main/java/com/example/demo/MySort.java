package com.example.demo;

import javax.sound.midi.MidiSystem;
import java.util.ArrayList;

public class MySort <AnyType extends Comparable<? super AnyType>> {
    private static final int CUTOFF = 10;

    public void insertionSort(AnyType[] a) {
        int j;
        for (int p = 1; p < a.length; p++) {
            AnyType temp = a[p];
            for (j = p; j > 0 && temp.compareTo(a[j - 1]) < 0; j--)
                a[j] = a[j - 1];
            a[j] = temp;
        }
    }

    private void insertionSort(AnyType[] a, int left, int right) {
        int j;
        for (int p = left + 1; p <= right; p++) {
            AnyType temp = a[p];
            for (j = p; j > left && temp.compareTo(a[j - 1]) < 0; j--)
                a[j] = a[j - 1];
            a[j] = temp;
        }
    }

    public void shellSort(AnyType[] a) {
        int j;
        for (int gap = a.length / 2; gap > 0; gap /= 2)
            for (int i = gap; i < a.length; i++) {
                AnyType temp = a[i];
                for (j = i; j >= gap && temp.compareTo(a[j - gap]) < 0; j -= gap)
                    a[j] = a[j - gap];
                a[j] = temp;
            }
    }

    private static int leftChild(int i) {
        return 2 * i + 1;
    }

    private void percDown(AnyType[] a, int i, int n) {
        int child;
        AnyType temp;
        for (temp = a[i]; leftChild(i) < n; i = child) {
            child = leftChild(i);
            if (child != n - 1 && a[child].compareTo(a[child + 1]) < 0)
                child++;
            if (temp.compareTo(a[child]) < 0)
                a[i] = a[child];
            else
                break;
        }
        a[i] = temp;
    }

    public void heapSort(AnyType[] a) {
        for (int i = a.length / 2 - 1; i >= 0; i--)
            percDown(a, i, a.length);
        for (int i = a.length - 1; i > 0; i--) {
            swapReference(a, 0, i);
            percDown(a, 0, i);
        }
    }

    private void swapReference(AnyType[] a, int i, int j) {
        AnyType temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private void mergeSort(AnyType[] a, AnyType[] tempArray, int left, int right) {
        if (left < right) {
            int center = (left + right) / 2;
            mergeSort(a, tempArray, left, center);
            mergeSort(a, tempArray, center + 1, right);
            merge(a, tempArray, left, center + 1, right);
        }
    }

    private void merge(AnyType[] a, AnyType[] tmpArray, int leftPos, int rightPos, int rightEnd) {
        int leftEnd = rightPos - 1;
        int tmpPos = leftPos;
        int numElement = rightEnd - leftPos + 1;
        while (leftPos <= leftEnd && rightPos <= rightEnd)
            if (a[leftPos].compareTo(a[rightPos]) <= 0)
                tmpArray[tmpPos++] = a[leftPos++];
            else
                tmpArray[tmpPos++] = a[rightPos++];
        while (leftPos <= leftEnd)
            tmpArray[tmpPos++] = a[leftPos++];
        while (rightPos <= rightEnd)
            tmpArray[tmpPos++] = a[rightPos++];
        for (int i = 0; i < numElement; i++, rightEnd--)
            a[rightEnd] = tmpArray[rightEnd];
    }

    @SuppressWarnings("unchecked")
    public void mergeSort(AnyType[] a) {
        AnyType[] tempArray = (AnyType[]) new Comparable[a.length];
        mergeSort(a, tempArray, 0, a.length - 1);
    }

    private AnyType median3(AnyType[] a, int left, int right) {
        int center = (left + right) / 2;
        if (a[center].compareTo(a[left]) < 0)
            swapReference(a, left, center);
        if (a[right].compareTo(a[left]) < 0)
            swapReference(a, left, right);
        if (a[right].compareTo(a[center]) < 0)
            swapReference(a, center, right);
        swapReference(a, center, right - 1);
        return a[right - 1];
    }

    private void quickSort(AnyType[] a, int left, int right) {
        if (left + CUTOFF <= right) {
            AnyType pivot = median3(a, left, right);
            int i = left, j = right - 1;
            for (; ; ) {
                while (a[++i].compareTo(pivot) < 0) {
                }
                while (a[--j].compareTo(pivot) > 0) {
                }
                if (i < j)
                    swapReference(a, i, j);
                else
                    break;
            }
            swapReference(a, i, right - 1);
            quickSort(a, left, i - 1);
            quickSort(a, i + 1, right);
        } else
            insertionSort(a, left, right);
    }

    public void quickSort(AnyType[] a) {
        quickSort(a, 0, a.length - 1);
    }

    private void quickSelect(AnyType[] a, int left, int right, int k) {
        if (left + CUTOFF <= right) {
            AnyType pivot = median3(a, left, right);
            int i = left, j = right - 1;
            for (; ; ) {
                while (a[++i].compareTo(pivot) < 0) {
                }
                while (a[--j].compareTo(pivot) > 0) {
                }
                if (i < j)
                    swapReference(a, i, j);
                else
                    break;
            }
            swapReference(a, i, right - 1);
            if (k <= i)
                quickSelect(a, left, i - 1, k);
            else if (k > i + 1)
                quickSelect(a, i + 1, right, k - i - 1);
        } else
            insertionSort(a, left, right);
    }
    public void quickSelect(AnyType[] a,int k){
        quickSelect(a,0,a.length-1,k);
    }
    public static void radixSortA(String [] arr,int stringLen){
        final int BUCKETS=256;
        ArrayList<String>[] buckets=new ArrayList[BUCKETS];
        for(int i=0;i<BUCKETS;i++)
            buckets[i]=new ArrayList<>();
        for(int pos=stringLen-1;pos>=0;pos--){
            for(String s:arr)
                buckets[s.charAt(pos)].add(s);
            int idx=0;
            for(ArrayList<String> thisBucket:buckets){
                for(String s:thisBucket)
                    arr[idx++]=s;
                thisBucket.clear();
            }
        }
    }
    public static void countingRadixSort(String[] arr,int stringLen){
        final int BUCKETS=256;
        int N=arr.length;
        String[] buffer=new String[N];
        String[] in=arr;
        String[] out=buffer;
        for(int pos=stringLen-1;pos>=0;pos--){
            int[] count=new int[BUCKETS+1];
            for(int i=0;i<N;i++)
                count[in[i].charAt(pos)+1]++;
            for(int b=1;b<=BUCKETS;b++)
                count[b]+=count[b-1];
            for(int i=0;i<N;i++)
                out[count[in[i].charAt(pos)]++]=in[i];
            String[] tmp=in;
            in=out;
            out=tmp;
        }
        if(stringLen%2==1)
            for(int i=0;i<arr.length;i++)
                out[i]=in[i];
    }
    public static void radixSort(String[] arr,int maxLen){
        final int BUCKETS=256;
        ArrayList<String>[] wordsByLength=new ArrayList[maxLen+1];
        ArrayList<String>[] buckets=new ArrayList[BUCKETS];
        for(int i=0;i<wordsByLength.length;i++)
            wordsByLength[i]=new ArrayList<String>();
        for(int i=0;i<BUCKETS;i++)
            buckets[i]=new ArrayList<>();
        for(String s:arr)
            wordsByLength[s.length()].add(s);
        int idx=0;
        for(ArrayList<String> wordList:wordsByLength)
            for(String s:wordList)
                arr[idx++]=s;
        int startingIndex=arr.length;
        for(int pos=maxLen-1;pos>=0;pos--){
            startingIndex-=wordsByLength[pos+1].size();
            for(int i=startingIndex;i<arr.length;i++)
                buckets[arr[i].charAt(pos)].add(arr[i]);
            idx=startingIndex;
            for(ArrayList<String> thisBucket:buckets){
                for(String s:thisBucket)
                    arr[idx++]=s;
                thisBucket.clear();
            }
        }
    }
}
