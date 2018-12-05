package com.wr.jvm.code.design.DP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: Alice
 * @date: 2018/8/14.
 * @since: 1.0.0
 */
public class Solution {

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        if (len1 == 0) {
            return getMedianOfOneArray(nums1, 0, len1 - 1);
        }
        if (len2 == 0) {
            return getMedianOfOneArray(nums2, 0, len2 - 1);
        }
        return getMedianOfTwoArray(nums1, 0, len1 - 1, nums2, 0, len2 - 1);
    }

    private static double getMedianOfOneArray(int[] nums1, int begin, int end) {
        int mid = (begin + end) >> 1;
        if ((begin + end) % 2 == 1) {
            return nums1[mid];
        } else {
            return (nums1[mid] + nums1[mid + 1]) / 2.0;
        }
    }

    private static double getMedianOfTwoArray(int[] nums1, int lo1, int hi1, int[] nums2, int lo2, int hi2) {
        int L1 = nums1[(lo1 + hi1) / 2];
        int R1 = nums1[(lo1 + hi1 + 1) / 2];
        int L2 = nums2[(lo2 + hi2) / 2];
        int R2 = nums2[(lo2 + hi2 + 1) / 2];
        if (L1 > R2) {
            return getMedianOfTwoArray(nums1, lo1, (lo1 + hi1) / 2, nums2, lo2, hi2);
        } else if (L2 < R1) {
            return getMedianOfTwoArray(nums1, (lo1 + hi1) / 2, hi1, nums2, lo2, hi2);
        }
        return (Math.max(L1, L2) + Math.min(R1, R2)) / 2.0;
    }

    private static double getMedian(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        if (n > m)   //保证数组1一定最短
            return findMedianSortedArrays(nums2, nums1);
        int L1 = 0, L2 = 0, R1 = 0, R2 = 0, c1, c2, lo = 0, hi = 2 * n;  //我们目前是虚拟加了'#'所以数组1是2*n长度
        while (lo <= hi)   //二分
        {
            c1 = (lo + hi) / 2;  //c1是二分的结果
            c2 = m + n - c1;
            L1 = (c1 == 0) ? Integer.MIN_VALUE : nums1[(c1 - 1) / 2];   //map to original element
            R1 = (c1 == 2 * n) ? Integer.MAX_VALUE : nums1[c1 / 2];
            L2 = (c2 == 0) ? Integer.MIN_VALUE : nums2[(c2 - 1) / 2];
            R2 = (c2 == 2 * m) ? Integer.MAX_VALUE : nums2[c2 / 2];

            if (L1 > R2)
                hi = c1 - 1;
            else if (L2 > R1)
                lo = c1 + 1;
            else
                break;
        }
        return (Math.max(L1, L2) + Math.min(R1, R2)) / 2.0;
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int len = nums.length;
        List<List<Integer>> fourSums = new ArrayList<>();
        int i = 0;
        while (i < len) {
            List<List<Integer>> threeSums = threeSum(nums, i + 1, target - nums[i]);
            if (threeSums.size() > 0) {
                for (List<Integer> tempSum : threeSums) {
                    tempSum.add(0, nums[i]);
                    fourSums.add(tempSum);
                }
                while (i < len - 1 && nums[i + 1] == nums[i]) {
                    i++;
                }
            }
            i++;

        }
        return fourSums;
    }

    public static List<List<Integer>> threeSum(int[] nums, int begin, int target) {
        int len = nums.length;
        List<List<Integer>> threeSums = new ArrayList<>();
        int i = begin;
        while (i < len) {
            List<List<Integer>> twoSums = twoSum(nums, i + 1, target - nums[i]);
            if (twoSums.size() > 0) {
                for (List<Integer> tempSum : twoSums) {
                    tempSum.add(0, nums[i]);
                    threeSums.add(tempSum);

                }
                while (i < len - 1 && nums[i + 1] == nums[i]) {
                    i++;
                }
            }
            i++;
        }
        return threeSums;
    }

    private static List<List<Integer>> twoSum(int[] nums, int begin, int target) {
        int lo = begin;
        int hi = nums.length - 1;
        List<List<Integer>> twoSums = new ArrayList<>();
        while (lo < hi) {
            if (nums[lo] + nums[hi] == target) {
                List<Integer> temp = new ArrayList<>();
                temp.add(nums[lo]);
                temp.add(nums[hi]);
                twoSums.add(temp);
                while (lo + 1 < hi && nums[lo] == nums[lo + 1]) {
                    lo++;
                }
                while (hi - 1 > lo && nums[hi] == nums[hi - 1]) {
                    hi--;
                }
                lo++;
                hi--;
            } else if (nums[lo] + nums[hi] < target) {
                lo++;
            } else if (nums[lo] + nums[hi] > target) {
                hi--;
            }
        }
        return twoSums;
    }


    public static boolean isValid(String s) {
        int len = s.length();
        char[] temp = new char[len];
        int index = 0;
        char current = s.charAt(0);
        if (current == ')' || current == '}' || current == ']') {
            return false;
        }
        temp[index++] = current;
        for (int i = 1; i < len; i++) {
            current = s.charAt(i);
            if (current == '(' || current == '{' || current == '[') {
                temp[index++] = current;
            } else if (index < 1) {
                return false;
            } else if (current == ')') {
                if (temp[index - 1] == '(') {
                    index--;
                    continue;
                }
                return false;
            } else if (current == '}') {
                if (temp[index - 1] == '{') {
                    index--;
                    continue;
                }
                return false;
            } else {
                if (temp[index - 1] == '[') {
                    index--;
                    continue;
                }
                return false;
            }
        }
        return index == 0;
    }

    public static int removeElement(int[] nums, int val) {
        int len = nums.length;
        int result = 0;
        int i=1;
        while(i<=len && nums[i-1]==val){
            i++;
        }
        if(i>len){
            return result;
        }
        nums[result++]=nums[i-1];
        while(i<len){
            while (i<len && nums[i]==val){
                i++;
            }
            if (i<len) {
                nums[result++] = nums[i++];
            }
        }
        return result;
    }

    public static int strStr(String haystack, String needle) {
        haystack.indexOf("ll");
        if(needle.isEmpty()){
            return 0;
        }
        int len1 = haystack.length();
        int len2 = needle.length();
        int i=0;
        int curIndex=0;
        while(i<=len1-len2){
            while(curIndex<len2 && haystack.charAt(i+curIndex)==needle.charAt(curIndex)){
                curIndex++;
            }
            if(curIndex == len2){
                return i;
            }
            i++;
            curIndex=0;
        }
        return -1;
    }

    public static int divide(int dividend, int divisor) {
        boolean positive = true;
        if((dividend>0 &&divisor<0) ||(dividend<0 &&divisor>0)){
            positive = false;
        }
        long dividend_l = (long) dividend;
        long divisor_l = (long)divisor;
        dividend_l = Math.abs(dividend_l);
        divisor_l = Math.abs(divisor_l);
        long result = 0;
        while(dividend_l>=divisor_l){
            long divisor_t = divisor_l;
            long result_t = 1;
            while(dividend_l > (divisor_t<<1)){
                result_t=result_t<<1;
                divisor_t=divisor_t<<1;
            }
            result+=result_t;
            dividend_l -= divisor_t;
        }
        result = positive?result:-result;
        if(result>Integer.MAX_VALUE || result<Integer.MIN_VALUE){
            return Integer.MAX_VALUE;
        }
        return (int)result;
    }


    public static void main(String[] args) {
        //System.out.println(strStr("hello","ll"));
        System.out.println(divide(2147483647,2));
        //System.out.println(isValid("[])"));
        /*int[] num1 = {3};
        int[] num2 = {-2,-1};
        System.out.println(getMedian(num1,num2));*/

       /* int[] nums = {-1,0,1,2,-1,-4};
        int[] nums2 = {-1,-5,-5,-3,2,5,0,4};
        int[] nums3 = {-3,-2,-1,0,0,1,2,3};
        int[] nums4 = {0,4,-5,2,-2,4,2,-1,4};
        System.out.println(fourSum(nums,-1));
       System.out.println(fourSum(nums2,-7));
        System.out.println(fourSum(nums3,0));
        System.out.println(fourSum(nums4,12));*/
    }
}
