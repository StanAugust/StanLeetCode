package data_structs.advanced;

/**
 * @ClassName: BinaryHeap   
 * @Description: 二叉堆（优先队列的底层实现），这里示范最大堆
 * @author Stan
 * @date: 2021年4月13日    
 * @param <Key>
 */
public class BinaryHeap<Key extends Comparable<Key>> {
	
	// 存储元素的数组
	private Key[] objects;
	
	// 当前堆中的元素个数
	private int size = 0;
	
	public BinaryHeap(int size) {
		// 索引0不用，所以多分配一个空间
		this.objects = (Key[]) new Comparable[size+1];
	}
	
	/**
	 * @Description: 父节点的索引  
	 * @param root
	 * @return
	 */
	public int parent(int root) {
		return root/2;
	}
	
	/**
	 * @Description: 左孩子的索引 
	 * @param root
	 * @return
	 */
	public int left(int root) {
		return root*2;
	}
	
	/**
	 * @Description: 右孩子的索引  
	 * @param root
	 * @return
	 */
	public int right(int root) {
		return root*2 + 1;
	}
	
	/**
	 * @Description: objects[i] 是否比 objects[j] 小? 
	 * @param i
	 * @param j
	 * @return
	 */
	private boolean less(int i, int j) {
		return objects[i].compareTo(objects[j]) < 0;
	}
	
	/**
	 * @Description: 交换  objects[i] 和objects[j]
	 * @param i
	 * @param j
	 */
	private void swap(int i, int j) {
		Key tmp = objects[i];
		
		objects[i] = objects[j];
		objects[j] = tmp;
	}
	
	/**
	 * @Description: 上浮第k个元素  
	 * @param k
	 */
	private void swim(int k) {
		 // 如果浮到堆顶，就不能再上浮了
		while(k>1 && less(parent(k),k)) {
			// 如果第 k 个元素比上层大，则将 k 换上去
			swap(parent(k), k);
			k = parent(k);
		}
	}
	
	/**
	 * @Description: 下沉第 k 个元素  
	 * @param k
	 */
	private void sink(int k) {
		// 如果沉到堆底，就沉不下去了
		while(left(k) <= size) {
			// 先假设左边节点较大
			int older = left(k);
			
			// 如果右边节点存在，比一下大小
			if(right(k)<=size && less(older, right(k))) {
				older = right(k);
			}
			
			// 结点 k 比俩孩子都大，就不必下沉了
			if(less(older, k))	break;
			// 否则，不符合最大堆的结构，下沉 k 结点
			swap(k, older);
			
			k = older;
		}
	}
	
	/**
	 * @Description: 插入元素 key  
	 * @param key
	 */
	public void insert(Key key) {
		size++;
		// 先把新元素加到最后
		objects[size] = key;
		// 然后让它上浮到正确的位置
		swim(size);
	}
	
	/**
	 * @Description: 删除并返回当前队列中最大元素  
	 * @return
	 */
	public Key delMax() {
		// 最大堆的堆顶就是最大元素
		Key max = objects[1];
		
		// 把这个最大元素换到最后，删除之
		swap(1,size);
		objects[size] = null;		
		size--;
		
		// 让 pq[1] 下沉到正确位置
		sink(1);
		
		return max;
	}
}
