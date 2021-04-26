package data_structs.binarytree;

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
		
	/**
	 * @Description: 方法一：借助后序遍历 递归寻找LCA(速度略快一些)
	 * @param root
	 * @param p
	 * @param q
	 * @return
	 * 时间复杂度：O(N),二叉树的所有节点有且只会被访问一次
	 * 空间复杂度：O(N),递归调用的栈深度，二叉树最坏情况下为一条链，此时高度为 N
	 */
	public TreeNode recursiveFindLCA(TreeNode root, TreeNode p, TreeNode q) {
		// base case
		if(root == null) return null;
		if(root==p || root==q) return root;
		
		// 这里的left和right并不是指root的左右孩子节点，而是指root的左右子树是否含有p和q，是则返回p或q，否则返回null
		// 而且由于借助的是后序遍历，p和q出发向上走，第一次相交的节点root就一定会是LCA
		TreeNode left = recursiveFindLCA(root.left, p, q);
		TreeNode right = recursiveFindLCA(root.right, p, q);
		
		// 情况1：如果p和q都在以root为根的树中，那么left和right一定分别是p和q（从base case可看出）
		if(left!=null && right!=null) {
			return root;
		}
		// 情况2：如果p和q都不在以root为根的树中，直接返回null 
		if(left==null && right==null) {
			return null;
		}
		// 情况3：如果p和q只有一个存在于以root为根的树中，函数返回该节点
		return left==null? right:left;
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
	
	private Map<Integer,TreeNode> parent = new HashMap<>();	// 存储所有节点的父节点
	private Set<Integer> visited = new HashSet<>();			// 记录已经访问过的祖先节点
	
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




















