package com.wr.jvm.code.inter.algorithm.string;

import org.apache.spark.sql.sources.In;

/**
 * @author: Alice
 * @date: 2018/12/7.
 * @since: 1.0.0
 * @desc: implement of strstr(leetcode 28)
 */
public class FoundationStr {
    /**
     * 类似于String.indexof方法
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }
        char[] hay = haystack.toCharArray();
        char[] nee = needle.toCharArray();
        int len1 = hay.length;
        int len2 = nee.length;
        if (len2 > len1) {
            return -1;
        }
        int i = 0;
        while (i <= len1 - len2) {
            int j = 0;
            while (j < len2 && hay[i + j] == nee[j]) {
                j++;
            }
            if (j == len2) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * 字符串转变成整数
     * @param str
     * @return
     */
    public static int myAtoi(String str) {
        char[] value = str.toCharArray();
        int len = str.length();
        int i = 0;
        while (i < len && value[i] == ' ')
            i++;
        boolean negativeFlag = false;
        if (i == len) {
            return 0;
        }
        if (value[i] == '-') {
            negativeFlag = true;
            i++;
        } else if (value[i] == '+') {
            i++;
        }
        int sum = 0;
        while (i < len && value[i] >= '0' && value[i] <= '9') {
            if (sum > Integer.MAX_VALUE / 10 || (sum == Integer.MAX_VALUE / 10 && value[i] > '7')) {
                return negativeFlag ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
            sum = sum * 10 + (value[i++]-'0');
        }
        return negativeFlag ? -1 * sum : sum;
    }

    /**
     * 二进制加法
     */
    public static String addBinary(String a, String b) {
        char[] aArr = a.toCharArray();
        char[] bArr = b.toCharArray();
        int alen = aArr.length,blen=bArr.length;
        int i=alen-1,j=blen-1;
        int cover=0;
        char[] result = new char[Math.max(alen,blen)+1];
        int index=result.length-1;
        while (i>=0 || j>=0){
            int av = i>=0?aArr[i--]-'0':0;
            int bv = j>=0?bArr[j--]-'0':0;
            int tmp = (av+bv+cover)%2;
            cover = av+bv+cover>>1;
            result[index--]= (char) ('0'+tmp);
        }
        if (cover==1){
            result[index--] = '1';
        }
        return String.valueOf(result,index+1,result.length-(index+1));
    }

    /**
     * 字符串匹配：动态规划，DP[i][j]表示s[0-i)和p[0-j)是否匹配
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch(String s, String p) {
        if (p.isEmpty()){
            return s.isEmpty();
        }
        char[] sArr = s.toCharArray();
        char[] pArr = p.toCharArray();
        int len1 = s.length();
        int len2 = p.length();
        boolean[][] dp = new boolean[len1+1][len2+1];
        dp[0][0]=true;
        for (int j=2;j<=len2;j++){
            dp[0][j] = pArr[j-1]=='*'&&dp[0][j-2];
        }
        for (int j=1;j<=len2;j++){
            for (int i=1; i<=len1;i++){
                if (pArr[j-1]=='.' || pArr[j-1]==sArr[i-1]){
                    dp[i][j]=dp[i-1][j-1];
                } else if (pArr[j-1]=='*'){
                    dp[i][j] = dp[i][j-2] ||(dp[i-1][j] && (pArr[j-2]=='.' || sArr[i-1]==pArr[j-2]));
                }
            }
        }
        return dp[len1][len2];
    }

    /**
     * 最长公共前缀
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length==0){
            return "";
        }
        int len = strs[0].length();
        for (int i=0;i<len;i++){
            for (int j=1;j<strs.length;j++){
                if (strs[j].length()<=i || strs[j].charAt(i) != strs[0].charAt(i)){
                    return strs[0].substring(0,i);
                }
            }
        }
        return strs[0];
    }

    /**
     * 转化成罗马字符
     * @param num
     * @return
     */
    public static String intToRoman(int num) {
        int[] numValue = {1,4,5,9,10,40,50,90,100,400,500,900,1000};
        String[] strValue = {"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};
        StringBuilder sb = new StringBuilder();
        for (int i=numValue.length-1;i>=0;i--){
            int time = num/numValue[i];
            while (time-->0){
                sb.append(strValue[i]);
            }
            num = num % numValue[i];
        }
        return sb.toString();
    }



    public static void main(String[] args) {
        /*String haystack = "mississippi", needle = "issip";
        System.out.println(strStr(haystack,needle));*/
        //System.out.println(myAtoi("   -127"));
        //System.out.println(addBinary("001",""));
        //System.out.println(isMatch("aab",".*a*b"));
        //System.out.println(longestCommonPrefix(new String[]{"aa","a"}));
        System.out.println(intToRoman(3));
    }

}
