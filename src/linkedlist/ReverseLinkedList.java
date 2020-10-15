package linkedlist;

/**
 * @ClassName: ReverseLinkedList   
 * @Description: 反转链表   
 * @author Stan
 * @date: 2020年9月25日
 */
public class ReverseLinkedList {
	
	/**
	 * @Description: 方法一：迭代反转  
	 * @param head
	 * 时间复杂度：O(n)，n 是列表的长度
	 * 空间复杂度：O(1)
	 */
	public ListNode iterativeReverse(ListNode head) {
		
		ListNode pre = null, cur = head;
		
		while(cur != null) {
			
			ListNode next = cur.next;
			
			cur.next = pre;
			pre = cur;
			
			cur = next;
		}
		
		return pre;
	}
	
	/**
	 * @Description: 方法二：递归反转  
	 * @param head
	 * 时间复杂度：O(n)，n 是列表的长度
	 * 空间复杂度：O(n)，递归栈调用层数
	 */
	public ListNode recursiveReverse(ListNode head) {
		
		if(head==null || head.next==null)	return head;
		
		// 后序遍历
		ListNode p = recursiveReverse(head.next);
		head.next.next = head;
		head.next = null;
		
		return p;
	}
}
