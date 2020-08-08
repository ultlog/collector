<h1 align="center">Collector</h1>
<p align="center">
  <a target="_blank" href="https://github.com/ultlog/collector/blob/master/LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue"></a>
  <a target="_blank" href="https://github.com/ultlog/collector/pulls"><img src=https://img.shields.io/badge/pr-welcome-green"></a>
  <a target="_blank" href="https://github.com/ultlog/collector/releases/"><img src="https://img.shields.io/github/v/release/ultlog/collector"></a>
  <a target="_blank" href="https://github.com/ultlog/collector/pulls?q=is%3Apr+is%3Aclosed"><img src="https://img.shields.io/github/issues-pr-closed/ultlog/collector"></a>
</p>
<p align="center">
Collector is a log collection component developed based on logback, which is suitable for systems using logback log framework. The collector sends the collected logs to the <a href="https://github.com/ultlog/ula/">ula</a> system by extending the appender interface in the logback framework. Compared to <a href="https://github.com/ultlog/searcher">searcher</a>,The collector can adapt to changing log formats, and there is no need to write additional scripts during deployment.
</p>
   
<p align="center">
  <a href="https://ultlog.com" target="_blank">
    文档
  </a>
  / 
  <a href="https://github.com/ultlog/ula/" target="_blank">
    ultlog-api
  </a>
  / 
  <a href="https://github.com/ultlog/ulu/" target="_blank">
    ultlog-ui
  </a>
  / 
  <a href="https://github.com/ultlog/searcher" target="_blank">
    searcher
  </a>
</p>

## Integrated

### Depend

````xml
<dependency>
    <groupId>com.ultlog</groupId>
    <artifactId>collector</artifactId>
    <version>1.0.0</version>
</dependency>
````
### Integrated
Add the following configuration to valid logback configuration files such as logback.xml / logback-test.xml:
````xml

<appender name="ultlog" class="com.ultlog.collector.appender.UlaAppender">
    <param name="url" value="{ula}"/>
    <param name="project" value="{project}"/>
    <param name="module" value="{module}"/>
    <param name="uuid" value="{uuid}"/>
</appender>

<root level="ERROR">
    <appender-ref ref="ultlog"/>
</root>
````
#### Async
If you want to use asynchronous appender, you can use the logback document about [appender](http://www.logback.cn/04%E7%AC%AC%E5%9B%9B%E7%AB%A0Appenders.html).

#### Config

|  Parameter|   Description |  
| ------ | ------ | 
| ula | Address of ula service |
| project | Project name |
| module | Module name (if non-microservice project can be equal to project) |
| uuid | Service unique attribute |

### Demo
````xml
<appender name="ultlog" class="com.ultlog.collector.appender.AsyncEsAppender">
    <param name="url" value="http://localhost:8080"/>
    <param name="project" value="ula-test"/>
    <param name="module" value="ula-test"/>
    <param name="uuid" value="ula-test-uuid"/>
</appender>
