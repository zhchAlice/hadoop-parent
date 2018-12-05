package com.wr.jvm.code.design.DP;

import scala.Int;

import java.util.*;

/**
 * @author: Alice
 * @date: 2018/8/20.
 * @since: 1.0.0
 */
public class Permutations {
    private static List<List<Integer>> result = new ArrayList<>();
    private static int[] map = new int[4];
    public static List<List<Integer>> permuteUnique(int[] nums) {
        if(nums == null || nums.length==0){
            return result;
        }
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        permuteUnique(nums,0,nums.length-1);
        for (List<Integer> li :result){
            int temp = li.get(0);
            switch (temp){
                case -1:
                    map[0]++;
                    break;
                case 0:
                    map[1]++;
                    break;
                case 1:
                    map[2]++;
                    break;
                case 2:
                    map[3]++;
                    break;
            }
        }
        for (int i=0;i<4;i++){
            System.out.println(map[i]);
        }
        return result;
    }

    private static void permuteUnique(int[] nums, int start, int end){
        if(start == end){
            List<Integer> temp = new ArrayList<>();
            for(int num:nums){
                temp.add(num);
            }
            result.add(temp);
        } else{
            int i=start;
            while(i<=end){
                int j = i - 1;
                while (j >= start && nums[j] != nums[i]) j--;
                if (start == i || j == start - 1) {// if i == idx or nums[i] does not equal to any vals in nums[start, ... i - 1]
                    swap(nums,start,i);
                    permuteUnique(nums,start+1,end);
                    swap(nums,start,i);

                }
                i++;
            }
        }
    }

    private static void swap(int[] nums, int start, int end){
        if(start == end){
            return;
        }
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
    }

    private int count = 0;
    private Vector<Character> result1 = null;
    public String getPermutation(int n, int k) {
        char[] nums = new char[n];
        for(int i=0;i<n;i++){
            nums[i]=(char)('1'+i);
        }
        int[] visited = new int[n];
        DFS(nums,visited,k,0);
        return String.valueOf(nums);
    }
    private void DFS(char[] nums,int[] visited, int k, int start){
        if(start == nums.length){
            count++;
        }
        for(int i=start;i<nums.length;i++){
            swap(nums,start,i);
            DFS(nums,visited,k,start+1);
            if(count == k){
                break;
            }
            swap(nums,start,i);
        }
    }

    private void swap(char[] nums, int i, int j){
        char temp = nums[i];
        nums[i]= nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {1,0,-1,0,-1,1,2};

        int[][] nums2 = {{1,2},{3,4}};
        //System.out.println(nums2.length);
        //permuteUnique(nums);
    }
}
