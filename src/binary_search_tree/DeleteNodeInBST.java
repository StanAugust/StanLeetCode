package binary_search_tree;

/**
 * @ClassName: DeleteNodeInBST   
 * @Description: 删除二叉搜索树中的指定节点,并保证二叉搜索树的性质不变   
 * @author Stan
 * @date: 2020年9月10日
 */
public class DeleteNodeInBST {
	
	public TreeNode deleteNode(TreeNode root, int key) {
		
		if(root == null)	return null;
		
		/*
		 * 1. 首先找到需要删除的节点；
		 * 2. 如果找到了，删除它。
		 */
		if(root.val == key) {
			
			// 情况1：要删除的节点为叶子节点，可以直接删除。
			// 情况2：要删除的节点只有一个非空子节点，那么让这个孩子接替自己的位置。
			if(root.left == null)	return root.right;
			if(root.right == null)	return root.left;
			
			// 情况3：要删除的节点有两个子节点，则找到前驱节点或后继节点来代替自己（这边用前驱）
			TreeNode predecessor = getMaxNode(root.left);
			root.val = predecessor.val;
			root.left = deleteNode(root.left, predecessor.val);
			
			
		}else if(root.val > key) {
			root.left = deleteNode(root.left, key);
			
		}else if(root.val < key) {
			root.right = deleteNode(root.right, key);
		}
		
		return root;
    }
	
	/**
	 * @Description: 找到指定节点的前驱节点
	 * @param node
	 * @return
	 */
	private TreeNode getMaxNode(TreeNode node) {
		
		while(node.right != null) {
			node = node.right;
		}
		
		return node;
	}
}
