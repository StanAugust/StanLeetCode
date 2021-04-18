package linkedlist;

/**
 * @ClassName: TwoPointer   
 * @Description: 双指针   
 * @author Stan
 * @date: 2021年4月18日
 */
public class TwoPointer {
	/*
	 * 快慢指针一般都初始化指向链表的头结点 head，前进时快指针 fast 在前，慢指针 slow 在后，巧妙解决一些链表中的问题
	 */
	
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
	 * @Description: 876.寻找链表的中点  
	 * @param head
	 * @return
	 */
	public ListNode middleNode(ListNode head) {
        // 让快指针一次前进两步，慢指针一次前进一步，当快指针到达链表尽头时，慢指针就处于链表的中间位置。
        ListNode fast ,slow;
        fast = slow = head;

        while(fast!=null && fast.next!=null){
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
	
	/**
	 * @Description: 19.删除链表的倒数第N个结点
	 * 					这个问题可以简化成另一个问题：  删除从列表开头数起的第(L−n+1) 个结点
	 * @param head
	 * @param n
	 * @return
	 */
	public ListNode removeNthFromEnd(ListNode head, int n) {
		
		/*
		 * 第一个指针从列表的开头向前移动 n+1步，而第二个指针将从列表的开头出发。现在，这两个指针被 n个结点分开。
		 * 我们通过同时移动两个指针向前来保持这个恒定的间隔，直到第一个指针到达最后一个结点。此时第二个指针将指向从最后一个结点数起的第 n个结点。
		 */
		
		//添加一个哑结点作为辅助，该结点位于列表头部。哑结点用来简化某些极端情况
		ListNode dummy = new ListNode(0);
		dummy.next = head;
		
		ListNode first=dummy, second=dummy;
		
		for(int i=0; i<n; i++) {
			first = first.next;
		}
		
		while(first.next != null) {
			first = first.next;
			second = second.next;
		}
		
		second.next = second.next.next;
		
		return dummy.next;
    }
}
