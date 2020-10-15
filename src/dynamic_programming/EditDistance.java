package dynamic_programming;

/**
 * @ClassName: EditDistance   
 * @Description: 编辑距离   
 * @author Stan
 * @date: 2020年10月13日
 */
public class EditDistance {
	
	/**
	 * @Description: 给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 。  
	 * @param word1
	 * @param word2
	 * @return
	 */
	public int minDistance(String word1, String word2) {
		// 存储 word1[0..i] 和 word2[0..j] 的最小编辑距离
        int len1 = word1.length(), len2 = word2.length();
        int[][] dp = new int[len1+1][len2+1];

        // base case
        for(int i=1; i<=len1; i++){
            dp[i][0] = i;
        }
        for(int j=1; j<=len2; j++){
            dp[0][j] = j;
        }

        // 自底向上求解
        for(int i=1; i<=len1; i++){
            for(int j=1; j<=len2; j++){

                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];    	// 如果两个字符相等，则啥也别做（skip），i、j同时向前移动
                }else{
                    dp[i][j] = min(dp[i-1][j]+1,    // 删除; 直接把s[i]这个字符删掉，前移i，继续跟j对比
                                   dp[i][j-1]+1,    // 插入; 直接在s1[i]插入一个和s2[j]一样的字符, 那么s2[j]就被匹配了，前移 j，继续跟 i 对比
                                   dp[i-1][j-1]+1   // 替换; 直接把s1[i]替换成s2[j],这样它俩就匹配了, 同时前移i,j继续对比
                                   );
                }
            }
        }

        return dp[len1][len2]; 
    }
	
	private int min(int a, int b, int c){
        return Math.min(a, Math.min(b,c));
    }
}
