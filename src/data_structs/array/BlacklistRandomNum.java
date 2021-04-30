package data_structs.array;

import java.util.HashMap;
import java.util.Random;

/**
 * @ClassName: BlacklistRandomNum   
 * @Description: 710. 黑名单中的随机数 
 * 					给定一个包含 [0，n) 中独特的整数的黑名单 B，写一个函数从 [0，n) 中返回一个不在 B 中的随机整数。
 * 					对它进行优化使其尽量少调用系统方法 Math.random() 。
 * 
 * eg: 输入: (输入是两个列表：调用成员函数名和调用的参数。BlacklistRandomNum的构造函数有两个参数，N 和黑名单 B。pick 没有参数，输入参数是一个列表，即使参数为空，也会输入一个 [] 空列表。)
 * 
 * 			["BlacklistRandomNum","pick","pick","pick"]
 * 			[[4,[2]],[],[],[]]
 * 	         输出: 
 * 			[null,1,3,1]
 * 
 * 
 * @author Stan
 * @date: 2021年4月30日
 */
/*
 * 整体思路: 可以将区间 [0,N) 看做一个数组，然后将 blacklist 中的元素移到数组的最末尾，同时用一个哈希表进行映射
 *
 * 1. 如果想高效地，等概率地随机获取元素，就要使用数组作为底层容器;
 * 2. 如果要保持数组元素的紧凑性，可以把待删除元素换到最后，然后 pop 掉末尾的元素，这样时间复杂度就是 O(1)了。记住需要额外的哈希表记录值到索引的映射。
 */

public class BlacklistRandomNum {
	int size;   // 最终数组中元素个数
    HashMap<Integer, Integer> map = new HashMap<>();

    public BlacklistRandomNum(int N, int[] blacklist) {
        size = N-blacklist.length;
        // 先将所有黑名单数字加入 map
        for(int black : blacklist){
            // 这里赋值多少都可以
            // 目的仅仅是把键存进哈希表
            // 方便快速判断数字是否在黑名单内
            map.put(black, 666);
        }

        int last = N-1;
        for(int black : blacklist){
            // 如果 b 已经在区间 [size, N)
            if(black >= size){
                continue;
            }
            // 可以直接忽略
            // 跳过所有黑名单中的数字
            while(map.containsKey(last)){
                last--;
            }

            // 将黑名单中的索引映射到合法数字
            map.put(black, last);
            last--;
        }

    }
    
    public int pick() {

        int randomIdx = new Random().nextInt(size);

        // 这个索引命中了黑名单，
        // 需要被映射到其他位置
        if(map.containsKey(randomIdx)){
            return map.get(randomIdx);
        }

        // 若没命中黑名单，则直接返回
        return randomIdx;
    }
}
