package binarytree;

/**
 * @ClassName: BalancedBinaryTree   
 * @Description: 平衡二叉树   
 * @author Stan
 * @date: 2020年9月11日
 */
public class BalancedBinaryTree {
	
	/**
	 * @Description: 给定一个二叉树，判断它是否是高度平衡的二叉树  
	 * @param root
	 * @return
	 */
	public void isBalanced(TreeNode root) {
		// 方法一：自顶向下的递归(时间复杂度高)
		topDownRecursion(root);
		// 方法二：自底向上的递归
		bottomUpRecursion(root);
	}
	
	/**
	 * @Description: 方法一：自顶向下的递归  
	 * @param root
	 * @return
	 * 时间复杂度：O(n^2)
	 * 		最坏情况下，二叉树是满二叉树，需要遍历二叉树中的所有节点，时间复杂度是 O(n)。
	 * 		对于节点 p，如果它的高度是 d，则 height(p) 最多会被调用 d次（即遍历到它的每一个祖先节点时）。对于平均的情况，一棵树的高度 h满足 O(h)=O(logn)，因为 d≤h，所以总时间复杂度为 O(nlogn)。对于最坏的情况，二叉树高度为O(n)，此时总时间复杂度为 O(n^2)
	 * 空间复杂度：O(n)，主要取决于递归调用的层数，不超过n
	 */
	private boolean topDownRecursion(TreeNode root) {
		if(root == null)	return true;
		
		/*
		 * 对于当前遍历到的节点，首先计算左右子树的高度，如果左右子树的高度差不超过 1，再分别递归地遍历左右子节点，并判断左子树和右子树是否平衡。
		 */
		if(Math.abs(getHeight(root.left) - getHeight(root.right)) <= 1) {
			
			return topDownRecursion(root.left)&&topDownRecursion(root.right);
			
		}else {
			
			return false;
		}
	}
	
	private int getHeight(TreeNode node) {
		
		if(node == null)	return 0;
		
		return Math.max(getHeight(node.left), getHeight(node.right))+1;
	}
	
	/**
	 * @Description: 方法二：自底向上的递归  
	 * @param root
	 * @return
	 * 时间复杂度：O(n)，使用自底向上的递归，每个节点的计算高度和判断是否平衡都只需要处理一次，最坏情况下需要遍历二叉树中的所有节点
	 * 空间复杂度：主要取决于递归调用的层数，不超过n
	 */
	private boolean bottomUpRecursion(TreeNode root) {
		
		return height(root) >= 0;
	}
	
	private int height(TreeNode node) {
		
		if(node == null)	return 0;
		
		/*
		 * 对于当前遍历到的节点，先递归地判断其左右子树是否平衡，再判断以当前节点为根的子树是否平衡。
		 * 如果一棵子树是平衡的，则返回其高度（高度一定是非负整数），否则返回 -1。
		 * 如果存在一棵子树不平衡，则整个二叉树一定不平衡。
		 */
		int leftHeight = height(node.left);
		int rightHeight = height(node.right);
		
		if(leftHeight==-1 || rightHeight==-1 || Math.abs(leftHeight-rightHeight)>1) {
			return -1;
		}	
		else {
			return Math.max(leftHeight,rightHeight)+1;
		}	
	}
}
