package data_structs.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: RandomizedSet   
 * @Description: 380. 常数时间插入、删除和获取随机元素   
 * 					设计一个支持在平均 时间复杂度 O(1) 下，执行以下操作的数据结构。
 * 					1. insert(val)：当元素 val 不存在时，向集合中插入该项；
 * 					2. remove(val)：元素 val 存在时，从集合中移除该项；
 * 					3. getRandom()：随机返回现有集合中的一项。每个元素应该有相同的概率被返回。
 * @author Stan
 * @date: 2021年4月30日
 */

/*
 * 本题的难点在于两点：
 * 1. 插入，删除，获取随机元素这三个操作的时间复杂度必须都是 O(1)
 * 2. getRandom 方法返回的元素必须等概率返回随机元素，也就是说，如果集合里面有 n 个元素，每个元素被返回的概率必须是 1/n。
 * 
 * 分析：
 * 对于插入，删除，查找这几个操作，哪种数据结构的时间复杂度是 O(1)?
 * 	HashSet 肯定算一个。哈希集合的底层原理就是一个大数组，我们把元素通过哈希函数映射到一个索引上；
 * 	但是能否在 O(1) 的时间内实现 getRandom 函数?其实是不能的，因为根据刚才说到的底层实现，元素是被哈希函数「分散」到整个数组里面的，更别说还有拉链法等等解决哈希冲突的机制，基本做不到 O(1) 时间等概率随机获取元素。
 * 	除了 HashSet，还有一些类似的数据结构，比如哈希链表 LinkedHashSet，但只是给 HashSet 增加了有序性，底层用链表结构存储元素的话，是无法在 O(1) 的时间内访问某一个元素的。
 * 
 * 思路：
 * 	1. 对于 getRandom 方法，如果想【等概率】且【在 O(1) 的时间】取出元素，一定要满足：底层用数组实现，且数组必须是紧凑的
 * 	2. 用数组存储元素的话，插入，删除的时间复杂度如何做到 O(1)？：
 * 		对数组尾部进行插入和删除操作不会涉及数据搬移
 */
public class RandomizedSet {
    private List<Integer> nums;						// 存储元素的值
    private HashMap<Integer, Integer> val_idx;		// 记录每个元素对应在 nums 中的索引

    /** Initialize your data structure here. */
    public RandomizedSet() {
        nums = new ArrayList<>();
        val_idx = new HashMap<>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
    	
        if(val_idx.containsKey(val)){		// 若 val 已存在，不用再插入
            return false;
        }else{
        	nums.add(nums.size(), val);		// 若 val 不存在，插入到 nums 尾部， 并记录 val 对应的索引值
            val_idx.put(val, nums.size()-1);
            return true;
        }
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
    	
        if(val_idx.containsKey(val)){		
            int idx = val_idx.get(val);				// 先拿到 val 的索引 idx
            int last = nums.get(nums.size()-1);

            nums.set(idx, last);					// 将最后一个元素对应的索引修改为 idx
            val_idx.put(last, idx);
            
            nums.remove(nums.size()-1);				// 在数组中删除最后一个元素 
            val_idx.remove(val);					// 删除元素 val 对应的索引

            return true;

        }else{						// 若 val 不存在，不用再删除
            return false;
        }
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        return nums.get(new Random().nextInt(nums.size()));		// 随机获取 nums 中的一个元素
    }
}
