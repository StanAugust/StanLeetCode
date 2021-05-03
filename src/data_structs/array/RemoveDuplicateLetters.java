package data_structs.array;

import java.util.Stack;

/**
 * @ClassName: RemoveDuplicateLetters   
 * @Description: 316/1081.	去除重复字母    
 * @author Stan
 * @date: 2021年5月3日
 */
public class RemoveDuplicateLetters {
	/**
	 * @Description: 给定一个字符串 s ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 返回结果的字典序最小（要求不能打乱其他字符的相对位置）。  
	 * @param s
	 * @return
	 */
	public String removeDuplicateLetters(String s) {
		Stack<Character> stack = new Stack<>();
		char[] str = s.toCharArray();
		
		// 维护一个数组记录字符串中各字符的数量
		int[] count = new int[26];
		for(char c : str){
			count[c-'a']++;
		}
		
		// boolean数组初始值为 false，记录栈中是否存在某个字符
		boolean[] inStack = new boolean[26];
		
		for(char c : str){
			// 每遍历过一个字符，都将对应的计数减一
			count[c-'a']--;
			
			if(inStack[c-'a'])  continue;
			
			// 单调栈保证最后的子序列满足字典序最小
			while(!stack.isEmpty() && stack.peek()>c){
				// 若之后不存在栈顶元素了，则停止 pop
				if(count[stack.peek()-'a'] == 0){
					break;
				}
				// 否则弹出
				inStack[stack.pop()-'a'] = false;
			}
			
			inStack[c-'a'] = true;
			stack.push(c);
		}
		
		StringBuilder sb = new StringBuilder();
		while(!stack.isEmpty()){
			sb.append(stack.pop());
		}
		
		return sb.reverse().toString();
	}
	
	/*
	 * 题目的要求总结出来有三点：
	 * 1. 要去重。
	 * 2. 去重字符串中的字符顺序不能打乱s中字符出现的相对顺序。
	 * 3. 在所有符合上一条要求的去重字符串中，字典序最小的作为最终结果。
	 * 
	 * 如果不考虑要求3，用 stack 和布尔数组 inStack 即可实现要求1和2
	 * 
	 * 如果要满足要求3，保证字典序，需要做些什么修改?
	 * 	     在向栈中插入字符'a'的这一刻，我们的算法需要知道，字符'a'的字典序和之前的两个字符'b'和'c'相比，谁大谁小?
	 * 	     如果当前字符'a'比之前的字符字典序小，就有可能需要把前面的字符 pop 出栈，让'a'排在前面
	 * ————用单调栈可以解决
	 * 
	 * 加入单调栈之后，还有一种情况需要考虑： 
	 * 	     在插入栈时，如果此时栈顶元素在整个s中只有一个，那么即使它的字典序较大，也不应该被pop
	 * 所以算法在stk.peek() > c 时会 pop 元素，其实这时候应该分两种情况：
	 * 	  1. 如果stk.peek()这个字符之后还会出现，那么可以把它 pop 出去，后面再 push 到栈里，刚好符合字典序的要求；
	 * 	  2. 如果stk.peek()这个字符之后不会出现了，前面也说了栈中不会存在重复的元素，那么就不能把它 pop 出去，否则就永远失去了这个字符。
	 * 那么关键就在于，如何让算法知道字符'a'之后有几个'b'有几个'c'呢?
	 * ————只需要维护一个count数组记录字符串中各字符的数量
	 * 
	 */
}
