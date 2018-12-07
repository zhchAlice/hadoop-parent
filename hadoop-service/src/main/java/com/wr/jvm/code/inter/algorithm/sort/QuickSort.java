package com.wr.jvm.code.inter.algorithm.sort;

/**
 * @author: Alice
 * @date: 2018/12/6.
 * @since: 1.0.0
 */
public class QuickSort {
    public static void sort(int[] nums, int lo, int hi){
        if (lo>hi){
            return;
        }
        int pos = findPartition(nums,lo,hi);
        sort(nums,lo,pos-1);
        sort(nums,pos+1,hi);
    }

    private static int findPartition(int[] nums, int lo, int hi) {
        int key = nums[lo];
        while (lo<hi){
            while (lo<hi && nums[hi]>=key){//从后往前扫描第一个比key小的元素
                hi--;
            }
            nums[lo] = nums[hi];
            while (lo<hi && nums[lo]<=key){
                lo++;
            }
            nums[hi]=nums[lo];
        }
        nums[lo] = key;
        return lo;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,2,4,10,6,8};
        sort(nums,0,nums.length-1);
        for (int num : nums) {
            System.out.println(num);
        }
    }
}
