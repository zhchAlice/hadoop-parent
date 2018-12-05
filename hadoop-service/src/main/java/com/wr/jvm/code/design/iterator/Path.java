package com.wr.jvm.code.design.iterator;

import java.util.*;

/**
 * @author: Alice
 * @date: 2018/8/29.
 * @since: 1.0.0
 */
public class Path {
    public static String simplifyPath(String path) {
        if(path == null || path.length()==0){
            return null;
        }
        Stack<String> paths = new Stack<>();
        String[] words = path.split("/");
        for(String word : words){
            if(word.equals("..")&& !paths.isEmpty()){
                paths.pop();
            }else if(!word.equals(".")&& !word.equals("")&& !word.equals("..")){
                paths.push(word);
            }
        }
        List<String> result = new ArrayList<>(paths);
        return "/"+String.join("/",result);
    }

    public static int minDistance(String word1, String word2) {
        int len1= word1.length();
        int len2 = word2.length();
        if(len1 == 0){
            return len2;
        }else if(len2==0){
            return len1;
        }
        int[][] DP= new int[len1+1][len2+1];
        DP[0][0]=0;
        for(int i=1;i<=len1;i++){
            DP[i][0]=1;
        }
        for(int j=1;j<=len2;j++){
            DP[0][j]=1;
        }
        for(int i=1;i<=len1;i++){
            for(int j=1;j<=len2;j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    DP[i][j]=DP[i-1][j-1];
                }else{
                    int tmp = Math.min(DP[i-1][j]+1,DP[i][j-1]+1);
                    DP[i][j]=Math.min(tmp,DP[i-1][j-1]+1);
                }
            }
        }
        return DP[len1][len2];
    }

    public static void main(String[] args) {
        System.out.println(minDistance("plasma","altruism"));
    }
}
