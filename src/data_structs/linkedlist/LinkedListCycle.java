package data_structs.linkedlist;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: LinkedListCycle   
 * @Description: 带有环的链表   
 * @author Stan
 * @date: 2021年3月30日
 */
public class LinkedListCycle {
	
	/**
	 * @Description: 141.给定一个链表，判断链表中是否有环。  
	 * @param head
	 * @return
	 */
	public boolean hasCycle(ListNode head) {
		
		/*
		 * 通过使用具有 不同速度 的快、慢两个指针遍历链表，空间复杂度可以被降低至 O(1)。慢指针每次移动一步，而快指针每次移动两步。
		 * 如果列表中不存在环，最终快指针将会最先到达尾部null，此时我们可以返回 false;
		 * 如果含有环，快指针最终会超慢指针一圈，和慢指针相遇，说明链表含有环.
		 */
		ListNode fast, slow;
        fast = slow = head;

        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast)    return true;
        }

        return false;
    }
	
	/**
	 * @Description: 142.给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。    
	 * @param head
	 * @return
	 */
	public ListNode detectCycle(ListNode head) {

        ListNode fast, slow;
        fast = slow = head;

        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;

            if(slow == fast)    break;
        }
        // fast 遇到空指针说明没有环
        if(fast==null || fast.next==null)   return null;

        // 当快慢指针相遇时,让其中任一个指针指向头节点,然后让它俩以相同速度前进,再次相遇时所在的节点位置就是环开始的位置
        fast = head;
        while(fast != slow){
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }
	
	/**
	 * @Description: 142.检测链表环形起点的另一种方法
	 * @param head
	 * @return
	 */
	public ListNode detectCycleWithSet(ListNode head) {
		
		/*
		 * 遍历链表中的每个节点，并将它记录下来；一旦遇到了此前遍历过的节点，就可以判定链表中存在环。
		 */
		if(head==null || head.next==null)   return null;
        
        Set<ListNode> visited = new HashSet<>();
        ListNode cur = head;

        while(cur != null){
            if(visited.contains(cur)){
                return cur;
            }
            visited.add(cur);
            cur = cur.next;
        }
        return cur;
	}
}
