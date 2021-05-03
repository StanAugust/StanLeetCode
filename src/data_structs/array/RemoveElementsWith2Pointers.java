package data_structs.array;

/**
 * @ClassName: RemoveElementsWith2Pointers   
 * @Description: 利用双指针去除重复元素    
 * @author Stan
 * @date: 2021年5月3日
 */
public class RemoveElementsWith2Pointers {
	/* 前面 O(1)时间删除/查找数组中的任意元素 就讲了一种技巧， 把待删除元素交换到最后一个，然后再删除，就可以避免数据搬移。 
	 * 还可以利用双指针的技巧原地修改数组
	 */
	
	/**
	 * @Description: 26. 删除有序数组中的重复项  
	 * 					给定一个有序数组 nums ，原地 删除重复出现的元素，返回删除后数组的新长度
	 * 
	 * @param nums	 eg：nums = [1,1,2]
	 * @return			2, nums = [1,2](函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。)
	 */
	public int removeDuplicates(int[] nums) {
        if(nums.length == 0)    return 0;

        int slow=0, fast=0;
        
        // fast找到一个不重复的元素就告诉 slow 并让 slow 前进一步。这样当 fast 指针遍历完整个数组 nums 后，nums[0..slow] 就是不重复元素。
        while(fast < nums.length){
            if(nums[fast] != nums[slow]){
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }
        
        return slow+1;
        
		/*
		 * 简单扩展：如果给定的是个有序链表，如何去重呢? 这是力扣第 83 题，其实和数组去重是一模一样的，唯一的区别是把数组赋值操作变成操作指针而已：
		 */    
    }
	
	/**
	 * @Description: 27. 移除元素  
	 * 					给定一个数组 nums 和一个值 val，原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度
	 * 
	 * @param nums	 eg: nums = [3,2,2,3], val = 3
	 * @param val
	 * @return			 2, nums = [2,2]
	 */
	public int removeElement(int[] nums, int val) {

        int slow=0, fast=0;
        
        // 如果 fast 遇到需要去除的元素，则直接跳过，否则就告诉 slow 指针，并让 slow 前进一步
        while(fast < nums.length){
            if(nums[fast] != val){
            	// 这里是先给 nums[slow] 赋值然后再给 slow++，这样可以保证 nums[0..slow-1] 是不包含值为 val 的元素的，最后的结果数组长度就是 slow
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        
        return slow;
    }
	
	/**
	 * @Description: 283. 移动零  
	 * 					 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序
	 * 
	 * @param nums	 eg:[0,1,0,3,12] --> [1,3,12,0,0]
	 */
	public void moveZeroes(int[] nums) {
        int slow=0, fast=0;
        
        // 其实就相当于移除 nums 中的所有 0，然后再把后面的元素都赋值为 0 即可
        while(fast < nums.length){
            if(nums[fast] != 0){
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }

        while(slow < nums.length){
            nums[slow++] = 0;
        }
    }
}
