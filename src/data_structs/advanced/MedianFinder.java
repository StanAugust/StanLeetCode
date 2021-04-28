package data_structs.advanced;

import java.util.PriorityQueue;

/**
 * @ClassName: MedianFinder   
 * @Description: 295. 数据流的中位数 ，注意是数据流
 * 					设计一个支持以下两种操作的数据结构：
 * 					1) void addNum(int num) - 从数据流中添加一个整数到数据结构中
 * 					2) double findMedian() - 返回目前所有元素的中位数
 * @author Stan
 * @date: 2021年4月28日
 */
public class MedianFinder {
	/*
	 * 在数据流中，数据会不断涌入结构中，那么也就面临着需要多次动态调整以获得中位数。 因此实现的数据结构需要既需要快速找到中位数，也需要做到快速调整。
	 * 
	 * 首先能想到就是AVL，在平衡状态下，树顶必定是中间数，然后再根据长度的奇偶性决定是否取两个数， 此方法效率高，但是手动编写较费时费力。
	 * 
	 * 除了AVL，还有没有什么常用的数据结构是动态有序的？优先级队列（二叉堆）行不行？
	 * 好像也不太行，因为优先级队列是一种受限的数据结构，只能从堆顶添加/删除元素，addNum 方法可以从堆顶插入元素，但是 findMedian 函数需要从数据中间取，这个功能优先级队列是没办法提供的。
	 * 
	 * 我们必然需要有序数据结构，本题的核心思路是使用两个优先级队列：
	 * 	     根据只需获得中间数的想法，可以将数据分为左右两边，一边以最大堆的形式实现，可以快速获得左侧最大数;
	 * 	     另一边则以最小堆的形式实现。其中需要注意的一点就是左右侧数据的长度差不能超过1。
	 * 	     这种实现方式的效率与AVL平衡二叉搜索树的效率相近，但编写更快
	 */
    private PriorityQueue<Integer> large;
    private PriorityQueue<Integer> small;

    /** initialize your data structure here. */
    public MedianFinder() {
        // 小顶堆-> 存放流中较大的值
        large = new PriorityQueue<>();
        // 大顶堆-> 存放流中较小的值
        small = new PriorityQueue<>((a, b)->{
            return b-a;
        });

    }
    
    public void addNum(int num) {
        // 这个代码不仅维护了两个堆的size差不超过1，还维护了large的堆顶元素大于等于small的堆顶元素
        // 太骚了！
        if(small.size() >= large.size()){
            small.offer(num);
            large.offer(small.poll());
        }else{
            large.offer(num);
            small.offer(large.poll());
        }

    }
    
    public double findMedian() {
        // 如果元素不一样多，多的那个堆顶元素就是中位数
        if(small.size() < large.size()){
            return large.peek();

        }else if(small.size() > large.size()){
            return small.peek();

        }

        // 否则，两个堆顶元素的平均数就是中位数
        return (large.peek() + small.peek()) / 2.0;
    }
}