# http请求client组件

特性
1. 使用httpclient
2. 支持代理访问

如何使用

1. 添加配置

http:

  client:
  
    #最大连接数
    maxTotal: 100
    #并发数
    defaultMaxPerRoute: 20
    #创建连接的最长时间
    connectTimeout: 5000
    #从连接池中获取到连接的最长时间
    connectionRequestTimeout: 5000
    #数据传输的最长时间
    socketTimeout: 30000
    proxyHost: proxy host domain or ip
    
2. 获取HttpClient对象，在service方法注入HttpClient
    
    @Autowired
    private WpingHttpClient wpingHttpClient;

    
