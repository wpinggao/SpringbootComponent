# Redis组件

特性
1. 使用redisson实现分布式锁
2. 集成redis + spring cache


1. 添加配置

spring:

  redis:
  
    lettuce:
      pool:
        max-active: 8
        max-wait: -1ms
        max-idle: 8
        min-idle: 0
    timeout: 10000ms
    database: 0
    sentinel:
      master: mastername
      nodes: node1:8001,node2:8001
    password: abc

wping:
  
  redis:
  
    # redis key 前缀，每个系统模块锁前缀不同，防止key键冲突
    prefix: "samples-redis:"
    
    redis-cache:
      enabled: true
      # cache 缓存默认时间
      default-cache-time: 30s
      cache-names:
        - name: common
          cache-time: 45s
        - name: base
          cache-time: 60s
    
2. 分布式锁注解

在public 方法(可以被spring aop代理的方法)上添加注解@RedisDistributeLock, 指定lockKey等属性，对于需要使用根据入参做动态key
的需求，请使用注解@RedisDistributeLockSuffix，

#特别注解，调用被@RedisDistributeLock注解的方法时，请catch异常，当获取锁失败时会抛出RedisDistributeLockException

@RedisDistributeLock(lockKey = "lockKey", waitTime = 10, leaseTime = 15)

3. 集成redis + spring cache

@Cacheable(cacheNames = "common", keyGenerator = "simpleKeyGenerator")

@Cacheable(cacheNames = "common", key = "#name") 

    
