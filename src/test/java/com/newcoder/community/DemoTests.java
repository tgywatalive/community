package com.newcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class) // 拿bean 与 @RunWith配合使用
public class DemoTests {

    @Test
    public void testMap() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();

        map1.put("post", "post1");
        map1.put("user", "user1");

        list.add(map1);

        Map<String, Object> map2 = new HashMap<>();

        map2.put("post", "post2");
        map2.put("user", "user2");
        list.add(map2);

        System.out.println(list);

        for (Map<String, Object> map : list) {
            for (String key : map.keySet()) {
                System.out.println("key = " + key);
                String val = (String) map.get(key);
                System.out.println("val = " + val);
            }

        }
    }

    @Test
    public void testDate() {
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date); // 当前时间
        Date date2 = new Date(System.currentTimeMillis() + 3600 * 24 * 100 * 1000l); // 100天后
        Date date3 = new Date(System.currentTimeMillis() + 3600 * 24 * 10 * 1000); // 10天后
        System.out.println(date2);
        System.out.println(date3);
    }
}
