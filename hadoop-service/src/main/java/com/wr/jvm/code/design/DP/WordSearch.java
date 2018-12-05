package com.wr.jvm.code.design.DP;

/**
 * @author: Alice
 * @date: 2018/8/30.
 * @since: 1.0.0
 */
public class WordSearch {
    private static boolean[][] visited;
    public static boolean exist(char[][] board, String word) {
        if(board == null ||board.length==0 ||board[0].length==0 || word==null || word.length()==0){
            return false;
        }
        int m = board.length;
        int n = board[0].length;
        int k = word.length();
        visited = new boolean[m][n];
        for(int i=0;i<m;i++){
            for(int j=0;j<n;j++){
                if(board[i][j]==word.charAt(0) && DFS(board,i,j,word,0)){
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean DFS(char[][] board, int i,int j, String word, int k){
        if(k == word.length()){
            return true;
        }
        if(i<0 || i>=board.length ||j<0 || j>=board[0].length ||board[i][j]!= word.charAt(k) || visited[i][j]){
            return false;
        }
        visited[i][j]=true;
        if(DFS(board,i-1,j,word,k+1)||DFS(board,i+1,j,word,k+1)||DFS(board,i,j-1,word,k+1)||DFS(board,i,j+1,word,k+1)){
            return true;
        }
        visited[i][j]=false;
        return false;
    }

    public static void main(String[] args) {

        /*char[][] board = {{'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}};*/
        char[][] board = {{'A'}};
        System.out.println(exist(board,"AB"));
    }
}
