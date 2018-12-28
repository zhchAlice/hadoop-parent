package com.wr.jvm.code.design.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: Alice
 * @date: 2018/12/28.
 * @since: 1.0.0
 */
public class PostOrder {
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root==null){
            return result;
        }
        postorderTraversal(root,result);
        return result;
    }

    private static void postorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null){
            return;
        }
        postorderTraversal(root.left,result);
        postorderTraversal(root.right,result);
        result.add(root.val);
    }

    public static List<Integer> postorderTraversalWithStack(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root==null){
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.peek();
            if (node.left == null && node.right == null){
                stack.pop();
                result.add(node.val);
            } else {
                if (node.right != null){
                    stack.push(node.right);
                    node.right=null;
                }
                if (node.left != null){
                    stack.push(node.left);
                    node.left=null;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        node1.right=node2;
        node2.left=node3;
        List<Integer> result = postorderTraversalWithStack(node1);
        System.out.println(result);
    }
}
