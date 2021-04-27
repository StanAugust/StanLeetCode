package data_structs.advanced;

import java.util.ArrayList;

/**
 * @ClassName: UseMonotonicQueue   
 * @Description: 239. 滑动窗口最大值   
 * 						给定一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 * 						返回滑动窗口中的最大值。
 * @author Stan
 * @date: 2021年4月27日
 */
public class UseMonotonicQueue {
	
	public int[] maxSlidingWindow(int[] nums, int k) {
		
		MonotonicQueue window = new MonotonicQueue();
		ArrayList<Integer> res = new ArrayList<>();
		
		for(int i=0; i<nums.length; i++) {
			if(i < k-1) {
				// 先填满窗口的前k-1
				window.push(nums[i]);
			}else {
				// 窗口向前滑动，加入新数字
				window.push(nums[i]);
				 // 记录当前窗口的最大值
				res.add(window.max());
				// 移除旧数字
				window.pop(nums[i-k+1]);
			}
		}
		
//		int[] arr = new int[res.size()];
//		
//      for (int i = 0; i < res.size(); i++) {
//          arr[i] = res.get(i);
//      }
//      return arr;
        
		return res.stream().mapToInt(Integer :: valueOf).toArray();	
	}
}
