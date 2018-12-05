package com.wr.jvm.code.design.search;

/**
 * @author: Alice
 * @date: 2018/8/31.
 * @since: 1.0.0
 */
public class BinarySearch {
    public static boolean search(int[] nums, int target) {
        if(nums==null || nums.length==0){
            return false;
        }
        return search(nums, 0, nums.length-1, target);
    }


    private static boolean search(int[] nums, int lo, int hi, int target){
        if(lo>hi){
            return false;
        }
        int mid = lo+(hi-lo>>1);
        if(nums[mid]== target){
            return true;
        }else if(target<nums[mid]){
            if (nums[mid]<nums[hi]){//右边有序，且target<nums[mid]<nums[hi]
                return search(nums,lo,mid-1,target);
            }else if (nums[mid]==nums[hi]){
                return search(nums,lo,hi-1,target);
            } else if (target>=nums[lo]){
                return search(nums,lo,mid-1,target);
            }else{
                return search(nums,mid+1,hi,target);
            }
        } else{
            if (nums[mid] > nums[lo]){ //左边有序，并且nums[lo]<nums[mid]<target
                return search(nums,mid+1,hi,target);
            } else if (nums[mid] == nums[lo]){
                return search(nums,lo+1,hi,target);
            } else if (nums[hi]>=target){//右边有序，且nums[hi]>=target
                return search(nums,mid+1,hi,target);
            }else {
                return search(nums,lo,mid-1,target);
            }
        }
    }

    public static void main(String[] args) {
        int[] nums={1,1};
        System.out.println(search(nums,3));
    }
}
