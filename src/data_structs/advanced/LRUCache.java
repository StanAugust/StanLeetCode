package data_structs.advanced;

import java.util.HashMap;

/**
 * @ClassName: LRUCache   
 * @Description: 运用你所掌握的数据结构，设计和实现一个 LRU (最近最少使用) 缓存机制 。
 * 				   实现 LRUCache 类：
 * 					1. LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存。
 * 					2. int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 * 					3. void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 * @author Stan
 * @date: 2021年4月27日
 */
public class LRUCache {
	/*
	 * LRUCache 这个数据结构必要的条件： 
	 * 	1. 显然 cache 中的元素必须有时序，以区分最近使用的和久未使用的数据，当容量满了之后要删除最久未使用的那个元素腾位置
	 * 	2. 我们要在 cache 中快速找某个 key 是否已存在并得到对应的 val；
	 * 	3. 每次访问 cache 中的某个 key，需要将这个元素变为最近使用的，也就是说 cache 要支持在任意位置快速插入和删除元素。
	 * 
	 * 那么，什么数据结构同时符合上述条件呢?
	 * 		哈希表查找快，但是数据无固定顺序；
	 * 		链表有顺序之分，插入删除快，但是查找慢。
	 * 所以结合一下，形成一种新的数据结构： 哈希链表 LinkedHashMap
	 * 
	 * 这里用自定义的类实现，用Java内置类型实现详见{@link LRUWithLinkedHashMap}
	 */
	
	private HashMap<Integer, Node> map; 
    private DoubleList cache;   // 用双向链表来实现缓存，新插入/刚使用的值在链表尾部，最久未使用的值在链表头部

    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        cache = new DoubleList();
    }
    
    
    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }else{
        	// 将该数据提升为最近使用的
            makeRecently(key);
            return map.get(key).val;
        }
    }
    
    public void put(int key, int value) {
        /*
         * 这两个判断的顺序很重要
         */
        if(map.containsKey(key)){
            // 删除旧的数据
            deleteKey(key);
        }
        if(cache.length() >= capacity){
            // 删除最久未使用的元素
            removeLeastRecently();
        }

        // 新插入的数据为最近使用的数据
        addRecently(key, value);

        return;
    }

    /**
     * @Description: 将某个 key 提升为最近使用的  
     * @param key
     */
    private void makeRecently(int key){

        Node x = map.get(key);

        cache.remove(x);
        cache.addLast(x);
    }

    /**
     * @Description: 添加最近使用的元素  
     * @param key
     * @param val
     */
    private void addRecently(int key, int val){
    	// 每次默认从链表尾部添加元素，那么显然越靠尾部的元素就是最近使用的，越靠头部的元素就是最久未使用的
        Node x = new Node(key, val);

        cache.addLast(x);
        map.put(key, x);
    }

    /**
     * @Description: 删除某一个 key  
     * @param key
     */
    private void deleteKey(int key){
        Node x = map.get(key);

        cache.remove(x);
        map.remove(key);
    }

    /**
     * @Description: 删除最久未使用的元素
     */
    private void removeLeastRecently(){
        Node lr = cache.removeFirst();
        map.remove(lr.key);
    }
}

/************************** 双向链表来实现缓存  **************************/
class DoubleList{
    // 头尾虚节点
    private Node head,tail;
    // 链表元素数
    private int len;

    public DoubleList(){
        // 初始化双向链表的数据
        head = new Node(0,0);
        tail = new Node(0,0);

        head.next = tail;
        tail.prev = head;

        len = 0;
    }

    /**
     * @Description: 在链表尾部添加节点 x，时间 O(1)  
     * @param x
     */
    public void addLast(Node x){
        x.prev = tail.prev;
        x.next = tail;

        tail.prev.next = x;
        tail.prev = x;

        len++;
    }

    /**
     * @Description: 删除链表中的 x节点(x 一定存在),时间 O(1)
     * @param x
     */
    public void remove(Node x){
    	// 双向链表保证删除操作也是O(1)
        x.prev.next = x.next;
        x.next.prev = x.prev;

        len--;
    }

    /**
     * @Description: 删除链表中第一个节点，并返回该节点，时间 O(1)  
     * @return
     */
    public Node removeFirst(){
        if(head.next == tail)   return null;

        Node first = head.next;
        remove(first);

        return first;
    }
 
    /**
     * @Description: 返回链表长度，时间 O(1)  
     * @return
     */
    public int length(){
        return len;
    }
}

class Node{
    public int key,val;
    public Node prev,next;

    public Node(int key, int val){
        this.key = key;
        this.val = val;
    }
}
