package com.wr.jvm.code.inter.algorithm.string;

/**
 * @author: Alice
 * @date: 2018/12/6.
 * @since: 1.0.0
 * @desc: 回文相关
 */
public class Palindrome {
    /**
     * 验证是否是有效的回文字符串
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s){
        s = s.toLowerCase();
        int lo = 0;
        int hi = s.length()-1;
        while (lo<hi){
            while (lo<hi && !Character.isLetterOrDigit(s.charAt(lo)))
                lo++;
            while (lo<hi && !Character.isLetterOrDigit(s.charAt(hi)))
                hi--;
            if (s.charAt(lo)!=s.charAt(hi)){
                return false;
            }
            lo++;hi--;
        }
        return true;
    }

    public static boolean isPalindrome2(String s){
        char[] chArray = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char ch : chArray){
            if (Character.isLetterOrDigit(ch)){
                sb.append(ch);
            }
        }
        String newStr = sb.toString().toLowerCase();
        String reverseStr = sb.reverse().toString().toLowerCase();
        return newStr.equals(reverseStr);
    }


    /**
     * 最长回文子串--（动态规划）
     * dp[i][j]表示字符串i到j范围内是否是回文字符串
     * dp[i][j]=s[i]==s[j]&&dp[i+1][j-1]
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        int maxlen=1,lo=0,hi=0;
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int i=0;i<len;i++){
            dp[i][i]=true;
            for (int j=0;j<i;j++){
                dp[j][i] = s.charAt(i)==s.charAt(j) &&(i-j==1 || dp[j+1][i-1]);
                if (dp[j][i] && maxlen < i-j+1){
                    maxlen = i-j+1;
                    lo=j;hi=i;
                }
            }
        }
        return s.substring(lo,hi+1);
    }


    public static void main(String[] args) {
        //String s = "A man, a plan, a canal: Panama";
        //System.out.println(isPalindrome(s));
        //System.out.println(isPalindrome2(s));

        String s = "aaaa";
        System.out.println(longestPalindrome(s));
    }
}
