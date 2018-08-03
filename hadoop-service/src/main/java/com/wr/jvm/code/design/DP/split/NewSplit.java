package com.wr.jvm.code.design.DP.split;

import java.util.HashMap;

/**
 * @author: Alice
 * @date: 2018/8/1.
 * @since: 1.0.0
 */
public class NewSplit {
    public static void main(String[] args){

//        int[] nums = {2,5,4,7,2,5,4,7,2,5,4,7,2,5,4,7,2,5,4,7,2,5,4,7};
//        int[] nums = {1,2,1,3,1,4,2};
        int[] nums = {2,5,1,1,1,1,4,1,7,3,7};
//        int[] nums = {10,2,11,13,1,1,5,1,1,1,10,2,11,12,5,1,1,1,1,1,10,2,11,10,5,1,1,1,1,34};
    }
    private static boolean canSplit(int[] nums){
        if(nums == null || nums.length < 7){
            return false;
        }
        HashMap<Long,Integer> indexMap = new HashMap<Long,Integer>();//<总和，位置>
        HashMap<Integer,Long> sumMap = new HashMap<Integer,Long>();//<位置，总和>

        long curSum = 0;
        for(int i = 0; i < nums.length; i++){
            indexMap.put(curSum,i);
            sumMap.put(i,curSum);
            curSum += nums[i];
        }
        long leftSum = nums[0];//最左段总和
        long rightSum = nums[nums.length - 1];//最右段总和
        int leftIndex = 1;//左分割点
        int rightIndex = nums.length - 2;//右分割点

        while(leftIndex + 3 < rightIndex){
            if(leftSum == rightSum){
                if(indexMap.get((leftSum << 1) + nums[leftIndex]) != null){
                    int middleIndex = indexMap.get((leftSum << 1) + nums[leftIndex]);//中间分割点
                    if(middleIndex > leftIndex + 1 && middleIndex < rightIndex - 1){
                        if(sumMap.get(rightIndex) - sumMap.get(middleIndex + 1) == leftSum){
                            return true;
                        }
                    }
                }
                leftSum += nums[leftIndex++];
                rightSum += nums[rightIndex--];
            }
            else{
                if(leftSum < rightSum){
                    leftSum += nums[leftIndex++];
                }
                else{
                    rightSum += nums[rightIndex--];
                }
            }
        }
        return false;
    }
}




