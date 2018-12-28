package com.wr.jvm.code.design.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: Alice
 * @date: 2018/12/28.
 * @since: 1.0.0
 */
public class InOrder {
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root==null){
            return result;
        }
        inorderTraversal(root,result);
        return result;
    }

    private static void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node == null){
            return;
        }
        inorderTraversal(node.left,result);
        result.add(node.val);
        inorderTraversal(node.right,result);
    }

    public static List<Integer> inorderTraversalWithStack(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root==null){
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.peek();
            while (node.left != null){
                stack.push(node.left);
                TreeNode tmp = node.left;
                node.left = null;
                node = tmp;
            }
            result.add(node.val);
            stack.pop();
            if (node.right!=null){
                stack.push(node.right);
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
        List<Integer> result = inorderTraversalWithStack(node1);
        System.out.println(result);
    }
}
