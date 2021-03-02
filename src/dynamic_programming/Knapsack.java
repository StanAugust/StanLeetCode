package dynamic_programming;

import java.util.List;

/**
 * @ClassName: Knapsack   
 * @Description: 背包问题   
 * @author Stan
 * @date: 2020年11月10日
 */
public class Knapsack {
	/*
	 * 引入：
	 * 	     一个可装载重量为W的背包和N个物品，每个物品有重量和价值两个属性。其中第i个物品的重量为wt[i]，价值为val[i]，现在用这个背包装物品，最多能装的价值是多少？
	 * 问题变体：
	 * 	     给定一个target，target可以是数字也可以是字符串，再给定一个数组nums，nums中装的可能是数字，也可能是字符串
	 * 	     问：能否使用nums中的元素做各种【排列组合】得到target?
	 * 
	 * 子问题：
	 * 	  1. 0-1背包：数组中的元素不可分割(只能用或不用，不能用一半)，不可重复使用
	 * 	  2. 完全背包：数组中的元素可重复使用
	 * 
	 * 问题形式：
	 * 	  1. 组合数问题：选 + 不选
	 * 	  2. True、False问题：选 or 不选
	 *    3. 最值问题：max/min(原样，不选+1)
	 * 
	 * 框架：
	 * 	for 状态1 in 状态1的所有取值：
	 * 		for 状态2 in 状态2的所有取值：
	 * 			for ...
	 * 				dp[状态1][状态2][...] = 择优(选择1，选择2...)
	 * 
	 * 分析：
	 * 	1.明确两点，「状态」和「选择」
	 * 		状态有两个，就是「背包的容量」和「可选择的物品」
	 * 		选择就是「装进背包」或者「不装进背包」
	 * 	2.明确dp数组的定义：状态有两个，所以需要一个二维dp数组
	 * 		dp[i][w]的定义如下：对于前i个物品，当前背包的容量为w，这种情况下可以装的最大价值是dp[i][w]
	 * 		根据这个定义，最终答案就是dp[N][W]
	 * 		base case 就是dp[0][..] = dp[..][0] = 0，因为没有物品或者背包没有空间的时候，能装的最大价值就是 0。
	 */
	/*
	 * 状态压缩：
	 * 状态转移方程参考的是二维表格里上一行（正上方和左上方）的值，从前向后赋值，新的值会覆盖旧的值；
	 * 如果从后向前赋值的，新的值还能够参考到原来旧的值，原来的旧值没有被覆盖，能保证状态转移正确。 
	 */
	
	/**
	 * @Description: 给定不同面额的硬币和一个总金额。写出函数来计算可以凑成总金额的硬币组合数。假设每一种面额的硬币有无限个。  
	 * @param amount	eg: amount = 5,coins = [1, 2, 5]
	 * @param coins 
	 * @return				4
	 */
	public int change(int amount, int[] coins) {
		/*
		 * 分析：完全背包形式，组合数问题
		 */
//        int n = coins.length;
        
//        int[][] dp = new int[n+1][amount+1];
        
//        //base case：如果目标为0，则“无为而治”是唯一的办法
//        for(int i=0; i<=n; i++) {
//        	dp[i][0] = 1;
//        }
//
//        for(int i=1; i<=n; i++){
//            for(int j=1; j<=amount; j++){
//                if(j < coins[i-1]) {		    // 背包容量不够 -> 不把此硬币放入背包
//                	dp[i][j] = dp[i-1][j];
//                }else {						// 背包容量足够 -> 选择是否要把此硬币放入背包
//                	dp[i][j] = dp[i-1][j] + dp[i][j-coins[i-1]];
//                }
//            }
//        }
//        
        
//        return dp[n][amount];
        
        /*
         * dp数组的转移只和 dp[i][..] 和 dp[i-1][..] 有关，所以进行状态压缩
         */
        int[] dp = new int[amount+1];
        
        dp[0] = 1;

        for(int coin : coins){
            for(int j=1; j<=amount; j++){
                if(j - coin >= 0){
                    dp[j] = dp[j] + dp[j-coin];
                }
            }
        }
        return dp[amount];
    }
	
	/**
	 * @Description: 给定一个由正整数组成且不存在重复数字的数组，找出和为给定目标正整数的组合的个数。  请注意，顺序不同的序列被视作不同的组合。
	 * @param nums		eg: nums = [1, 2, 3], target = 4
	 * @param target
	 * @return				7
	 */
	public int combinationSum4(int[] nums, int target) {  
		/*
		 * 分析：完全背包形式，组合数问题
		 */
        int[] dp = new int[target+1];
        
        dp[0] = 1;
        
        // 这题的关键是：顺序不同的序列被视作不同的组合，即求的是一个排列问题
        // 此时需将target放在外循环，将nums放在内循环。
        for(int i=1; i<=target; i++) {
        	for(int num : nums) {
        		if(i - num >= 0) {
        			dp[i] = dp[i] + dp[i-num];
        		}
        	}
        }        
        return dp[target];
    }
	
	/**
	 * @Description: 给定一个非负整数数组，a1, a2, ..., an, 和一个目标数，S。对于数组中的任意一个整数，你都可以从 + 或 -中选择一个符号添加在前面。
	 * 				   返回可以使最终数组和为目标数 S 的所有添加符号的方法数。
	 * @param nums		eg: nums:[1, 1, 1, 1, 1], S:3
	 * @param S		
	 * @return				5
	 */
	public int findTargetSumWays(int[] nums, int S) {
		/*
		 * 分析：0-1背包形式，组合数问题
		 */

        int sum = 0, n = nums.length;
        for(int num : nums){
            sum += num;
        }

        if(Math.abs(S) > Math.abs(sum)) return 0;
        
        // 这题的关键：表格的长度t要表示为：t=(sum*2)+1，其中sum表示nums中执行全部执行加/减能达到的数，1表示中间的0
        // 整个表格区域应该是分为三部分：-/0/+
        int t = sum*2;
        int[] dp = new int[t+1];	
        
        // base case
        if(nums[0] == 0){   
            dp[sum] = 2; // 因为加减0都得0
        }else{
            dp[sum+nums[0]] = 1;
            dp[sum-nums[0]] = 1;
        }

        // 0-1背包，nums放在外循环，target在内循环，且内循环倒序(状态压缩)；
        for(int i=1; i<n; i++){
            for(int j=t; j>=0; j--){	
                // 边界
            	// 这道题的关键不是nums[i]的选与不选，而是nums[i]是加还是减
                int l = j-nums[i]>=0 ? j-nums[i] : 0;
                int r = j+nums[i]<=t ? j+nums[i]: 0;
                
                dp[j] = dp[l] + dp[r];
            }
        }
        return dp[sum+S];
    }
	
	/**
	 * @Description: 给定一个非空字符串 s和一个包含非空单词的列表 wordDict，判定 s是否可以被空格拆分为一个或多个在字典中出现的单词。  拆分时可以重复使用字典中的单词。
	 * @param s				eg: s = "leetcode", wordDict = ["leet", "code"]
	 * @param wordDict
	 * @return					true(因为 "leetcode" 可以被拆分成 "leet code")
	 */
	public boolean wordBreak(String s, List<String> wordDict) {
		/*
		 * 分析： 完全背包形式，判断T/F问题
		 */
        int len = s.length();
        
        // s[0..i]是否可以被wordDict拆分
        boolean[] dp = new boolean[len+1];
        dp[0] = true;

        for(int i=0; i<len; i++){
            for(int j=i+1; j<=len; j++){
                if(wordDict.contains(s.substring(i,j))){
                    // 要不要把s[i..j)放进背包里，做选择： dp[j] = dp[i] || dp[j] ; 
                    dp[j] = dp[i] || dp[j] ;
                }
            }
        }
        return dp[len];
    }
	
	/**
	 * @Description: 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。 
	 * @param nums		eg:[1, 5, 11, 5]
	 * @return				true(数组可以分割成 [1, 5, 5] 和 [11])
	 */
	public boolean canPartition(int[] nums) {
		/*
		 * 分析：问题可以变形一下：是否可以从nums中挑选出一些正整数，使得这些数的和【等于】整个数组元素的和sum 的一半。
		 * ——> 0-1背包形式的判断T/F问题
		 */
        int n = nums.length;

        int sum = 0;
        for(int num : nums){
            sum += num;
        }

        if(sum % 2 != 0)    return false;
        else sum /= 2;

        boolean[] dp = new boolean[sum+1];
        dp[0] = true;

        for(int i=0; i<n; i++){
            for(int j=sum; j>=0; j--){
                if(j - nums[i] >= 0){
                    dp[j] = dp[j] || dp[j-nums[i]];
                }
            }
        }

        return dp[sum];
    }
	
	/**
	 * @Description: 给定不同面额的硬币 coins 和一个总金额 amount。计算可以凑成总金额所需的最少的硬币个数,你可以认为每种硬币的数量是无限的。如果没有任何一种硬币组合能组成总金额，返回 -1。
	 * @param coins		eg: coins = [1, 2, 5], amount = 11
	 * @param amount
	 * @return				3(11 = 5+5+1)
	 */
	public int coinChange(int[] coins, int amount) {
		/*
		 * 分析：完全背包形式的最值问题
		 */
        int[] dp = new int[amount+1];
        
        // base case,凑成amount至多只需要amout个1元硬币,所以取amonut+1相当于取了最大值
        for(int i=1; i<=amount; i++){
            dp[i] = amount+1;
        }    

        for(int coin : coins){
            for(int j=1; j<=amount; j++){
                if(j-coin >= 0){
                    dp[j] = Math.min(dp[j], dp[j-coin]+1);
                }
            }
        }

        return dp[amount]==amount+1? -1:dp[amount];
    }
	
	/**
	 * @Description: 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。请你找出并返回 strs 的最大子集的大小，该子集中 最多 有 m 个 0 和 n 个 1 。
	 * @param strs		eg: strs = ["10", "0001", "111001", "1", "0"], m = 5, n = 3
	 * @param m
	 * @param n
	 * @return				 4（最多有 5 个 0 和 3 个 1 的最大子集是 {"10","0001","1","0"}）
	 */
	public int findMaxForm(String[] strs, int m, int n) {
		/*
		 * 分析：在背包问题中，我们只有一种容量，而在这道题中，我们有 0 和 1 两种容量。每个物品（字符串）需要分别占用 0 和 1 的若干容量，并且所有物品的价值均为 1
		 * 		所以本质上还是0-1背包形式的最值问题，只不过有两个背包，所以状态有3个：「物品」、「背包1的容量」和「背包2的容量」
		 */
        int[][] dp = new int[m+1][n+1];

        //base case为dp[0][..][..] = 0, dp[..][0][0] = 0

        for(String s : strs){
            int[] cnt = countNums(s);
            
            for(int j=m; j>=0; j--){
                for(int k=n; k>=0; k--){
                    if(j-cnt[0]>=0 && k-cnt[1]>=0){
                        dp[j][k] = Math.max(dp[j][k], dp[j-cnt[0]][k-cnt[1]]+1);
                    }
                }
            }
        }

        return dp[m][n];
    }

    public int[] countNums(String s){
        int[] cnt = new int[2];

        char[] arr = s.toCharArray();
        for(char c : arr){
            cnt[c-'0']++;
        }

        return cnt;
    }

}
