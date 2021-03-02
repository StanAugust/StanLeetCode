package dynamic_programming;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @ClassName: GreedyAlgorithm   
 * @Description: 贪心算法   
 * @author Stan
 * @date: 2020年12月16日
 */
public class GreedyAlgorithm {
	/*
	 * 贪心选择性质：每一步都做出一个局部最优的选择，最终的结果就是全局最优。但这是一种特殊性质，其实只有一部分问题拥有这个性质。
	 * 
	 * 贪心算法可以认为是动态规划算法的一个特例，相比动态规划，贪心算法效率比动态规划要高
	 * 比如说一个算法问题使用暴力解法需要指数级时间，如果能使用动态规划消除重叠子问题，就可以降到多项式级别的时间，如果满足贪心选择性质，那么可以进一步降低时间复杂度，达到线性级别的
	 */
	
	/*
	 * 贪心算法之区间调度问题：有很多形如 [start, end] 的闭区间，请你算出这些区间中【最多有几个互不相交的区间】
	 * 	思路：
	 * 		1.从区间集合 intervals 中选择一个区间 x，这个 x 是在当前所有区间中结束最早的（end 最小）
	 * 		2.把所有与 x 区间相交的区间从区间集合 intervals 中删除
	 * 		3.重复步骤 1 和 2，直到 intervals 为空为止。之前选出的那些 x就是最大不相交子集。
	 */
	/**
	 * @Description: 435.给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠。  
	 * @param intervals		eg:[ [1,2], [2,3], [3,4], [1,3] ]
	 * @return					1(移除 [1,3]后，剩下的区间没有重叠)
	 */
	public int eraseOverlapIntervals(int[][] intervals) {
        int n = intervals.length;
        if(n == 0)  return 0;
        
        // 按区间终点end进行升序排序
        Arrays.sort(intervals, new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b){
                return a[1]-b[1];
            }
        });

        int count = 1;    // 表示有几个区间不重叠，初始化为1，至少有一个区间不相交

        int x_end = intervals[0][1];	// 排序后，第一个区间就是 x
        
        for(int[] interval : intervals){
            int start = interval[0];
            if(start >= x_end){
                count++;
                x_end = interval[1];	// 找到下一个选择的区间了
            }
        }

        return n-count;
    }
	
	/**
	 * @Description: 452.用最少的箭头射爆气球
	 * @param points	eg:[[10,16],[2,8],[1,6],[7,12]]
	 * @return				2(x=6 可以射爆 [2,8],[1,6] 两个气球，以及 x=11 射爆另外两个气球)
	 */
	public int findMinArrowShots(int[][] points) {
        int n = points.length;
        if(n == 0)  return 0;

        // 按区间终点end进行升序排序
        Arrays.sort(points, new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b){
                return Integer.compare(a[1],b[1]);
            }
        });

        int count = 1;

        int x_end = points[0][1];
        for(int[] point : points){
            int start = point[0];
            if(start > x_end){
                count++;
                x_end = point[1];
            }
        }

        return count;
    }
	
	/**
	 * @Description: 646.当且仅当 b<c 时，数对(c,d) 才可以跟在 (a,b) 后面。我们用这种形式来构造一个数对链。  
	 * 				   给定一个数对集合，找出能够形成的最长数对链的长度。不需要用到所有的数对，可以以任何顺序选择其中的一些数对来构造。
	 * 
	 * @param pairs		eg:[[1,2], [2,3], [3,4]]
	 * @return				2(最长的数对链是 [1,2] -> [3,4])
	 */
	public int findLongestChain(int[][] pairs) {
        // 按区间终点end进行升序排序
        Arrays.sort(pairs, new Comparator<int[]>(){
            @Override
            public int compare(int[] a, int[] b){
                return a[1]-b[1];
            }
        });

        int count = 1;

        int x_end = pairs[0][1];
        for(int[] pair : pairs){
            int start = pair[0];
            if(start > x_end){
                count++;
                x_end = pair[1];
            }
        }

        return count;
    }
	
	/*
	 * 贪心算法之跳跃游戏：
	 */
	/**
	 * @Description: 55.给定一个非负整数数组 nums，你最初位于数组的 第一个下标 。数组中的每个元素代表你在该位置可以跳跃的最大长度。
	 * 					判断你是否能够到达最后一个下标。
	 * 
	 * @param nums		eg:[3,2,1,0,4]
	 * @return				false(无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0 ， 所以永远不可能到达最后一个下标)
	 */
	public boolean canJump(int[] nums) {
		int n = nums.length;
        int farthest = 0;
        
        // 思路：请问通过题目中的跳跃规则，最多能跳多远？如果能够越过最后一格，返回 true，否则返回 false
        
        for(int i=0; i<n-1; i++){
            // 每一步都计算一下从当前位置最远能够跳到哪里，然后和全局的最远位置 farthest 做对比，通过每一步的最优解，更新全局最优解，这就是贪心
            farthest = Math.max(farthest, i+nums[i]);
            // 可能碰到了 0，卡住跳不动了
            if(farthest <= i)   return false;
        }

        return farthest >= n-1;
	}
	
	/**
	 * @Description: 45.给定一个非负整数数组，你最初位于数组的第一个位置。数组中的每个元素代表你在该位置可以跳跃的最大长度。
						你的目标是使用最少的跳跃次数到达数组的最后一个位置。

	 * @param nums		eg:[2,3,1,1,4]
	 * @return				2(从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置)
	 */
	public int jump(int[] nums) {

        int n = nums.length;
        int cur_end = 0, farthest = 0, step = 0;

        for(int i=0; i<n-1; i++){
            // i和cur_end标记了可以选择的跳跃步数，farthest 标记了[i..cur_end]中能够跳到的最远距离，step记录了跳跃次数
            farthest = Math.max(farthest, i+nums[i]);

            if(i == cur_end){
                step++;
                cur_end = farthest;
            }
        }

        return step;
    }
	
	

}
