package data_structs.array;

/**
 * @ClassName: BinarySearch   
 * @Description: 二分查找  
 * @author Stan
 * @date: 2021年4月18日
 */
public class BinarySearch {
	/*
	 * 二分查找的运用：
	 * 	【最常见】：在有序数组中搜索给定的某个目标值的索引
	 * 	【推广】：如果目标值存在重复，修改版的二分查找可以返回目标值的左侧边界索引或者右侧边界索引
	 * 	【再推广】：不考虑数组，只要当搜索空间有序，就可以通过二分进行剪枝
	 * 
	 * 基本框架（寻找一个数）：
	 * int binarySearch(int[] nums, int target) {
	 *     int left = 0, right = ...;
	 *     
	 *     while(...) {
	 *     	   int mid = left + (right - left) / 2;
	 *     
	 *     	   if (nums[mid] == target) {
	 *     	       ...
	 *     	   } else if (nums[mid] < target) {
	 *     	       left = ...
	 *     	   } else if (nums[mid] > target) {
	 *     	       right = ...
	 *     	   }
	 *     	}
	 *     	return ...;
	 * }
	 * 
	 * 寻找左侧边界框架：
	 * int left_bound(int[] nums, int target){
	 *     int left=0, right=nums.length-1;
	 *     
	 *     while (left <= right){
	 *         int mid = (left+right)/2;
	 *         
	 *         if (nums[mid] == target){
	 *         	   right = mid-1;	// 收缩右侧边界
	 *         
	 *         } else if (nums[mid] < target){
	 *             left = mid+1;	// 搜索区间变为 [mid+1, right]
	 *             
	 *         } else {
	 *         	   right = mid-1;	// 搜索区间变为 [left, mid-1]
	 *         }
	 *     }
	 *     
	 *     // 检查出界情况
	 *     if (left >= nums.length || nums[left] != target)
	 *        return -1;
	 *        
	 *     return left;
	 * }
	 * 
	 * 寻找右侧边界框架：
	 * int right_bound(int[] nums, int target){
	 *     int left=0, right=nums.length-1;
	 *     
	 *     while (left <= right){
	 *         int mid = (left+right)/2;
	 *         
	 *         if (nums[mid] == target){
	 *         	   left = mid+1;	// 收缩左侧边界
	 *         
	 *         } else if (nums[mid] > target){
	 *             right = mid-1;	// 搜索区间变为 [left, mid-1]
	 *             
	 *         } else {
	 *         	   left = mid+1;	// 搜索区间变为 [mid+1, right]
	 *         }
	 *     }
	 *     
	 *     // 改为检查右侧出界情况即可
	 *     if (right < 0 || nums[right] != target)
	 *        return -1;
	 *        
	 *     return right;
	 * }
	 * 
	 */
	
	
	// 704，34
	// 875,1011
	
	/**
	 * @Description: 704. 二分查找（最基本的框架）
	 * 					给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。 
	 * @param nums
	 * @param target
	 * @return
	 */
	public int search(int[] nums, int target) {
        int left=0, right=nums.length-1;

        while(left <= right){
            int mid = (left+right)/2;

            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }

        return -1;
    }
	
	/**
	 * @Description: 34. 在排序数组中查找元素的第一个和最后一个位置
	 * 					给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
	 * 					如果数组中不存在目标值 target，返回 [-1, -1]。
	 * 
	 * @param nums	 eg: nums = [5,7,7,8,8,10], target = 8
	 * @param target
	 * @return			 [3,4]
	 */
	public int[] searchRange(int[] nums, int target) {
		// 将寻找双边边界的问题转为寻找单边边界
        int left = getLeftBoundUseBinarySearch(nums, target);
        int right = getLeftBoundUseBinarySearch(nums, target+1);

        if(left==nums.length || nums[left]!=target)
            return new int[] {-1,-1};
        else
            return new int[] {left,right-1};
    }

	/*
	 * 通过二分查找确定左侧边界
	 */
    private int getLeftBoundUseBinarySearch(int[] nums, int target){
        int left=0, right=nums.length;

        while(left < right){
            int mid = (left+right)/2;

            if(nums[mid] >= target){
                right = mid;
            }else{
                left = mid+1;
            }
        }

        return left;
    }
	
    /**
     * @Description: 875. 爱吃香蕉的珂珂  
     * 					 这里有 N 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警卫已经离开了，将在 H 小时后回来。
     * 					珂珂可以决定她吃香蕉的速度 K （单位：根/小时）。每个小时，她将会选择一堆香蕉，从中吃掉 K 根。如果这堆香蕉少于 K 根，她将吃掉这堆的所有香蕉，然后这一小时内不会再吃更多的香蕉。  
     * 					珂珂喜欢慢慢吃，但仍然想在警卫回来前吃掉所有的香蕉。
     * 					返回她可以在 H 小时内吃掉所有香蕉的最小速度 K（K 为整数）。
     * 
     * @param piles	 eg: piles = [3,6,7,11], H = 8
     * @param h
     * @return			4
     */
    public int minEatingSpeed(int[] piles, int h) {

        // 暴力解法：速度最小是1，最大是max(piles)，因为一次最多也只能吃一堆
        // for(int speed=1; i<=max(piles); i++){
        //     // 以 speed 是否能在 h 小时内吃完香蕉
        //     if(canFinish(piles,speed,h))
        //         return speed;
        // }

        // 发现这个for循环就是在连续的空间搜索——>二分查找
        // 由于要求的是最小速度，所以用探求左侧边界的二分查找

        int left=1, right=getMaxPile(piles)+1;

        while(left < right){
            // 防止溢出
            int mid = left + right/2;

            if(canEat(piles, mid, h)){
                right = mid;
            }else{
                left = mid+1;
            }
        }
        return left;
    }

    /*
     * 以 speed是否能在 H小时内吃完piles
     */
    private boolean canEat(int[] piles, int speed, int h){
        int time = 0;

        for(int pile : piles){
            time += timeOf(pile, speed);
        }

        return time<=h;
    }

    /*
     * 以speed吃完pile所需时间 
     */
    private int timeOf(int pile, int speed){
        return (pile/speed) + ((pile%speed>0) ? 1:0);
    }

    /*
     * 返回piles中最大值
     */
    private int getMaxPile(int[] piles){
        int max = 0;
        for(int pile : piles){
            max = Math.max(max, pile);
        }
        return max;
    }
    
    
    /**
     * @Description: 1011. 在D天内送达包裹的能力  
     * 					传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量的顺序往传送带上装载包裹。我们装载的重量不会超过船的最大运载重量。
     * 					返回能在 D 天内将传送带上的所有包裹送达的船的最低运载能力。
     * 
     * @param weights	eg: weights = [1,2,3,4,5,6,7,8,9,10], D = 5
     * @param D
     * @return				15
     */
    public int shipWithinDays(int[] weights, int D) {
    	
    	// 本质上和上一题一样
        // 载重能力最低为max(weights)：一天只运一个包裹; 最高为sum(weights)：一天就可以全部运完
    	
        int left = getMax(weights), right = getSum(weights)+1;

        while(left < right){
            int mid = (left+right) / 2;

            if(canFinish(weights, mid, D)){
                right = mid;
            }else{
                left = mid+1;
            }
        }

        return left;
    }

    /*
     * 如果载重为 cap，是否能在 D 天内运完货物
     */
    private boolean canFinish(int[] weights, int cap, int D){
        int i = 0;
        for(int day=0; day<D; day++){
            int maxCap = cap;
            while((maxCap -= weights[i]) >= 0 ){
                i++;
                if(i == weights.length)
                    return true;
            }
        }

        return false;
    }

    private int getMax(int[] weights){
        int max = 0;
        for(int weight : weights){
            max = Math.max(max, weight);
        }
        return max;
    }

    private int getSum(int[] weights){
        int sum = 0;
        for(int weight : weights){
            sum += weight;           
        }
        return sum;
    }
}
