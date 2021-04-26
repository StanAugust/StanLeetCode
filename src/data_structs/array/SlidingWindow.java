package data_structs.array;

import data_structs.linkedlist.TwoPointers;;

/**
 * @ClassName: SlidingWindow   
 * @Description: 双指针之滑动窗口   
 * @author Stan
 * @date: 2021年4月13日
 */
public class SlidingWindow {
	/*
	 * 双指针主要分为两类：
	 *  【快慢指针】：主要解决链表中的问题，比如典型的判定链表中是否包含环（详见{@link TwoPointers}）
	 * 	【左右指针】：主要解决数组（或者字符串）中的问题，比如二分查找（详见{@link BinarySearch}）、反转数组等
	 * 			 这边主要介绍滑动窗口算法，用来解决子串匹配问题
	 * 
	 * 框架：
	 * 
	 * Map<Character, Integer> need, window;
	 * 
	 * for(char c:子串){
	 *     need[c]++;
	 * }
	 * 
	 * int left = 0, right = 0;
	 * 
	 * while (right < 主串s.size()) {
	 *     // 增大窗口
	 *     window.add(s[right]);
	 *     right++;
	 *     // 进行窗口内数据的一系列更新
	 *     ...
	 *     
	 *     while (window needs shrink) {
	 *     	   // 缩小窗口
	 *     	   window.remove(s[left]);
	 *         left++;
	 *         // 进行窗口内数据的一系列更新
	 *         ...
	 *     }
	 * }
	 * 
	 */
	
	// 76,567,438,3,424
}
