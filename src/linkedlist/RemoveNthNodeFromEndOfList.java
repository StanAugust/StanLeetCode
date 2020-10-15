package linkedlist;

/**
 * @ClassName: RemoveNthNodeFromEndOfList   
 * @Description: 删除链表的倒数第N个节点   
 * @author Stan
 * @date: 2020年9月25日
 */
public class RemoveNthNodeFromEndOfList {
	
	/**
	 * @Description: 这个问题可以简化成另一个问题：  删除从列表开头数起的第(L−n+1) 个结点
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
