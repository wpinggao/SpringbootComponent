# 分布式ID组件
1. 支持雪花ID(snowflake)

如何使用snowflake

1. 添加配置

wping:

  snowflake:
  
    enabled: true
    # workerId 工作ID (0~31)
    worker-id: 0
    # datacenterId 数据中心ID (0~31)
    datacenter-id: 0
    
2. 注解 @SnowFlakeService, 通过SnowFlakeService.nexitId()或者一个ID
