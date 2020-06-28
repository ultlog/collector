# collector
### 引入

````xml
<dependency>
    <groupId>com.ultlog</groupId>
    <artifactId>collector</artifactId>
    <version>0.0.1</version>
</dependency>
````
### 集成
在logback.xml/logback-test.xml等生效的logback配置文件中添加如下配置
````xml

<appender name="ultlog" class="com.ultlog.collector.appender.AsyncEsAppender">
    <param name="threadPoolProviderName" value="com.ultlog.collector.support.DefaultThreadPoolProvider"/>
    <param name="url" value="{ula-ip}:{ula-port}/api/v1/log"/>
    <param name="project" value="{project}"/>
    <param name="module" value="{module}"/>
    <param name="uuid" value="{uuid}"/>
</appender>

<root level="ERROR">
    <appender-ref ref="ultlog"/>
</root>
````
变量含义

|  |  |  
| ------ | ------ | 
| ula-ip | ula服务的ip |  
| ula-port | ula服务的ip端口 |  
| project | 项目名称 | 
| module | 模块名称（如果非微服务项目可以与project相等） | 
| uuid | 服务唯一属性 |

实例
````xml
<appender name="ultlog" class="com.ultlog.collector.appender.AsyncEsAppender">
    <param name="threadPoolProviderName" value="com.ultlog.collector.support.DefaultThreadPoolProvider"/>
    <param name="url" value="http://localhost:8080/api/v1/log"/>
    <param name="project" value="ula-test"/>
    <param name="module" value="ula-test"/>
    <param name="uuid" value="ula-test-uuid"/>
</appender>

```` 
### ula
ula服务部署说明点击[此处](https://github.com/ultlog/ula/blob/master/README.md)
