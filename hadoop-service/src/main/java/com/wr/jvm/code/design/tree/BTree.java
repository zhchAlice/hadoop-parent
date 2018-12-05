package com.wr.jvm.code.design.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Alice
 * @date: 2018/9/21.
 * @since: 1.0.0
 */
public class BTree {
    public static List<TreeNode> generateTrees(int n) {
        List<TreeNode> result = new ArrayList<>();
        return generateTree(1, n);

    }

    private static List<TreeNode> generateTree(int start, int end) {
        List<TreeNode> subTree = new ArrayList<>();
        if (start > end) {
            subTree.add(null);
            return subTree;
        }
        for (int k = start; k <= end; k++) {
            List<TreeNode> leftSubTree = generateTree(start, k - 1);
            List<TreeNode> rightSubTree = generateTree(k + 1, end);
            for (TreeNode left : leftSubTree) {
                for (TreeNode right : rightSubTree) {
                    TreeNode root = new TreeNode(k);
                    root.left = left;
                    root.right = right;
                    subTree.add(root);
                }
            }
        }
        return subTree;
    }

    private static boolean flag = false;

    private static void swap(TreeNode node1, TreeNode node2) {
        int tmp = node1.val;
        node1.val = node2.val;
        node2.val = tmp;
    }

    public static void recoverTree(TreeNode root) {
        TreeNode cur = root;
        TreeNode parent = null,first = null,second = null;
        if (cur == null) {
            return;
        }
        TreeNode pre;
        while (cur != null) {
            if (cur.left == null) {
                if (parent != null && parent.val > cur.val) {
                    if (null == first) {
                        first = parent;
                    }
                    second = cur;
                }
                parent = cur;
                cur = cur.right;
            } else {
                pre = cur.left;
                while (pre.right != null && pre.right != cur)
                    pre = pre.right;
                if (null == pre.right) {
                    pre.right = cur;
                    cur = cur.left;
                } else {
                    pre.right = null;
                    if (parent != null && parent.val > cur.val) {
                        if (null == first) {
                            first = parent;
                        }
                        second = cur;
                    }
                    parent = cur;
                    cur = cur.right;
                }
            }
        }
        if (first != null)
            swap(first, second);
    }

    public static TreeNode sortedArrayToBST(int[] nums) {
        if(nums == null || nums.length==0){
            return null;
        }
        return sortedArrayToBST(nums,0,nums.length-1);
    }

    private static TreeNode sortedArrayToBST(int[] nums, int start, int end){
        if(start==end){
            return new TreeNode(nums[start]);
        }
        int mid = start+(end-start>>1);
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBST(nums,start,mid-1);
        root.right = sortedArrayToBST(nums,mid+1,end);
        return root;
    }

    /*public static TreeNode sortedListToBST(ListNode head) {
        ListNode cur=head;
        int len = 0;
        while(null != cur){
            len++;
            cur=cur.next;
        }
        return sortedListToBST(head,0,len-1);
    }

    private static TreeNode sortedListToBST(ListNode head, int start, int end) {
        if(start>end){
            return null;
        }
        int mid=start+(end-start>>1);
        TreeNode leftChild = sortedListToBST(head,start,mid-1);
        TreeNode root = new TreeNode(head.val);
        root.left = leftChild;
        root.right=sortedListToBST(head.next,mid+1,end);
        return root;
    }*/

    private static ListNode node;

    public static TreeNode sortedListToBST(ListNode head) {
        if(head == null){
            return null;
        }

        int size = 0;
        ListNode runner = head;
        node = head;

        while(runner != null){
            runner = runner.next;
            size ++;
        }

        return inorderHelper(0, size - 1);
    }

    public static TreeNode inorderHelper(int start, int end){
        if(start > end){
            return null;
        }

        int mid = start + (end - start) / 2;
        TreeNode left = inorderHelper(start, mid - 1);

        TreeNode treenode = new TreeNode(node.val);
        treenode.left = left;
        node = node.next;

        TreeNode right = inorderHelper(mid + 1, end);
        treenode.right = right;

        return treenode;
    }



    public static void main(String[] args) {
        /*List<TreeNode> result = generateTrees(3);
        System.out.println("success");*/
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        /*node1.left = node3;
        node3.right = node2;*/
        node3.left=node1;
        node3.right=node4;
        node4.left=node2;
        //recoverTree(node3);
        //System.out.println("success");
        //int[] nums={-10,-3,0,5,9};
        //TreeNode root = sortedArrayToBST(nums);

        ListNode l1=new ListNode(-10);
        ListNode l2=new ListNode(-3);
        ListNode l3=new ListNode(0);
        ListNode l4=new ListNode(5);
        ListNode l5=new ListNode(9);
        l1.next=l2;
        l2.next=l3;
        l3.next=l4;
        l4.next=l5;
        TreeNode root = sortedListToBST(l1);
        System.out.println("success");
    }
}
