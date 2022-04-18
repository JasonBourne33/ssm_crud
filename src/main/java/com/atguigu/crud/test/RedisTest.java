package com.atguigu.crud.test;

//import com.tuling.config.RedisConfig;
//import org.junit.jupiter.api.Test;
import com.atguigu.crud.config.RedisConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@SpringJUnitConfig(classes = RedisConfig.class)
public class RedisTest {

    // 它是线程安全的
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void testKeyTypeOperations(){
        // String类型
        ValueOperations operations = redisTemplate.opsForValue();
        operations.set("name","徐庶");

        System.out.println(operations.get("name"));
    }

//    @Test
//    public void testKeyBoundOperations(){
//        // String类型
//        System.out.println(redisTemplate.boundValueOps("name").get());
//    }
}
