package data_structs.advanced;

import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * @ClassName: LFUWithLinkedHashSet   
 * @Description: 请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。
 * 				   实现 LFUCache 类：
 * 					1. LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
 * 					2. int get(int key) - 如果键存在于缓存中，则获取键的值，否则返回 -1
 * 					3. void put(int key, int value) - 如果键已存在，则变更其值；如果键不存在，请插入键值对。当缓存达到其容量时，则应该在插入新项之前，使最不经常使用的项无效。在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最近最久未使用 的键。
 * @author Stan
 * @date: 2021年4月13日
 */
public class LFUWithLinkedHashSet {
	/*
	 * 【算法执行过程中的几个显而易见的事实】：
	 * 
	 * 1.调用 get(key) 方法时，要返回该 key 对应的 val。
	 * 2.只要用 get 或者 put 方法访问一次某个 key，该 key 的 freq 就要 +1。 
	 * 3.如果在容量满了的时候进行插入，则需要将 freq 最小的 key 删除，如果最小的 freq 对应多个 key，则删除其中最旧的那一个。
	 * 
	 * 【如果希望能够在 O(1) 的时间内解决这些需求，可以使用基本数据结构来逐个击破】：
	 * 
	 * 1.使用一个 HashMap 存储 key 到 val 的映射，就可以快速计算 get(key)
	 * 2.使用一个 HashMap 存储 key 到 freq 的映射，就可以快速操作 key 对应的 freq
	 * 3.这个需求应该是 LFU 算法的核心:
	 * 	 3.1   首先，肯定是需要 freq 到 key 的映射，用来找到 freq 最小的 key;
	 *   3.2   将 freq 最小的 key 删除，那就得快速得到当前所有 key 最小的 freq 是多少,
	 *   		想要时间复杂度 O(1)的话，肯定不能遍历一遍去找，那就用一个变量 minFreq 来记录当前最小的 freq 吧;
	 *   3.3   可能有多个 key 拥有相同的 freq，所以 freq 对 key 是一对多的关系，即一个 freq 对应一个 key 的列表;
	 *   3.4   希望 freq 对应的 key 的列表是存在时序的，便于快速查找并删除最旧的 key;
	 *   3.5  希望能够快速删除 key列表中的任何一个 key，因为如果频次为 freq 的某个 key 被访问，那么它的频次就会变成freq+1，
	 *   		就应该从 freq 对应的 key 列表中删除，加到 freq+1 对应的 key 的列表中；
	 *   
	 * 【结论】：
	 * 链表不能快速访问链表节点，但是插入元素具有时序；哈希集合中的元素无序，但是可以对元素进行快速的访问和删除；
	 * 它俩结合起来就兼具了哈希集合和链表的特性，既可以在 O(1) 时间内访问或删除其中的元素，又可以保持插入的时序，这就是LinkedHashSet
	 */
	
	HashMap<Integer, Integer> keyToVal = new HashMap<>();           // KV表,构造key到val的映射
    HashMap<Integer, Integer> keyToFreq = new HashMap<>();          // KF表,构造key到freq的映射
    HashMap<Integer, LinkedHashSet> freqTokey = new HashMap<>();    // FK表,构造freq到keyde映射

    int minFreq;    // 记录最小频率
    int capacity;   // 记录缓存最大容量

    public LFUWithLinkedHashSet(int capacity) {
        this.capacity = capacity;
        this.minFreq = 0;
    }
    
    public int get(int key) {
        if(!keyToVal.containsKey(key)){           
            return -1;
        }

        increaseFreq(key);      // 增加key的频率
        return keyToVal.get(key);
    }
    
    public void put(int key, int value) {

        if(capacity <= 0)   return;

        if(keyToVal.containsKey(key)){      // 若key已存在.直接修改对应的val
            keyToVal.put(key, value);
            increaseFreq(key);      // key对应的freq加一
            return;

        }

        /* key不存在,需要插入  */
        if(keyToVal.size() >= capacity){    // 若容量已满,淘汰一个freq最小的key
            removeMinFreqKey();
        }

        keyToVal.put(key, value);           // 向缓存中插入新的值
        keyToFreq.put(key, 1);
        freqTokey.putIfAbsent(1, new LinkedHashSet<>());
        freqTokey.get(1).add(key);

        // 插入新 key 后最小的 freq 肯定是 1
        minFreq = 1;
    }

    private void increaseFreq(int key){
        int freq = keyToFreq.get(key);

        freqTokey.get(freq).remove(key);
        if(freqTokey.get(freq).isEmpty()){
            freqTokey.remove(freq);
            // 如果这个 freq 恰好是 minFreq，更新 minFreq
            if(freq == minFreq){
                minFreq++;
            }
        }

        freq += 1;
        keyToFreq.put(key,freq);

        freqTokey.putIfAbsent(freq, new LinkedHashSet<>());
        freqTokey.get(freq).add(key);
    }

    private void removeMinFreqKey(){
        // minFreq 的 LinkedHashSet
        LinkedHashSet<Integer> keySet = freqTokey.get(minFreq);

        // 其中最先被插入的那个key就是该被淘汰的key
        int deleteKey = keySet.iterator().next();

        // 更新FK表
        keySet.remove(deleteKey);
        if(keySet.isEmpty()){
            freqTokey.remove(minFreq);
            // 这里不需要更新minFreq值,因为调用这个函数之后一定会插入新值,minFreq就会被设置为1
        }

        // 更新KV、KF表
        keyToVal.remove(deleteKey);
        keyToFreq.remove(deleteKey);
    }
}
