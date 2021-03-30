package linkedlist;

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
	 * @Description: 给定一个链表，判断链表中是否有环。  
	 * @param head
	 * @return
	 */
	public boolean hasCycle(ListNode head) {
		
		/*
		 * 通过使用具有 不同速度 的快、慢两个指针遍历链表，空间复杂度可以被降低至 O(1)。慢指针每次移动一步，而快指针每次移动两步。
		 * 如果列表中不存在环，最终快指针将会最先到达尾部，此时我们可以返回 false。
		 */
		ListNode fast,slow;
		
		if(head == null)    return false;

        slow = head;
        if(slow.next == null)   return false;
        if(slow.next == slow)   return true;

        fast = slow.next;

        while(fast != null){

            if(fast == slow)    return true;

            slow = slow.next;
            
            if(fast.next != null){	// 在调用 next 字段之前，始终检查节点是否为空。
                fast = fast.next.next;
            }else{
                fast = null;
            }
        }

        return false;
    }
	
	/**
	 * @Description: 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。  
	 * @param head
	 * @return
	 */
	public ListNode detectCycle(ListNode head) {
		
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
