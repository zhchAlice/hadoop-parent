package com.wr.jvm.code.inter.algorithm.sort;

/**
 * @author: Alice
 * @date: 2018/12/6.
 * @since: 1.0.0
 */
public class BuddleSort {
    public static void sort(int[] nums){
        int len = nums.length;
        for (int i=0;i<len-1;i++){
            for (int j=0;j<len-1-i;j++){
                if (nums[j]>nums[j+1]){
                    int tmp = nums[j];
                    nums[j]=nums[j+1];
                    nums[j+1]=tmp;
                }
            }
        }
    }

    public static void main(String[] args){
        int[] nums = {10,9};
        sort(nums);
        for (int num : nums) {
            System.out.println(num);
        }
    }
}
