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
	 *         if (nums[mid] < target){
	 *             left = mid+1;	// 搜索区间变为 [mid+1, right]
	 *             
	 *         }else {
	 *         	   right = mid-1;	// 收缩右侧边界
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
	 *         if (nums[mid] > target){
	 *             right = mid-1;	// 搜索区间变为 [left, mid-1]
	 *             
	 *         }else {
	 *         	   left = mid+1;	// 收缩左侧边界
	 *         }
	 *     }
	 *     
	 *     // 改为检查右侧出界情况即可
	 *     if (right < 0 || nums[right] != target)
	 *        return -1;
	 *        
	 *     return left;
	 * }
	 * 
	 */
	
	
	// 704，34
	// 875,1011
	
}
