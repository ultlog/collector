# collector
## 前言 

collector是基于logback开发的日志收集组件，适用于使用logback日志框架的系统。collector通过对logback框架中的appender接口扩展而将收集到的日志发送到[ula](#ula)系统。相比于searcher，
collector能够适应更改多变的日志格式，并且在部署时不需要编写额外的脚本。

## 集成

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
    <param name="url" value="{ula}/api/v1/log"/>
    <param name="project" value="{project}"/>
    <param name="module" value="{module}"/>
    <param name="uuid" value="{uuid}"/>
</appender>

<root level="ERROR">
    <appender-ref ref="ultlog"/>
</root>
````
配置项说明

|  配置项|   说明 |  
| ------ | ------ | 
| ula | ula服务的地址 |  
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
