package binary_search_tree;

/**
 * @ClassName: ValidateBST   
 * @Description: 验证二叉搜索树   
 * @author Stan
 * @date: 2020年9月10日
 */
public class ValidateBST {
	
	/**
	 * @Description: 给定一个二叉树，判断其是否是一个有效的二叉搜索树。  
	 * @param root
	 * @return
	 */
	public boolean isValidBST(TreeNode root) {
		
		/*
		 * 节点的左子树只包含小于当前节点的数。
		 * 节点的右子树只包含大于当前节点的数。
		 * 所有左子树和右子树自身必须也是二叉搜索树。
		 */
		
		return isValisBST(root, null, null);
	}
	
	private boolean isValisBST(TreeNode root, TreeNode min, TreeNode max) {
		
		if(root == null)	return true;
		
		if(min!=null && root.val<=min.val)	return false;
		if(max!=null && root.val>=max.val)	return false;
		
		return isValisBST(root.left, min, root) && isValisBST(root.right, root, max);
	}
}
