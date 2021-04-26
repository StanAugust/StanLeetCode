package data_structs.binarytree;

import java.util.HashMap;

/**
 * @ClassName: BuildBinaryTree   
 * @Description: 根据两个遍历序列构造二叉树
 * @author Stan
 * @date: 2020年9月9日
 */
public class BuildBinaryTree {
	
	int[] preOrder;
	int[] inOrder;
	int[] postOrder;
	
	int pre_idx;
	int post_idx;
	
	HashMap<Integer, Integer> value_idx = new HashMap<>();	//中序序列的值-索引映射
	

	/**
	 * @Description: 从前序与中序遍历序列构造二叉树  
	 * @param inOrder
	 * @param preOrder
	 * @return
	 */
	public TreeNode buildFromInAndPre(int[] inOrder, int[] preOrder) {
		this.inOrder = inOrder;
		this.preOrder = preOrder;
		
		pre_idx = 0;	// 前序遍历中的第一个节点就是根节点
		
		// 构造映射，帮助快速定位根节点
		for(int i=0; i<preOrder.length; i++) {
			value_idx.put(inOrder[i], i);
		}
		
		return inAndPreHelper(0, inOrder.length-1);
	}
	
	private TreeNode inAndPreHelper(int in_left, int in_right) {
		
		if(in_left > in_right) {
			return null;
		}
		
		int rootVal = preOrder[pre_idx];
		int rootIdx = value_idx.get(rootVal);	// 在中序遍历中定位根节点
		TreeNode root = new TreeNode(rootVal);
		
		pre_idx++;
		
		root.left = inAndPreHelper(in_left, rootIdx-1);
		root.right = inAndPreHelper(rootIdx+1, in_right);
		
		return root;
	}
	
	/**
	 * @Description: 从中序与后序遍历序列构造二叉树  
	 * @param inOrder
	 * @param postOrder
	 * @return
	 */
	public TreeNode buildFromInAndPost(int[] inOrder, int[] postOrder) {
		this.inOrder = inOrder;
		this.postOrder = postOrder;
		
		post_idx = postOrder.length-1;	//后序的最后一个节点是根节点
		
		// 构造映射，帮助快速定位根节点
		for(int i=0; i<preOrder.length; i++) {
			value_idx.put(inOrder[i], i);
		}
		
		return inAndPostHelper(0, inOrder.length-1);
	}
	
	private TreeNode inAndPostHelper(int in_left, int in_right) {
		
		if(in_left > in_right) {
			return null;
		}
		
		int rootVal = postOrder[post_idx];
		int rootIdx = value_idx.get(rootVal);	// 在中序遍历中定位根节点
		TreeNode root = new TreeNode(rootVal);
		
		post_idx--;
		
		root.right = inAndPostHelper(rootIdx+1, in_right);		//根据后序遍历，要先构造右子树再构造左子树
		root.left = inAndPostHelper(in_left, rootIdx-1);
		
		return root;
	}
}
