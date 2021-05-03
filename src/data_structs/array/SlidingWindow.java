package data_structs.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	/**
	 * @Description: 76. 最小覆盖子串
	 *   				给定一个字符串 s、一个字符串 t 。返回 s中涵盖t所有字符的最小子串。如果 s中不存在涵盖 t所有字符的子串，则返回空字符串 "" 。
	 *   
	 * @param s		eg: s = "ADOBECODEBANC", t = "ABC"
	 * @param t
	 * @return			"BANC"
	 */
	public String minWindow(String s, String t) {
		
		// 两个hashmap要分开定义，否则会指向同一个map
		HashMap<Character, Integer> need = new HashMap<>();
		HashMap<Character, Integer> window = new HashMap<>();
		
		for(char c : t.toCharArray()) {
			need.put(c, need.getOrDefault(c, 0)+1);
		}
		
		int left=0, right=0;
		
		// 表示窗口中满足 need 条件的字符个数
        // 如果 valid 和 need.size 的大小相同，则说明窗口已满足条件，已经完全覆盖了串 t
		int valid = 0;
		
		// 记录最小覆盖子串的起始索引及长度
        int start=0, len=Integer.MAX_VALUE;
		
		while(right < s.length()) {
			// c 是将移入窗口的字符
			char c = s.charAt(right);
			// 右移窗口
			right++;
			
			// 进行窗口内数据的一系列更新
			if(need.containsKey(c)) {
				window.put(c, window.getOrDefault(c, 0)+1);
				
				if(window.get(c).equals(need.get(c))) {
					valid++;
				}
			}
			
			// 判断左侧窗口是否要收缩
			while(valid == need.size()) {
				// 在这里更新最小覆盖子串
				if(right-left < len) {
					start = left;
					len = right-left;
				}
				
				// d 是将移出窗口的字符
				char d = s.charAt(left);
				// 左移窗口
				left++;
				
				// 进行窗口内数据的一系列更新
				if(need.containsKey(d)) {
					if(window.get(d).equals(need.get(d))) {
						valid--;
					}
					
					window.put(d, window.get(d)-1);
				}
			}
		}
		
		// 返回最小覆盖子串
		return len==Integer.MAX_VALUE ? "" : s.substring(start, start+len);
	}
	
	/**
	 * @Description: 567. 字符串的排列  
	 * 					给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的排列。
	 * 					换句话说，第一个字符串的排列之一是第二个字符串的子串 。
	 * 
	 * @param s1	eg: s1 = "ab" s2 = "eidbaooo"
	 * @param s2
	 * @return			True("ba")
	 */
	public boolean checkInclusion(String s1, String s2) {
		
		HashMap<Character, Integer> need = new HashMap<>();
		HashMap<Character, Integer> window = new HashMap<>();
		
		for(char c : s1.toCharArray()) {
			need.put(c, need.getOrDefault(c, 0)+1);
		}
		
		int left=0, right=0;
		int valid = 0;
		
		while(right < s2.length()) {
			char c = s2.charAt(right);
			right++;
			
			// 窗口扩大时，应该更新哪些数据
			if(need.containsKey(c)) {
				window.put(c, window.getOrDefault(c, 0)+1);
				
				if(window.get(c).equals(need.get(c))) {
					valid++;
				}
			}
			
			// 判断左侧窗口是否要收缩
			while(right - left >= s1.length()) {
				// 在这里判断是否找到了合法的子串
				if(valid == need.size()) {
					return true;
				}
				
				char d = s2.charAt(left);
				left++;
				
				// 窗口缩小时，应该更新哪些数据
				if(need.containsKey(d)) {					
					if(window.get(d).equals(need.get(d))) {
						valid--;
					}
					window.put(d, window.get(d)-1);
				}
				
			}
		}
		
		return false;
    }
	
	/**
	 * @Description: 438. 找到字符串中所有字母异位词(字母相同，但排列不同的字符串)
	 * 					给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，返回这些子串的起始索引。
	 * 
	 * @param s		eg: s: "cbaebabacd" p: "abc"
	 * @param p
	 * @return			[0, 6] (起始索引等于 0 的子串是 "cba", 它是 "abc" 的字母异位词; 起始索引等于 6 的子串是 "bac", 它是 "abc" 的字母异位词。)
	 */
	public List<Integer> findAnagrams(String s, String p) {
		
		HashMap<Character, Integer> need = new HashMap<>();
		HashMap<Character, Integer> window = new HashMap<>();
		
		for(char c : p.toCharArray()) {
			need.put(c, need.getOrDefault(c, 0)+1);
		}
		
		int left=0, right=0;
		int valid = 0;
		List<Integer> ans = new ArrayList<>();
		
		while(right < s.length()) {
			char c = s.charAt(right);
			right++;
			
			// 窗口扩大时，应该更新哪些数据
			if(need.containsKey(c)) {
				window.put(c, window.getOrDefault(c, 0)+1);
				
				if(window.get(c).equals(need.get(c))) {
					valid++;
				}
			}
			
			// 判断左侧窗口是否要收缩
			while(right - left >= p.length()) {
				// 在这里判断是否找到了合法的子串
				if(valid == need.size()) {
					ans.add(left);
				}
				
				char d = s.charAt(left);
				left++;
				
				// 窗口缩小时，应该更新哪些数据
				if(need.containsKey(d)) {					
					if(window.get(d).equals(need.get(d))) {
						valid--;
					}
					window.put(d, window.get(d)-1);
				}
				
			}
		}
		
		return ans;
    }
	
	/**
	 * @Description: 3. 无重复字符的最长子串
	 * 					给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度  
	 * 
	 * @param s		eg: s = "abcabcbb"
	 * @return			3 (因为无重复字符的最长子串是 "abc"，所以其长度为 3)
	 */
	public int lengthOfLongestSubstring(String s) {
		
		// 单个字符串匹配的问题，只需要window即可
		HashMap<Character, Integer> window = new HashMap<>();

        int left=0,right=0;
        int len = 0;

        while(right < s.length()){
            char c = s.charAt(right);
            right++;

            window.put(c, window.getOrDefault(c,0)+1);
            
            // 当 window[c] 值大于 1 时，说明窗口中存在重复字符，不符合条件，就该移动 left 缩小窗口了
            while(window.get(c) > 1){
                char d = s.charAt(left);
                left++;

                window.put(d, window.get(d)-1);
            }
            
            // 要在收缩窗口完成后更新答案，因为窗口收缩的 while 条件是存在重复元素，所以收缩完成后一定保证窗口中没有重复
            len = Math.max(len, right-left);

        }

        return len;
    }

	/**
	 * @Description: 424. 替换后的最长重复字符  
	 * 					给定一个仅由大写英文字母组成的字符串，可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。在执行上述操作后，找到包含重复字母的最长子串的长度。
	 * 
	 * @param s		eg: s = "AABABBA", k = 1
	 * @param k
	 * @return			4 (将中间的一个'A'替换为'B',字符串变为 "AABBBBA"。子串 "BBBB" 有最长重复字母)
	 */
	public int characterReplacement(String s, int k) {
		// 单个字符串匹配的问题，只需要window即可
		HashMap<Character, Integer> window = new HashMap<>();

        int left=0, right=0;
        int max = 0;    // 记录单个字符出现的最大次数
        
        while(right < s.length()){
            char c = s.charAt(right);
            right++;

            window.put(c, window.getOrDefault(c,0)+1);
            // 在这里维护 max ，因为每一次右边界读入一个字符，字符频数增加，才会使得 max 增加
            max = Math.max(max, window.get(c));
            
            // 满足该区间内除了出现次数最多的那一类字符之外，剩余的字符数量不超过 k 个
            // 如果不满足，说明此时k不够用，需要收缩窗口
            while(right-left-max > k){
            	char d = s.charAt(left);	
            	left++;
            	
            	window.put(d, window.get(d)-1);
            	
            	/*
            	 * 这边窗口收缩了，但不需要每次都把max更新
            	 * 因为根据我们的算法，当max和k一定时，区间最大长度也就定了。且k是不变的,max是可能变化的，因此，只有max增加的情况，len才能拿到最大值。
            	 * 
            	 * 所以当我们找到一个max之后，就能说找到了一个长度为d = max+k的合法区间，所以最终答案一定不小于d。
            	 * 
            	 * 所以，当发现继续向右扩展right不合法的时候，不需要不断地右移left，只需要保持区间长度为d向右滑动即可。
            	 * 如果有某个合法区间大于d，说明这个区间一定在某个时刻存在count[t]+1>max，
            	 * 这时再去更新max即可。
            	 */         
            }
        }

        return right-left;
	}
}
