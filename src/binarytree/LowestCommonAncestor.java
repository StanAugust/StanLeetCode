package binarytree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: LowestCommonAncestor   
 * @Description: 找到二叉树中两个指定节点的最近公共祖先
 * @author Stan
 * @date: 2020年9月10日
 */
public class LowestCommonAncestor {
	
	private TreeNode ans;
	
	private Map<Integer,TreeNode> parent = new HashMap<>();	// 存储所有节点的父节点
	private Set<Integer> visited = new HashSet<>();			// 记录已经访问过的祖先节点
	 
	/**
	 * @Description: 方法一：递归寻找LCA(速度略快一些)
	 * @param root
	 * @param p
	 * @param q
	 * @return
	 * 时间复杂度：O(N),二叉树的所有节点有且只会被访问一次
	 * 空间复杂度：O(N),递归调用的栈深度，二叉树最坏情况下为一条链，此时高度为 N
	 */
	public TreeNode recursiveFindLCA(TreeNode root, TreeNode p, TreeNode q) {
		this.ans = null;
		
		dfs(root, p, q);
		return ans;
	}
	
	/**
	 * @Description: 借助后序遍历
	 * @see https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree/solution/er-cha-shu-de-zui-jin-gong-gong-zu-xian-by-leetc-2/
	 * 		官方解答方法一 动画演示
	 * 
	 */
	private boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
		
		if(root == null)	return false;
		
		boolean lson = dfs(root.left, p, q);
		boolean rson = dfs(root.right, p, q);
		
		int val = root.val;
		
		// lson&&rson 说明左子树和右子树均包含p节点或q节点，如果左子树包含的是 p节点，那么右子树只能包含 q节点，反之亦然，
		// 第二判断条件，即是考虑了root恰好是 p节点或 q节点且它的左子树或右子树有一个包含了另一个节点的情况
		// 因为是自底向上从叶子节点开始更新的，所以在所有满足条件的公共祖先中一定是深度最大的LCA先被访问到
		if( (lson&&rson) || ((val==p.val||val==q.val)&&(lson||rson)) ) {
			ans = root;
		}
		
		return lson || rson || (val==p.val||val==q.val);
	}
	
	/**
	 * @Description: 方法二：存储父节点寻找LCA 
	 * @param root
	 * @param p
	 * @param q
	 * @return
	 * 时间复杂度：O(N),二叉树的所有节点有且只会被访问一次
	 * 空间复杂度：O(N),递归调用的栈深度，二叉树最坏情况下为一条链，此时高度为 N
	 */
	public TreeNode storeParentToFindLCA(TreeNode root, TreeNode p, TreeNode q) {
		
		dfs(root);	// 从根节点开始遍历整棵二叉树，用哈希表记录每个节点的父节点指针。
		
		while(p != null) {		// 从 p节点开始不断往它的祖先移动，并记录已经访问过的祖先节点。
			visited.add(p.val);
			p = parent.get(p.val);
		}
		
		while(q != null) {		// 再从 q节点开始不断往它的祖先移动，如果有祖先已经被访问过，即意味着这是LCA
			if(visited.contains(q.val)) {
				return q;
			}
			
			q = parent.get(q.val);
		}
		
		return null;
	}
	
	/**
	 * @Description: 借助先序遍历 ，用哈希表记录每个节点的父节点指针
	 * @param root
	 */
	private void dfs(TreeNode root) {
		if(root.left != null) {
			parent.put(root.left.val, root);
			dfs(root.left);
		}
		
		if(root.right != null) {
			parent.put(root.right.val, root);
			dfs(root.right);
		}
	}
}




















