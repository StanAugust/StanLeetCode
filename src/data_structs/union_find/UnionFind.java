package data_structs.union_find;

/**
 * @ClassName: UnionFind   
 * @Description: 并查集算法   
 * @author Stan
 * @date: 2021年3月19日
 */
public class UnionFind {
	
	private int count;		// 记录连通分量的个数
	private int[] parent;	// 记录每个节点的父节点,最终会形成一个森林
	private int[] children;	// 记录每个节点拥有的子节点的数量

	/**
	 * 构造器
	 * @param n	节点总数
	 */
	public UnionFind(int n) {
		
		count = n;	// 一开始互不连通
		
		parent = new int[n];	
		children = new int[n];
		
		for(int i=0; i<n; i++) {
			parent[i] = i;		// 每个节点的父节点初始都是自己
			children[i] = 1;	// 每个节点初始孩子数也只有自己本身
		}
	}
	
	/**
	 * @Description: 将两节点相连  
	 * @param p
	 * @param q
	 */
	public void union(int p, int q) {
		
		int rootP = find(p);
		int rootQ = find(q);
		
		if(rootP == rootQ)	return;
		
		// 将两棵树合并为一颗
		// 优化: 为了树的平衡性,将小树接到大树下
		if(children[rootP] < children[rootQ]) {
			
			parent[rootP] = rootQ;
			children[rootQ] += children[rootP];
			
		}else {
			
			parent[rootQ] = rootP;
			children[rootP] += children[rootQ];
		}
		
		count--;	// 两个分量合二为一
	}
	
	/**
	 * @Description: 判断两节点是否相连  
	 * @param p
	 * @param q
	 * @return
	 */
	public boolean connect(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		
		return rootP == rootQ;
	}
	
	/**
	 * @Description: 返回连通分量的个数  
	 * @return
	 */
	public int count() {
		return count;
	}
	
	/**
	 * @Description: 返回指定节点的根节点  
	 * @param x
	 * @return
	 */
	private int find(int x) {
		// 优化: 路径压缩
		// 画图理解, 在每次查找的时候顺手将树高缩短,最终所有树高不会超过3(union时可能会超过)
		while( parent[x] != x) {
			parent[x] = parent[parent[x]];
			x = parent[x];
		}
		
		return x;
	}
}
