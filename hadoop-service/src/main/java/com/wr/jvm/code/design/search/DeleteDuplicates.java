package com.wr.jvm.code.design.search;

/**
 * @author: Alice
 * @date: 2018/8/31.
 * @since: 1.0.0
 */
public class DeleteDuplicates {

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    public static ListNode partition(ListNode head, int x) {
        if(head == null || head.next==null){
            return head;
        }
        ListNode newHead = new ListNode(0);
        newHead.next=head;
        ListNode prev = newHead;
        ListNode cur = head;
        while(cur!=null){
            while (cur!=null && cur.val < x){
                cur=cur.next;
                prev = prev.next;
            }
            if (cur == null){
                break;
            }
            while(cur.next!=null && cur.next.val >= x){
                cur = cur.next;
            }
            if(cur.next==null){
                break;
            }else{
                ListNode tmp = prev.next;
                ListNode tmp2 = cur.next.next;
                prev.next=cur.next;
                cur.next.next=tmp;
                cur.next=tmp2;
                prev=prev.next;
                cur=cur.next;
            }
        }
        return newHead.next;
    }

    public static ListNode reverseBetween(ListNode head, int m, int n) {
        if(m==n){
            return head;
        }
        ListNode new_head = new ListNode(0);
        new_head.next=head;
        ListNode cur=new_head;
        if (n-m==1){
            ListNode m_pre,m_node,n_node,n_next;
            int index = 1;
            while(index<m){
                cur=cur.next;
                index++;
            }
            m_pre=cur;
            m_node=cur=cur.next;
            n_node=cur=cur.next;
            n_next=cur.next;
            m_pre.next=n_node;
            n_node.next=m_node;
            m_node.next=n_next;
            return new_head.next;

        }else {
            ListNode m_pre,m_node,m_next,n_pre,n_node,n_next;

            int index = 1;
            while(index<m){
                cur=cur.next;
                index++;
            }
            m_pre=cur;
            m_node=cur=cur.next;
            m_next=cur=cur.next;
            index+=2;
            while(index<n){
                cur=cur.next;
                index++;
            }
            n_pre=cur;
            n_node=cur=cur.next;
            n_next=cur.next;
            m_pre.next=n_node;
            n_node.next=m_next;
            n_pre.next=m_node;
            m_node.next=n_next;
            return reverseBetween(new_head.next,m+1,n-1);
        }
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l1.next=l2;
        l2.next=l3;
        l3.next=l4;
        l4.next=l5;
        //l5.next=l6;
        reverseBetween(l1,1,4);
    }
}
