package com.wr.jvm.code.design.DP;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: Alice
 * @date: 2018/9/5.
 * @since: 1.0.0
 */
public class LargestRectangle {

    /**思路：*/
    public static int largestRectangleArea(int[] heights) {
        Stack<Integer> s = new Stack<>();
        int i=0;
        int len = heights.length;
        int maxArea = 0;
        while (i<len){
            while (s.isEmpty() || (i < len) && (heights[i] > heights[s.peek()])) {
                s.push(i++);
            }
            while (i<len && !s.isEmpty() && heights[i] <= heights[s.peek()]){
                int index = s.pop();
                maxArea = Math.max(maxArea,heights[index]*(s.isEmpty()?i:i-s.peek()-1));
            }
        }
        while (!s.isEmpty()){
            int index = s.pop();
            maxArea = Math.max(maxArea,heights[index]*(s.isEmpty()?i:i-s.peek()-1));
        }
        return maxArea;
    }

    public static int maximalRectangle(char[][] matrix) {
        int m = matrix.length;
        int n = m==0?0:matrix[0].length;
        int[] height = new int[n+1];
        int result=0;
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(matrix[i][j]==0){
                    height[j]=0;
                }else{
                    height[j]=i==0?1:height[j]+1;
                }
            }
            result = Math.max(result,maxArea(height,n+1));
        }
        return result;
    }

    private static int maxArea(int[] height, int len){
        Stack<Integer> s = new Stack<>();
        int i=0;
        int result = 0;
        while(i<len){
            if(s.isEmpty() || height[i]>=height[s.peek()]){
                s.push(i++);
            }else{
                int index = s.pop();
                result = Math.max(result, height[index]*(s.isEmpty()?i:i-s.peek()-1));
            }
        }
        return result;
    }

    public static List<Integer> grayCode(int n) {
        int len = (int)Math.pow(2,n);
        List<Integer> result = new ArrayList<>();
        for (int i=0;i<len;i++){
            result.add(i^(i>>1));
        }
        return result;
    }


    public static void main(String[] args) {
        int[] heights={2,1,5,6,2,3,0};
        int[][] matrix = {{1,0,1,0,0},{1,0,1,1,1},{1,1,1,1,1},{1,0,0,1,0}};
        //System.out.println(largestRectangleArea(heights));
        //int len = (int) Math.pow(3,2);
        //System.out.println(Math.pow(3,2));
        grayCode(0);
        //System.out.println(getMaxArea(heights,heights.length));
    }
}
