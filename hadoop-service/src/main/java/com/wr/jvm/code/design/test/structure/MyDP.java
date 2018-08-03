package com.wr.jvm.code.design.test.structure;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: Alice
 * @date: 2018/8/2.
 * @since: 1.0.0
 */
public class MyDP {
    /**最短编辑距离*/
    private static int leastEditLength(String str1, String str2){
        int aLen = str1.length();
        int blen = str2.length();
        int[][] matrix = new int[aLen+1][blen+1];
        for (int i=0;i<=aLen;i++){
            for (int j=0;j<=blen;j++){
                if (0==i){
                    matrix[0][j] = j;
                } else if (0 == j){
                    matrix[i][0] = i;
                } else {
                    int flag = str1.charAt(i-1)== str2.charAt(j-1)?0:1;
                    int tempMin = Math.min(matrix[i-1][j]+1,matrix[i][j-1]+1);
                    matrix[i][j] = Math.min(tempMin,matrix[i-1][j-1]+flag);
                }
            }
        }
        return matrix[aLen][blen];
    }

    /**最大连乘积*/
    private static int maxKProduct(char[] num, int k){
        int len = num.length;
        int[][] matrix = new int[len][k];  //matrix[i][j]表示前i个元素，拆分成j+1项的最大连乘积
        for (int i=0; i<len; i++){
            for (int j=0; j<k; j++){
                if (j==0) {
                    matrix[i][j] = getNumValue(num,0,i);
                } else {
                    int maxValue = 0;
                    for (int z = 0; z < i; z++) {
                        int temp = matrix[z][j - 1] * getNumValue(num, z + 1, i);
                        if (maxValue < temp) {
                            maxValue = temp;
                        }
                    }
                    matrix[i][j] = maxValue;
                }
            }
        }
        return matrix[len-1][k-1];
    }

    /**最大连乘积 -- 获取子字符串对应的整数值*/
    private static int getNumValue(char[] num, int begin, int end){
        int value = 0;
        while (end>=begin){
            value = value *10 + (num[begin]-'0');
            begin++;
        }
        return value;
    }


    /**最长公共子序列（可以不连续）*/
    private static int lcs(char[] str1, char[] str2){
        int alen = str1.length;
        int blen = str2.length;
        int[][] matrix = new int[alen+1][blen+1];  //matrix[i][j]表示str1的前i个位置和str2的前j个位置的最长公共子序列
        for (int i=0; i<=alen; i++){
            for (int j=0; j<=blen; j++){
                if (i==0 || j==0){
                    matrix[i][j] = 0;
                } else if (str1[i-1] == str2[j-1]){
                    matrix[i][j] = matrix[i-1][j-1]+1;
                }else {
                    matrix[i][j] = Math.max(matrix[i-1][j],matrix[i][j-1]);
                }
            }
        }
        for (int i=0; i <= alen; i++) {
            for (int j=0; j <= blen; j++) {
                System.out.printf("%-5d", matrix[i][j]);
            }
            System.out.println();
        }
        int rlen = matrix[alen][blen];
        char[] result = new char[rlen];
        int i=0;
        if (alen<=blen){
            while (alen >1){
                if (matrix[alen][blen]==matrix[alen -1][blen]+1){
                    result[i++] = str1[alen -1];
                    if (i == rlen){
                        break;
                    }
                }
                alen--;
            }
        } else {
            while (blen >=1){
                if (matrix[alen][blen] == matrix[alen][blen-1]+1){
                    result[i++] = str2[blen -1];
                    if (i == rlen){
                        break;
                    }
                }
                blen--;
            }
        }
        System.out.println(StringUtils.reverse(new String(result)));
        return rlen;
    }

    /**最长递增子序列*/
    private static int LIS(int[] num){
        int len = num.length;
        int[] result = new int[len];  //result[i]表示到第i个位置，最长递增子序列
        for (int i=0; i<len; i++){
            int tempMax = 1;
            for (int j = 0;j<i;j++){
                if (num[j] < num[i]) {
                    tempMax = Math.max(tempMax,result[j]+1);
                } else {
                    tempMax = Math.max(tempMax, result[j]);
                }
            }
            result[i] = tempMax;
        }

        for (int j=0; j < len; j++) {
            System.out.printf("%-5d", result[j]);
        }
        return result[len-1];
    }

    public static void main(String[] args) {
        //System.out.println(leastEditLength("sitting","kitten"));;
        //System.out.println(maxKProduct("12345".toCharArray(),2));
        //System.out.println(lcs("abcde".toCharArray(), "bef".toCharArray()));
        int[] num = {10,22,9,33,21,50,41,60,80};
        System.out.println(LIS(num));
    }
}
