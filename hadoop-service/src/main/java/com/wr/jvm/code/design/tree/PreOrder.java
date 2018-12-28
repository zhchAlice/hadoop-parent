package com.wr.jvm.code.design.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author: Alice
 * @date: 2018/12/28.
 * @since: 1.0.0
 */
public class PreOrder {

    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root==null){
            return result;
        }
        preorderTraversal(root,result);
        return result;
    }

    private static void preorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null){
            return;
        }
        result.add(root.val);
        preorderTraversal(root.left,result);
        preorderTraversal(root.right,result);
    }

    public static List<Integer> preorderTraversalWithStack(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root==null){
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            TreeNode node = stack.pop();
            result.add(node.val);
            if (null != node.left)
                stack.push(node.left);
            if (null!=node.right)
                stack.push(node.right);
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        node3.left=node1;
        node3.right=node2;
        List<Integer> result = preorderTraversalWithStack(node1);
        System.out.println(result);
    }
}
