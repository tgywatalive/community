package com.newcoder.community;

import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.dao.MessageMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.Message;
import com.newcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) // 拿bean 与 @RunWith配合使用
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testStrings() {
        String rediskey = "test:count";

        redisTemplate.opsForValue().set(rediskey, 1);


        System.out.println(redisTemplate.opsForValue().get(rediskey));
        System.out.println(redisTemplate.opsForValue().increment(rediskey));
        System.out.println(redisTemplate.opsForValue().decrement(rediskey));
    }

    @Test
    public void testHashes() {
        String rediskey = "test:user";

        redisTemplate.opsForHash().put(rediskey, "id", 1);
        redisTemplate.opsForHash().put(rediskey, "username", "zhangsan");

        System.out.println(redisTemplate.opsForHash().get(rediskey, "id"));
        System.out.println(redisTemplate.opsForHash().get(rediskey, "username"));
    }

    @Test
    public void testLists() {
        String rediskey = "test:ids";

        redisTemplate.opsForList().leftPush(rediskey, 101);
        redisTemplate.opsForList().leftPush(rediskey, 102);
        redisTemplate.opsForList().leftPush(rediskey, 103);

        //stringRedisTemplate.opsForList().leftPush(rediskey, "104");

        System.out.println(redisTemplate.opsForList().size(rediskey));
        System.out.println(redisTemplate.opsForList().index(rediskey, 0));
        System.out.println(redisTemplate.opsForList().range(rediskey, 0, 2));

        System.out.println(redisTemplate.opsForList().leftPop(rediskey));
        System.out.println(redisTemplate.opsForList().leftPop(rediskey));
        System.out.println(redisTemplate.opsForList().leftPop(rediskey));
        System.out.println(redisTemplate.opsForList().leftPop(rediskey));
    }

    @Test
    public void testSets() {
        String redisKey = "test:teachers";

        redisTemplate.opsForSet().add(redisKey, "刘备", "关羽", "张飞", "赵云", "诸葛亮");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        // 随机弹出一个元素
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        // 查看所有元素
        System.out.println(redisTemplate.opsForSet().members(redisKey));
    }

    @Test
    public void testSortedSets() {
        String redisKey = "test:students";

        redisTemplate.opsForZSet().add(redisKey, "唐僧", 80);
        redisTemplate.opsForZSet().add(redisKey, "悟空", 90);
        redisTemplate.opsForZSet().add(redisKey, "八戒", 50);
        redisTemplate.opsForZSet().add(redisKey, "白龙马", 60);
        //stringRedisTemplate.opsForZSet().add(redisKey, "\"沙僧\"", 70);

        // 个数
        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "八戒"));
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey, "八戒"));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, 2));
    }

    @Test
    public void testKeys() {
        redisTemplate.delete("test:user");

        System.out.println(redisTemplate.hasKey("test:user"));

        redisTemplate.expire("test:students", 10, TimeUnit.SECONDS);
    }

    // 多次访问同一个key
    @Test
    public void testBoundOperations() {
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);

        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();

        System.out.println(operations.get());
    }

    // 编程式事务
    @Test
    public void testTransactional() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:tx";

                // 启用事务
                operations.multi();

                operations.opsForSet().add(redisKey, "zhangsan");
                operations.opsForSet().add(redisKey, "lisi");
                operations.opsForSet().add(redisKey, "wangwu");

                System.out.println(operations.opsForSet().members(redisKey));

                // 提交事务
                return operations.exec();
            }
        });
        System.out.println(obj);
    }
}
