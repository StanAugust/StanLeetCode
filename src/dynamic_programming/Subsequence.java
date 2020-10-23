package dynamic_programming;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @ClassName: Subsequence   
 * @Description: 子序列相关问题解题模板   
 * @author Stan
 * @date: 2020年10月21日
 */
public class Subsequence {
	/**
	 * 子序列不要求是连续的序列，而子串、子数组是连续的
	 * 一般来说，这类问题都是让求一个最长子序列，因为最短子序列就是一个字符。一旦涉及到子序列和最值，几乎可以肯定考察的是动态规划技巧，时间复杂度一般都是 O(n^2)。
	 * 
	 * 两种思路：
	 * 	思路一：定义一维dp数组，dp数组的定义：在子数组array[0..i]中，以array[i]结尾的目标子序列的长度是dp[i]。
	 * 		模板：
	 * 			int n = array.length;
	 * 			int[] dp = new int[n];
	 * 			for (int i = 1; i < n; i++) {
	 * 				for (int j = 0; j < i; j++) {
	 * 					dp[i] = 最值(dp[i], dp[j] + ...)
	 * 				}
	 * 			}
	 * 	思路二：定义二维dp数组
	 * 		模板：
	 * 			int n = arr.length;
	 * 			int[][] dp = new dp[n][n];
	 * 			for (int i = 0; i < n; i++) {
	 * 				for (int j = 1; j < n; j++) {
	 * 					if (arr[i] == arr[j]) 	dp[i][j] = dp[i][j] + ...
	 * 					else	dp[i][j] = 最值(...)
	 * 				}
	 * 			}
	 * 		2.1 涉及两个字符串/数组时，dp数组的定义：在子数组arr1[0..i]和子数组arr2[0..j]中，我们要求的子序列长度为dp[i][j]。
	 * 			解决两个字符串的dp问题，一般都是用两个指针 i,j 分别指向两个字符串的最后，然后一步步往前走，缩小问题的规模。
	 * 		2.2 只涉及一个字符串/数组时，dp数组的定义：在子数组array[i..j]中，我们要求的子序列的长度为dp[i][j]。
	 */
	
	/**
	 * @Description: 给定一个无序的整数数组，找到其中最长上升子序列的长度。  (思路一)
	 * @param nums	eg:[10,9,2,5,3,7,101,18]
	 * @return		eg:4([2,3,7,101])
	 */
	public int lengthOfLIS(int[] nums) {

        int max = 0;

        //dp[i] 表示以 nums[i] 这个数结尾的最长递增子序列的长度。
        int[] dp = new int[nums.length];
        //base case：dp[i] 初始值为 1，因为以 nums[i] 结尾的最长递增子序列起码要包含它自己。
        for(int i=0; i<nums.length; i++){
            dp[i] = 1;
        }

        for(int i=0; i<nums.length; i++){
            for(int j=0; j<i; j++){
                if(nums[j] < nums[i]){
                    dp[i] = Math.max(dp[i],dp[j]+1);	//既然是递增子序列，只要找到前面那些结尾比 num[i]小的子序列，然后把 num[i]接到最后，就可以形成一个新的递增子序列，而且这个新的子序列长度加一
                }
            }

            max = Math.max(max,dp[i]);
        }

        return max;
    }
	
	/**
	 * @Description: (思路一扩展)LIS如何运用到二维数组中——难点在于排序技巧  
	 * 				俄罗斯套娃信封问题：给定一些标记了宽度和高度的信封，宽度和高度以整数对形式 (w, h) 出现。当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里
	 * 							请计算最多能有多少个信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
	 * @param envelopes
	 * @return
	 */
	public int maxEnvelopes(int[][] envelopes) {
		
		/*
		 * 先对宽度w进行升序排序，如果遇到w相同的情况，则按照高度h降序排序。之后把所有的h作为一个数组，在这个数组上计算 LIS 的长度就是答案。
		 * 关键点：
		 * 		对于宽度w相同的数对，要对其高度h进行降序排序。因为两个宽度相同的信封不能相互包含的，而逆序排序保证在w相同的数对中最多只选取一个计入 LIS。
		 */
        int n = envelopes.length;
        //按宽度升序排列，如果宽度一样，则按高度降序排列
        Arrays.sort(envelopes,new Comparator<int[]>() {
			@Override
			public int compare(int[] a, int[] b) {
				return a[0]==b[0]? b[1]-a[1] : a[0]-b[0];
			}
		});
        //对高度数组寻找LIS
        int[] height = new int[n];
        for(int i=0; i<n; i++){
            height[i] = envelopes[i][1];
        }

        return lengthOfLIS(height);
    }
	
	/**
	 * @Description: 给定两个字符串 text1 和 text2，返回这两个字符串的最长公共子序列的长度。 (思路2.1)
	 * @param text1
	 * @param text2
	 * @return
	 */
	public int longestCommonSubsequence(String text1, String text2) {
		// 存储text1[1..i]和text2[1..j]的LCS的长度
        int len1=text1.length(), len2=text2.length();
        int[][] dp = new int[len1+1][len2+1];

        // base case
        // dp[0][j]和dp[i][0]均为0
        // java int数组初始化均为0

        //用两个指针 i 和 j 从后往前遍历 s1 和 s2，如果 s1[i]==s2[j]，那么这个字符一定在 lcs 中；否则的话，s1[i] 和 s2[j] 这两个字符至少有一个不在 lcs 中，需要丢弃一个。
        for(int i=1; i<=len1; i++){
            for(int j=1; j<=len2; j++){
                if(text1.charAt(i-1) == text2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }

        return dp[len1][len2];
	}
	
	/**
	 * @Description: 给定两个单词 word1 和 word2，计算出将 word1 转换成 word2 所使用的最少操作数 ,即两个单词间的最小编辑距离。(思路2.1)  
	 * @param word1
	 * @param word2
	 * @return
	 */
	public int minEditDistance(String word1, String word2) {
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
	
	/**
	 * @Description: 给定一个字符串 s ，找到其中最长的回文子序列，并返回该序列的长度。 (思路2.2)
	 * @param s
	 * @return
	 */
	public int longestPalindromeSubseq(String s) {
        
        //dp[i][j]: 在子串s[i..j]中，最长回文子序列的长度
        int len = s.length();
        int[][] dp = new int[len][len];

        //base case
        //如果只有一个字符，最长回文子序列长度是 1
        for(int i=0; i<len; i++){
            dp[i][i] = 1;
        }
        
        // 想求dp[i][j]，假设已知道了子问题dp[i+1][j-1]的结果，那么dp[i][j]取决于s[i]和s[j]是否相等
        // 反着遍历保证正确的状态转移
        for(int i=len-1; i>=0; i--){
            for(int j=i+1; j<len; j++){ // j肯定要大等于i
                if(s.charAt(i) == s.charAt(j)){
                    dp[i][j] = dp[i+1][j-1] + 2;
                }else{
                    dp[i][j] = Math.max(dp[i][j-1], dp[i+1][j]);	//如果不相等，说明它俩不可能同时出现在s[i..j]的最长回文子序列中，那么把它俩分别加入s[i+1..j-1]中，看看哪个子串产生的回文子序列更长即可
                }
            }
        }

        // 整个 s 的最长回文子串长度
        return dp[0][len-1];
    }
}
