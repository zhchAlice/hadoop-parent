package com.wr.jvm.code.inter.algorithm.search;

/**
 * @author: Alice
 * @date: 2018/12/6.
 * @since: 1.0.0
 */
public class BinarySearch {
    public static int search(int[] nums, int lo, int hi,int target){
        if (lo>hi || target<nums[lo] || target>nums[hi]){
            return -1;
        }
        while (lo<=hi){
            int mid = lo+(hi-lo>>1);
            if (target == nums[mid]){
                return mid;
            }else if (target<nums[mid]){
                hi=mid-1;
            } else {
                lo=mid+1;
            }
        }
        return -1;
    }

    public static int searchInsert(int[] nums, int lo, int hi, int target){
        while(lo<=hi){
            int mid = lo+(hi-lo>>1);
            if (target == nums[mid]){
                return mid;
            }else if (target<nums[mid]){
                hi=mid-1;
            }else {
                lo=mid+1;
            }
        }
        return lo;
    }

    public static boolean searchMatrix(int[][] matrix, int target){
        if (matrix==null || matrix.length==0 || matrix[0].length==0){
            return false;
        }
        int m = matrix.length;
        int n = matrix[0].length;
        int lo = 0,hi=m*n-1;
        while(lo<=hi){
            int mid = lo+(hi-lo>>1);
            int value = matrix[mid/n][mid%n];
            if (target == value){
                return true;
            }else if (target<value){
                hi=mid-1;
            }else {
                lo=mid+1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] nums = {1,3,5,6};
        //System.out.println(search(nums,0,nums.length-1,5));
        System.out.println(searchInsert(nums,0,nums.length-1,7));
    }
}
