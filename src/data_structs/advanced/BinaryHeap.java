package data_structs.advanced;

/**
 * @ClassName: BinaryHeap   
 * @Description: 二叉堆（优先队列的底层实现）   
 * @author Stan
 * @date: 2021年4月13日    
 * @param <Key>
 */
public class BinaryHeap<Key extends Comparable<Key>> {
	
	private Key[] objects;
	
	// 当前堆中的元素个数
	private int size = 0;
	
	public BinaryHeap(int size) {
		this.objects = (Key[]) new Comparable[size+1];
	}
	
	
	public int parent(int root) {
		return root/2;
	}
	
	public int left(int root) {
		return root*2;
	}
	
	public int right(int root) {
		return root*2 + 1;
	}
	
	private boolean less(int i, int j) {
		return objects[i].compareTo(objects[j]) < 0;
	}
	
	private void swap(int i, int j) {
		Key tmp = objects[i];
		
		objects[i] = objects[j];
		objects[j] = tmp;
	}
	
	private void swim(int k) {
		while(k>1 && less(parent(k),k)) {
			swap(parent(k), k);
			k = parent(k);
		}
	}
	
	private void sink(int k) {
		while(left(k) <= size) {
			int older = left(k);
			
			if(right(k)<=size && less(older, right(k))) {
				older = right(k);
			}
			
			if(less(older, k))	break;
			
			swap(k, older);
			
			k = older;
		}
	}
	
	public void insert(Key key) {
		size++;
		
		objects[size] = key;
		swim(size);
	}
	
	public Key delMax() {
		Key max = objects[1];
		
		swap(1,size);
		objects[size] = null;
		
		size--;
		sink(1);
		
		return max;
	}
}
