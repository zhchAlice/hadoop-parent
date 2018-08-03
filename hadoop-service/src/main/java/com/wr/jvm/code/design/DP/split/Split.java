package com.wr.jvm.code.design.DP.split;

/**
 * @author: Alice
 * @date: 2018/7/31.
 * @since: 1.0.0
 */
public class Split {

    public boolean canSplit(int[] array, int length){
        int sum =0;
        for (int i=0; i<length; i++){
            sum += array[i];
        }
        if (sum % 4 != 0) {
            return false;
        }
        int firstSplitIndex = internalSplit(array,sum,0,length-1);
        if (firstSplitIndex == -1) {
            return false;
        }
        int halfSum = sum >> 1;
        int secondSplitIndex = internalSplit(array, halfSum,0,firstSplitIndex);
        int thirdSplitIndex = internalSplit(array, halfSum, firstSplitIndex+1,length-1);
        return secondSplitIndex!=-1 && thirdSplitIndex!=-1;

    }
    private int internalSplit(int[] array, int sum, int begin, int end) {
        int half = sum >> 1;
        int i = begin;
        int j = end;
        int leftSum = 0;
        int rightSum = 0;
        while (i<=j){
            if (leftSum > half || rightSum > half) {
                return -1;
            }
            if (leftSum < half){
                leftSum += array[i++];
            }
            if (rightSum < half) {
                rightSum += array[j--];
            }
        }
        if (leftSum != half || rightSum != half || i!=j+1) {
            return -1;
        }
        return i-1;
    }

    public static void main(String[] args) {
        Split split = new Split();
        int[] array = {2, 3, 4, 0, 1, 1, 1, 1, 2};
        boolean result = split.canSplit(array,array.length);
        System.out.println(result);
    }
}
