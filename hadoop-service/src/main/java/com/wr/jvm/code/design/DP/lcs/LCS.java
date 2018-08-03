package com.wr.jvm.code.design.DP.lcs;

/**
 * @author: Alice
 * @date: 2018/7/31.
 * @since: 1.0.0
 */
public class LCS {

    /**最长公共子序列（可以不连续）*/
    private static void computeLCS(String a, String b) {
        int alen = a.length();
        int blen = b.length();
        int[][] c = new int[alen+1][blen+1];
        for (int i=0; i<=alen; i++) {
            for (int j=0; j<=blen; j++){
                if (i==0 || j ==0) {
                    c[i][j] = 0;
                } else if (a.charAt(i-1) == b.charAt(j-1)) {
                    c[i][j] = c[i-1][j-1] +1;
                } else {
                    c[i][j] = Math.max(c[i-1][j],c[i][j-1]);
                }
            }
        }
        for (int i=0; i <= alen; i++) {
            for (int j=0; j <= blen; j++) {
                System.out.printf("%-5d", c[i][j]);
            }
            System.out.println();
        }
        int commonLength = c[alen][blen];
        StringBuilder sb = new StringBuilder();
        int tempLen = blen;
        for (int i=alen; i>0; i--) {
            if (c[i][tempLen]>c[i-1][tempLen]){
                sb.append(a.charAt(i-1));
                commonLength--;
            }
            if (commonLength == 0) {
                break;
            }
        }
        System.out.println("LCS:"+sb.reverse().toString());

    }

    /**最长公共子串（必须连续）*/
    private static void computeLCS2(String a, String b) {
        int alen = a.length();
        int blen = b.length();
        int result = 0;
        int resultEndIndex = 0;
        int[][] c = new int[alen+1][blen+1];
        for (int i=0; i<=alen; i++) {
            for (int j=0; j<=blen; j++){
                if (i==0 || j ==0) {
                    c[i][j] = 0;
                } else if (a.charAt(i-1) == b.charAt(j-1)) {
                    c[i][j] = c[i-1][j-1] +1;
                    if (c[i][j] > result) {
                        result = c[i][j];
                        resultEndIndex=i-1;
                    }
                } else {
                    c[i][j] = 0;
                }
            }
        }
        for (int i=0; i <= alen; i++) {
            for (int j=0; j <= blen; j++) {
                System.out.printf("%-5d", c[i][j]);
            }
            System.out.println();
        }
        System.out.println("LCS2:"+a.substring(resultEndIndex-result+1,result));
    }

    private static int computeLIS(int arr[]){
        int length = arr.length;
        int[] tail = new int[length];
        int index = 0;
        int size = 0;
        for (int i=0; i<length; i++) {
            index = binSearch(tail,i, arr[i]);
            tail[index]=arr[i];
            if (index==size){
                size++;
            }
        }
        return size;
    }

    private static int binSearch(int[] tail, int hi, int key) {
        int i=0;
        int j=hi;
        while (i != j){
            int mid = (i+j)>>1;
            if (tail[mid]<key){
                i=mid+1;
            }else {
                j=mid;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        /*LCS.computeLCS("abcd","abd");
        LCS.computeLCS2("abcd","abd");*/
        int[] arr = {3, 4, 7, 2, 5};
        System.out.println(computeLIS(arr));
    }
}
