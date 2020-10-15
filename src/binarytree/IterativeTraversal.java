package binarytree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @ClassName: IterativeTraversal   
 * @Description: DFS：前/中/后序遍历 迭代（统一写法版）   
 * @author Stan
 * @date: 2020年9月7日
 */
public class IterativeTraversal {
	
	/**
	 * @Description: 中序遍历  
	 * @param root
	 * @return
	 * @see https://leetcode-cn.com/problems/binary-tree-preorder-traversal/solution/dai-ma-sui-xiang-lu-chi-tou-qian-zhong-hou-xu-de-d/	动画演示
	 */
	public List<Integer> inOrder(TreeNode root){
		List<Integer> res = new ArrayList<Integer>();
		Stack<TreeNode> stack = new Stack<>();
		
		if(root != null) {
			stack.push(root);
		}
		
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();	// 将该节点弹出，避免重复操作
			
			if(node != null) {		// 弹出的不为空节点，下面再将右中左节点添加到栈中
				
				if(node.right != null) {	// 添加右节点
					stack.push(node.right);
				}
				
				stack.push(node);			// 添加中节点
				stack.push(null);			// 中节点访问过，但是还没有处理，需要做一下标记。
				
				if(node.left != null) {		// 添加左节点
					stack.push(node.left);
				}
				
			}else {		 // 弹出的为空节点
				node = stack.pop();			// 重新取出栈中元素
				res.add(node.val);			// 加入到数组中
			}
			
		}
		
		return res;
	}
	
	/**
	 * @Description: 先序遍历  
	 * @param root
	 * @return
	 */
	public List<Integer> preOrder(TreeNode root){
		
		List<Integer> res = new ArrayList<Integer>();
		Stack<TreeNode> stack = new Stack<>();
		
		if(root != null) {
			stack.push(root);
		}
		
		while(!stack.isEmpty()) {
			TreeNode node = stack.pop();
			
			if(node != null) {
				
				if(node.right != null) {		// 右
					stack.push(node.right);
				}
				if(node.left != null) {			// 左
					stack.push(node.left);
				}
				
				stack.push(node);				// 中
				stack.push(null);
				
			}else {
				node = stack.pop();
				res.add(node.val);
			}
		}
		
		return res;
	}

	/**
	 * @Description: 后序遍历  
	 * @param root
	 * @return
	 */
	public List<Integer> postOrder(TreeNode root){
		
		List<Integer> res = new ArrayList<Integer>();
		Stack<TreeNode> stack = new Stack<>();
		
		if(root != null) {
			stack.push(root);
		}
		
		while(!stack.isEmpty()) {
			TreeNode node = stack.pop();
			
			if(node != null) {
				
				stack.push(node);			// 中
				stack.push(null);
				
				if(node.right != null) {	// 左
					stack.push(node.right);
				}
				if(node.left != null) {		// 右
					stack.push(node.left);
				}
				
			}else {
				node = stack.pop();
				res.add(node.val);
			}
		}
		
		return res;
	}

}
