package dynamic_programming;

/**
 * @ClassName: StockIssues   
 * @Description: 股票买卖系列问题   
 * @author Stan
 * @date: 2020年10月26日
 */
public class StockIssues {
	/*
	 * for 状态1 in 状态1的所有取值：
	 * 	for 状态2 in 状态2的所有取值：
	 * 		for ...
	 * 			dp[状态1][状态2][...] = 择优(选择1，选择2...)
	 * 
	 * 这个问题，每天都有三种「选择」：买入、卖出、无操作，我们用 buy, sell, rest 表示这三种选择。
	 * 但问题是，并不是每天都可以任意选择这三种选择的，因为 sell 必须在 buy 之后，buy 必须在 sell 之后。
	 * 那么 rest 操作还应该分两种状态，一种是 buy 之后的 rest（持有了股票），一种是 sell 之后的 rest（没有持有股票）。
	 * 而且还有交易次数 k 的限制，就是说 buy 还只能在 k > 0 的前提下操作。
	 * 
	 * 这个问题的「状态」有三个，第一个是天数，第二个是允许交易的最大次数，第三个是当前的持有状态（即之前说的 rest 的状态，我们不妨用 1 表示持有，0 表示没有持有）。然后用一个三维数组就可以装下这几种状态的全部组合
	 * 
	 * 想求的最终答案是 dp[n-1][K][0]，即最后一天，最多允许 K 次交易，最多获得多少利润。因为 [1]代表手上还持有股票，[0]表示手上的股票已经卖出去了，很显然后者得到的利润一定大于前者。
	 */
	
	/**
	 * @Description: (k=any integer)给定一个整数数组prices，它的第 i个元素 prices[i]是一支给定的股票在第 i天的价格。设计一个算法来计算你所能获取的最大利润。你最多可以完成 k笔交易。
	 * 				注意: 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。  
	 * @param k
	 * @param prices
	 * @return
	 */
	public int maxProfit_k_any(int k, int[] prices) {
		int n = prices.length;
        if(n < 1)   return 0;

        // 一次交易由买入和卖出构成，至少需要两天。所以说有效的k 应该不超过 n/2，如果超过，就没有约束作用了，相当于 k = +infinity。
        if(k > n/2){
            return maxProfit_k_inf(prices);
        }
        
//        原始框架
//        int[][][] dp = new int[n][k+1][2];
//
//        for(int i=0; i<n; i++){
//            for(int j=1; j<=k; j++){
//                // base case
//                if(i == 0){
//                    dp[i][j][0] = 0;
//                    dp[i][j][1] = -prices[i];
//                    continue;
//                }
//                dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1]+prices[i]);	 // 今天我没有持有股票，有两种可能:要么是我昨天就没有持有，然后今天选择 rest，所以我今天还是没有持有;要么是我昨天持有股票，但是今天我 sell 了，所以我今天没有持有股票了。
//                dp[i][j][1] = Math.max(dp[i-1][j][1], dp[i-1][j-1][0]-prices[i]);// 今天我持有着股票，有两种可能:要么我昨天就持有着股票，然后今天选择 rest，所以我今天还持有着股票;要么我昨天本没有持有，但今天我选择 buy，所以今天我就持有股票了。
//            }
//        }
//
//        return dp[n-1][k][0];
        
        // 由于今天的状态值只参考了昨天的状态值，如果为了节省空间也可以直接把第一维去掉。(为了过LeetCode某些极端测试用例)
        int[][] dp = new int[k+1][2];
        // base case
        for(int i=0; i<=k; i++){
            dp[i][0] = 0;
            dp[i][1] = -prices[0];
        }

        for(int i=0; i<n; i++){
            for(int j=1; j<=k; j++){
                dp[j][0] = Math.max(dp[j][0], dp[j][1]+prices[i]);
                dp[j][1] = Math.max(dp[j][1], dp[j-1][0]-prices[i]);
            }
        }

        return dp[k][0];
	}
	/**
	 * @Description: (k= +infinity)可以尽可能地完成更多的交易（多次买卖一支股票）  
	 * @param prices
	 * @return
	 */
	public int maxProfit_k_inf(int[] prices){
		int n = prices.length;
		if(n < 1) return 0;
		
		int[][] dp = new int[n][2];
		
		for(int i=0; i<n; i++){
			// base case
			if(i-1 == -1){
				dp[i][0] = 0;
				dp[i][1] = -prices[i];
				continue;
			}
			// 如果 k 为正无穷，那么就可以认为 k 和 k - 1 是一样的。可以这样改写框架：
			// dp[i][k][0] = max(dp[i-1][k][0], dp[i-1][k][1] + prices[i])
			// dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i])
			//             = max(dp[i-1][k][1], dp[i-1][k][0] - prices[i])
			
			dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1]+prices[i]);
			dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0]-prices[i]);
		}
		
		return dp[n-1][0];
		
		// 由于今天的状态值只参考了昨天的状态值，可以直接把第一维去掉。进行dp数组的空间压缩
//		int n = prices.length;
//	      
//        int dp_i_0=0, dp_i_1=Integer.MIN_VALUE;
//            
//        for(int i=0; i<n; i++){
//            int tmp = dp_i_0;
//            dp_i_0 = Math.max(dp_i_0, dp_i_1+prices[i]);
//            dp_i_1 = Math.max(dp_i_1, tmp-prices[i]);
//        }
//        
//        return dp_i_0;
	}
	
	/**
	 * @Description: (k= +infinity with cooldown)在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
	 * 					你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
	 * 					卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
	 * @param prices
	 * @return
	 */
	public int maxProfit_with_cool(int[] prices) {
		int n = prices.length;
        if(n < 1) return 0;
        
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        int dp_pre_0 = 0;    // 代表 dp[i-2][0]

        for(int i=0; i<n; i++){
            int pre = dp_i_0;

            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, dp_pre_0 - prices[i]); //每次 sell 之后要等一天才能继续交易。第 i 天选择 buy 的时候，要从 i-2 的状态转移，而不是 i-1 。

            dp_pre_0 = pre;
        }

        return dp_i_0;
	}
	
	/**
	 * @Description: (k= +infinity with fee)你可以无限次地完成交易，但是你每笔交易都需要付手续费。这里的一笔交易指买入持有并卖出股票的整个过程  
	 * @param prices
	 * @param fee
	 * @return
	 */
	public int maxProfit_with_fee(int[] prices, int fee) {
        int n = prices.length;
        if(n < 1)   return 0;
        
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;

        for(int i=0; i<n; i++){
            // int tmp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1+prices[i]);
            dp_i_1 = Math.max(dp_i_1, dp_i_0-prices[i]-fee);	// 每次交易要支付手续费，只要把手续费从利润中减去即可。
        }

        return dp_i_0;
    }
	
	/**
	 * @Description: (k=1)最多只允许完成一笔交易（即买入和卖出一支股票一次）  
	 * @param prices
	 * @return
	 */
	public int maxProfit_k_1(int[] prices) {
		int n = prices.length;

        if(n < 1)   return 0;

        int[][] dp = new int[n][2];

        for(int i=0; i<n; i++){
            // base case
            if(i-1 == -1){
                dp[i][0] = 0;
                dp[i][1] = -prices[i];
                continue;
            }
            
            // dp[i][1][0] = max(dp[i-1][1][0], dp[i-1][1][1] + prices[i])
            // dp[i][1][1] = max(dp[i-1][1][1], dp[i-1][0][0] - prices[i]) 
            //		       = max(dp[i-1][1][1], -prices[i])
            // 解释：k = 0 的 base case，所以 dp[i-1][0][0] = 0。

    		// 现在发现 k 都是 1，不会改变，即 k 对状态转移已经没有影响了。
    		// 可以进行进一步化简去掉所有 k：
			//	dp[i][0] = max(dp[i-1][0], dp[i-1][1] + prices[i])
			//	dp[i][1] = max(dp[i-1][1], -prices[i])
            
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1]+prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], -prices[i]);
        }

        return dp[n-1][0];
	}
	
	/**
	 * @Description: 最多可以完成 两笔 交易  
	 * @param prices
	 * @return
	 */
	public int maxProfit_k_2(int[] prices) {
		// k=2 和前面题目的情况稍微不同，因为上面的情况都和 k的关系不太大。要么 k是正无穷，状态转移和 k没关系了；要么 k=1，跟 k=0 这个base case挨得近，最后也没有存在感。
		// k=2 和前面讲的 k是任意正整数的情况中，对 k的处理就凸显出来了，由于没有消掉 k 的影响，所以必须要对 k进行穷举
		int n = prices.length;
        if(n < 1)   return 0;

        int k=2;
        int[][][] dp = new int[n][k+1][2];

        for(int i=0; i<n; i++){
            for(int j=1; j<=k; j++){
                // base case
                if(i == 0){
                    //第i天，还有j次，手里没有股票，当i=0，手里没股票，最大利润为0
                    dp[i][j][0] = 0;
                    //当i=0，手里有股票，因为还没有盈利，最大利润为 负prices[i]
                    dp[i][j][1] = -prices[i];
                    continue;
                }

                dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i-1][j][1], dp[i-1][j-1][0] - prices[i]);
            }
        }
        return dp[n-1][k][0];
	}
}
