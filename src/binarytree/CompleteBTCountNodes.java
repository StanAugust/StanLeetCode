package binarytree;

/**
 * @ClassName: CompleteBTCountNodes   
 * @Description: 完全二叉树的节点数   
 * @author Stan
 * @date: 2020年9月17日
 */
public class CompleteBTCountNodes {
	
	public int countNodes(TreeNode root) {
		/*
		 * 完全二叉树：除了最底层节点可能没填满外，其余每层节点数都达到最大值，并且最下面一层的节点都集中在该层最左边的若干位置。
		 * 
		 * 所以一棵完全二叉树的两棵子树，至少有一棵是满二叉树
		 * 
		 * 那么计算过程一定有某棵子树 会触发hl==hr，只会消耗O(logN)的复杂度而不会继续递归
		 */
        TreeNode l=root, r=root;

        // 计算root的左、右子树的高度
        int hl=0,hr=0;

        while(l != null){
            l = l.left;
            hl++;
        }
        while(r != null){
            r = r.right;
            hr++;
        }

        // 如果左右子树的高度相同，则是一颗满二叉树
        if(hl == hr){
            return (int)Math.pow(2,hl)-1;
        }

        // 如果左右高度不同，则按照普通二叉树的逻辑计算
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}
