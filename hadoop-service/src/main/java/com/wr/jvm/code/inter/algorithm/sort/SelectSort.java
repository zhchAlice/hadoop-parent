package com.wr.jvm.code.inter.algorithm.sort;

/**
 * @author: Alice
 * @date: 2018/12/6.
 * @since: 1.0.0
 */
public class SelectSort {
    public static void sort(int[] nums){
        int len = nums.length;
        for (int i=0;i<len-1;i++){
            int min_index = i;
            for (int j=i+1;j<len;j++){
                if (nums[j]<nums[min_index]){
                    min_index=j;
                }
            }
            if (min_index != i){
                int tmp = nums[min_index];
                nums[min_index]=nums[i];
                nums[i] = tmp;
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,3,2,4,10,6,8};
        sort(nums);
        for (int num : nums) {
            System.out.println(num);
        }
    }
}
