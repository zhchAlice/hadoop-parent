package com.wr.jvm.code.inter.algorithm.sort;

/**
 * @author: Alice
 * @date: 2018/12/6.
 * @since: 1.0.0
 */
public class MergeSort {
    public static void sort(int[] nums, int lo, int hi){
        if (lo>=hi){
            return;
        }
        int mid = (lo+hi)>>1;
        sort(nums,lo,mid);
        sort(nums,mid+1,hi);
        merge(nums,lo,mid,hi);
    }

    private static void merge(int[] nums, int lo, int mid, int hi) {
        int left = lo;
        int right = mid+1;
        int[] tmp = new int[hi-lo+1];
        int index = 0;
        while (left<=mid && right<=hi){
            if (nums[left]<=nums[right]){
                tmp[index++] = nums[left++];
            }else {
                tmp[index++] = nums[right++];
            }
        }
        while (left<=mid){
            tmp[index++]=nums[left++];
        }
        while (right<=hi){
            tmp[index++]=nums[right++];
        }
        for (int i=lo;i<=hi;i++){
            nums[i]=tmp[i-lo];
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,3,2,4,10,6,8};
        sort(nums,0,nums.length-1);
        for (int num : nums) {
            System.out.println(num);
        }
    }
}
