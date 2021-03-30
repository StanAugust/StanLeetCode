package union_find;

/**
 * @ClassName: UFApplication   
 * @Description: 并查集的yingyong   
 * @author Stan
 * @date: 2021年3月22日
 */
public class UFApplication {
	/*
	 * 并查集算法：主要解决图论中的【动态连通性】问题
	 * 
	 * 这里所说的【连通】是一种等价关系，也就是说具有如下三个性质：
	 * 		1.自反性：节点p和p是连通的
	 * 		2.对称性：如果节点p和q连通，那么q和p也连通
	 * 		3.传递性：如果节点p和q连通，q和r连通，那么p和r也连通
	 * 
	 * 这个算法本身不难，能不能应用出来主要是看抽象问题的能力，是否能够把原始问题抽象成一个有关图论的问题
	 * 
	 * 并查集的应用：
	 * 		1.DFS的替代(虽然实现复杂一些，甚至效率也略低，但这是使用 Union-Find 算法的通用思想，可以一学)
	 * 		2.判断等价关系
	 */
	
	/**
	 * @Description: 130.被围绕的区域  ：给定一个m*n的矩阵 board，由若干字符'X'和'O'，找到所有四面被'X'围绕的区域，并将这些区域里所有的'O'用'X'填充
	 * @param board
	 */
	public void surroundedRegions(char[][] board) {
		/* 
		 * 整体思路： 可以把那些不需要被替换的'O'看成一个拥有独门绝技的门派，它们有一个共同祖师爷叫 dummy，这些'O'和 dummy 互相连通，而那些需要被替换的'O'与 dummy 不连通。
		 */
		if(board.length == 0)   return;

        int m = board.length;
        int n = board[0].length;
        
        // !将二维坐标映射到一维的常用技巧: 二维坐标 (x,y) 可以转换成 x*n+y这个数
        UnionFind uf = new UnionFind(m*n + 1);
        // 索引 [0.. m*n-1] 都是棋盘内坐标的一维映射，那就让这个虚拟的 dummy节点占据索引 m*n
        int dummy = m*n;

        // 将首列和末列的'O'与 dummy 连通
        for(int i=0; i<m; i++){
            if(board[i][0] == 'O'){
                uf.union(i*n, dummy);
            }

            if(board[i][n-1] =='O'){
                uf.union(i*n+(n-1), dummy);
            }
        }
        // 将首行和末行的'O'与 dummy 连通
        for(int j=1; j<n-1; j++){
            if(board[0][j] == 'O'){
                uf.union(j, dummy);
            }

            if(board[m-1][j] =='O'){
                uf.union((m-1)*n+j, dummy);
            }
        }

        // !方向数组 directions 是上下左右搜索的常用手法
        int[][] directions = new int[][]{{1,0}, {-1,0}, {0,1}, {0,-1}};
        
        for(int i=1; i<m-1; i++){
            for(int j=1; j<n-1; j++){
                if(board[i][j] == 'O'){
                	// 将此'O'与上下左右的'O'连通
                    for(int k=0; k<4; k++){
                        int x = i+directions[k][0];
                        int y = j+directions[k][1];

                        if(board[x][y] == 'O'){
                            uf.union(i*n+j, x*n+y);
                        }
                    }
                }
            }
        }
        
        // 所有不和 dummy 连通的 O，都要被替换
        for(int i=1; i<m-1; i++){
            for(int j=1; j<n-1; j++){

                if(board[i][j] == 'O'){
                    if(!uf.connect(i*n+j, dummy)){
                        board[i][j] = 'X';
                    }
                }
            }
        }
    }
	
	/**
	 * @Description: 990.判定合法等式 ：给定一个由表示变量之间关系的字符串方程组成的数组，每个字符串方程 equations[i] 的长度为 4，并采用两种不同的形式之一："a==b" 或 "a!=b"。
	 * 								在这里，a 和 b 是小写字母（不一定不同），表示单字母变量名。
	 * 								只有当可以将整数分配给变量名，以便满足所有给定的方程时才返回 true，否则返回 false。 
	 * @param equations
	 * @return
	 */
	public boolean equationsPossible(String[] equations) {
		/*
		 * 整体思路：将 equations 中的算式根据 == 和 != 分成两部分，先处理 == 算式，使得他们通过相等关系各自勾结成门派；然后处理 != 算式，检查不等关系是否破坏了相等关系的连通性。
		 */
        UnionFind uf = new UnionFind(26);

        for(String equation: equations){
            if(equation.charAt(1) == '='){
                uf.union(equation.charAt(0)-'a', equation.charAt(3)-'a');
            }
        }

        for(String equation: equations){
            if(equation.charAt(1) == '!'){
                if(uf.connect(equation.charAt(0)-'a', equation.charAt(3)-'a')){
                    return false;
                }
            }
        }

        return true;
    }
}
