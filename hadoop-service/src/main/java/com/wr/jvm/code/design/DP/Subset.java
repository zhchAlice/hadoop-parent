package com.wr.jvm.code.design.DP;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Alice
 * @date: 2018/8/30.
 * @since: 1.0.0
 */
public class Subset {
    public static List<List<Integer>> subsets(int[] nums) {
        if (nums == null || nums.length == 0){
            return null;
        }
        List<List<Integer>> result = new ArrayList<>();
        int len = nums.length;
        for (int i=0;i<=len;i++){
            List<List<Integer>> tmp = DFS(nums,0,len,i);
            result.addAll(tmp);
        }
        return result;
    }

    private static List<List<Integer>> DFS(int[] nums, int start,int len,int remain) {
        if (remain == 0){
            List<List<Integer>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }else{
            List<List<Integer>> result = new ArrayList<>();
            for (int i=start;i<len;i++){
                List<List<Integer>> tmp = DFS(nums,i+1,len,remain-1);
                for (int j=0;j<tmp.size();j++){
                    tmp.get(j).add(0,nums[i]);
                    result.add(tmp.get(j));
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        subsets(nums);
    }
}
