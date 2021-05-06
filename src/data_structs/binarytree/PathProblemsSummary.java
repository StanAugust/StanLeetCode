package data_structs.binarytree;

/**
 * @ClassName: PathProblemsSummary   
 * @Description: 二叉树的路径问题汇总   
 * @author Stan
 * @date: 2020年9月15日
 */
public class PathProblemsSummary {
	
	private int maxDia;
	
	/**
	 * @Description: 求二叉树的直径（一棵二叉树的直径长度是任意两个结点路径长度中的最大值，这条路径可能穿过也可能不穿过根结点）  
	 * @param root
	 * @return
	 */
	public int diameterOfBinaryTree(TreeNode root) {
		/*
		 * 一条路径的长度为该路径经过的节点数减一，所以求直径（即求路径长度的最大值）等于求路径经过节点数的最大值减一
		 * 而任意一条路径均可以被看作由某个节点为起点，从其左儿子和右儿子向下遍历的路径拼接得到(depth_lson + root + depth_rson)。
		 */
		maxDia = 1;
		depth(root);
		return maxDia-1;
	}
	private int depth(TreeNode node) {
        if (node == null) return 0; 		// 访问到空节点了，返回0
        int L = depth(node.left); 			// 左儿子为根的子树的深度
        int R = depth(node.right); 			// 右儿子为根的子树的深度
        maxDia = Math.max(maxDia, L+R+1); 	// 更新ans
        return Math.max(L, R) + 1; 			// 返回该节点为根的子树的深度
    }
	
	/**
	 * @Description: 124. 二叉树中的最大路径和  
	 * 					(路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到【任意节点】的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。)
	 * @param root	eg: root = [1,-2,3]
	 * @return			4(最优路径是1 -> 3 ，路径和为1 + 3 = 6)
	 */
	private int maxPath = Integer.MIN_VALUE;
	
	public int maxPathSum(TreeNode root) {
		// 整体思路和上面一样
        maxPath = root.val;
        dfs(root);
        return maxPath;
    }
	private int dfs(TreeNode root){
        if(root == null)    return 0;

        int L = Math.max(dfs(root.left), 0);	// 要注意求最大路径和，所以如果子节点值为负数，起反作用，直接归零
        int R = Math.max(dfs(root.right), 0);

        maxPath = Math.max(maxPath, L+R+root.val);

        return Math.max(L, R) + root.val;
    }
		
	/**
	 * @Description: 二叉树的最大深度  
	 * @param root
	 * @return
	 */
	public int maxDepth(TreeNode root) {
        if(root == null)    return 0;

        int leftHeight = maxDepth(root.left);
        int rightHeight = maxDepth(root.right);

        return Math.max(leftHeight,rightHeight)+1;
    }
	
	/**
	 * @Description: 二叉树的最小深度  
	 * @param root
	 * @return
	 */
	public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //1.左孩子和右孩子都为空的情况，说明到达了叶子节点，直接返回1即可
        if (root.left == null && root.right == null) {
            return 1;
        }

        int leftHeight = minDepth(root.left);
        int rightHeight = minDepth(root.right);
        
        //2.如果左右孩子其中一个为空，那么需要返回非空的那个孩子的深度（说明有一个height必然为0，所以可以返回 leftHeight+rightHeight+1）
        //3.如果左右孩子都不为空，返回较小深度+1
        return root.left==null||root.right==null ? leftHeight+rightHeight+1 : Math.min(leftHeight,rightHeight)+1;
    }

}
