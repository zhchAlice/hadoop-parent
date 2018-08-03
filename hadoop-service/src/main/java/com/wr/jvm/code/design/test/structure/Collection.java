package com.wr.jvm.code.design.test.structure;

import com.wr.jvm.code.design.test.hotcache.HotObject;

import java.util.*;

/**
 * @author: Alice
 * @date: 2018/8/1.
 * @since: 1.0.0
 */
public class Collection {
    /**有序序列返回位置或者可以插入的位置*/
    private static int getLocation(int a[], int key) {
        int index = 0;
        for (int i=0; i<a.length; i++) {
            if (a[i] == key) {
                return i;
            }else if (a[i] > key){
                return index;
            }else {
                index=i+1;
            }
        }
        return index;
    }

    private static int getFirstIndex(String str1, String str2) {
        char[] source = str1.toCharArray();
        char[] target = str2.toCharArray();
        int iMax = source.length - target.length;
        if (iMax < 0){
            return -1;
        }
        for (int i=0; i<iMax; i++){
            while (source[i] != target[0] && i<iMax){
                i++;
            }
            int j = 0;
            int jmax = target.length;
            while (i<source.length && j<jmax && source[i]==target[j]){
                i++;
                j++;
            }
            if (j == jmax){
                return i-jmax;
            }
        }
        return -1;
    }

    private static void listSort(List<HotObject> objects) {
        objects.sort(new Comparator<HotObject>() {
            @Override
            public int compare(HotObject o1, HotObject o2) {
                return (int) (o1.getHot().get() - o2.getHot().get());
            }
        });
    }

    private static void mapSort(Map<String,HotObject> objects) {
        List<Map.Entry<String,HotObject>> objList = new ArrayList<>(objects.entrySet());
        objList.sort(new Comparator<Map.Entry<String, HotObject>>() {
            @Override
            public int compare(Map.Entry<String, HotObject> o1, Map.Entry<String, HotObject> o2) {
                return (int) (o1.getValue().getHot().get() - o2.getValue().getHot().get());
            }
        });
    }

    public static void main(String[] args) {
        System.out.println(getFirstIndex("abcd","cg"));
       /* int[] a = {1,3,5,6};
        System.out.println(getLocation(a,5));
        System.out.println(getLocation(a,2));
        System.out.println(getLocation(a,7));
        System.out.println(getLocation(a,0));*/
    }

}
