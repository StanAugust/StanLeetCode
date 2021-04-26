package data_structs.binarytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @ClassName: LevelTraversal   
 * @Description: (BFS应用)层序遍历   
 * @author Stan
 * @date: 2020年9月8日
 */
public class LevelTraversal {
	
	/**
	 * @Description: BFS遍历结果：一维数组 ；层序遍历结果：二维数组
	 * 				   在 BFS 遍历的基础上区分遍历的每一层，就得到了层序遍历。
	 * @param root
	 * @return
	 */
	public List<List<Integer>> levelOrder(TreeNode root){
		
		List<List<Integer>> res = new ArrayList<>();
		Queue<TreeNode> queue = new LinkedList<>();
		
		if(root != null) {
			queue.offer(root);
		}
		
		/*
		 * 如何区分队列中的结点来自哪一层：
		 * 		在每一层遍历开始前，先记录队列中的结点数量n（也就是这一层的结点数量），然后一口气处理完这一层的n个结点
		 */
		while(!queue.isEmpty()) {
			
			int currentLevelSize = queue.size();
			
			List<Integer> currentLevel = new ArrayList<>();
			
			for(int i=0; i<currentLevelSize; i++) {
				
				TreeNode node = queue.poll();
				currentLevel.add(node.val);
				
				if(node.left != null) {
					queue.offer(node.left);
				}
				
				if(node.right != null) {
					queue.offer(node.right);
				}
			}
			
			res.add(currentLevel);
		}
		
		return res;
	}
}
