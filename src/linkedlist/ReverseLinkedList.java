package linkedlist;

/**
 * @ClassName: ReverseLinkedList
 * @Description: 反转链表
 * @author Stan
 * @date: 2020年9月25日
 */
public class ReverseLinkedList {

	/**
	 * @Description: 将「以 head 为起点」的链表反转，并返回反转之后的头结点 
	 * 				   方法一：迭代反转
	 * @param head
	 */
	public ListNode iterativeReverse(ListNode head) {

		ListNode pre = null, cur = head;

		while (cur != null) {
			ListNode next = cur.next;

			cur.next = pre;
			pre = cur;

			cur = next;
		}

		return pre;
	}

	/**
	 * @Description: 将「以 head 为起点」的链表反转，并返回反转之后的头结点 
	 * 				   方法二：递归反转
	 * @param head
	 */
	public ListNode recursiveReverse(ListNode head) {

		if (head.next == null)
			return head; // 说明是原链表的最后一个结点，返回该结点

		// 后序遍历
		ListNode last = recursiveReverse(head.next); // 一直递归得到链表的最后一个结点

		head.next.next = head;
		head.next = null;

		return last;
	}

	/**
	 * @Description: 反转「以 head 为起点」的链表的前N个结点，并返回反转之后的头结点。
	 * @param head
	 * @param n
	 * @return
	 */
	ListNode successor = null;  // 标记第 n+1 个结点
	
	public ListNode reverseN(ListNode head, int n) {
		
		if(n == 1) {		// 说明找到了第n个结点
			successor = head.next;
			return head;
		}	
		
	    // 以 head.next 为起点，需要反转前 n-1 个节点
		ListNode last = reverseN(head.next, n-1);
		
		head.next.next = head;
		head.next = successor;	// 让反转之后的 head节点和后面的节点连起来
		
		return last;
	}
	
	/**
	 * @Description: 92.反转「以 head 为起点」的链表的索引区间 [m,n]（索引从 1 开始）中的元素
	 * @param head
	 * @param m
	 * @param n
	 * @return
	 */
	public ListNode reverseBetween(ListNode head, int m, int n) {
		// base case
		if(m == 1) {
			return reverseN(head, n);
		}
		
		// 前进到反转的起点触发 base case
		head.next = reverseBetween(head.next, m-1, n-1);
		
		return head;
	}
	
	/**
	 * @Description: 25.将「以 head 为起点」的链表每k个节点一组进行翻转，并返回翻转后的链表。
	 * 				   如果节点总数不是k的整数倍，那么将最后一小节保持原有顺序。  
	 * @param head
	 * @param k
	 * @return
	 */
	public ListNode reverseKGroup(ListNode head, int k) {
		
		if(head == null)	return null;
		
		ListNode a,b;	// a标记本组的组头，b标记下一组的组头
		a = b = head;
		
		for(int i=0; i<k; i++) {
			// 不足 k 个，不需要反转，base case
			if(b == null)	return a;
			b = b.next;
		}
		
		// 反转前 k 个元素
		ListNode newHead = reverseN(a, k);
		
		// 递归反转后续链表并连接起来
		a.next = reverseKGroup(b, k);
		
		return newHead;
	}
	
	/**
	 * @Description: 234.判断一个链表是否为回文链表(优化空间复杂度)
	 * @param head
	 * @return
	 */
	public boolean isPalindrome(ListNode head) {
		
		// 1. 利用快慢指针找到链表的中点
		ListNode slow,fast;
		slow = fast = head;
		
		while(fast!=null && fast.next!=null) {
			slow = slow.next;
			fast = fast.next.next;
		}// slow 指针现在指向链表中点
		
		// 2. 如果fast没有指向null，说明链表长度是奇数，slow还要再前进一步
		if(fast != null)	slow = slow.next;
		
		// 3. 反转以slow为首的链表，然后就可以开始比较
		ListNode left = head;
		ListNode right = recursiveReverse(slow);
		
		while(right != null) {
			if(left.val != right.val)	return false;
			
			left = left.next;
			right = right.next;			
		}
		
		return true;
	}
}