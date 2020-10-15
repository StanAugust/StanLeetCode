package binary_search_tree;

/**
 * @ClassName: LowestCommonAncestor   
 * @Description: 找到二叉搜索树中两个指定节点的最近公共祖先 (找到分割点)
 * @author Stan
 * @date: 2020年9月11日
 */
public class LowestCommonAncestor {
	
	/**
	 * @Description: 方法一：递归寻找LCA  
	 * @param root
	 * @param p
	 * @param q
	 * @return
	 * 时间复杂度：O(N),在最坏的情况下可能需要访问 BST 中所有的节点
	 * 空间复杂度：O(N),递归调用的栈深度
	 */
	public TreeNode recursiveFindLCA(TreeNode root, TreeNode p, TreeNode q) {
		
		if(root == null)	return null;
				
		if(root.val>p.val && root.val>p.val) {
			return recursiveFindLCA(root.left, p, q);
			
		}else if (root.val<p.val && root.val<p.val) {
			return recursiveFindLCA(root.right, p, q);
			
		}else {
			return root;
		}
	}
	
	/**
	 * @Description: 方法二：迭代寻找LCA，和方法一思路一样
	 * @param root
	 * @param p
	 * @param q
	 * @return
	 * 时间复杂度：O(N),在最坏的情况下可能需要访问 BST 中所有的节点
	 * 空间复杂度：O(1)
	 */
	public TreeNode iterativeFindLCA(TreeNode root, TreeNode p, TreeNode q) {
		if(root == null)	return null;
		
		while(root != null) {			
			if(root.val>p.val && root.val>p.val) {
				root = root.left;
				
			}else if (root.val<p.val && root.val<p.val) {
				root = root.right;
				
			}else {
				return root;
			}
		}
		
		return null;
	}
}
