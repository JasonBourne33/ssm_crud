package com.atguigu.crud.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Configuration
@EnableTransactionManagement    // 开启Spring声明式事务
//@ComponentScan("com.tuling")
//@EnableRedisRepositories("com.tuling.repository")
public class RedisConfig {

    // 1. redis的连接工厂
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {

        // 设置单机redis远程服务地址
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration("47.112.147.20", 6379);
        standaloneConfiguration.setPassword("123");

        // 集群
        // new RedisClusterConfiguration(传入多个远程地址ip)

        // 连接池相关配置 (过期）
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数（根据并发量估算）
        jedisPoolConfig.setMaxTotal(100);
        // 最大连接数= 最大空闲数
        jedisPoolConfig.setMaxIdle(100);
        // 最小空闲连接数
        jedisPoolConfig.setMinIdle(10);
        // 最长等待时间 0无限等待 解决线程堆积问题 最好设置
        jedisPoolConfig.setMaxWaitMillis(2000);

        // 在SDR2.0+ 建议使用JedisClientConfiguration 来设置连接池
        // 默认设置了读取超时和连接超时为2秒
        JedisClientConfiguration clientConfiguration =
                JedisClientConfiguration.builder()
                        .usePooling()
                        .poolConfig(jedisPoolConfig).build();


        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(standaloneConfiguration, clientConfiguration);
        return jedisConnectionFactory;
    }

    // 2. redis模板类（工具类）
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // StringRedisSerializer 只能传入String类型的值（存入redis之前就要转换成String)
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 改成Jackson2JsonRedisSerializer   推荐
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        return redisTemplate;
    }

    // 2. redis模板类（工具类）
    @Bean
    public RedisTemplate tranRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // StringRedisSerializer 只能传入String类型的值（存入redis之前就要转换成String)
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 改成Jackson2JsonRedisSerializer   推荐
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));

        redisTemplate.setEnableTransactionSupport(true);  // 支持spring的声明式事务
        return redisTemplate;
    }

//    @Bean
//    public PlatformTransactionManager transactionManager() throws SQLException {
//        return new DataSourceTransactionManager(dataSource());
//    }
//
//
//    @Bean
//    public DataSource dataSource() throws SQLException {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        builder.setType(EmbeddedDatabaseType.H2);
//        return builder.build();
//    }
//
//    // 配置在订阅者
//    // 消息监听者容器  负责： 管理线程池、消息分发，分发给对应管道的监听者
//    // 可以动态设置订阅者、和管道， 通过这个特性在不重启服务器的情况下  动态设置订阅者、和管道
//    @Bean
//    RedisMessageListenerContainer container(MessageListenerAdapter messageListenerAdapter) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        // 设置连接工厂
//        container.setConnectionFactory(redisConnectionFactory());
//        // 设置监听者绑定的管道
//        List<Topic> topicList = new ArrayList<>();
//        topicList.add(new PatternTopic("log"));
//
//        // 第一个参数： 监听者     , 第二个参数  ：绑定管道
//        container.addMessageListener(messageListenerAdapter, topicList);
//        return container;
//    }
//
//
//    /**
//     * 消息侦听器适配器,能将消息委托给目标侦听器方法(多余）
//     *
//     * @return
//     */
//    @Bean
//    public MessageListenerAdapter listenerAdapter(MessageListener listener) {
//        return new MessageListenerAdapter(listener);
//    }
}
