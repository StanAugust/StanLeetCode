package data_structs.advanced;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @ClassName: Twitter   
 * @Description: 355. 设计一个简化版的推特(Twitter)，可以让用户实现发送推文，关注/取消关注其他用户，能够看见关注人（包括自己）的最近十条推文。
 * 					设计需要支持以下的几个功能：
 * 					1) postTweet(userId, tweetId): 创建一条新的推文
 * 					2) getNewsFeed(userId): 检索最近的十条推文。每个推文都必须是由此用户关注的人或者是用户自己发出的。推文必须按照时间顺序由最近的开始排序。
 * 					3) follow(followerId, followeeId): 关注一个用户
 * 					4) unfollow(followerId, followeeId): 取消关注一个用户
 * @author Stan
 * @date: 2021年4月28日
 */

/*
 * 这几个 API 中大部分都很好实现，最核心的功能难点应该是 getNewsFeed，因为返回的结果必须在时间上有序，但问题是用户的关注是动态变化的，怎么办？
 * 
 * 这里就涉及到算法了：如果把每个用户各自的推文存储在链表里，每个链表节点存储文章 id 和一个时间戳 time（记录发帖时间以便比较），
 * 而且这个链表是按 time 有序的，那么如果某个用户关注了 k 个用户，就可以用合并 k 个有序链表的算法合并出有序的推文列表，正确地 getNewsFeed 了！
 * 
 * 所以根据刚才的分析，需要一个 User 类，储存 user 信息，还需要一个 Tweet 类，储存推文信息，并且要作为链表的节点。
 */
public class Twitter {

    // 全局共享的一个时间戳
    private static int timestamp = 0;
    
	/*
	 * Tweet 和  User 都作为内部类:
	 * 	是因为 Tweet 类必须要用到一个全局时间戳 timestamp，而  User 类又需要用到 Tweet 类记录用户发送的推文
	 */    
    /**
     * @ClassName: Tweet   
     * @Description: 推文类   
     */
    private static class Tweet{
        private int id;
        private int time;
        private Tweet next;
        
        // 需要传入推文内容（id）和发文时间
        public Tweet(int id, int time){
            this.id = id;
            this.time = time;
            this.next = null;
        }

    }
    
    /**
     * @ClassName: User   
     * @Description: 用户类   
     */
    private static class User{

        private int id;
        public Set<Integer> followers;	// 关注列表
        public Tweet head;				// 用户发表的推文链表头结点

        public User(int id){
            this.id = id;

            followers = new HashSet<>();
            follow(id);		// 关注一下自己
            this.head = null;
        }

		/*
		 * 除此之外，根据面向对象的设计原则，「关注」「取关」和「发文」应该是 User 的行为，况且关注列表和推文列表也存储在 User 类中，
		 * 所以我们也应该给  User 添加 follow，unfollow 和 post 这几个方法
		 */
        
        /**
         * @Description: 关注某人  
         * @param id
         */
        public void follow(int id){
            followers.add(id);
        }

        /**
         * @Description: 取关某人  
         * @param id
         */
        public void unfollow(int id){
            if(id != this.id){		// 不可以取关自己
                followers.remove(id);
            }
        }

        /**
         * @Description: 发送推文  
         * @param tweetID
         */
        public void post(int tweetID){
            Tweet tweet = new Tweet(tweetID, timestamp);
            timestamp++;

            // 将新建的推文插入链表头
            // 越靠前的推文，timestamp越大
            tweet.next = head;
            head = tweet;
        }

    }
    
    // userID 和 User类的映射
    private HashMap<Integer, User> userMap = new HashMap<>();

    /** Initialize your data structure here. */
    public Twitter() {
    }
    
    /**
     * @Description: user编写一条新tweet  
     * @param userId
     * @param tweetId
     */
    public void postTweet(int userId, int tweetId) {
    	// 若 userId 不存在，则新建
        if(!userMap.containsKey(userId))
            userMap.put(userId, new User(userId));
        
        User user = userMap.get(userId);
        user.post(tweetId);
    }
    
    /**
     * @Description: follower 关注 followee  
     * @param followerId
     * @param followeeId
     */
    public void follow(int followerId, int followeeId) {
    	// 若 follower 不存在，则新建
        if(!userMap.containsKey(followerId))
            userMap.put(followerId, new User(followerId));
        // 若 followee 不存在，则新建
        if(!userMap.containsKey(followeeId))
            userMap.put(followeeId, new User(followeeId));

        userMap.get(followerId).follow(followeeId);
    }
    
    /**
     * @Description: follower 取关 followee，如果 Id 不存在则什么都不做  
     * @param followerId
     * @param followeeId
     */
    public void unfollow(int followerId, int followeeId) {
        if(userMap.containsKey(followerId)){
            userMap.get(followerId).unfollow(followeeId);
        }
    }
    
    /**
     * @Description: 返回该 user 关注的人（包括他自己）最近的动态 id， 最多 10 条，而且这些动态必须按从新到旧的时间线顺序排列。  
     * @param userId
     * @return
     */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new ArrayList<>();

        if(!userMap.containsKey(userId)) return res;

        // 关注列表的用户 Id
        Set<Integer> followers = userMap.get(userId).followers;
        
        // 实现合并 k 个有序链表的算法需要用到优先级队列，这种数据结构是「二叉堆」最重要的应用
        
        // 自动通过 time 属性从大到小排序，容量为 users 的大小。因为 time 越大意味着时间越近，应该排在前面
        PriorityQueue<Tweet> pq = new PriorityQueue<>(followers.size(), (a,b)->(b.time-a.time));

        // 先将所有链表头结点插入优先级队列
        for(int id : followers){
            Tweet t = userMap.get(id).head;
            
            if(t != null)   pq.add(t);
        }

        while(!pq.isEmpty()){
            // 最多返回10条
            if(res.size() == 10)    break;

            // 弹出最近发表的（time最大的）
            Tweet tweet = pq.poll();
            res.add(tweet.id);

            // 将下一篇tweet插入队列进行排序
            if(tweet.next != null){
                pq.add(tweet.next);
            }
        }

        return res;
    }
    
}
