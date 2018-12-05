package com.wr.jvm.code.design.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Alice
 * @date: 2018/10/9.
 * @since: 1.0.0
 */
public class TraversalTree {

    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result=new ArrayList<Integer>();
        TreeNode dump = new TreeNode(0);
        dump.left=root;
        TreeNode cur=dump;
        while(null!=cur){
            if(cur.left==null){
                cur=cur.right;
            }else{
                TreeNode pre = cur.left;
                while(pre.right!=null && pre.right!=cur){
                    pre =pre.right;
                }
                if(pre.right==null){
                    pre.right=cur;
                    cur=cur.left;
                }else{
                    printReverse(cur.left,pre,result);
                    pre.right=null;
                    cur=cur.right;
                }
            }
        }
        return result;
    }

    private static void printReverse(TreeNode from,TreeNode to, List<Integer> result){
        reverse(from,to);
        TreeNode tmp = to;
        result.add(tmp.val);
        while(tmp!= from){
            tmp=tmp.right;
            result.add(tmp.val);
        }
        reverse(to,from);
    }

    private static void reverse(TreeNode from,TreeNode to){
        TreeNode x = from;
        TreeNode y = from.right;
        while(x!=to){
            TreeNode z = y.right;
            y.right=x;
            x=y;
            y=z;
        }
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        TreeNode node6 = new TreeNode(6);
        TreeNode node7 = new TreeNode(7);
        TreeNode node8 = new TreeNode(8);
        TreeNode node9 = new TreeNode(9);
        node9.left=node5;
        node9.right=node8;
        node5.left=node1;
        node5.right=node4;
        node4.left=node2;
        node4.right=node3;
        node8.right=node7;
        node7.left=node6;
        /*node1.right = node2;
        node2.left = node3;*/
        List<Integer> result = postorderTraversal(node9);
        System.out.println(result);
    }
}
