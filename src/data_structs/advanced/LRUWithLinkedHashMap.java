package data_structs.advanced;

import java.util.LinkedHashMap;

/**
 * @ClassName: LRUWithLinkedHashMap   
 * @Description: 用Java内置类型 LinkedHashMap 来实现LRU Cache    
 * @author Stan
 * @date: 2021年4月13日
 */
public class LRUWithLinkedHashMap {
	
	private int capacity;
    private LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

    public LRUWithLinkedHashMap(int capacity) {
        this.capacity = capacity;
    }
    
    public int get(int key) {
        if(!cache.containsKey(key)){
            return -1;
        }
        
        // 将 key 变为最近使用
        makeRecently(key);
        return cache.get(key);
    }
    
    public void put(int key, int value) {

        if(cache.containsKey(key)){
            // 修改旧的值
            cache.put(key, value);
            makeRecently(key);

            return;
        }
        if(cache.size() >= capacity){
            // 链表头部就是最久未使用的 key
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }

        // 将新的key添加到链表尾部
        cache.put(key, value);

        return;
    }

    /**
     * @Description: 将某个 key 提升为最近使用的  
     * @param key
     */
    private void makeRecently(int key){

        int val = cache.get(key);

        // 删除key，重新插入到队尾
        cache.remove(key);
        cache.put(key, val);
    }

}
