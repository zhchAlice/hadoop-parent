package com.wr.jvm.code.design.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: Alice
 * @date: 2018/12/28.
 * @since: 1.0.0
 */
public class LevelTraversal {
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (null == root){
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()){
            int curLevelSize = queue.size();
            List<Integer> curLevelList = new ArrayList<>();
            for (int i=0;i<curLevelSize;i++){
                TreeNode node = queue.poll();
                curLevelList.add(node.val);
                if (node.left!=null){
                    queue.add(node.left);
                }
                if (node.right!=null){
                    queue.add(node.right);
                }
            }
            result.add(curLevelList);
        }
        return result;
    }

    public static void main(String[] args) {
        TreeNode node3 = new TreeNode(3);
        TreeNode node9 = new TreeNode(9);
        TreeNode node20 = new TreeNode(20);
        TreeNode node15 = new TreeNode(15);
        TreeNode node7 = new TreeNode(7);
        node3.left=node9;
        node3.right = node20;
        node20.left=node15;
        node20.right=node7;
        List<List<Integer>> result = levelOrder(node3);
        for (int i=0;i<result.size();i++){
            System.out.println(result.get(i));
        }
    }
}
