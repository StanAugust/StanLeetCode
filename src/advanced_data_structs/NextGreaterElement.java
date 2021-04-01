package advanced_data_structs;

import java.util.Stack;

/**
 * @ClassName: NextGreaterElement   
 * @Description: 单调栈的应用   
 * @author Stan
 * @date: 2021年3月30日
 */
public class NextGreaterElement {
	/*
	 * 单调栈实际上就是栈，只是用了一些巧妙的逻辑，使得每次新元素入栈后，栈内的元素都保持有序
	 * 
	 * 单调栈用途不太广泛，只用来处理 NextGreaterElement 这一典型问题
	 */
	
	/**
	 * @Description: 单调栈模板  
	 * @param nums
	 * @return
	 * 时间复杂度：O(n),整体看每个元素都被push了一次，最多被pop一次，不要被for嵌套while迷惑
	 */
	public int[] nextGreaterElement(int[] nums) {
		int n = nums.length;

        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();

        // for循环要从后往前扫描
        for(int i=n-1; i>=0; i--){

            // 把两个大值之间的元素排除，因为小值的存在没有意义
            while(!stack.empty() && stack.peek()<=nums[i]){
                stack.pop();
            }

            res[i] = stack.empty()? -1 : stack.peek();
            
            stack.push(nums[i]);
        }
        return res;
	}
	
	/**
	 * @Description: 739.根据每日气温列表，重新生成一个列表，计算：对于每一天，还要至少等多少天才能等到一个更暖和的气温；如果等不到那一天，填 0
	 * @param T		eg: T = [73,74,75,71,69,76]
	 * @return		    [1,1,3,2,1,0]
	 */
	public int[] dailyTemperatures(int[] T) {

        int days = T.length;
        int[] res = new int[days];

        Stack<Integer> stack = new Stack<>();

        for(int i=days-1; i>=0; i--){
        	
        	// 求当前距离 next greater number 的距离，将值改为下标即可
            while(!stack.empty() && T[stack.peek()]<=T[i]){
                stack.pop();
            }

            res[i] = stack.empty()? 0 : (stack.peek()-i);

            stack.push(i);
        }

        return res;
    }
	
	/**
	 * @Description: 503. 给定一个循环数组，输出每个元素的下一个更大元素。
	 * @param nums	eg: nums = [1,2,1]
	 * @return			[2,-1,2]
	 */
	public int[] nextGreaterElementCicularly(int[] nums) {

        int n = nums.length;

        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();

        // 环形数组，常用套路就是将数组长度翻倍：[1,2,1]->[1,2,1,1,2,1]
        // 但是可以不用真的构造新数组，只需模拟长度翻倍即可
        for(int i=2*n-1; i>=0; i--){

            // 索引求模，其它的和模板一样
            while(!stack.empty() && stack.peek()<=nums[i%n]){
                stack.pop();
            }

            res[i%n] = stack.empty()? -1 : stack.peek();
            
            stack.push(nums[i%n]);
        }

        return res;
    }
}
