package com.wr.jvm.code.design.test.structure;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: Alice
 * @date: 2018/8/1.
 * @since: 1.0.0
 */
public class Structure {

    private static void quicksort(int a[], int low, int high){
        if (low < high) {
            int index = partition(a, low, high);
            quicksort(a,low,index-1);
            quicksort(a, index+1, high);
        }
    }

    private static int partition(int[] a, int low, int high) {
        int pivot = a[low];
        while (low < high) {
            while (low<high && a[high]>=pivot) {
                high--;
            }
            a[low] = a[high];
            while (low<high && a[low]<= pivot){
                low++;
            }
            a[high] = a[low];
        }
        a[low] = pivot;
        return low;
    }

    private static void mergesort(int a[], int low, int high) {
        int mid = (low+high)>>1;
        if (low < high) {
            mergesort(a, low, mid);
            mergesort(a,mid+1,high);
            merge(a,low,mid,high);
        }
    }

    private static void merge(int[] a, int low, int mid, int high) {
        int[] temp = new int[high-low+1];
        int i=low;
        int j=mid+1;
        int k=0;
        while (i<=mid && j<=high) {
            if (a[i]<a[j]){
                temp[k++] = a[i++];
            } else{
                temp[k++] = a[j++];
            }
        }
        while (i<=mid) {
            temp[k++] = a[i++];
        }
        while (j<=high){
            temp[k++] = a[j++];
        }
        for (int k2=0; k2<temp.length; k2++) {
            a[low+k2] = temp[k2];
        }

    }

    public static void main(String[] args) {
        int[] p = { 34, 21, 54, 18, 23, 76, 38, 98, 45, 33, 27, 51, 11, 20, 79,
                30, 89, 41 };
        //quicksort(p,0,p.length-1);
        mergesort(p,0,p.length-1);
        for (int i = 0; i < p.length; i++) {
            System.out.print(p[i] + " ");
        }

    }
}
