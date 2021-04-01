package advanced_data_structs;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @ClassName: MonotonicQueue   
 * @Description: 单调队列   
 * @author Stan
 * @date: 2021年4月1日
 */
public class MonotonicQueue {
	/*
	 * 单调队列，同样地，也只是一个队列，但是在往队内push时，进行了额外的操作，使得队列中元素保持有序
	 * 
	 * 单调队列可以解决滑动窗口相关问题
	 */
	
	private Deque<Integer> deque = new LinkedList<>();
	
	public void push(int x) {
		
		// 插入新数x时，把队里小于x的数全部删除，每次push都执行这个操作，最后队里剩下的元素就会保持一个单调递减的顺序
		while(!deque.isEmpty() && deque.peekLast()<x) {
			deque.pollLast();
		}
		
		// 将x插入尾部
		deque.offerLast(x);
	}	
	
	public int max() {
		// 因为push的额外操作，所以队头的元素肯定是最大的
		return deque.peekFirst();
	}

	public void pop(int x) {
		// 同样因为push的额外操作，此时想删除的队头元素x不一定还在队内了，如果不在，就无需删除了		
		if(x == deque.peekFirst()) {
			deque.pollFirst();
		}
	}
}
