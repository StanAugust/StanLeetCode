package data_structs.advanced;

import java.util.HashMap;
import java.util.Stack;

/**
 * @ClassName: FreqStack   
 * @Description: 895. 最大频率栈   
 * 					FreqStack 有两个函数：
 * 						push(int x)，将整数 x 推入栈中。
 * 						pop()，它移除并返回栈中出现最频繁的元素。如果最频繁的元素不只一个，则移除并返回最接近栈顶的元素。
 * @author Stan
 * @date: 2021年4月26日
 */
public class FreqStack {
    // 记录 FreqStack 中元素的最大频率
    int maxFreq = 0;
    // 记录 FreqStack 中每个 val 对应的出现频率
    HashMap<Integer, Integer> val_freq = new HashMap<>();
    // 记录频率 freq 对应的 val 列表
    HashMap<Integer, Stack<Integer>> freq_val = new HashMap<>();

    public FreqStack() {

    }
    
    public void push(int val) {
        // 修改VF表：val 对应的 freq 加一
        int freq = val_freq.getOrDefault(val, 0)+1;
        val_freq.put(val, freq);
        
        // 修改 FV 表：在 freq 对应的列表加上 val
        // 这边为什么修改 FV 表只需要在新的 freq 上 push，而不需要在旧的 freq 上pop ? : 因为push()和pop()是配合着调用的，模拟一遍示例数据即可明白
        freq_val.putIfAbsent(freq, new Stack<>());
        freq_val.get(freq).push(val);
        
        // 更新 maxFreq
        maxFreq = Math.max(maxFreq,freq);
    }
    
    public int pop() {
        // 修改 FV 表：pop 出一个 maxFreq 对应的元素 v
        Stack<Integer> val = freq_val.get(maxFreq);
        int v = val.pop();

        // 修改 VF 表：v 对应的 freq 减一
        int freq = val_freq.get(v)-1;
        val_freq.put(v, freq);

        // 更新 maxFreq
        if(val.isEmpty()){
            maxFreq--;
        }
        
        return v;
    }
}